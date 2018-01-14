package com.example.tsult.messmenegment.AddMealPkg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.tsult.messmenegment.Home.DatabaseCreation;

import java.util.ArrayList;

/**
 * Created by tsult on 07-Jul-17.
 */

public class AddMealDBOperation {

    private DatabaseCreation databaseCreation;
    private Meal meal;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private String date;

    public AddMealDBOperation(Context context, String date) {
        this.context = context;
        this.date = date;
        databaseCreation = new DatabaseCreation(context);
    }

    public void open() {
        sqLiteDatabase = databaseCreation.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public boolean AddMeal(Meal meal) {
        this.open();
        ContentValues values = new ContentValues();

        values.put(AddMealDBHelper.MEMBER_ID, meal.getmId());
        values.put(AddMealDBHelper.MEMBER_NAME, meal.getmName());
        values.put(AddMealDBHelper.MEAL_DAY, meal.getDay());
        values.put(AddMealDBHelper.DATE, meal.getDate());
        values.put(AddMealDBHelper.MEAL, meal.getMeal());
        values.put(AddMealDBHelper.IDENTIFIER, meal.getIdentifier());
        long id = sqLiteDatabase.insert(AddMealDBHelper.TABLE_NAME, null, values);
        this.close();

        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean UpdateMeal(Meal meal) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(AddMealDBHelper.MEMBER_ID, meal.getmId());
        values.put(AddMealDBHelper.MEMBER_NAME, meal.getmName());
        values.put(AddMealDBHelper.MEAL_DAY, meal.getDay());
        values.put(AddMealDBHelper.DATE, meal.getDate());
        values.put(AddMealDBHelper.MEAL, meal.getMeal());
        values.put(AddMealDBHelper.IDENTIFIER, meal.getIdentifier());
        long id = sqLiteDatabase.update(AddMealDBHelper.TABLE_NAME, values, AddMealDBHelper.MEMBER_ID+" =? and "+AddMealDBHelper.DATE+" =? and "+AddMealDBHelper.IDENTIFIER + "=?", new String[]{Integer.toString(meal.getmId()), meal.getDate(), meal.getIdentifier()});
        this.close();

        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Meal> getMeal(String identifier) {
        ArrayList<Meal> meals = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+AddMealDBHelper.TABLE_NAME+ " where "+ AddMealDBHelper.DATE + " = '"+date+"'"+ " and "+ AddMealDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            for (int i = 1; i <= cursor.getCount(); i++) {
                int mId = cursor.getInt(cursor.getColumnIndex(AddMealDBHelper.MEMBER_ID));
                String mName = cursor.getString(cursor.getColumnIndex(AddMealDBHelper.MEMBER_NAME));
                String date = cursor.getString(cursor.getColumnIndex(AddMealDBHelper.DATE));
                int meal = cursor.getInt(cursor.getColumnIndex(AddMealDBHelper.MEAL));
                int day = cursor.getInt(cursor.getColumnIndex(AddMealDBHelper.MEAL_DAY));
                cursor.moveToNext();
                meals.add(new Meal(day, mId, mName, date, meal, identifier));
            }
        }
        cursor.close();
        this.close();
        return meals;
    }

    public ArrayList<Meal> getIndMeal(int id, String identifier) {
        ArrayList<Meal> meals = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+AddMealDBHelper.TABLE_NAME+ " where "+ AddMealDBHelper.MEMBER_ID + " = "+id+" and "+ AddMealDBHelper.IDENTIFIER+" = '"+identifier+"' ORDER BY "+AddMealDBHelper.MEAL_DAY+" ASC", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            for (int i = 1; i <= cursor.getCount(); i++) {
                int day = cursor.getInt(cursor.getColumnIndex(AddMealDBHelper.MEAL_DAY));
                int mId = cursor.getInt(cursor.getColumnIndex(AddMealDBHelper.MEMBER_ID));
                String mName = cursor.getString(cursor.getColumnIndex(AddMealDBHelper.MEMBER_NAME));
                String date = cursor.getString(cursor.getColumnIndex(AddMealDBHelper.DATE));
                int meal = cursor.getInt(cursor.getColumnIndex(AddMealDBHelper.MEAL));
                cursor.moveToNext();
                meals.add(new Meal(day, mId, mName, date, meal, identifier));
            }
        }
        cursor.close();
        this.close();
        return meals;
    }

    public int getAllMeal(String identifier){
        int allMeal;
        final String COLUMN = "total_meal";
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select sum("+ AddMealDBHelper.MEAL+") as "+ COLUMN+ " from "+AddMealDBHelper.TABLE_NAME+ " where "+AddMealDBHelper.IDENTIFIER+ " = '"+identifier+"'",null);
        cursor.moveToFirst();
        allMeal = cursor.getInt(cursor.getColumnIndex(COLUMN));
        if (cursor == null){
            cursor.close();
            this.close();
            return 0;
        }else {
            cursor.close();
            this.close();
            return allMeal;
        }
    }

    public int getIndividualMeal(int id, String identifier){
        int allMeal;
        final String COLUMN = "total_meal";
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select sum("+ AddMealDBHelper.MEAL+") as "+ COLUMN+ " from "+AddMealDBHelper.TABLE_NAME+ " where "+AddMealDBHelper.MEMBER_ID+" = "+id+ " and "+ AddMealDBHelper.IDENTIFIER+" = '"+identifier+"'" ,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            allMeal = cursor.getInt(cursor.getColumnIndex(COLUMN));
        }else {
            allMeal = 0;
        }

        cursor.close();
        this.close();
        return allMeal;
    }

    public boolean deleteMeal(int mId, String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddMealDBHelper.TABLE_NAME, AddMealDBHelper.MEMBER_ID +" = "+mId+ " and "+AddMealDBHelper.IDENTIFIER+ " = '"+identifier+"'", null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean deleteAllMeal(String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddMealDBHelper.TABLE_NAME, AddMealDBHelper.IDENTIFIER+ " = '"+identifier+"'", null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

}