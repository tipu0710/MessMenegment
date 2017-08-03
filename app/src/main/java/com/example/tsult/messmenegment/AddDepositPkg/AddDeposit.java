package com.example.tsult.messmenegment.AddDepositPkg;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMealPkg.Meal;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddDeposit extends AppCompatActivity {

    private RecyclerView depositList;
    private Button datePickBtn, addDepositBtn;
    private EditText moneyEt;
    private AddDepositDBOperation addDepositDBOperation;
    private ArrayList<Deposit>deposits;
    private AddDepositAdapter addDepositAdapter;
    private int id;
    private String mName, showDate;
    private boolean status, check;
    private int year,month,day, depositID;
    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deposit);

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
        id = intent.getIntExtra("id",-1);
        mName = intent.getStringExtra("name");
        check = intent.getBooleanExtra("check", false);
        if (check){
            identifier = intent.getStringExtra("identifier");
            addDepositBtn.setVisibility(View.GONE);
            datePickBtn.setVisibility(View.GONE);
            moneyEt.setVisibility(View.GONE);
        }else if (status){
            addDepositBtn.setText("UPDATE");
            showDate = intent.getStringExtra("date");
            int money = intent.getIntExtra("money",-1);
            depositID = intent.getIntExtra("depositId",-1);
            moneyEt.setText(String.valueOf(money));
            identifier = intent.getStringExtra("identifier");

        }else {
            showDate = day + "/"+ (month+1) + "/"+year;
            identifier = MealInfo.getMonthName(MealInfo.getMonth())+" - "+MealInfo.getYear();
        }

        addDepositDBOperation = new AddDepositDBOperation(this, id);

        datePickBtn.setText(showDate);

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
                    moneyEt.setError(String.valueOf(R.string.error_msg));
                }else {
                    if (status){
                        Deposit deposit = new Deposit(depositID, id, Integer.parseInt(money), showDate, identifier);
                        boolean status1 = addDepositDBOperation.updateDepositList(deposit);
                        if (status1){
                            Toast.makeText(AddDeposit.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            status = false;
                            createList();
                            moneyEt.setText("");
                            addDepositBtn.setText("ADD");

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
        addDepositAdapter = new AddDepositAdapter(this, deposits);
        depositList.setAdapter(addDepositAdapter);
    }


}
