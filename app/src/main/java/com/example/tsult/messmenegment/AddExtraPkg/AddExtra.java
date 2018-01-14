package com.example.tsult.messmenegment.AddExtraPkg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddDepositPkg.AddDeposit;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositAdapter;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.Info;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;

import java.util.ArrayList;

public class AddExtra extends AppCompatActivity {

    private RecyclerView extraList;
    private AddExtraAdapter addExtraAdapter;
    private AddExtraDBOperation addExtraDBOperation;
    private ArrayList<Extra>extras;
    private EditText descriptionEt;
    private EditText amountEt;
    private Button addExtraBtn;
    private boolean condition, check;
    private int extraId;
    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_extra);

        extraList = (RecyclerView) findViewById(R.id.extra_list);
        descriptionEt = (EditText) findViewById(R.id.description_et);
        amountEt = (EditText) findViewById(R.id.extra_et);
        addExtraBtn = (Button) findViewById(R.id.add_extra);

        final Intent intent = getIntent();
        condition = intent.getBooleanExtra("status",false);
        check = intent.getBooleanExtra("check", false);
        if (check && !condition){
            identifier = intent.getStringExtra("identifier");
            addExtraBtn.setVisibility(View.GONE);
            descriptionEt.setVisibility(View.GONE);
            amountEt.setVisibility(View.GONE);
        }else if (condition){
            extraId = intent.getIntExtra("extraId",-1);
            String des = intent.getStringExtra("description");
            descriptionEt.setText(des);
            String amount = String.valueOf(intent.getIntExtra("amount",0));
            amountEt.setText(amount);
            addExtraBtn.setText("UPDATE");
            identifier = intent.getStringExtra("identifier");
        }else {
            identifier = MealInfo.getYear()+" - "+MealInfo.getMonth();
        }

        addExtraDBOperation = new AddExtraDBOperation(this);

        createList();
        addExtraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = amountEt.getText().toString();
                String des = descriptionEt.getText().toString();
                if (money.isEmpty()){
                    amountEt.setError(getString(R.string.error_msg));
                }else if (des.isEmpty()){
                    descriptionEt.setError(getString(R.string.error_msg));
                }else {
                    if (condition){
                        Extra extra = new Extra(extraId, des, Integer.parseInt(money), identifier);
                        boolean status1 = addExtraDBOperation.updateExtraList(extra);
                        if (status1){
                            Toast.makeText(AddExtra.this, "Successfully update", Toast.LENGTH_SHORT).show();
                            condition = false;
                            createList();
                            if (check){
                                identifier = intent.getStringExtra("identifier");
                                addExtraBtn.setVisibility(View.GONE);
                                descriptionEt.setVisibility(View.GONE);
                                amountEt.setVisibility(View.GONE);
                            }
                            descriptionEt.setText("");
                            amountEt.setText("");
                            addExtraBtn.setText("ADD");
                        }else {
                            Toast.makeText(AddExtra.this, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Extra extra = new Extra(des,Integer.parseInt(money), identifier);
                        boolean status1 = addExtraDBOperation.addExtraList(extra);
                        if (status1){
                            Toast.makeText(AddExtra.this, "Successfully added", Toast.LENGTH_SHORT).show();
                            createList();
                            descriptionEt.setText("");
                            amountEt.setText("");
                        }else{
                            Toast.makeText(AddExtra.this, "Failed to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void createList(){
        extras = new ArrayList<>();
        extras = addExtraDBOperation.getExtraList(identifier);
        extraList.setHasFixedSize(true);
        extraList.setLayoutManager(new LinearLayoutManager(this));
        addExtraAdapter = new AddExtraAdapter(this, extras, check);
        extraList.setAdapter(addExtraAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (check){
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
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
    }
}


