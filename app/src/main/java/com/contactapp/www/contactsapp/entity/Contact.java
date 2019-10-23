package com.contactapp.www.contactsapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String contactName;

    private String phoneNumber;

    private String timeValue;

    private String otpSMS;

    public Contact(String contactName, String phoneNumber,String timeValue,String otpSMS) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.timeValue = timeValue;
        this.otpSMS = otpSMS;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public String getOtpSMS() {
        return otpSMS;
    }
}
