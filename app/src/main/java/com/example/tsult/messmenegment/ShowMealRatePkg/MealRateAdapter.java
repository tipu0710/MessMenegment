package com.example.tsult.messmenegment.ShowMealRatePkg;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaar;
import com.example.tsult.messmenegment.AddDepositPkg.AddDeposit;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtra;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.ShowIndViMeal;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.BazarList.BazarList;
import com.example.tsult.messmenegment.MemberDetailsPkg.MemberDetails;
import com.example.tsult.messmenegment.PreviousDataPkg.PreviousData;
import com.example.tsult.messmenegment.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by tsult on 21-Jul-17.
 */

public class MealRateAdapter extends RecyclerView.Adapter<MealRateAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Member>members;
    private String identifier;
    private AddMealDBOperation addMealDBOperation;
    private AddDepositDBOperation addDepositDBOperation;

    public MealRateAdapter(Context context, ArrayList<Member> members, String identifier) {
        this.context = context;
        this.members = members;
        this.identifier = identifier;
        addMealDBOperation = new AddMealDBOperation(context, null);
        addDepositDBOperation = new AddDepositDBOperation(context, 0);
    }

    @Override
    public MealRateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_rate_single_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MealRateAdapter.ViewHolder holder, final int position) {
        holder.nameTv.setText(members.get(position).getmName());
        double rate = MealInfo.getDepositInfo(context, members.get(position).getmId(), identifier);
        holder.costTv.setText(new DecimalFormat("##.##").format(rate));
        int totalMeal = addMealDBOperation.getIndividualMeal(members.get(position).getmId(), identifier);
        holder.mealTv.setText(String.valueOf(totalMeal));
        int totalDeposit = addDepositDBOperation.getIndividualDeposit(members.get(position).getmId(), identifier);
        holder.depositTv.setText(String.valueOf(totalDeposit));
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.mytheme);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.custom_dialoge);

                Button meal = (Button) dialog.findViewById(R.id.show_meal_btn);
                Button deposit = (Button) dialog.findViewById(R.id.show_deposit_btn);
                Button bazaar = (Button) dialog.findViewById(R.id.show_bazaar_btn);
                Button extra = (Button) dialog.findViewById(R.id.show_extra_btn);
                Button details = (Button) dialog.findViewById(R.id.show_details_btn);

                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MealInfo.Preference.SaveInfo(context, identifier, true);
                        Intent intent1 = new Intent(context, MemberDetails.class);
                        intent1.putExtra("id", members.get(position).getmId());
                        intent1.putExtra("phone", members.get(position).getmPhone());
                        intent1.putExtra("email", members.get(position).getnEmail());
                        intent1.putExtra("name", members.get(position).getmName());
                        context.startActivity(intent1);
                        dialog.dismiss();
                    }
                });

                meal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context, ShowIndViMeal.class);
                        intent1.putExtra("id",members.get(position).getmId());
                        intent1.putExtra("name",members.get(position).getmName());
                        intent1.putExtra("identifier", members.get(position).getIdentifier());
                        intent1.putExtra("status", true);
                        context.startActivity(intent1);
                        dialog.dismiss();
                    }
                });

                deposit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context, AddDeposit.class);
                        intent1.putExtra("id",members.get(position).getmId());
                        intent1.putExtra("name",members.get(position).getmName());
                        intent1.putExtra("identifier", members.get(position).getIdentifier());
                        intent1.putExtra("check", true);
                        context.startActivity(intent1);
                        dialog.dismiss();
                    }
                });

                bazaar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context, BazarList.class);
                        intent1.putExtra("id",members.get(position).getmId());
                        intent1.putExtra("name",members.get(position).getmName());
                        intent1.putExtra("identifier", members.get(position).getIdentifier());
                        intent1.putExtra("check", true);
                        context.startActivity(intent1);
                        dialog.dismiss();
                    }
                });

                extra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context, AddExtra.class);
                        intent1.putExtra("identifier", members.get(position).getIdentifier());
                        intent1.putExtra("check", true);
                        context.startActivity(intent1);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv,mealTv, depositTv, costTv;
        ImageView details;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.display_name);
            costTv = (TextView) itemView.findViewById(R.id.display_cost);
            mealTv = (TextView) itemView.findViewById(R.id.total_meal_show);
            depositTv = (TextView) itemView.findViewById(R.id.ind_deposit_show);
            details = (ImageView) itemView.findViewById(R.id.details);
        }
    }
}
