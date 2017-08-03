package com.example.tsult.messmenegment.AddExtraPkg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tsult.messmenegment.AddDepositPkg.Deposit;
import com.example.tsult.messmenegment.Home.DatabaseCreation;

import java.util.ArrayList;

/**
 * Created by tsult on 17-Jul-17.
 */

public class AddExtraDBOperation {
    private DatabaseCreation databaseCreation;
    private Extra extra;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public AddExtraDBOperation(Context context) {
        this.context = context;
        databaseCreation = new DatabaseCreation(context);
    }

    public void open() {
        sqLiteDatabase = databaseCreation.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }
    public boolean addExtraList(Extra extra) {
        this.open();

        ContentValues values = new ContentValues();

        values.put(AddExtraDBHelper.DESCRIPTION, extra.getDescription());
        values.put(AddExtraDBHelper.AMOUNT, extra.getAmount());
        values.put(AddExtraDBHelper.IDENTIFIER, extra.getIdentifier());

        long id = sqLiteDatabase.insert(AddExtraDBHelper.TABLE_NAME, null, values);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateExtraList(Extra extra) {
        this.open();

        ContentValues values = new ContentValues();

        values.put(AddExtraDBHelper.DESCRIPTION, extra.getDescription());
        values.put(AddExtraDBHelper.AMOUNT, extra.getAmount());
        values.put(AddExtraDBHelper.IDENTIFIER, extra.getIdentifier());

        long id = sqLiteDatabase.update(AddExtraDBHelper.TABLE_NAME, values, AddExtraDBHelper.EXTRA_ID+ " = "+extra.geteId(), null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Extra> getExtraList(String identifier) {
        ArrayList<Extra> extras = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+AddExtraDBHelper.TABLE_NAME +" where "+ AddExtraDBHelper.IDENTIFIER+ " = '"+ identifier+"'", null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for (int i = 1; i <= cursor.getCount(); i++) {
                int extraId = cursor.getInt(cursor.getColumnIndex(AddExtraDBHelper.EXTRA_ID));
                String description = cursor.getString(cursor.getColumnIndex(AddExtraDBHelper.DESCRIPTION));
                int money = cursor.getInt(cursor.getColumnIndex(AddExtraDBHelper.AMOUNT));
                cursor.moveToNext();
                extras.add(new Extra(extraId, description, money, identifier));
            }

        }

        cursor.close();
        this.close();
        return extras;
    }

    public int getAllExtraCost(String identifier){
        int allCost;
        final String COLUMN = "total_extra_cost";
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select sum("+ AddExtraDBHelper.AMOUNT+") as "+ COLUMN+ " from "+AddExtraDBHelper.TABLE_NAME+  " where "+ AddExtraDBHelper.IDENTIFIER+" = '"+identifier+"'",null);
        cursor.moveToFirst();
        allCost = cursor.getInt(cursor.getColumnIndex(COLUMN));
        if (cursor == null){
            cursor.close();
            this.close();
            return 0;
        }else {
            cursor.close();
            this.close();
            return allCost;
        }
    }

    public boolean deleteExtra(String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddExtraDBHelper.TABLE_NAME, AddExtraDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }
}
