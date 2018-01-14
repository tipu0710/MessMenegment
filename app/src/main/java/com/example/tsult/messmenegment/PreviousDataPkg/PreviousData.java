package com.example.tsult.messmenegment.PreviousDataPkg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;

public class PreviousData extends AppCompatActivity {


    private RecyclerView previousList;
    private ArrayList<PreviousTable>previousTables;
    private AddMemberDBOperation addMemberDBOperation;
    private PreviousDataAdapter previousDataAdapter;
    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_data);
        previousList = (RecyclerView) findViewById(R.id.previous_data_list);


        identifier =MealInfo.getYear() +" - "+ MealInfo.getMonth();
        previousList.setHasFixedSize(true);
        previousList.setLayoutManager(new LinearLayoutManager(this));
        previousTables = new ArrayList<>();
        addMemberDBOperation = new AddMemberDBOperation(this);
        previousTables = addMemberDBOperation.getAllTables(identifier);
        previousDataAdapter = new PreviousDataAdapter(previousTables, this);
        previousList.setAdapter(previousDataAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
