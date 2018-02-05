package com.example.tsult.messmenegment.ShowMealRatePkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBOperation;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBHelper;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by tsult on 17-Jul-17.
 */

public class MealInfo {
    private AddMemberDBOperation addMemberDBOperation;
    private AddMealDBOperation addMealDBOperation;
    private AddBazaarDBOperation addBazaarDBOperation;
    private AddDepositDBOperation addDepositDBOperation;
    private AddExtraDBOperation addExtraDBOperation;
    private Context context;
    private int meal, bazaar;
    private double rate;
    private String identifier;
    public static String PRE_NAME = "preference";

    public MealInfo(Context context, String identifier) {
        this.context = context;
        this.identifier = identifier;
        addMealDBOperation = new AddMealDBOperation(context, null);
        addBazaarDBOperation = new AddBazaarDBOperation(0, context);
        addDepositDBOperation = new AddDepositDBOperation(context, 0);
        addMemberDBOperation = new AddMemberDBOperation(context);
        meal = addMealDBOperation.getAllMeal(identifier);
        bazaar = addBazaarDBOperation.getAllBazaarCost(identifier);
        addExtraDBOperation = new AddExtraDBOperation(context);
    }

    public static double getDepositInfo(Context context, int id, String identifier){

        AddDepositDBOperation addDepositDBOperation = new AddDepositDBOperation(context, id);
        int deposit = addDepositDBOperation.getIndividualDeposit(id, identifier);

        AddBazaarDBOperation addBazaarDBOperation = new AddBazaarDBOperation(id, context);
        int bazaarCost = addBazaarDBOperation.getAllBazaarCost(identifier);

        AddMealDBOperation addMealDBOperation = new AddMealDBOperation(context, null);
        int mealNumber = addMealDBOperation.getAllMeal(identifier);
        double rate;
        if (mealNumber != 0){
            rate = Double.parseDouble(new DecimalFormat("##.##").format((double) bazaarCost / (double) mealNumber));
        }else {
            rate = 0.0;
        }

        AddExtraDBOperation addExtraDBOperation = new AddExtraDBOperation(context);
        int extraCost = addExtraDBOperation.getAllExtraCost(identifier);

        AddMemberDBOperation addMemberDBOperation = new AddMemberDBOperation(context);
        int memberNumber = addMemberDBOperation.getMemberList(identifier).size();
        double extraRate =(double) extraCost/(double) memberNumber;

        double cost = ((double) addMealDBOperation.getIndividualMeal(id, identifier)* rate)+ extraRate;

        double rest = (double) deposit - cost;

        return rest;

    }

    public double getMealRate(){
        if (meal != 0){
            rate = Double.parseDouble(new DecimalFormat("##.##").format((double) bazaar / (double) meal));
        }else {
            rate = 0.0;
        }
        return rate;
    }

    public double getExtraRate(){
        int cost = addExtraDBOperation.getAllExtraCost(identifier);
        int member = addMemberDBOperation.getMemberList(identifier).size();
        if (member>0){
            return (double) cost/(double) member;
        }else {
            return 0.0;
        }
    }

    public void deleteMember(int id){
        boolean status = addMemberDBOperation.deleteMember(id, identifier);
        if (status){
            addMealDBOperation.deleteMeal(id, identifier);
            addBazaarDBOperation.deleteBazaar(id, identifier);
            addDepositDBOperation.deleteDeposit(id, identifier);
            Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ShowMember.class);
            intent.putExtra("status",false);
            context.startActivity(intent);

        }else {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getDay(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getMonth(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return 1+calendar.get(Calendar.MONTH);
    }
    public static int getYear(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.get(Calendar.YEAR);
    }
    public static String getMonthName(int month){
        switch (month){
            case 1:{
                return "January";
            }
            case 2:{
                return "February";
            }
            case 3:{
                return "March";
            }
            case 4:{
                return "April";
            }
            case 5:{
                return "May";
            }
            case 6:{
                return "June";
            }
            case 7:{
                return "July";
            }
            case 8:{
                return "August";
            }
            case 9:{
                return "September";
            }
            case 10:{
                return "October";
            }
            case 11:{
                return "November";
            }
            case 12:{
                return "December";
            }
            default:{
                return "Month not match";
            }
        }
    }

    public static class Preference{
        public static void SaveInfo(Context context, String identifier, boolean isSaved){
            SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor saveIt;
            saveIt = preferences.edit();
            saveIt.putString("identifier", identifier);
            saveIt.putBoolean("isSaved", isSaved);
            saveIt.commit();
        }

        public static Info getInfo(Context context){
            SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
            Info info = new Info(preferences.getString("identifier", getMonthName(getMonth())+" - "+ getYear()), preferences.getBoolean("isSaved", false));
            return info;
        }
        public static void ClearPreference(Context context){
            SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor saveIt;
            saveIt = preferences.edit();
            saveIt.clear();
            saveIt.commit();
        }
    }


}
