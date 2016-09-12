package com.example.dpanayotov.callloggingexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dpanayotov.callloggingexample.model.CallRecordColumn;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dpanayotov on 9/12/2016
 */
public class CallRecordColumnAdapter extends RecyclerView.Adapter<CallRecordColumnAdapter
        .CallRecordColumnViewHolder> {

    private List<CallRecordColumn> columns;

    public CallRecordColumnAdapter(List<CallRecordColumn> columns) {
        this.columns = columns;
    }

    @Override
    public CallRecordColumnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .call_record_column_item, parent, false);
        return new CallRecordColumnViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CallRecordColumnViewHolder holder, int position) {
        CallRecordColumn callRecordColumn = columns.get(position);
        holder.id.setText(Integer.toString(callRecordColumn.getId()));
        holder.key.setText(callRecordColumn.getKey());
        holder.value.setText(callRecordColumn.getValue());
    }

    @Override
    public int getItemCount() {
        return columns.size();
    }

    public class CallRecordColumnViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id)
        public TextView id;
        @BindView(R.id.key)
        public TextView key;
        @BindView(R.id.value)
        public TextView value;

        public CallRecordColumnViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
