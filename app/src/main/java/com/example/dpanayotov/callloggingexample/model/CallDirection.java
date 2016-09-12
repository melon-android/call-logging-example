package com.example.dpanayotov.callloggingexample.model;

import android.provider.*;
import android.provider.CallLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpanayotov on 9/10/2016
 */
public enum CallDirection {

    UNKNOWN(0, "Unknown"),
    INCOMING_TYPE(CallLog.Calls.INCOMING_TYPE, "Incoming"),
    OUTGOING_TYPE(CallLog.Calls.OUTGOING_TYPE, "Outgoing"),
    MISSED_TYPE(CallLog.Calls.MISSED_TYPE, "Missed"),
    VOICEMAIL_TYPE(CallLog.Calls.VOICEMAIL_TYPE, "Voicemail"),
    REJECTED_TYPE(CallLog.Calls.REJECTED_TYPE, "Rejected"),
    BLOCKED_TYPE(CallLog.Calls.BLOCKED_TYPE, "Blocked");

    private String friendlyString;

    private int value;

    private CallDirection(int value, String friendlyString) {
        this.friendlyString = friendlyString;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return friendlyString;
    }

}