package com.example.tsult.messmenegment.AddMealPkg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;

public class ShowIndViMeal extends AppCompatActivity {

    private RecyclerView mealList;
    private AddMealDBOperation addMealDBOperation;
    private ArrayList<Meal>meals;
    private RecyclerView.Adapter showMealAdapter;

    private TextView nameTv;

    private String mName, identifier;
    private int mId;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ind_vi_meal);

        mealList = (RecyclerView) findViewById(R.id.ind_meal_list);
        nameTv = (TextView) findViewById(R.id.ind_member_name);

        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mId = intent.getIntExtra("id",-1);
        status = intent.getBooleanExtra("status", false);
        if (status){
            identifier = intent.getStringExtra("identifier");
        }else {
            identifier = MealInfo.getMonthName(MealInfo.getMonth())+" - "+MealInfo.getYear();
        }
        nameTv.setText(mName);

        addMealDBOperation = new AddMealDBOperation(this, null);
        meals = new ArrayList<>();
        meals = addMealDBOperation.getIndMeal(mId, identifier);
        for (int i=0; i<meals.size(); i++){
        }
        mealList.setHasFixedSize(true);
        mealList.setLayoutManager(new LinearLayoutManager(this));

        showMealAdapter = new ShowMealAdapter(this, meals);
        mealList.setAdapter(showMealAdapter);

    }
}
