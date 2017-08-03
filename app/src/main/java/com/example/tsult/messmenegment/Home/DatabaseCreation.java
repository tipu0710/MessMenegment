package com.example.tsult.messmenegment.Home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBHelper;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBHelper;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBHelper;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBHelper;
import com.example.tsult.messmenegment.AddMember.AddMemberDatabaseHelper;

/**
 * Created by tsult on 01-Aug-17.
 */

public class DatabaseCreation extends SQLiteOpenHelper {

    public static final String DB_NAME = "member_db";
    public static final int DB_VERSION = 1;

    public DatabaseCreation(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AddBazaarDBHelper.BAZAAR_QUERY);
        db.execSQL(AddDepositDBHelper.QUERY);
        db.execSQL(AddExtraDBHelper.QUERY);
        db.execSQL(AddMemberDatabaseHelper.TABLE_QUERY);
        db.execSQL(AddMealDBHelper.TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
