package com.example.dpanayotov.callloggingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.dpanayotov.callloggingexample.model.CallRecordColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dpanayotov on 9/11/2016
 */
public class CallLogEntryFullscreenActivity extends AppCompatActivity {

    public static final String KEY_RAW_DATA = "KEY_RAW_DATA";

    @BindView(R.id.list)
    RecyclerView list;

    List<CallRecordColumn> columns = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);
        ButterKnife.bind(this);
        parseRawData();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext
                ());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        CallRecordColumnAdapter adapter = new CallRecordColumnAdapter(columns);
        list.setAdapter(adapter);
    }

    private void parseRawData() {
        if (getIntent().hasExtra(KEY_RAW_DATA)) {
            HashMap<String, String> rawData = (HashMap<String, String>) getIntent()
                    .getSerializableExtra(KEY_RAW_DATA);
            columns.addAll(rawDataToColumnObjects(rawData));
        }
    }

    private List<CallRecordColumn> rawDataToColumnObjects(HashMap<String, String> rawData) {
        Iterator iterator = rawData.entrySet().iterator();
        List<CallRecordColumn> columns = new ArrayList<>();
        int counter = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry) iterator.next();
            columns.add(new CallRecordColumn(counter++, pair.getKey(), pair.getValue()));
        }
        return columns;
    }
}
