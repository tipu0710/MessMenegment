package com.example.tsult.messmenegment.AddMember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;

public class AddFromPrevious extends AppCompatActivity {

    private ArrayList<Member> members;
    private RecyclerView previousList;
    private RecyclerView.Adapter adapter;
    private String previousMonth;
    private AddMemberDBOperation addMemberDBOperation;
    private TextView showText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_previous);
        previousList = (RecyclerView) findViewById(R.id.previous_list);

        members = new ArrayList<>();

        addMemberDBOperation = new AddMemberDBOperation(this);
        if (addMemberDBOperation.getAllTables(MealInfo.getYear() +" - "+ MealInfo.getMonth()).size()>0){
            previousMonth = (addMemberDBOperation.getAllTables(MealInfo.getYear() +" - "+ MealInfo.getMonth())).get(0).getName();

            members = addMemberDBOperation.getMemberList(previousMonth);
            previousList.setHasFixedSize(true);
            previousList.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ContactListAdapter(members, this, true);
            previousList.setAdapter(adapter);
        }else {
            showText = (TextView) findViewById(R.id.none_text);
            showText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddFromPrevious.this, Main2Activity.class));
    }
}
