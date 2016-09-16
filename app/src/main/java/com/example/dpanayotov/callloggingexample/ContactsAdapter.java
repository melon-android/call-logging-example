package com.example.dpanayotov.callloggingexample;

import android.graphics.Color;
import android.provider.Contacts;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dpanayotov.callloggingexample.model.CallLog;
import com.example.dpanayotov.callloggingexample.model.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dpanayotov on 9/14/2016
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactRecyclerView> {

    List<Contact> contacts;

    ContactsAdapterItemOnClickListener onClickListener;

    private int selectedPosition = -1;

    public ContactsAdapter(List<Contact> contacts, ContactsAdapterItemOnClickListener
            onClickListener) {
        this.contacts = contacts;
        this.onClickListener = onClickListener;
    }

    @Override
    public ContactRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,
                parent, false);
        return new ContactRecyclerView(itemView);
    }

    @Override
    public void onBindViewHolder(ContactRecyclerView holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.getDisplayName());
        if (contact.getPhoneNumbers().size() > 0) {
            holder.phoneNumbers.setVisibility(View.VISIBLE);
            holder.phoneNumbers.setText("");
            for (String phoneNumber : contact.getPhoneNumbers()) {
                holder.phoneNumbers.append(phoneNumber + "\n");
            }
        } else {
            holder.phoneNumbers.setVisibility(View.GONE);
        }
        holder.root.setBackgroundColor(selectedPosition == position ? Color.LTGRAY : Color
                .TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactRecyclerView extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        public TextView name;
        @BindView(R.id.phone_numbers)
        public TextView phoneNumbers;

        public View root;

        public ContactRecyclerView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            root = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int oldPosition = selectedPosition;
                    selectedPosition = getLayoutPosition();
                    boolean selected = oldPosition != selectedPosition;
                    notifyItemChanged(selectedPosition);
                    if (selected) notifyItemChanged(oldPosition);
                    onClickListener.onItemClicked(contacts.get(getLayoutPosition()), selected);
                }
            });
        }
    }

    public interface ContactsAdapterItemOnClickListener {
        void onItemClicked(Contact contact, boolean selected);
    }
}
