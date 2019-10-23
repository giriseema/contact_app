package com.contactapp.www.contactsapp.activity;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.contactapp.www.contactsapp.R;
import com.contactapp.www.contactsapp.constant.Constant;
import com.contactapp.www.contactsapp.contactbean.ContactBean;
import com.contactapp.www.contactsapp.entity.Contact;
import com.contactapp.www.contactsapp.permission.CommonPermission;
import com.contactapp.www.contactsapp.view_model.ContactViewModel;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailContactActivity extends AppCompatActivity {
    private static final String TAG = DetailContactActivity.class.getName();
    private ContactBean contactBean;
    private TextView nameContact;
    private TextView contactNumber;
    private Dialog dialog;
    private TextView otpTxtView;
    private EditText smsEdiTxt;
    private String mContactName = "";
    private String mContactNumber = "";
    private CommonPermission commonPermission;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 100;
    private OkHttpClient mClient = new OkHttpClient();
    private String smsString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        nameContact = findViewById(R.id.contact_name);
        contactNumber = findViewById(R.id.contact_number);
        commonPermission = new CommonPermission(this);
        //get selected contact bean Parcelable from intent extra
        if (getIntent() != null) {
            contactBean = new ContactBean();
            contactBean = getIntent().getParcelableExtra(Constant.CONTACT_DETAIL_KEY);
            mContactName = contactBean.getFirstName() + " " + contactBean.getLastName();
            mContactNumber = contactBean.getPhoneNumber();
            nameContact.setText(mContactName);
            contactNumber.setText(mContactNumber);
        }

    }

    // onClick operation
    public void sendMessage(View view) {
        //check permission before sending SMS and OTP
        if (commonPermission.checkPermissionSMS()) showDialogSendOTP();
        else commonPermission.requestPermissionSMS(MY_PERMISSIONS_REQUEST_SEND_SMS);

    }

    // if permission allowed show dialog box to send SMS
    private void showDialogSendOTP() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sent_message_screen);
        otpTxtView = dialog.findViewById(R.id.otp_gen);
        smsEdiTxt = dialog.findViewById(R.id.edt_sms);
        dialog.findViewById(R.id.sent_OTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isOnline(DetailContactActivity.this)) {
                    sendMessage();
                } else {
                    Toast.makeText(DetailContactActivity.this, getString(R.string.no_internet),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //create random number using thread local random
        int random_number = ThreadLocalRandom.current().nextInt(100000, 999999);
        otpTxtView.setText(String.valueOf(random_number));
        dialog.show();
    }

    /**
     * here sendMessage method send SMS on selected contact after checking validation
     */
    private void sendMessage() {
        try {
            //check edit text empty on not
            if (!TextUtils.isEmpty(smsEdiTxt.getText())) {
                smsString = getString(R.string.hi_otp) + otpTxtView.getText().toString() +
                        "\n" + smsEdiTxt.getText().toString();
                callBackMethod();
            } else {
                Toast.makeText(DetailContactActivity.this, getString(R.string.please_enter_msg),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(DetailContactActivity.this,
                    getString(R.string.failed_msg),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    /**
     * this method call the send SMS /OTP request
     */
    private void callBackMethod() {
        post(getString(R.string.backend_url), new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailContactActivity.this,
                                getString(R.string.failed_msg),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailContactActivity.this, getString(R.string.success_msg),
                                    Toast.LENGTH_SHORT).show();
                            Contact contact = new Contact(mContactName,
                                    mContactNumber,
                                    String.valueOf(System.currentTimeMillis()),
                                    smsString);
                            ContactViewModel contactViewModel = ViewModelProviders.of(DetailContactActivity.this).get(ContactViewModel.class);
                            contactViewModel.insert(contact);
                            dialog.dismiss();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailContactActivity.this,
                                    getString(R.string.failed_msg),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * generate POST request to send sms to respective number
     *
     * @param url sending url
     * @param callback to show result
     * @return result of post request
     */
    Call post(String url, Callback callback) {

        RequestBody formBody = new FormBody.Builder()
                .add("Body", smsString)
                .add("To", contactBean.getPhoneNumber())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call response = mClient.newCall(request);
        response.enqueue(callback);
        return response;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

          if (requestCode == 100) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showDialogSendOTP();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(permissions[0])) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setTitle(getString(R.string.info));
                        alertDialog.setMessage(getString(R.string.allow_permission));
                        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent settingIntent = new Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                settingIntent.setData(Uri.parse("package:"
                                        + DetailContactActivity.this.getPackageName()));
                                startActivity(settingIntent);
                            }
                        });
                        alertDialog.setNegativeButton(getString(R.string.no), null);
                        alertDialog.show();
                    }else
                        commonPermission.requestPermissionSMS(MY_PERMISSIONS_REQUEST_SEND_SMS);

                }
            }

        }
    }
}
