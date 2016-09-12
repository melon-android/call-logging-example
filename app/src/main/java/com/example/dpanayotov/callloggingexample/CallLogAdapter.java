package com.example.dpanayotov.callloggingexample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dpanayotov.callloggingexample.model.CallDirection;
import com.example.dpanayotov.callloggingexample.model.CallLog;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dpanayotov on 9/10/2016
 */
public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogRecyclerView> {

    private static final String ZERO_CALL_DURATION = "0";

    private List<CallLog> callLogs;

    private CallLogAdapterItemOnClickListener onClickListener;

    public CallLogAdapter(List<CallLog> callLogs, CallLogAdapterItemOnClickListener
            onClickListener) {
        Log.d("zxc", "CallLogAdapter");
        this.callLogs = callLogs;
        this.onClickListener = onClickListener;
    }

    @Override
    public CallLogRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("zxc", "onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_item,
                parent, false);
        return new CallLogRecyclerView(itemView);
    }

    @Override
    public int getItemCount() {
        Log.d("zxc", "getItemCount " + callLogs.size());
        return callLogs.size();
    }

    @Override
    public void onBindViewHolder(CallLogRecyclerView holder, int position) {
        Log.d("zxc", "onBindViewHolder " + position);
        CallLog callLog = callLogs.get(position);
        holder.callNumber.setText(callLog.getPhoneNumber());
        holder.callDate.setText(callLog.getCallDate().toString());
        if (callLog.getCallDirection() != null) {
            if (Arrays.asList(CallDirection.INCOMING_TYPE, CallDirection.OUTGOING_TYPE,
                    CallDirection.VOICEMAIL_TYPE).contains(callLog.getCallDirection()) && callLog
                    .getCallDuration() != null && !ZERO_CALL_DURATION.equals(callLog
                    .getCallDuration())) {
                holder.callDuration.setVisibility(View.VISIBLE);
                holder.callDuration.setText(callLog.getCallDuration());
            } else {
                holder.callDuration.setVisibility(View.GONE);
            }
            holder.callDirection.setVisibility(View.VISIBLE);
            holder.callDirection.setImageResource(callLog.getCallDirection().getResId());

        } else {
            holder.callDirection.setVisibility(View.GONE);
        }
    }

    public class CallLogRecyclerView extends RecyclerView.ViewHolder {

        @BindView(R.id.call_number)
        public TextView callNumber;
        @BindView(R.id.call_date)
        public TextView callDate;
        @BindView(R.id.call_duration)
        public TextView callDuration;
        @BindView(R.id.call_direction)
        public ImageView callDirection;

        public CallLogRecyclerView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClicked(callLogs.get(getLayoutPosition()));
                }
            });
        }
    }

    public interface CallLogAdapterItemOnClickListener {
        void onItemClicked(CallLog callLog);
    }

}
