package com.example.tsult.messmenegment.AddMealPkg;

import android.content.Context;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;

/**
 * Created by tsult on 25-Jun-17.
 */

public class AddMealAdapter extends RecyclerView.Adapter<AddMealAdapter.ViewHolder> {

    private ArrayList<Member>members;
    private ArrayList<Meal>meals;
    private Context context;
    private ArrayList<MealDetails> mealDetailses, mealDetailses2;
    private String date, identifier;
    private int day;
    private String collection="";
    private AddMealDBOperation addMealDBOperation;
    private boolean mealStatus = false;
    private boolean memberStatus = false, status= false;

    public AddMealAdapter(ArrayList<Member> members, Context context, String date, int day, String identifier) {
        this.members = members;
        this.context = context;
        this.date = date;
        this.day = day;
        this.identifier = identifier;
        if (members.size()<=0){
            memberStatus = true;
        }

        addMealDBOperation = new AddMealDBOperation(context, date);
        meals = new ArrayList<>();
        meals = addMealDBOperation.getMeal(identifier);
        mealDetailses = new ArrayList<>();
        mealDetailses2 = new ArrayList<>();
        if (meals.size()<=0) {
            for (int i = 0; i < members.size(); i++) {
                mealDetailses.add(i, new MealDetails(members.get(i).getmName(), 0, members.get(i).getmId(), identifier));
                mealDetailses2.add(i, new MealDetails(members.get(i).getmName(), 0, members.get(i).getmId(), identifier));
            }
        }else {
            mealStatus = true;
            for (int i = 0; i <= members.size() - 1; i++) {
                mealDetailses.add(i, new MealDetails(meals.get(i).getmName(), meals.get(i).getMeal(), meals.get(i).getmId(), identifier));
                mealDetailses2.add(i, new MealDetails(meals.get(i).getmName(), meals.get(i).getMeal(), meals.get(i).getmId(), identifier));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == members.size()) ? R.layout.add_meal_button : R.layout.add_meal_member_single_row;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row;
        if(viewType == R.layout.add_meal_member_single_row){
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_meal_member_single_row, parent, false);
        }

        else {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_meal_button, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(position == members.size()) {
            if (memberStatus){
                holder.addBtn.setEnabled(false);
            }
            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    for (int i = 0; i<mealDetailses2.size(); i++){
                        Meal meal = new Meal(day, mealDetailses2.get(i).getmId(), mealDetailses2.get(i).getmName(), date, mealDetailses2.get(i).getMealNumber(), identifier);
                        if (!mealStatus){
                            status = addMealDBOperation.AddMeal(meal);
                        }else {
                            status = addMealDBOperation.UpdateMeal(meal);
                        }
                        if (!status){
                            collection = mealDetailses2.get(i).getmName()+ "\n";
                        }

                    }

                    if (collection.equals("")){
                        Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(context, "Failed to add "+collection, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else {
            holder.mNameTv.setText(members.get(position).getmName());
            /*if (mealStatus){
                holder.addBtn.setText("UPDATE");
            }*/
            if (!mealStatus){
                holder.mCountEt.setText("");
            }else {
                holder.mCountEt.setText(String.valueOf(mealDetailses.get(holder.getAdapterPosition()).getMealNumber()));
            }

            holder.mCountEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    int pos = holder.getAdapterPosition();
                    int meal;
                    String string;
                    string = holder.mCountEt.getText().toString();
                    if (string.isEmpty()){
                        meal = 0;
                    }else {
                        meal = Integer.parseInt(holder.mCountEt.getText().toString());
                    }

                    mealDetailses2.set(pos, new MealDetails(mealDetailses.get(pos).getmName(), meal, mealDetailses2.get(pos).getmId(), identifier));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return members.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTv;
        EditText mCountEt;
        Button addBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            mNameTv = (TextView) itemView.findViewById(R.id.add_meal_member_name_tv);
            mCountEt = (EditText) itemView.findViewById(R.id.add_meal_count_et);
            addBtn = (Button) itemView.findViewById(R.id.meal_adding_button);
        }
    }



}
