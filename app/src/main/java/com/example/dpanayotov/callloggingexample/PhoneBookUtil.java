package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.dpanayotov.callloggingexample.model.CallDirection;
import com.example.dpanayotov.callloggingexample.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dpanayotov on 9/10/2016
 */
public class PhoneBookUtil {

    public static List<com.example.dpanayotov.callloggingexample.model.CallLog> getCallDetails
            (Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission
                (context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            List<com.example.dpanayotov.callloggingexample.model.CallLog> callLogs = null;
            Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    null, null, null, null);
            if (managedCursor != null) {
                callLogs = parseCallLogs(managedCursor);
                managedCursor.close();
            }
            return callLogs;
        }
        return null;
    }

    private static List<com.example.dpanayotov.callloggingexample.model.CallLog> parseCallLogs
            (Cursor cursor) {

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        com.example.dpanayotov.callloggingexample.model.CallLog callLog;

        List<com.example.dpanayotov.callloggingexample.model.CallLog> callLogs = new ArrayList<>();

        while (cursor.moveToNext()) {

            callLog = new com.example.dpanayotov.callloggingexample.model.CallLog();

            callLog.setPhoneNumber(cursor.getString(number));
            callLog.setCallDate(new Date(Long.valueOf(cursor.getString(date))));
            callLog.setCallDuration(cursor.getString(duration));
            callLog.setCallDirection(getCallDirection(cursor.getInt(type)));
            callLog.setRawValues(parseRawValues(cursor));

            callLogs.add(0, callLog);
        }

        return callLogs;
    }

    private static CallDirection getCallDirection(int value) {
        for (CallDirection callDirection : CallDirection.values()) {
            if (callDirection.getValue() == value) {
                return callDirection;
            }
        }
        return null;
    }

    private static HashMap<String, String> parseRawValues(Cursor cursor) {
        HashMap<String, String> values = new HashMap<>();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            values.put(cursor.getColumnName(i), cursor.getString(i));
        }
        return values;
    }

    public static List<Contact> getContacts(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        List<Contact> contacts = null;
        if (cur.getCount() > 0) {
            contacts = new ArrayList<>();
            Contact contact;
            while (cur.moveToNext()) {
                contact = new Contact();
                contact.setId(cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID)));
                contact.setDisplayName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts
                        .DISPLAY_NAME)));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) >
                        0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new
                                    String[]{Integer.toString(contact.getId())}, null);
                    while (pCur.moveToNext()) {
                        contact.addPhoneNumber(pCur.getString(pCur.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }
                    pCur.close();
                }
                contact.setLookupKey(cur.getString(cur.getColumnIndex(ContactsContract.Contacts
                        .LOOKUP_KEY)));
                contact.setRawData(parseRawValues(cur));
                contacts.add(contact);
            }
        }
        return contacts;
    }

    public static String addContact(String name, String number, Context context) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue
                        (ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue
                        (ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds
                                .StructuredName.CONTENT_ITEM_TYPE).withValue(ContactsContract
                        .CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());

        ops.add(ContentProviderOperation.
                newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference
                (ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data
                .MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue
                (ContactsContract.CommonDataKinds.Phone.NUMBER, number).withValue
                (ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds
                        .Phone.TYPE_MOBILE).build());

        // add the photo
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo. CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, toByteArray(getDefaultPhoto(context)))
                        .build());

        // Asking the Contact provider to create a new contact
        try {
            return context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops)
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private static Bitmap getDefaultPhoto(Context context){
        return getBitmapFromAsset(context, "control.jpg");
    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }

    public static byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    public static void removeContact(String lookupKey, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        context.getContentResolver().delete(uri, null, null);
    }

    public static int getMaxContactPhotoSize(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // Note that this URI is safe to call on the UI thread.
            final Uri uri = ContactsContract.DisplayPhoto.CONTENT_MAX_DIMENSIONS_URI;
            final String[] projection = new String[] { ContactsContract.DisplayPhoto.DISPLAY_MAX_DIM };
            final Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
            try {
                c.moveToFirst();
                return c.getInt(0);
            } finally {
                c.close();
            }
        }
        // fallback: 96x96 is the max contact photo size for pre-ICS versions
        return 96;
    }

}
