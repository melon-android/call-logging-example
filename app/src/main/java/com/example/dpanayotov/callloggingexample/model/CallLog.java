package com.example.dpanayotov.callloggingexample.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by dpanayotov on 9/10/2016
 */
public class CallLog {

    private String phoneNumber;
    private Date callDate;
    private String callDuration;
    private CallDirection callDirection;
    //HashMap instead of Map since HashMap implements Serialziable
    private HashMap<String, String> rawValues;

    public CallLog(String phoneNumber, String callType, Date callDate, String callDuration,
                   CallDirection callDirection, HashMap<String, String> rawValues) {
        this.phoneNumber = phoneNumber;
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.callDirection = callDirection;
        this.rawValues = rawValues;
    }

    public CallLog() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getCallDate() {
        return callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public CallDirection getCallDirection() {
        return callDirection;
    }

    public HashMap<String, String> getRawValues() {
        return rawValues;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public void setCallDirection(CallDirection callDirection) {
        this.callDirection = callDirection;
    }

    public void setRawValues(HashMap<String, String> rawValues) {
        this.rawValues = rawValues;
    }
}
