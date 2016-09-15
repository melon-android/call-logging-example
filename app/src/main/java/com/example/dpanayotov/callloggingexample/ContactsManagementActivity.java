package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
        setContentView(R.layout.activity_contacts_management);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_CONTACTS}, 13);

        } else {
            refreshContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 13 && permissions.length > 0 && Manifest.permission.WRITE_CONTACTS
                .equals(permissions[0]) && grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            refreshContacts();
        }
    }

    private void refreshContacts() {
        List<Contact> newContacts = PhoneBookUtil.getContacts(this);
        if (newContacts != null) {
            contacts.clear();
            contacts.addAll(newContacts);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClicked(Contact contact) {
        Toast.makeText(this, "Item clicked: " + contact.getDisplayName(), Toast.LENGTH_SHORT)
                .show();
    }
}
