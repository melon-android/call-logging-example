package com.example.dpanayotov.callloggingexample.model;

/**
 * Created by dpanayotov on 9/12/2016
 */
public class CallRecordColumn {
    private int id;
    private String key;
    private String value;

    public CallRecordColumn(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
