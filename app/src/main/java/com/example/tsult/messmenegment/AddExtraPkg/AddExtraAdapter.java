package com.example.tsult.messmenegment.AddExtraPkg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

/**
 * Created by tsult on 17-Jul-17.
 */

public class AddExtraAdapter extends RecyclerView.Adapter<AddExtraAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Extra>extras;
    private boolean check;

    public AddExtraAdapter(Context context, ArrayList<Extra> extras, boolean check) {
        this.context = context;
        this.extras = extras;
        this.check = check;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_extra_single_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.descriptionTv.setText(extras.get(position).getDescription());
        holder.amountTv.setText(String.valueOf(extras.get(position).getAmount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddExtra.class);
                intent.putExtra("status", true);
                intent.putExtra("check",check);
                intent.putExtra("extraId", extras.get(position).geteId());
                intent.putExtra("description",extras.get(position).getDescription());
                intent.putExtra("amount", extras.get(position).getAmount());
                intent.putExtra("identifier", extras.get(position).getIdentifier());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return extras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTv;
        TextView amountTv;
        public ViewHolder(View itemView) {
            super(itemView);
            descriptionTv = (TextView) itemView.findViewById(R.id.description_single_row);
            amountTv = (TextView) itemView.findViewById(R.id.amount_single_row);
        }
    }
}
