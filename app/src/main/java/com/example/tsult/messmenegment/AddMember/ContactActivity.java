package com.example.tsult.messmenegment.AddMember;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private ArrayList<AddMemberDetailsInfo> addMemberDetailInfos;
    private RecyclerView contactListView;
    private RecyclerView.Adapter adapter;
    private static final int PERMISSION_REQUEST_CONTACT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactListView = (RecyclerView) findViewById(R.id.contact_list);

        addMemberDetailInfos = new ArrayList<>();
        getContactList();

        contactListView.setHasFixedSize(true);
        contactListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(addMemberDetailInfos, this);
        contactListView.setAdapter(adapter);
    }

    private void getContactList() {

        showContact();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursorAndroidContact;
        cursorAndroidContact = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursorAndroidContact.getCount() > 0){
            while (cursorAndroidContact.moveToNext()){

                AddMemberDetailsInfo addMemberDetailsInfo = new AddMemberDetailsInfo();

                String contactID = cursorAndroidContact.getString(cursorAndroidContact.getColumnIndex(ContactsContract.Contacts._ID));
                String contactDisplayName = cursorAndroidContact.getString(cursorAndroidContact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                addMemberDetailsInfo.setmName(contactDisplayName);

                int hasPhoneNumber = Integer.parseInt( cursorAndroidContact.getString(cursorAndroidContact.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0){
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactID},
                            null);

                    while (phoneCursor.moveToNext()){
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        addMemberDetailsInfo.setmPhone(phoneNumber);
                    }

                    phoneCursor.close();
                }

                addMemberDetailInfos.add(addMemberDetailsInfo);
            }
        }

        cursorAndroidContact.close();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Main2Activity.class));
    }

    private void showContact(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestContactPermission();
        }
    }

    private void requestContactPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_CONTACT);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
        }
    }
}
