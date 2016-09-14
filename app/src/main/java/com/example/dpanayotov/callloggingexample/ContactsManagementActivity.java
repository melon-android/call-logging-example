package com.example.dpanayotov.callloggingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dpanayotov.callloggingexample.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsManagementActivity extends AppCompatActivity implements ContactsAdapter
        .ContactsAdapterItemOnClickListener {

    @BindView(R.id.list)
    RecyclerView list;

    private List<Contact> contacts = new ArrayList<>();

    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);
        ButterKnife.bind(this);
        adapter = new ContactsAdapter(contacts, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext
                ());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshContacts();
    }

    private void refreshContacts() {
        contacts.clear();
        contacts.addAll(PhoneBookUtil.getContacts(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(Contact contact) {
        Toast.makeText(this, "Item clicked: "+contact.getDisplayName(), Toast.LENGTH_SHORT).show();
    }
}
