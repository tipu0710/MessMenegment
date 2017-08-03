package com.example.tsult.messmenegment.AddMealPkg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

/**
 * Created by tsult on 11-Jul-17.
 */

public class ShowMealAdapter extends RecyclerView.Adapter<ShowMealAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Meal>meals;

    public ShowMealAdapter(Context context, ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_meal_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.dateTv.setText(meals.get(position).getDate());
        holder.mealTv.setText(String.valueOf(meals.get(position).getMeal()));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        TextView mealTv;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.date_tv_tv);
            mealTv = (TextView) itemView.findViewById(R.id.meal_tv);
        }
    }
}
