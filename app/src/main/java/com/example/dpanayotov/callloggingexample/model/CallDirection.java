package com.example.dpanayotov.callloggingexample.model;

import android.provider.*;
import android.provider.CallLog;

import com.example.dpanayotov.callloggingexample.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpanayotov on 9/10/2016
 */
public enum CallDirection {

    UNKNOWN(0, R.drawable.call_unknown, "Unknown"),
    INCOMING_TYPE(CallLog.Calls.INCOMING_TYPE, R.drawable.call_incoming, "Incoming"),
    OUTGOING_TYPE(CallLog.Calls.OUTGOING_TYPE, R.drawable.call_outgoing, "Outgoing"),
    MISSED_TYPE(CallLog.Calls.MISSED_TYPE, R.drawable.call_missed, "Missed"),
    VOICEMAIL_TYPE(CallLog.Calls.VOICEMAIL_TYPE, R.drawable.call_voicemail, "Voicemail"),
    REJECTED_TYPE(CallLog.Calls.REJECTED_TYPE, R.drawable.call_rejected, "Rejected"),
    BLOCKED_TYPE(CallLog.Calls.BLOCKED_TYPE, R.drawable.call_blocked, "Blocked");

    private int value;

    private int resId;

    private String friendlyString;

    private CallDirection(int value, int resId, String friendlyString) {
        this.value = value;
        this.resId = resId;
        this.friendlyString = friendlyString;
    }

    public int getValue() {
        return value;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public String toString() {
        return friendlyString;
    }

}