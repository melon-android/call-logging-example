package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, 0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .READ_CALL_LOG}, 13);

        } else {
            ((TextView) findViewById(R.id.call)).setText(CallUtil.getCallDetails(this));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 13 && permissions.length > 0 && Manifest.permission.READ_CALL_LOG
                .equals(permissions[0]) && grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            ((TextView) findViewById(R.id.call)).setText(CallUtil.getCallDetails(this));
        }
    }
}
