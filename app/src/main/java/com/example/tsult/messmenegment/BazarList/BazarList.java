package com.example.tsult.messmenegment.BazarList;

import android.app.Activity;
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
import com.example.tsult.messmenegment.AddBazarPkg.BazaarerDetails;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.MemberDetailsPkg.MemberDetails;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.Info;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.util.ArrayList;

public class BazarList extends Activity {

    private int id;
    private String mName;

    private RecyclerView bazaarList;
    private AddBazaarDBOperation addBazaarDBOperation;
    private ArrayList<Bazaar> bazaars;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private String identifier, mPhone, mEmail;
    private BazaarerDetails bazaarerDetails;
    private boolean check;
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar_list);

        info = MealInfo.Preference.getInfo(this);

        bazaarList = (RecyclerView) findViewById(R.id.ind_bazaar_list);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        mName = intent.getStringExtra("name");
        mPhone = intent.getStringExtra("phone");
        mEmail = intent.getStringExtra("email");
        check = intent.getBooleanExtra("check", false);

        bazaarerDetails = new BazaarerDetails(mName, id, mPhone, mEmail, check);

        if (check){
            identifier = intent.getStringExtra("identifier");
        }else if (info.isSaved()){
            identifier = info.getIdentifier();
        }else
         {
            identifier = MealInfo.getYear()+" - "+MealInfo.getMonth();
        }

        addBazaarDBOperation = new AddBazaarDBOperation(id, this);
        bazaars = new ArrayList<>();
        bazaars = addBazaarDBOperation.getBazaarList(identifier);
        bazaarList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        bazaarList.setLayoutManager(layoutManager);

        mAdapter = new BazaarAdapter(bazaars, this, bazaarerDetails);
        bazaarList.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (check){
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
            intent.putExtra("id",id);
            intent.putExtra("name",mName);
            intent.putExtra("phone",mPhone);
            intent.putExtra("email",mEmail);
            startActivity(intent);
        }*/

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
