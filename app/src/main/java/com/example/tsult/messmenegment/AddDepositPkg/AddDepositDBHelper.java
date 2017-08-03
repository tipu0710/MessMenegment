package com.example.tsult.messmenegment.AddDepositPkg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

/**
 * Created by tsult on 16-Jul-17.
 */

public class AddDepositDBHelper {

    public static final String TABLE_NAME = "deposit_table";
    public static final String MEMBER_ID = "member_id";
    public static final String DEPOSIT_ID = "deposit_id";
    public static final String MONEY = "amount";
    public static final String DATE = "date_of_deposit";
    public static final String IDENTIFIER = "identifier";

    public static final String QUERY = "create table "+TABLE_NAME+" ("+
            DEPOSIT_ID+" integer primary key, "+
            MEMBER_ID+" integer, "+
            MONEY+" integer, "+
            DATE+" text, "+
            IDENTIFIER+ " text);";
}
