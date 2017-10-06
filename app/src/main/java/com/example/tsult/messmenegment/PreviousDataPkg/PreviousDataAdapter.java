package com.example.tsult.messmenegment.PreviousDataPkg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBOperation;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMealRatePkg.ShowMealRate;

import java.util.ArrayList;

/**
 * Created by tsult on 20-Jul-17.
 */

public class PreviousDataAdapter extends RecyclerView.Adapter<PreviousDataAdapter.ViewHolder> {

    private ArrayList<PreviousTable> previousTables;
    private Context context;

    public PreviousDataAdapter(ArrayList<PreviousTable> previousTables, Context context) {
        this.previousTables = previousTables;
        this.context = context;
    }

    @Override
    public PreviousDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_data_single_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PreviousDataAdapter.ViewHolder holder, final int position) {
        holder.tableName.setText(previousTables.get(position).getName());
        final String identifier = previousTables.get(position).getName();
        holder.tableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealInfo.Preference.SaveInfo(context, identifier, true);
                Intent intent = new Intent(context, ShowMealRate.class);
                intent.putExtra("table",identifier);
                intent.putExtra("status", true);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder warning = new AlertDialog.Builder(context);
                warning.setTitle("Warning!!");
                warning.setMessage(Html.fromHtml("Are you sure want to delete "+ identifier +"?"));
                warning.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddBazaarDBOperation addBazaarDBOperation = new AddBazaarDBOperation(0, context);
                        addBazaarDBOperation.deleteAllBazaar(identifier);

                        AddDepositDBOperation addDepositDBOperation = new AddDepositDBOperation(context, 0);
                        addDepositDBOperation.deleteAllDeposit(identifier);

                        AddExtraDBOperation addExtraDBOperation = new AddExtraDBOperation(context);
                        addExtraDBOperation.deleteExtra(identifier);

                        AddMealDBOperation addMealDBOperation = new AddMealDBOperation(context, null);
                        addMealDBOperation.deleteAllMeal(identifier);

                        AddMemberDBOperation addMemberDBOperation = new AddMemberDBOperation(context);
                        addMemberDBOperation.deleteAllMember(identifier);
                        Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, PreviousData.class));

                    }
                });
                warning.setNegativeButton("No", null);
                warning.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return previousTables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button tableName;
        ImageButton delete;
        public ViewHolder(View itemView) {
            super(itemView);
            tableName = (Button) itemView.findViewById(R.id.table_name);
            delete = (ImageButton) itemView.findViewById(R.id.delete_data);
        }
    }
}
