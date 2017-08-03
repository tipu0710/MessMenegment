package com.example.tsult.messmenegment.BazarList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaar;
import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarAdapter;
import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBOperation;
import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarMember;
import com.example.tsult.messmenegment.AddBazarPkg.Bazaar;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.util.ArrayList;

public class BazarList extends AppCompatActivity {

    private int id;
    private String mName;

    private RecyclerView bazaarList;
    private AddBazaarDBOperation addBazaarDBOperation;
    private ArrayList<Bazaar> bazaars;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private String identifier;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar_list);

        bazaarList = (RecyclerView) findViewById(R.id.ind_bazaar_list);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        mName = intent.getStringExtra("name");
        check = intent.getBooleanExtra("check", false);

        if (check){
            identifier = intent.getStringExtra("identifier");
        }else {
            identifier = MealInfo.getMonthName(MealInfo.getMonth())+" - "+MealInfo.getYear();
        }

        addBazaarDBOperation = new AddBazaarDBOperation(id, this);
        bazaars = new ArrayList<>();
        bazaars = addBazaarDBOperation.getBazaarList(identifier);
        bazaarList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        bazaarList.setLayoutManager(layoutManager);

        mAdapter = new BazaarAdapter(bazaars, this);
        bazaarList.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
