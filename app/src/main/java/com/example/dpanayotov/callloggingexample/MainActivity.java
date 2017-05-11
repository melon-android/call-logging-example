package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_call_log)
    public void onCallLogButtonClick() {
        startActivity(new Intent(MainActivity.this, CallLogActivity.class));
    }

    @OnClick(R.id.btn_contacts_management)
    public void onContactsManagementButtonClick() {
        startActivity(new Intent(MainActivity.this, ContactsManagementActivity.class));
    }

    @OnClick(R.id.btn_contact_image_size)
    public void onConcatImageSizeButtonClick() {
        Toast.makeText(this, "Max dimensions: " + PhoneBookUtil.getMaxContactPhotoSize(this),
                Toast.LENGTH_SHORT).show();
    }
}
