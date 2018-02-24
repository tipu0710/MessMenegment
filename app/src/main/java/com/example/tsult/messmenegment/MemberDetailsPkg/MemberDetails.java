package com.example.tsult.messmenegment.MemberDetailsPkg;

import android.Manifest;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddDepositPkg.AddDeposit;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddDepositPkg.Deposit;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.ShowIndViMeal;
import com.example.tsult.messmenegment.BazarList.BazarList;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.Info;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MemberDetails extends Activity {

    private TextView mNameTV;
    private TextView totalMealTv;
    private TextView depositTv;
    private TextView restMoneyTv;
    private TextView  perPersonExtraCostTv;
    private Button mealRecords, bazaarRecords, depositRecords;

    private MealInfo mealInfo;
    private Info info;
    private AddMealDBOperation addMealDBOperation;
    private AddDepositDBOperation addDepositDBOperation;
    private String mName, mPhone, mEmail, identifier;
    private int id, meal, totalMoney;
    private double mealRate, totalCost, restMoney, perPersonExtraCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        info = MealInfo.Preference.getInfo(this);

        mNameTV = (TextView) findViewById(R.id.member_name_tv);
        totalMealTv = (TextView) findViewById(R.id.ind_total_meal_tv);
        depositTv = (TextView) findViewById(R.id.ind_total_deposit_tv);
        restMoneyTv = (TextView) findViewById(R.id.ind_rest_money_tv);
        perPersonExtraCostTv = (TextView) findViewById(R.id.ind_extra_tv);
        mealRecords = (Button) findViewById(R.id.meal_records);
        bazaarRecords = (Button) findViewById(R.id.bazaar_records);
        depositRecords = (Button) findViewById(R.id.deposit_records);

        if (info.isSaved()){
            identifier = info.getIdentifier();
        }else
        {
            identifier =MealInfo.getYear()+" - "+ MealInfo.getMonth();
        }

        final Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mPhone = intent.getStringExtra("phone");
        mEmail = intent.getStringExtra("email");
        id = intent.getIntExtra("id", -1);

        addMealDBOperation = new AddMealDBOperation(this, null);
        meal = addMealDBOperation.getIndividualMeal(id, identifier);
        totalMealTv.setText(String.valueOf(meal));

        mealInfo = new MealInfo(this, identifier);
        mealRate = mealInfo.getMealRate();

        perPersonExtraCost = mealInfo.getExtraRate();
        perPersonExtraCostTv.setText(String.valueOf(perPersonExtraCost));

        totalCost = (meal * mealRate)+perPersonExtraCost;

        addDepositDBOperation = new AddDepositDBOperation(this, id);
        totalMoney = addDepositDBOperation.getIndividualDeposit(id, identifier);
        depositTv.setText(String.valueOf(totalMoney));

        restMoney = Double.parseDouble(new DecimalFormat("##.##").format((double) totalMoney - totalCost));
        restMoneyTv.setText(String.valueOf(restMoney));

        mNameTV.setText(mName);

        mealRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MemberDetails.this, ShowIndViMeal.class);
                intent1.putExtra("id",id);
                intent1.putExtra("name",mName);
                intent1.putExtra("phone",mPhone);
                intent1.putExtra("email",mEmail);
                startActivity(intent1);
            }
        });

        bazaarRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberDetails.this, BazarList.class);
                intent.putExtra("id",id);
                intent.putExtra("name",mName);
                intent.putExtra("phone",mPhone);
                intent.putExtra("email",mEmail);
                startActivity(intent);
            }
        });

        depositRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MemberDetails.this, AddDeposit.class);
                intent2.putExtra("id",id);
                intent2.putExtra("name",mName);
                intent2.putExtra("phone",mPhone);
                intent2.putExtra("email",mEmail);
                intent2.putExtra("memberDCheck", true);
                startActivity(intent2);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (info.isSaved()){
            Intent intent = new Intent(this, ShowMealRate.class);
            intent.putExtra("table",info.getIdentifier());
            intent.putExtra("status", true);
            startActivity(intent);
            MealInfo.Preference.ClearPreference(this);
        }else {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }

    }
}
