package com.example.tsult.messmenegment.PreviousDataPkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;

public class PreviousData extends AppCompatActivity {


    private RecyclerView previousList;
    private ArrayList<PreviousTable>previousTables;
    private AddMemberDBOperation addMemberDBOperation;
    private PreviousDataAdapter previousDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_data);
        previousList = (RecyclerView) findViewById(R.id.previous_data_list);

        previousList.setHasFixedSize(true);
        previousList.setLayoutManager(new LinearLayoutManager(this));
        previousTables = new ArrayList<>();
        addMemberDBOperation = new AddMemberDBOperation(this);
        previousTables = addMemberDBOperation.getAllTables();
        previousDataAdapter = new PreviousDataAdapter(previousTables, this);
        previousList.setAdapter(previousDataAdapter);
    }
}
