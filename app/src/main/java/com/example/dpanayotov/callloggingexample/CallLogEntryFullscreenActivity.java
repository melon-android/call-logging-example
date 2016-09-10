package com.example.dpanayotov.callloggingexample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dpanayotov on 9/11/2016
 */
public class CallLogEntryFullscreenActivity extends AppCompatActivity {

    public static final String KEY_RAW_DATA = "KEY_RAW_DATA";

    @BindView(R.id.raw_data)
    EditText rawDataEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_entry_fullscreen);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(KEY_RAW_DATA)) {
            HashMap<String, String> rawData = (HashMap<String, String>) getIntent()
                    .getSerializableExtra(KEY_RAW_DATA);
            Iterator iterator = rawData.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            int counter = 0;
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                sb.append("[");
                sb.append(counter++);
                sb.append("] ");
                sb.append(pair.getKey());
                sb.append(": ");
                sb.append(pair.getValue());
                sb.append("\n");
            }
            rawDataEditText.setText(sb);
        }
    }
}
