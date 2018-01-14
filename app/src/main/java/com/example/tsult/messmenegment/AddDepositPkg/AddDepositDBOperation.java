package com.example.tsult.messmenegment.AddDepositPkg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tsult.messmenegment.Home.DatabaseCreation;

import java.util.ArrayList;

/**
 * Created by tsult on 16-Jul-17.
 */

public class AddDepositDBOperation {
    private DatabaseCreation databaseCreation;
    private SQLiteDatabase sqLiteDatabase;
    private Deposit deposit;
    private Context context;
    private int mId;

    public AddDepositDBOperation(Context context, int mId) {
        this.context = context;
        this.mId = mId;
        databaseCreation = new DatabaseCreation(context);
    }

    public void open() {
        sqLiteDatabase = databaseCreation.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public boolean addDepositList(Deposit deposit) {
        this.open();

        ContentValues values = new ContentValues();

        values.put(AddDepositDBHelper.MEMBER_ID, deposit.getmId());
        values.put(AddDepositDBHelper.DATE, deposit.getDate());
        values.put(AddDepositDBHelper.MONEY, deposit.getMoney());
        values.put(AddDepositDBHelper.IDENTIFIER, deposit.getIdentifier());

        long id = sqLiteDatabase.insert(AddDepositDBHelper.TABLE_NAME, null, values);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateDepositList(Deposit deposit) {
        this.open();

        ContentValues values = new ContentValues();

        values.put(AddDepositDBHelper.MEMBER_ID, deposit.getmId());
        values.put(AddDepositDBHelper.DATE, deposit.getDate());
        values.put(AddDepositDBHelper.MONEY, deposit.getMoney());
        values.put(AddDepositDBHelper.IDENTIFIER, deposit.getIdentifier());

        long id = sqLiteDatabase.update(AddDepositDBHelper.TABLE_NAME, values, AddDepositDBHelper.MEMBER_ID+ " = "+deposit.getmId()+ " and "+AddDepositDBHelper.DEPOSIT_ID+" = "+ deposit.getDepositId(), null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Deposit> getDepositList(String identifier) {
        ArrayList<Deposit> deposits = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + AddDepositDBHelper.TABLE_NAME + " where " + AddDepositDBHelper.MEMBER_ID + " = "+mId+ " and "+ AddDepositDBHelper.IDENTIFIER+" = '"+identifier+"'", null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for (int i = 1; i <= cursor.getCount(); i++) {
                int depositId = cursor.getInt(cursor.getColumnIndex(AddDepositDBHelper.DEPOSIT_ID));
                int mId = cursor.getInt(cursor.getColumnIndex(AddDepositDBHelper.MEMBER_ID));
                String date = cursor.getString(cursor.getColumnIndex(AddDepositDBHelper.DATE));
                int money = cursor.getInt(cursor.getColumnIndex(AddDepositDBHelper.MONEY));
                String iden = cursor.getString(cursor.getColumnIndex(AddDepositDBHelper.IDENTIFIER));
                cursor.moveToNext();
                deposits.add(new Deposit(depositId, mId, money, date, iden));
            }

        }

        cursor.close();
        this.close();
        return deposits;
    }

    public int getIndividualDeposit(int id, String identifier){
        int allMeal;
        final String COLUMN = "total_meal";
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select sum("+ AddDepositDBHelper.MONEY+") as "+ COLUMN+ " from "+AddDepositDBHelper.TABLE_NAME+ " where "+AddDepositDBHelper.MEMBER_ID+" = "+id+ " and "+ AddDepositDBHelper.IDENTIFIER+" = '"+identifier+"'" ,null);
        cursor.moveToFirst();
        if (cursor.getCount()> 0){
            allMeal = cursor.getInt(cursor.getColumnIndex(COLUMN));
        }else {
            allMeal = 0;
        }

        cursor.close();
        this.close();
        return allMeal;
    }

    public boolean deleteDeposit(int mId, String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddDepositDBHelper.TABLE_NAME, AddDepositDBHelper.MEMBER_ID+" = "+mId+ " and "+ AddDepositDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAllDeposit(String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddDepositDBHelper.TABLE_NAME, AddDepositDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

}