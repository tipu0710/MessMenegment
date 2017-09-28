package com.example.tsult.messmenegment.AddDepositPkg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaar;
import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

/**
 * Created by tsult on 16-Jul-17.
 */

public class AddDepositAdapter extends RecyclerView.Adapter<AddDepositAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Deposit>deposits;

    public AddDepositAdapter(Context context, ArrayList<Deposit> deposits) {
        this.context = context;
        this.deposits = deposits;
    }

    @Override
    public AddDepositAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_list_single_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AddDepositAdapter.ViewHolder holder, final int position) {

        holder.showDateTv.setText(deposits.get(position).getDate());
        holder.showMoneyTv.setText(String.valueOf(deposits.get(position).getMoney()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDeposit.class);
                intent.putExtra("status", true);
                intent.putExtra("id", deposits.get(position).getmId());
                intent.putExtra("date",deposits.get(position).getDate());
                intent.putExtra("money", deposits.get(position).getMoney());
                intent.putExtra("depositId", deposits.get(position).getDepositId());
                intent.putExtra("identifier", deposits.get(position).getIdentifier());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deposits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView showDateTv;
        TextView showMoneyTv;

        public ViewHolder(View itemView) {
            super(itemView);
            showDateTv = (TextView) itemView.findViewById(R.id.show_date);
            showMoneyTv = (TextView) itemView.findViewById(R.id.show_money);
        }
    }
}