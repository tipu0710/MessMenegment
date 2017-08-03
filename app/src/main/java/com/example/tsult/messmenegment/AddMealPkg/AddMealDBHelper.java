package com.example.tsult.messmenegment.AddMealPkg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

/**
 * Created by tsult on 07-Jul-17.
 */

public class AddMealDBHelper {

    public static final String TABLE_NAME = "meal_table";
    public static final String MEAL_ID = "meal_id";
    public static final String MEMBER_ID = "member_id";
    public static final String MEAL_DAY = "day";
    public static final String MEMBER_NAME = "member_name";
    public static final String DATE = "meal_date";
    public static final String MEAL = "meal_number";
    public static final String IDENTIFIER = "identifier";

    public static final String TABLE_QUERY = "create table "+ TABLE_NAME+ " ("+
            MEAL_ID+ " integer , "+
            MEMBER_ID+ " integer, "+
            MEMBER_NAME +" text, "+
            MEAL_DAY + " integer, "+
            DATE+ " text, "+
            MEAL+ " integer, "+
            IDENTIFIER+" text);";
}
