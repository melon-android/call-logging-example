package com.example.dpanayotov.callloggingexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dpanayotov.callloggingexample.model.CallDirection;
import com.example.dpanayotov.callloggingexample.model.CallLog;

import java.util.List;

/**
 * Created by dpanayotov on 9/10/2016.
 */
public class CallLogAdapter extends ArrayAdapter<CallLog>{

    public CallLogAdapter(Context context, List<CallLog> callLogs) {
        super(context, R.layout.call_item, callLogs);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("zxc", "getView "+position);

        CallLog callLog = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.call_item, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.call_number)).setText(callLog.getPhoneNumber());
        ((TextView)convertView.findViewById(R.id.call_date)).setText(callLog.getCallDate().toString());
        switch (callLog.getCallDirection()){
            case INCOMING_TYPE:
            case OUTGOING_TYPE:
            case VOICEMAIL_TYPE: {
                ((TextView)convertView.findViewById(R.id.call_duration)).setText(callLog.getCallDuration());
                break;
            }
        }
       //((TextView)convertView.findViewById(R.id.call_direction)).setText(callLog.getCallDirection().toString());

        return convertView;
    }
}
