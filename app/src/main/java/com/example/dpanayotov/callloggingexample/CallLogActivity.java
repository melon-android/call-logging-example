package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dpanayotov.callloggingexample.model.CallLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class CallLogActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView list;

    private List<CallLog> callLogs = new ArrayList<>();

    private CallLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);
        ButterKnife.bind(this);
        adapter = new CallLogAdapter(this, callLogs);
        list.setAdapter(adapter);
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
            callLogs.addAll(CallUtil.getCallDetails(this));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 13 && permissions.length > 0 && Manifest.permission.READ_CALL_LOG
                .equals(permissions[0]) && grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            callLogs.addAll(CallUtil.getCallDetails(this));
            adapter.notifyDataSetChanged();
        }
    }

    @OnItemClick(R.id.list)
    void OnItemClickList(int position){
        Intent intent = new Intent(CallLogActivity.this, CallLogEntryFullscreenActivity.class);
        intent.putExtra(CallLogEntryFullscreenActivity.KEY_RAW_DATA, adapter.getItem(position).getRawValues());
        startActivity(intent);
    }
}
