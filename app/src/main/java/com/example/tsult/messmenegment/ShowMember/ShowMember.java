package com.example.tsult.messmenegment.ShowMember;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;

public class ShowMember extends Activity {

    private TextView textView;
    private RecyclerView mRecyclerView;
    private AddMemberDBOperation addMemberDBOperation;
    private ArrayList<Member> members;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member);

        textView = (TextView) findViewById(R.id.member);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textView = (TextView) findViewById(R.id.member);

        addMemberDBOperation = new AddMemberDBOperation(this);
        members = new ArrayList<>();
        Intent intent = getIntent();
        status = intent.getBooleanExtra("status",false);
        if (status){
            textView.setVisibility(View.GONE);
        }else {
            textView.setText(Html.fromHtml("<u>Member List</u>"));
        }

        members = addMemberDBOperation.getMemberList(MealInfo.getYear()+" - "+MealInfo.getMonth());
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(members,this,status);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Main2Activity.class));
    }
}
