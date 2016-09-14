package com.example.dpanayotov.callloggingexample.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dpanayotov on 9/14/2016
 */
public class Contact {
    private int id;
    private String displayName;
    private List<String> phoneNumbers;
    private HashMap<String, String> rawData;

    public Contact(int id, String displayName, List<String> phoneNumbers, HashMap<String, String> rawData) {
        this.id = id;
        this.displayName = displayName;
        this.phoneNumbers = phoneNumbers;
        this.rawData = rawData;
    }

    public Contact(){
        this.phoneNumbers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public HashMap<String, String> getRawData() {
        return rawData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(String string){
        this.phoneNumbers.add(string);
    }

    public void addPhoneNumbers(List<String> phoneNumbers){
        this.phoneNumbers.addAll(phoneNumbers);
    }

    public void setRawData(HashMap<String, String> rawData) {
        this.rawData = rawData;
    }
}

