package com.example.tsult.messmenegment.MemberDetailsPkg;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.ShowIndViMeal;
import com.example.tsult.messmenegment.BazarList.BazarList;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MemberDetails extends AppCompatActivity {

    private TextView mNameTV;
    private TextView mPhoneTV;
    private TextView mEmailTV;
    private TextView totalMealTv;
    private TextView depositTv;
    private TextView mealRateTv;
    private TextView restMoneyTv;
    private TextView totalCostTv, totalExtraCostTv, perPersonExtraCostTv;
    private ImageButton callBtn;
    private ImageButton emailBtn;

    private MealInfo mealInfo;
    private AddMealDBOperation addMealDBOperation;
    private AddDepositDBOperation addDepositDBOperation;
    private AddExtraDBOperation addExtraDBOperation;
    private String mName, mPhone, mEmail, identifier;
    private int id, meal, totalMoney, totalExtraCost;
    private double mealRate, totalCost, restMoney, perPersonExtraCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        emailBtn = (ImageButton) findViewById(R.id.email_btn);
        callBtn = (ImageButton) findViewById(R.id.call_btn);
        mNameTV = (TextView) findViewById(R.id.member_name_tv);
        mPhoneTV = (TextView) findViewById(R.id.member_phone_tv);
        mEmailTV = (TextView) findViewById(R.id.member_email_tv);
        totalMealTv = (TextView) findViewById(R.id.ind_total_meal_tv);
        depositTv = (TextView) findViewById(R.id.ind_total_deposit_tv);
        mealRateTv = (TextView) findViewById(R.id.ind_total_meal_rate_tv);
        restMoneyTv = (TextView) findViewById(R.id.ind_rest_money_tv);
        totalCostTv = (TextView) findViewById(R.id.ind_total_cost_tv);
        totalExtraCostTv = (TextView) findViewById(R.id.total_extra_tv);
        perPersonExtraCostTv = (TextView) findViewById(R.id.ind_extra_tv);

        identifier = MealInfo.getMonthName(MealInfo.getMonth())+" - "+MealInfo.getYear();

        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mPhone = intent.getStringExtra("phone");
        mEmail = intent.getStringExtra("email");
        id = intent.getIntExtra("id", -1);

        addMealDBOperation = new AddMealDBOperation(this, null);
        meal = addMealDBOperation.getIndividualMeal(id, identifier);
        totalMealTv.setText(String.valueOf(meal));

        mealInfo = new MealInfo(this, identifier);
        mealRate = mealInfo.getMealRate();
        mealRateTv.setText(String.valueOf(mealRate));

        addExtraDBOperation = new AddExtraDBOperation(this);
        totalExtraCost = addExtraDBOperation.getAllExtraCost(identifier);
        totalExtraCostTv.setText(String.valueOf(totalExtraCost));

        perPersonExtraCost = mealInfo.getExtraRate();
        perPersonExtraCostTv.setText(String.valueOf(perPersonExtraCost));

        totalCost = (meal * mealRate)+perPersonExtraCost;
        totalCostTv.setText(String.valueOf(totalCost));

        addDepositDBOperation = new AddDepositDBOperation(this, id);
        totalMoney = addDepositDBOperation.getIndividualDeposit(id, identifier);
        depositTv.setText(String.valueOf(totalMoney));

        restMoney = Double.parseDouble(new DecimalFormat("##.##").format((double) totalMoney - totalCost));
        restMoneyTv.setText(String.valueOf(restMoney));

        mNameTV.setText(mName);
        mPhoneTV.setText(mPhone);
        mEmailTV.setText(mEmail);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + mPhone));
                if (ActivityCompat.checkSelfPermission(MemberDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MemberDetails.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }
                startActivity(intent);
            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmail.equals("Not available")){
                    Toast.makeText(MemberDetails.this, "Email not available!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent(MemberDetails.this, SendMail.class);
                    intent1.putExtra("mail",mEmail);
                    startActivity(intent1);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.members_details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.bazaar_list_menu:
                Intent intent = new Intent(MemberDetails.this, BazarList.class);
                intent.putExtra("id",id);
                intent.putExtra("name",mName);
                startActivity(intent);
                break;
            case R.id.meal_list_menu:
                Intent intent1 = new Intent(MemberDetails.this, ShowIndViMeal.class);
                intent1.putExtra("id",id);
                intent1.putExtra("name",mName);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
