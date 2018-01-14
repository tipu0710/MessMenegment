package com.example.tsult.messmenegment.AddMealPkg;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarAdapter;
import com.example.tsult.messmenegment.AddBazarPkg.Bazaar;
import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddMeal extends AppCompatActivity {

    private RecyclerView addMealMemberList;
    private AddMemberDBOperation addMemberDBOperation;
    private ArrayList<Member> members;
    private RecyclerView.Adapter mealAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button datePickerBtn;
    int year,month,day;

    private String showDate, identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        addMealMemberList = (RecyclerView) findViewById(R.id.add_meal_member_list);
        datePickerBtn = (Button) findViewById(R.id.date_picker_btn);
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());

        year = calendar.get(Calendar.YEAR);
        month =calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate = day + "/"+ (month+1) + "/"+year;
        datePickerBtn.setText(showDate);
        identifier =MealInfo.getYear()+" - "+ MealInfo.getMonth();
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeal.this,dateListener,year,month,day);
                datePickerDialog.show();
            }
            private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int myear, int mmonth, int dayOfMonth) {
                    day = dayOfMonth;
                    showDate = dayOfMonth + "/"+ (mmonth+1) + "/"+myear;
                    datePickerBtn.setText(showDate);
                    showList();
                }
            };
        });
        showList();
    }

    private void showList(){
        addMemberDBOperation = new AddMemberDBOperation(this);
        members = new ArrayList<>();

        members = addMemberDBOperation.getMemberList(identifier);
        mealAdapter = new AddMealAdapter(members, this, showDate, day, identifier);

        layoutManager = new LinearLayoutManager(this);
        addMealMemberList.setHasFixedSize(true);
        addMealMemberList.setLayoutManager(layoutManager);

        addMealMemberList.setAdapter(mealAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
