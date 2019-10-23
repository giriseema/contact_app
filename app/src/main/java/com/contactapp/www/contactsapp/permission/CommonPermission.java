package com.contactapp.www.contactsapp.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.contactapp.www.contactsapp.MainActivity;

/**
 * Common Permission class for access application features
 * here get permission for SMS sending permission
 */
public class CommonPermission {
    private static final String TAG = CommonPermission.class.getName();
    private Activity activity;

    public CommonPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionSMS(){
        int smsPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.SEND_SMS);
        return smsPermission == PackageManager.PERMISSION_GRANTED;

    }

    public void requestPermissionSMS(int requestCode) {
        int storage_Write = ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);
        if (storage_Write != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, requestCode);
        }
    }
}
