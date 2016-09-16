package com.example.dpanayotov.callloggingexample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dpanayotov.callloggingexample.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsManagementActivity extends AppCompatActivity implements ContactsAdapter
        .ContactsAdapterItemOnClickListener {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.remove_contact)
    FloatingActionButton removeContact;

    private List<Contact> contacts = new ArrayList<>();

    private ContactsAdapter adapter;

    private Contact selectedContact = null;

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
    public void onItemClicked(Contact contact, boolean selected) {
        if (selected) {
            selectedContact = contact;
            removeContact.setVisibility(View.VISIBLE);
        } else {
            removeContact.setVisibility(View.GONE);
        }
        Toast.makeText(this, "Item clicked: " + contact.getDisplayName(), Toast.LENGTH_SHORT)
                .show();
    }

    @OnClick(R.id.add_contact)
    void addContactButtonOnClick() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.dialog_add_contact, null);
        final AddContactDialogBinder binder = new AddContactDialogBinder(promptsView);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertDialogBuilder.setView(promptsView).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = binder.contactName.getText().toString();
                String number = binder.contactNumber.getText().toString();
                if (name != null && !name.isEmpty() && number != null && !number.isEmpty()) {
                    PhoneBookUtil.addContact(name, number, ContactsManagementActivity.this);
                    refreshContacts();
                } else {
                    Toast.makeText(ContactsManagementActivity.this, "Please fill in both fields "
                            + "to crate a new contact.", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).create();

        alertDialog.show();
    }

    @OnClick(R.id.remove_contact)
    void removeContactButtonClick() {
        PhoneBookUtil.removeContact(selectedContact.getLookupKey(), this);
        refreshContacts();
    }

    class AddContactDialogBinder {
        @BindView(R.id.contact_name)
        EditText contactName;
        @BindView(R.id.contact_number)
        EditText contactNumber;

        public AddContactDialogBinder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
