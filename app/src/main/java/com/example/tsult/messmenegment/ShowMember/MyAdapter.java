package com.example.tsult.messmenegment.ShowMember;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddDepositPkg.AddDeposit;
import com.example.tsult.messmenegment.AddMember.AddMember;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.MemberDetailsPkg.MemberDetails;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by tsult on 15-Jun-17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private ArrayList<Member> members;
    private Context context;
    private boolean status;
    private int i = 1;

    public MyAdapter(ArrayList<Member> members, Context context, boolean status) {
        this.members = members;
        this.context = context;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_row, parent, false);
        ViewHolder vh = new ViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (status){
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);

            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) holder.mName.getLayoutParams();
            params.gravity = Gravity.CENTER;
            holder.mName.setLayoutParams(params);
        }

        holder.mName.setText(members.get(position).getmName());

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackgroundResource(R.drawable.round4);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.round4);
        }

        holder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status){
                    Intent intent = new Intent(context, MemberDetails.class);
                    String name= members.get(position).getmName();
                    String phone = members.get(position).getmPhone();
                    String email = members.get(position).getnEmail();
                    int id = members.get(position).getmId();
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, AddDeposit.class);
                    String name= members.get(position).getmName();
                    String phone = members.get(position).getmPhone();
                    String email = members.get(position).getnEmail();
                    int id = members.get(position).getmId();
                    intent.putExtra("status", false);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                }
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMember.class);
                String name= members.get(position).getmName();
                String phone = members.get(position).getmPhone();
                String email = members.get(position).getnEmail();
                int id = members.get(position).getmId();
                intent.putExtra("status", true);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder warning = new AlertDialog.Builder(context);
                warning.setTitle("Warning!!");
                warning.setMessage(Html.fromHtml("If you delete any member, you will lose all of these data: <p><ul><li> Member</li><li> Meal</li><li> Bazaar</li><li> Deposit</li></ul><p>Do you really want to delete?"));
                warning.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MealInfo mealInfo = new MealInfo(context, MealInfo.getYear()+" - "+MealInfo.getMonth());
                        mealInfo.deleteMember(members.get(position).getmId());
                    }
                });
                warning.setNegativeButton("No", null);
                warning.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return members.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        ImageView delete, edit;
        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.member_name);
            delete = (ImageView) itemView.findViewById(R.id.call);
            edit = (ImageView) itemView.findViewById(R.id.mail);
        }
    }
}
