package com.example.tsult.messmenegment.AddMember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.Meal;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFromPrevious extends AppCompatActivity {

    private ArrayList<Member> members;
    private RecyclerView previousList;
    private RecyclerView.Adapter adapter;
    private String previousMonth;
    private AddMemberDBOperation addMemberDBOperation;
    private TextView showText;
    private int day, month, year, checkSize = 0;
    private String identifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_previous);


        identifier = MealInfo.getYear()+" - "+ MealInfo.getMonth();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month =calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        previousList = (RecyclerView) findViewById(R.id.previous_list);

        members = new ArrayList<>();

        addMemberDBOperation = new AddMemberDBOperation(this);
        if (addMemberDBOperation.getAllTables(MealInfo.getYear() +" - "+ MealInfo.getMonth()).size()>0){
            previousMonth = (addMemberDBOperation.getAllTables(MealInfo.getYear() +" - "+ MealInfo.getMonth())).get(0).getName();

            members = addMemberDBOperation.getMemberList(previousMonth);
            previousList.setHasFixedSize(true);
            previousList.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PreviousAdapter();
            previousList.setAdapter(adapter);
        }else {
            showText = (TextView) findViewById(R.id.none_text);
            showText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ok) {
            if (checkSize>0){
                int count =0;
                for (int i = 0; i< members.size(); i++){
                    if (members.get(i).isTrue()){
                        boolean bol = AddPreviousMember(members.get(i).getmName(), members.get(i).getmPhone(), "");
                        if (bol)
                            count++;
                    }
                }
                Toast.makeText(this, count + " members successfully added!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Main2Activity.class));
            }else {
                Toast.makeText(this, "Please select atleast 1 member!", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.ViewHolder>{



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(row);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.name.setText(members.get(position).getmName());
            holder.number.setText(members.get(position).getmPhone());

            holder.select.setChecked(members.get(position).isTrue());
            holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    boolean is;
                    if (members.get(position).isTrue()){
                        is = false;
                        checkSize--;
                    }else {
                        is = true;
                        checkSize++;
                    }
                    Member member = new Member(members.get(position).getmId(), members.get(position).getmName(),
                            members.get(position).getmPhone(), members.get(position).getnEmail(),
                            members.get(position).getIdentifier(), is);
                    members.set(position, member);
                }
            });
        }

        @Override
        public int getItemCount() {
            return members.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            TextView number;
            CheckBox select;

            public ViewHolder(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.contact_name);
                number = (TextView) itemView.findViewById(R.id.contact_number);
                select = (CheckBox) itemView.findViewById(R.id.contact_selection_radio_button);
            }
        }
    }

    private boolean AddPreviousMember(String name, String phone, String email){
        AddMemberDBOperation addMemberDBOperation = new AddMemberDBOperation(this);
        AddMealDBOperation addMealDBOperation = new AddMealDBOperation(this, day + "/"+ (month+1) + "/"+year);
        ArrayList<Member> members = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();
        int mId = 0;
        String mName = null;

        if (email.isEmpty()){
            email = "Not available";
        }
        Member member = new Member(name,phone,email, identifier);

        boolean id = addMemberDBOperation.AddMemberList(member);
        if (id){
            members = addMemberDBOperation.getMemberList(identifier);

            meals = addMealDBOperation.getMeal(identifier);

            if (meals.size()>0){
                for (int i =0; i<members.size(); i++){
                    mId = members.get(i).getmId();
                    mName = members.get(i).getmName();
                }
                for (int i = day; i>=1; i--){
                    Meal meal = new Meal(i, mId, mName, i + "/"+ (month+1) + "/"+year, 0, identifier);
                    addMealDBOperation.AddMeal(meal);
                }
            }else {
                for (int i =0; i<members.size(); i++){
                    mId = members.get(i).getmId();
                    mName = members.get(i).getmName();
                }
                for (int i = day-1; i>=1; i--){
                    Meal meal = new Meal(i, mId, mName, i + "/"+ (month+1) + "/"+year, 0, identifier);
                    addMealDBOperation.AddMeal(meal);
                }
            }


        }

        return id;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddFromPrevious.this, Main2Activity.class));
    }
}
