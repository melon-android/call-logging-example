package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.telecom.Call;
import android.util.Log;

import com.example.dpanayotov.callloggingexample.model.CallDirection;
import com.example.dpanayotov.callloggingexample.model.Contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        contact.addPhoneNumber(pCur.getString(pCur.getColumnIndex(ContactsContract
                                .CommonDataKinds.Phone.NUMBER)));
                    }
                    pCur.close();
                }
                contacts.add(contact);
            }
        }
        return contacts;
    }
}