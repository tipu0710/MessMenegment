package com.example.tsult.messmenegment.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBOperation;
import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarMember;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtra;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMeal;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMember.AddMember;
import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.PreviousDataPkg.PreviousData;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

public class MainActivity extends Activity{
    private Button addMemberBtn;
    private Button showMemberListBtn;
    private Button addOptionBtn;
    private Button showMealRateBtn, previousDataBtn;
    private Button addBazaarBtn, addMealBtn, addDepositBtn, addExtraBtn;
    private View button_list, layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addMemberBtn = (Button) findViewById(R.id.add_member_btn);
        showMemberListBtn =(Button) findViewById( R.id.show_member_btn);
        addOptionBtn = (Button) findViewById(R.id.add_option_btn);
        showMealRateBtn = (Button) findViewById(R.id.show_meal_rate_btn);
        addBazaarBtn = (Button) findViewById(R.id.add_bazaar_btn);
        addMealBtn = (Button) findViewById(R.id.add_meal_btn);
        addDepositBtn = (Button) findViewById(R.id.add_deposit_btn);
        addExtraBtn = (Button) findViewById(R.id.add_extra_btn);
        previousDataBtn = (Button) findViewById(R.id.show_previous_data_btn);

        button_list = findViewById(R.id.button_view);
        layout = findViewById(R.id.layout);

        MealInfo.Preference.SaveInfo(this, MealInfo.getMonthName(MealInfo.getMonth())+" - "+ MealInfo.getYear(), false);
        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMember.class);
                startActivity(intent);
            }
        });

        showMemberListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMember.class);
                intent.putExtra("status",false);
                startActivity(intent);
            }
        });

        button_list.setVisibility(View.GONE);

        addOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_list.setVisibility(button_list.getVisibility()== View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        addBazaarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBazaarMember.class);
                startActivity(intent);
            }
        });

        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeal.class);
                startActivity(intent);
            }
        });
        addDepositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMember.class);
                intent.putExtra("status", true);
                startActivity(intent);
            }
        });
        addExtraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExtra.class);
                intent.putExtra("status",false);
                startActivity(intent);
            }
        });
        showMealRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMealRate.class);
                startActivity(intent);
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_list.setVisibility(View.GONE);
            }
        });
        previousDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreviousData.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MealInfo.Preference.ClearPreference(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
