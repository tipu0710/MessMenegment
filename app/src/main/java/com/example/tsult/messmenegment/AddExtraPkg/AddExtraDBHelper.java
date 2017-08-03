package com.example.tsult.messmenegment.AddExtraPkg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

/**
 * Created by tsult on 17-Jul-17.
 */

public class AddExtraDBHelper {

    public static final String TABLE_NAME = "extra_table";
    public static final String EXTRA_ID = "extra_id";
    public static final String DESCRIPTION = "description";
    public static final String AMOUNT = "amount";
    public static final String IDENTIFIER = "table_identifier";

    public static final String QUERY = "create table "+TABLE_NAME+" ("+
            EXTRA_ID+ " integer primary key, "+
            DESCRIPTION+" text, "+
            AMOUNT+" integer, "+
            IDENTIFIER+" text);";

}
