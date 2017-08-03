package com.example.tsult.messmenegment.AddBazarPkg;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMember.MyAdapter;

import java.util.ArrayList;

public class AddBazaarMember extends Activity {

    private RecyclerView memberList;
    private AddMemberDBOperation addMemberDBOperation;
    private ArrayList<Member> members;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bazar);
        memberList = (RecyclerView) findViewById(R.id.member_list_bazaar);

        addMemberDBOperation = new AddMemberDBOperation(this);
        members = new ArrayList<>();
        members = addMemberDBOperation.getMemberList(MealInfo.getMonthName(MealInfo.getMonth())+" - "+MealInfo.getYear());
        memberList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        memberList.setLayoutManager(mLayoutManager);

        mAdapter = new AddBazaarAdapter(members,this);
        memberList.setAdapter(mAdapter);

    }
}
