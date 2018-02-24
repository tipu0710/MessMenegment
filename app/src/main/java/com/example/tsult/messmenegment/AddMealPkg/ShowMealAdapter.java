package com.example.tsult.messmenegment.AddMealPkg;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tsult.messmenegment.R;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by tsult on 11-Jul-17.
 */

public class ShowMealAdapter extends RecyclerView.Adapter<ShowMealAdapter.ViewHolder> {

    private Context context;
    private int i = 0;
    private ArrayList<Meal>meals;
    private Integer[] colors = {
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6
    };

    private SecureRandom random;

    public ShowMealAdapter(Context context, ArrayList<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_meal_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        random = new SecureRandom();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (i == 6){
            i = 0;
        }
        holder.dateTv.setText(meals.get(position).getDate());
        holder.dateTv.setTextColor(ContextCompat.getColor(context, colors[i]));
        holder.mealTv.setText(String.valueOf(meals.get(position).getMeal()));
        holder.mealTv.setTextColor(ContextCompat.getColor(context, colors[i]));
        i++;
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
