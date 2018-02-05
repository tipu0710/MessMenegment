package com.example.tsult.messmenegment.AddMember;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        askForContactPermission();
        contactListView.setHasFixedSize(true);
        contactListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(addMemberDetailInfos, this);
        contactListView.setAdapter(adapter);
    }

    private void getContactList() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursorAndroidContact = null;
        try {
            cursorAndroidContact = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
        }catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        if (cursorAndroidContact.getCount() > 0) {
            while (cursorAndroidContact.moveToNext()) {

                AddMemberDetailsInfo addMemberDetailsInfo = new AddMemberDetailsInfo();

                String contactID = cursorAndroidContact.getString(cursorAndroidContact.getColumnIndex(ContactsContract.Contacts._ID));
                String contactDisplayName = cursorAndroidContact.getString(cursorAndroidContact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                addMemberDetailsInfo.setmName(contactDisplayName);

                int hasPhoneNumber = Integer.parseInt(cursorAndroidContact.getString(cursorAndroidContact.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactID},
                            null);

                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        addMemberDetailsInfo.setmPhone(phoneNumber);
                    }

                    phoneCursor.close();
                }
                addMemberDetailsInfo.setChecked(false);
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

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please confirm Contacts access to show which member you need to add");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.

                    getContactList();
                }
            } else {
                getContactList();
            }
        } else {
            getContactList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "No Permissions ", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}