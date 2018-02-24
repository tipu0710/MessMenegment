package com.example.tsult.messmenegment.Home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaar;
import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarMember;
import com.example.tsult.messmenegment.AddDepositPkg.AddDeposit;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.ShowIndViMeal;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.BazarList.BazarList;
import com.example.tsult.messmenegment.MemberDetailsPkg.MemberDetails;
import com.example.tsult.messmenegment.MemberDetailsPkg.SendMail;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Tipu on 7/12/2017.
 */

public class MemberDetailsListAdapter extends RecyclerView.Adapter<MemberDetailsListAdapter.ViewHolder> {

    private ArrayList<Member> members;
    private Context context;
    private AddDepositDBOperation addDepositDBOperation;

    private Animation fabOpen, fabClose, fabForward, fabBackword;
    private boolean isFabOpen = false;

    public MemberDetailsListAdapter(ArrayList<Member> members, Context context) {
        this.context=context;
        this.members = members;
        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fabBackword = AnimationUtils.loadAnimation(context, R.anim.rotate_backword);
        fabForward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward);

        fabOpen.setDuration(230);
        fabClose.setDuration(230);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_row, parent, false);
        ViewHolder vh = new ViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int id = members.get(position).getmId();
        final String mName = members.get(position).getmName();
        final String mPhone = members.get(position).getmPhone();
        final String mEmail = members.get(position).getnEmail();
        final String identifier = MealInfo.getYear()+" - "+MealInfo.getMonth();

        addDepositDBOperation = new AddDepositDBOperation(context, id);
        AddMealDBOperation addMealDBOperation = new AddMealDBOperation(context, null);
        holder.mName.setText(mName);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + mPhone));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }
                context.startActivity(intent);
            }
        });

        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmail.equals("Not available")){
                    Toast.makeText(context, "Email not available!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent(context, SendMail.class);
                    intent1.putExtra("mail",mEmail);
                    intent1.putExtra("phone", mPhone);
                    intent1.putExtra("name", mName);
                    intent1.putExtra("id", id);
                    context.startActivity(intent1);
                }
            }
        });

        double rate = MealInfo.getDepositInfo(context, id, identifier);
        holder.restOfMoney.setText(Double.toString(Double.parseDouble(new DecimalFormat("##.##").format(rate))));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (position == getItemCount()-1){
            lp.setMargins(20, 0, 20, 140);
            holder.v2.setLayoutParams(lp);
        }else {
            lp.setMargins(20, 0, 20, 20);
            holder.v2.setLayoutParams(lp);
            holder.v2.setPadding(0,5,0,13);
        }

        holder.menuBar.setOnClickListener(new View.OnClickListener() {

            ImageButton meal;
            ImageButton deposit, addDeposit, bazaar, close, addBazaar;
            @Override
            public void onClick(View v) {

                final Dialog customDialog = new Dialog(context, R.style.mytheme);
                Window window = customDialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                customDialog.setContentView(R.layout.custom_window);
                customDialog.show();

                meal = (ImageButton) customDialog.findViewById(R.id.meal_img_btn);
                deposit = (ImageButton) customDialog.findViewById(R.id.deposit_img_btn);
                addDeposit = (ImageButton) customDialog.findViewById(R.id.add_deposit_img_btn);
                bazaar = (ImageButton) customDialog.findViewById(R.id.bazaar_img_btn);
                addBazaar = (ImageButton) customDialog.findViewById(R.id.add_bazaar_img_btn);
                close = (ImageButton) customDialog.findViewById(R.id.close_img_btn);
                isFabOpen = false;
                AnimateMenu();

                meal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context, ShowIndViMeal.class);
                        intent1.putExtra("id",id);
                        intent1.putExtra("name",mName);
                        intent1.putExtra("phone",mPhone);
                        intent1.putExtra("email",mEmail);
                        context.startActivity(intent1);
                        customDialog.dismiss();
                    }
                });

                deposit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(context, AddDeposit.class);
                        intent2.putExtra("id",id);
                        intent2.putExtra("name",mName);
                        intent2.putExtra("phone",mPhone);
                        intent2.putExtra("email",mEmail);
                        intent2.putExtra("memberDCheck", true);
                        context.startActivity(intent2);
                        customDialog.dismiss();
                    }
                });

                addDeposit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AddDeposit.class);
                        intent.putExtra("status", false);
                        intent.putExtra("id",id);
                        intent.putExtra("name",mName);
                        intent.putExtra("phone", mPhone);
                        intent.putExtra("email", mEmail);
                        context.startActivity(intent);
                        customDialog.dismiss();
                    }
                });

                bazaar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnimateMenu();
                        Intent intent = new Intent(context, BazarList.class);
                        intent.putExtra("id",id);
                        intent.putExtra("name",mName);
                        intent.putExtra("phone",mPhone);
                        intent.putExtra("email",mEmail);
                        context.startActivity(intent);
                        customDialog.dismiss();
                    }
                });

                addBazaar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AddBazaar.class);
                        intent.putExtra("condition",false);
                        intent.putExtra("id",id);
                        intent.putExtra("name",mName);
                        intent.putExtra("phone", mPhone);
                        intent.putExtra("email", mEmail);
                        intent.putExtra("identifier", identifier);
                        intent.putExtra("check",true);
                        context.startActivity(intent);
                        customDialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnimateMenu();
                        android.os.Handler handler = new android.os.Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                customDialog.dismiss();
                            }
                        }, 230);

                    }
                });
            }

            private void AnimateMenu(){
                if (isFabOpen) {
                    meal.startAnimation(fabClose);
                    addBazaar.startAnimation(fabClose);
                    bazaar.startAnimation(fabClose);
                    addDeposit.startAnimation(fabClose);
                    deposit.startAnimation(fabClose);
                    close.startAnimation(fabClose);
                    isFabOpen = false;
                } else {
                    meal.startAnimation(fabOpen);
                    addBazaar.startAnimation(fabOpen);
                    bazaar.startAnimation(fabOpen);
                    deposit.startAnimation(fabOpen);
                    addDeposit.startAnimation(fabOpen);
                    close.startAnimation(fabOpen);
                    isFabOpen = true;
                }
            }
        });

        holder.v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName, restOfMoney;
        ImageView call, mail, menuBar;
        View v2;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.member_name);
            call = (ImageView) itemView.findViewById(R.id.call);
            mail = (ImageView) itemView.findViewById(R.id.mail);
            restOfMoney= (TextView) itemView.findViewById(R.id.rest_money);
            menuBar = (ImageView) itemView.findViewById(R.id.menu_bar);
            v2 = itemView.findViewById(R.id.pad);
        }
    }

}
