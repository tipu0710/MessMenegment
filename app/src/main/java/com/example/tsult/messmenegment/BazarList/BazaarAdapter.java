package com.example.tsult.messmenegment.BazarList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaar;
import com.example.tsult.messmenegment.AddBazarPkg.Bazaar;
import com.example.tsult.messmenegment.AddBazarPkg.BazaarerDetails;
import com.example.tsult.messmenegment.R;

import java.util.ArrayList;

/**
 * Created by tsult on 23-Jun-17.
 */

class BazaarAdapter extends RecyclerView.Adapter<BazaarAdapter.ViewHolder>{


    private ArrayList<Bazaar>bazaars;
    private Context context;
    private BazaarerDetails bazaarerDetails;

    public BazaarAdapter(ArrayList<Bazaar> bazaars, Context context, BazaarerDetails bazaarerDetails) {
        this.bazaars = bazaars;
        this.context = context;
        this.bazaarerDetails = bazaarerDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_bazaar_list_single_row, parent, false);
        ViewHolder holder = new ViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.dateTV.setText(bazaars.get(position).getDate());
        holder.costTV.setText(String.valueOf(bazaars.get(position).getCost()));
        Bitmap bitmap = AddBazaar.Converter.getImage(bazaars.get(position).getMemo());
        holder.memoIV.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddBazaar.class);
                intent.putExtra("condition",true);
                intent.putExtra("id",bazaars.get(position).getmId());
                intent.putExtra("bazaarId",bazaars.get(position).getbId());
                intent.putExtra("name", bazaars.get(position).getmName());
                intent.putExtra("date", bazaars.get(position).getDate());
                intent.putExtra("cost", bazaars.get(position).getCost());
                intent.putExtra("image", bazaars.get(position).getMemo());
                intent.putExtra("phone", bazaarerDetails.getmPhone());
                intent.putExtra("email",bazaarerDetails.getmEmail());
                intent.putExtra("identifier",bazaars.get(position).getIdentifier());
                intent.putExtra("status", bazaarerDetails.isCheck());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bazaars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV, costTV;
        ImageView memoIV;
        public ViewHolder(View itemView) {
            super(itemView);
            dateTV = (TextView) itemView.findViewById(R.id.date_tv);
            costTV = (TextView) itemView.findViewById(R.id.cost_tv);
            memoIV = (ImageView) itemView.findViewById(R.id.memo_iv);
        }
    }
}
