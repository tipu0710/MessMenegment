package com.example.tsult.messmenegment.AddBazarPkg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tsult.messmenegment.Home.DatabaseCreation;

import java.util.ArrayList;

/**
 * Created by tsult on 22-Jun-17.
 */

public class AddBazaarDBOperation {
    private DatabaseCreation databaseCreation;
    private SQLiteDatabase sqLiteDatabase;
    private Bazaar bazaar;
    private Context context;
    private int mId;

    public AddBazaarDBOperation(int mId, Context context) {
        this.context = context;
        databaseCreation = new DatabaseCreation(context);
        this.mId = mId;
    }

    public void open(){
        sqLiteDatabase = databaseCreation.getWritableDatabase();
    }
    public void close(){
        sqLiteDatabase.close();
    }

    public boolean AddBazaarList(Bazaar bazaar){
        this.open();
        ContentValues values = new ContentValues();

        values.put(AddBazaarDBHelper.BAZAAR_MEMBER_ID,bazaar.getmId());
        values.put(AddBazaarDBHelper.BAZAAR_MEMBER_NAME, bazaar.getmName());
        values.put(AddBazaarDBHelper.BAZAAR_DATE, bazaar.getDate());
        values.put(AddBazaarDBHelper.BAZAAR_COST, bazaar.getCost());
        values.put(AddBazaarDBHelper.BAZAAR_MEMO, bazaar.getMemo());
        values.put(AddBazaarDBHelper.IDENTIFIER, bazaar.getIdentifier());

        long id = sqLiteDatabase.insert(AddBazaarDBHelper.BAZAAR_TABLE, null, values);
        this.close();

        if (id>0){
            return true;
        }
        else {
            return false;
        }
    }
public boolean UpdateBazaarList(Bazaar bazaar){
        this.open();
        ContentValues values = new ContentValues();

        values.put(AddBazaarDBHelper.BAZAAR_ID, bazaar.getbId());
        values.put(AddBazaarDBHelper.BAZAAR_MEMBER_ID,bazaar.getmId());
        values.put(AddBazaarDBHelper.BAZAAR_MEMBER_NAME, bazaar.getmName());
        values.put(AddBazaarDBHelper.BAZAAR_DATE, bazaar.getDate());
        values.put(AddBazaarDBHelper.BAZAAR_COST, bazaar.getCost());
        values.put(AddBazaarDBHelper.BAZAAR_MEMO, bazaar.getMemo());
        values.put(AddBazaarDBHelper.IDENTIFIER, bazaar.getIdentifier());

        long id = sqLiteDatabase.update(AddBazaarDBHelper.BAZAAR_TABLE, values, AddBazaarDBHelper.BAZAAR_ID+" = "+ bazaar.getbId()+" and "+ AddBazaarDBHelper.BAZAAR_MEMBER_ID+ " = "+bazaar.getmId(), null);
        this.close();

        if (id>0){
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Bazaar> getBazaarList(String identifier){
        ArrayList<Bazaar> bazaars = new ArrayList<>();
        this.open();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ AddBazaarDBHelper.BAZAAR_TABLE + " where "+ AddBazaarDBHelper.BAZAAR_MEMBER_ID + "= "+ mId+ " and "+ AddBazaarDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            for (int i =1; i<=cursor.getCount(); i++){
                int bId = cursor.getInt(cursor.getColumnIndex(AddBazaarDBHelper.BAZAAR_ID));
                int mId = cursor.getInt(cursor.getColumnIndex(AddBazaarDBHelper.BAZAAR_MEMBER_ID));
                String mName = cursor.getString(cursor.getColumnIndex(AddBazaarDBHelper.BAZAAR_MEMBER_NAME));
                String date = cursor.getString(cursor.getColumnIndex(AddBazaarDBHelper.BAZAAR_DATE));
                int cost = cursor.getInt(cursor.getColumnIndex(AddBazaarDBHelper.BAZAAR_COST));
                byte[] memo = cursor.getBlob(cursor.getColumnIndex(AddBazaarDBHelper.BAZAAR_MEMO));
                String ident = cursor.getString(cursor.getColumnIndex(AddBazaarDBHelper.IDENTIFIER));
                cursor.moveToNext();
                bazaars.add(new Bazaar(bId, mId,mName,date,cost,memo, ident));
            }

        }

        cursor.close();
        this.close();
        return bazaars;
    }

    public int getAllBazaarCost(String identifier){
        int allMeal;
        final String COLUMN = "total_bazaar_cost";
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select sum("+ AddBazaarDBHelper.BAZAAR_COST+") as "+ COLUMN+ " from "+AddBazaarDBHelper.BAZAAR_TABLE+" where "+ AddBazaarDBHelper.IDENTIFIER+" = '"+identifier+"'",null);
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

    public boolean deleteBazaar(int mId, String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddBazaarDBHelper.BAZAAR_TABLE, AddBazaarDBHelper.BAZAAR_MEMBER_ID +" = "+ mId+ " and "+ AddBazaarDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id>0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteAllBazaar(String identifier){
        this.open();
        long id = sqLiteDatabase.delete(AddBazaarDBHelper.BAZAAR_TABLE, AddBazaarDBHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id>0){
            return true;
        }
        else {
            return false;
        }
    }

}
