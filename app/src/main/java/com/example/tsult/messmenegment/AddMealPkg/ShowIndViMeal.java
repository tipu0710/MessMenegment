package com.example.tsult.messmenegment.AddMealPkg;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.MemberDetailsPkg.MemberDetails;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.Info;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;

import java.util.ArrayList;

public class ShowIndViMeal extends Activity {

    private RecyclerView mealList;
    private AddMealDBOperation addMealDBOperation;
    private ArrayList<Meal>meals;
    private RecyclerView.Adapter showMealAdapter;

    private TextView nameTv;

    private String mName, identifier, mPhone, mEmail;
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
        mPhone = intent.getStringExtra("phone");
        mEmail = intent.getStringExtra("email");
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

        mealList.setHasFixedSize(true);
        mealList.setLayoutManager(new LinearLayoutManager(this));

        showMealAdapter = new ShowMealAdapter(this, meals);
        mealList.setAdapter(showMealAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (status){
            Info info = MealInfo.Preference.getInfo(this);
            Intent intent = new Intent(this, ShowMealRate.class);
            if (info.isSaved()){
                intent.putExtra("table",info.getIdentifier());
                intent.putExtra("status", true);
            }else {
                intent.putExtra("table",identifier);
            }
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MemberDetails.class);
            intent.putExtra("id",mId);
            intent.putExtra("name",mName);
            intent.putExtra("phone",mPhone);
            intent.putExtra("email",mEmail);
            startActivity(intent);
        }
    }
}
