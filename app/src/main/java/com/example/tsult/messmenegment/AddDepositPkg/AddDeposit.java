package com.example.tsult.messmenegment.AddDepositPkg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.BazaarerDetails;
import com.example.tsult.messmenegment.AddMealPkg.Meal;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.MemberDetailsPkg.MemberDetails;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.Info;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddDeposit extends Activity {

    private RecyclerView depositList;
    private Button datePickBtn, addDepositBtn;
    private EditText moneyEt;
    private AddDepositDBOperation addDepositDBOperation;
    private ArrayList<Deposit>deposits;
    private AddDepositAdapter addDepositAdapter;
    private int id;
    private String mName, showDate, phone, email;
    private boolean status, check, memberDcheck, isMDcheck;
    private int year,month,day, depositID;
    private String identifier;
    private BazaarerDetails bazaarerDetails;
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deposit);

        info = MealInfo.Preference.getInfo(this);


        depositList = (RecyclerView) findViewById(R.id.deposit_list);
        datePickBtn = (Button) findViewById(R.id.date_btn);
        addDepositBtn = (Button) findViewById(R.id.add_deposit);
        moneyEt = (EditText) findViewById(R.id.money_et);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);

        final Intent intent = getIntent();
        status = intent.getBooleanExtra("status",false);
        memberDcheck = intent.getBooleanExtra("memberDCheck", false);
        isMDcheck = intent.getBooleanExtra("isDcheck", false);
        id = intent.getIntExtra("id",-1);
        mName = intent.getStringExtra("name");
        check = intent.getBooleanExtra("check", false);



        if (!isMDcheck){
            if (memberDcheck){
                mName = intent.getStringExtra("name");
                phone = intent.getStringExtra("phone");
                email = intent.getStringExtra("email");
                datePickBtn.setVisibility(View.GONE);
                addDepositBtn.setVisibility(View.GONE);
                moneyEt.setVisibility(View.GONE);
            }
        }

        if (check && !status && !info.isSaved()){
            identifier = intent.getStringExtra("identifier");
            addDepositBtn.setVisibility(View.GONE);
            datePickBtn.setVisibility(View.GONE);
            moneyEt.setVisibility(View.GONE);
        }else if (!status && info.isSaved()){
            identifier = info.getIdentifier();
            addDepositBtn.setVisibility(View.GONE);
            datePickBtn.setVisibility(View.GONE);
            moneyEt.setVisibility(View.GONE);
        }else if (status || isMDcheck){
            addDepositBtn.setText("UPDATE");
            showDate = intent.getStringExtra("date");
            int money = intent.getIntExtra("money",-1);
            depositID = intent.getIntExtra("depositId",-1);
            moneyEt.setText(String.valueOf(money));
            identifier = intent.getStringExtra("identifier");
        }else {
            showDate = day + "/"+ (month+1) + "/"+year;
            identifier = MealInfo.getYear()+" - "+MealInfo.getMonth();
        }

        addDepositDBOperation = new AddDepositDBOperation(this, id);

        datePickBtn.setText(showDate);

        bazaarerDetails = new BazaarerDetails(mName, id, phone, email, memberDcheck);

        createList();

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddDeposit.this,dateListener,year,month,day);
                dpd.show();
            }
            private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int myear, int mmonth, int dayOfMonth) {
                    showDate = dayOfMonth + "/"+ (mmonth+1) + "/"+myear;
                    datePickBtn.setText(showDate);
                }
            };
        });

        addDepositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = moneyEt.getText().toString();

                if (money.isEmpty()){
                    moneyEt.setError(getString(R.string.error_msg));
                }else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    if (status || isMDcheck){
                        Deposit deposit = new Deposit(depositID, id, Integer.parseInt(money), showDate, identifier);
                        boolean status1 = addDepositDBOperation.updateDepositList(deposit);
                        if (status1){
                            Toast.makeText(AddDeposit.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            status = false;
                            isMDcheck = false;

                            createList();
                            if (memberDcheck || check){
                                mName = intent.getStringExtra("name");
                                phone = intent.getStringExtra("phone");
                                email = intent.getStringExtra("email");
                                id = intent.getIntExtra("id", -1);
                                datePickBtn.setVisibility(View.GONE);
                                addDepositBtn.setVisibility(View.GONE);
                                moneyEt.setVisibility(View.GONE);
                            }else {
                                moneyEt.setText("");
                                addDepositBtn.setText("ADD");
                            }


                        }else{
                            Toast.makeText(AddDeposit.this, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Deposit deposit = new Deposit(id, Integer.parseInt(money), showDate, identifier);
                        boolean status1 = addDepositDBOperation.addDepositList(deposit);
                        if (status1){
                            Toast.makeText(AddDeposit.this, "Successfully added", Toast.LENGTH_SHORT).show();
                            createList();
                            moneyEt.setText("");
                        }else{
                            Toast.makeText(AddDeposit.this, "Failed to add", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });


    }

    private void createList(){
        deposits = new ArrayList<>();
        deposits = addDepositDBOperation.getDepositList(identifier);
        depositList.setHasFixedSize(true);
        depositList.setLayoutManager(new LinearLayoutManager(this));
        addDepositAdapter = new AddDepositAdapter(this, deposits, memberDcheck, check, bazaarerDetails);
        depositList.setAdapter(addDepositAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (check){
            Info info = MealInfo.Preference.getInfo(this);
            Intent intent = new Intent(this, ShowMealRate.class);
            if (info.isSaved()){
                intent.putExtra("table",info.getIdentifier());
                intent.putExtra("status", true);
            }else {
                intent.putExtra("table",identifier);
            }

            startActivity(intent);
        }else if (memberDcheck){
            Intent intent = new Intent(this, MemberDetails.class);
            intent.putExtra("id",id);
            intent.putExtra("name",mName);
            intent.putExtra("phone",phone);
            intent.putExtra("email",email);
            startActivity(intent);
        }else if (!status && !memberDcheck && !check){
            Intent intent = new Intent(this, ShowMember.class);
            intent.putExtra("status", true);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }*/

        startActivity(new Intent(AddDeposit.this, Main2Activity.class));
    }
}
