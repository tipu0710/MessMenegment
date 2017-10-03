package com.example.tsult.messmenegment.AddBazarPkg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

/**
 * Created by tsult on 22-Jun-17.
 */

public class AddBazaarAdapter extends RecyclerView.Adapter<AddBazaarAdapter.ViewHolder> {

    private ArrayList<Member> members;
    private Context context;
    private int i = 1;

    public AddBazaarAdapter(ArrayList<Member> members, Context context) {
        this.members = members;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bazaar_single_row_2, parent, false);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mNameTv.setText(members.get(position).getmName());
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackgroundResource(R.drawable.round4);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.round4);
        }
        holder.mNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddBazaar.class);
                String name= members.get(position).getmName();
                String phone = members.get(position).getmPhone();
                String email = members.get(position).getnEmail();
                int id = members.get(position).getmId();
                String ident = members.get(position).getIdentifier();
                intent.putExtra("condition",false);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("identifier", ident);
                intent.putExtra("check",true);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTv;
        public ViewHolder(View itemView) {
            super(itemView);
            mNameTv = (TextView) itemView.findViewById(R.id.bazaar_member_name);
        }
    }
}
