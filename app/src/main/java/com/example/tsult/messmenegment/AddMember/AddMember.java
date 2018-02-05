package com.example.tsult.messmenegment.AddMember;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.Meal;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddMember extends Activity {

    private EditText mNameET;
    private EditText mPhoneET;
    private EditText mEmailET;
    private Button mAddBtn;
    private AddMemberDBOperation addMemberDBOperation;
    private AddMealDBOperation addMealDBOperation;
    private ArrayList<Member>members;
    private ArrayList<Meal>meals;
    private int year,month,day, mId;
    private String mName, mPhone, mEmail, identifier;
    private boolean value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mNameET = (EditText) findViewById(R.id.full_name);
        mPhoneET = (EditText) findViewById(R.id.phone_number);
        mEmailET = (EditText) findViewById(R.id.email);
        mAddBtn = (Button) findViewById(R.id.add_member_info);

        identifier = MealInfo.getYear()+" - "+MealInfo.getMonth();
        Intent intent = getIntent();
        value = intent.getBooleanExtra("status",false);
        if (value){
            mId = intent.getIntExtra("id",-1);
            mName = intent.getStringExtra("name");
            mNameET.setText(mName);
            mPhone = intent.getStringExtra("phone");
            mPhoneET.setText(mPhone);
            mEmail = intent.getStringExtra("email");
            mEmailET.setText(mEmail);
            mAddBtn.setText("UPDATE");
        }

        members = new ArrayList<>();
        meals =new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        year = calendar.get(Calendar.YEAR);
        month =calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        addMemberDBOperation = new AddMemberDBOperation(this);
        addMealDBOperation = new AddMealDBOperation(this, day + "/"+ (month+1) + "/"+year);

        //Adding to database
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameET.getText().toString();
                String phone = mPhoneET.getText().toString();
                String email = mEmailET.getText().toString();
                if (name.isEmpty()){
                    mNameET.setError(getString(R.string.error_msg));
                }
                else if (phone.isEmpty()){
                    mPhoneET.setError(getString(R.string.error_msg));
                }else {
                    if (value){
                        Member member = new Member(mId, name, phone, email, identifier, false);
                        boolean id = addMemberDBOperation.UpdateMemberList(member);
                        if (id){
                            Toast.makeText(AddMember.this, "Successfully update!", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(AddMember.this, ShowMember.class);
                            intent1.putExtra("status",false);
                            startActivity(intent1);
                            finish();
                        }
                    }else {
                        if (email.isEmpty()){
                            email = "Not available";
                        }
                        Member member = new Member(name,phone,email, identifier);
                        boolean id = addMemberDBOperation.AddMemberList(member);
                        if (id){
                            mNameET.setText("");
                            mPhoneET.setText("");
                            mEmailET.setText("");
                            Toast.makeText(AddMember.this, "Successfully added!", Toast.LENGTH_SHORT).show();
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


                        }else {
                            Toast.makeText(AddMember.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (value){
            Intent intent1 = new Intent(AddMember.this, ShowMember.class);
            intent1.putExtra("status",false);
            startActivity(intent1);
        }else {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
        finish();
    }
}
