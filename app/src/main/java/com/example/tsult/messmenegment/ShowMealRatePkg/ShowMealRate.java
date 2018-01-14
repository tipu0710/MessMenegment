package com.example.tsult.messmenegment.ShowMealRatePkg;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShowMealRate extends Activity {

    private TextView totalMeal, totalBazaarCost, mealRate, extraTv;
    private RecyclerView memberList;

    private AddMealDBOperation addMealDBOperation;
    private AddBazaarDBOperation addBazaarDBOperation;
    private AddExtraDBOperation addExtraDBOperation;
    private AddMemberDBOperation addMemberDBOperation;
    private ArrayList<Member> members;
    private MealRateAdapter mealRateAdapter;

    private int meal, bazaar, extraCost;
    private double rate;
    private boolean status = false;
    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_meal_rate);
        totalMeal = (TextView) findViewById(R.id.total_meal);
        totalBazaarCost = (TextView) findViewById(R.id.total_bazaar_cost);
        mealRate = (TextView) findViewById(R.id.meal_rate);
        extraTv = (TextView) findViewById(R.id.meal_extra);
        memberList = (RecyclerView) findViewById(R.id.member_meal_details_list);


        Intent intent = getIntent();
        status = intent.getBooleanExtra("status",false);
        if (status){
            identifier = intent.getStringExtra("table");
        }else {
            identifier =MealInfo.getYear()+" - "+ MealInfo.getMonth();
        }

        getControl();

    }

    public void getControl(){
        addMealDBOperation = new AddMealDBOperation(this, null);
        meal = addMealDBOperation.getAllMeal(identifier);
        totalMeal.setText(String.valueOf(meal));

        addBazaarDBOperation = new AddBazaarDBOperation(0, this);
        bazaar = addBazaarDBOperation.getAllBazaarCost(identifier);
        totalBazaarCost.setText(String.valueOf(bazaar));

        addExtraDBOperation = new AddExtraDBOperation(this);
        extraCost = addExtraDBOperation.getAllExtraCost(identifier);
        extraTv.setText(String.valueOf(extraCost));

        if (meal != 0){
            rate = (double) bazaar / (double) meal;
            mealRate.setText(new DecimalFormat("##.##").format(rate));
        }else {
            mealRate.setText("0");
        }

        memberList.setHasFixedSize(true);
        memberList.setLayoutManager(new LinearLayoutManager(this));
        addMemberDBOperation = new AddMemberDBOperation(this);
        members = new ArrayList<>();
        members = addMemberDBOperation.getMemberList(identifier);
        mealRateAdapter = new MealRateAdapter(this, members, identifier);
        memberList.setAdapter(mealRateAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MealInfo.Preference.ClearPreference(this);
        startActivity(new Intent(this, Main2Activity.class));
    }
}
