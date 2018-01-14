package com.example.tsult.messmenegment.AddMember;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

/**
 * Created by Tipu on 31/12/2017.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{

    private ArrayList<AddMemberDetailsInfo> addMemberDetailInfos;
    private Context context;
    private ArrayList<Member> members;
    private boolean check = false;

    public ContactListAdapter(ArrayList<AddMemberDetailsInfo> addMemberDetailInfos, Context context) {
        this.addMemberDetailInfos = addMemberDetailInfos;
        this.context = context;
    }

    public ContactListAdapter(ArrayList<Member> members, Context context, boolean check) {
        this.context = context;
        this.members = members;
        this.check = check;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!check){
            holder.name.setText(addMemberDetailInfos.get(position).getmName());
            holder.number.setText(addMemberDetailInfos.get(position).getmPhone());
        }else {
            holder.name.setText(members.get(position).getmName());
            holder.number.setText(members.get(position).getmPhone());
        }
    }

    @Override
    public int getItemCount() {
        if (!check){
            return addMemberDetailInfos.size();
        }else {
            return members.size();
        }
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
