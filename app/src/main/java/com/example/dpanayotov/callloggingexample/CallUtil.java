package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Date;

/**
 * Created by dpanayotov on 9/10/2016
 */
public class CallUtil {
    public static StringBuffer getCallDetails(Context context) {

        Log.d("zxc", "1");

        StringBuffer sb = new StringBuffer();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            Log.d("zxc", "2");
            Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
            int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
            int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
            sb.append( "Call Details :");
            while ( managedCursor.moveToNext() ) {
                String phNumber = managedCursor.getString( number );
                String callType = managedCursor.getString( type );
                String callDate = managedCursor.getString( date );
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString( duration );
                String dir = null;
                int dircode = Integer.parseInt( callType );
                switch( dircode ) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
                sb.append("\n----------------------------------");
            }
            managedCursor.close();
            return sb;
        }
        return null;
    }
}
