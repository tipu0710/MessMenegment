package com.example.tsult.messmenegment.AddMember;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.Meal;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ContactActivity extends AppCompatActivity {

    private ArrayList<AddMemberDetailsInfo> addMemberDetailInfos;
    private RecyclerView contactListView;

    private RecyclerView.Adapter adapter;
    private static final int PERMISSION_REQUEST_CONTACT = 0;
    private int day, month, year, checkedSize = 0;
    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactListView = (RecyclerView) findViewById(R.id.contact_list);

        identifier = MealInfo.getYear()+" - "+ MealInfo.getMonth();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month =calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        addMemberDetailInfos = new ArrayList<>();

        askForContactPermission();
        contactListView.setHasFixedSize(true);
        contactListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ok) {
            if (checkedSize>0){
                int count =0;
                for (int i = 0; i< addMemberDetailInfos.size(); i++){
                    if (addMemberDetailInfos.get(i).isCheckedContact()){
                        boolean bol = AddContactMember(addMemberDetailInfos.get(i).getmName(), addMemberDetailInfos.get(i).getmPhone(), "");
                        if (bol)
                            count++;
                    }
                }
                Toast.makeText(this, count + " members successfully added!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Main2Activity.class));
            }else {
                Toast.makeText(this, "Please select atleast 1 member!", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(row);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.name.setText(addMemberDetailInfos.get(position).getmName());
            holder.number.setText(addMemberDetailInfos.get(position).getmPhone());

            holder.select.setChecked(addMemberDetailInfos.get(position).isCheckedContact());
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean is;
                    if (addMemberDetailInfos.get(position).isCheckedContact()){
                        is = false;
                        checkedSize--;
                    }else {
                        is = true;
                        checkedSize++;
                    }
                    AddMemberDetailsInfo info = new AddMemberDetailsInfo(addMemberDetailInfos.get(position).getmName(),
                            addMemberDetailInfos.get(position).getmPhone(), is);
                    addMemberDetailInfos.set(position, info);
                }
            });
        }

        @Override
        public int getItemCount() {
            return addMemberDetailInfos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            TextView number;
            CheckBox select;

            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.contact_name);
                number = (TextView) itemView.findViewById(R.id.contact_number);
                select = (CheckBox) itemView.findViewById(R.id.contact_selection_radio_button);
            }
        }
    }

    private boolean AddContactMember(String name, String phone, String email){
        AddMemberDBOperation addMemberDBOperation = new AddMemberDBOperation(this);
        AddMealDBOperation addMealDBOperation = new AddMealDBOperation(this, day + "/"+ (month+1) + "/"+year);
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();
        int mId = 0;
        String mName = null;

        if (email.isEmpty()){
            email = "Not available";
        }
        Member member = new Member(name,phone,email, identifier);

        boolean id = addMemberDBOperation.AddMemberList(member);
        if (id){
            members = addMemberDBOperation.getMemberList(identifier);

            meals = addMealDBOperation.getMeal(identifier);

            if (meals.size()>0){
                for (int i =0; i<members.size(); i++){
                    mId = members.get(i).getmId();
                    mName = members.get(i).getmName();
                }
                for (int i = day; i>=1; i--){
                    Meal meal = new Meal(i, mId, mName, i + "/"+ (month+1) + "/"+year, 0, identifier);
                    addMealDBOperation.AddMeal(meal);
                }
            }else {
                for (int i =0; i<members.size(); i++){
                    mId = members.get(i).getmId();
                    mName = members.get(i).getmName();
                }
                for (int i = day-1; i>=1; i--){
                    Meal meal = new Meal(i, mId, mName, i + "/"+ (month+1) + "/"+year, 0, identifier);
                    addMealDBOperation.AddMeal(meal);
                }
            }


        }

        return id;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Main2Activity.class));
    }

}