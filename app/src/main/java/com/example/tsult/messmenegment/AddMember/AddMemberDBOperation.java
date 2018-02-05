package com.example.tsult.messmenegment.AddMember;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarDBOperation;
import com.example.tsult.messmenegment.AddDepositPkg.AddDepositDBOperation;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBHelper;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtraDBOperation;
import com.example.tsult.messmenegment.AddMealPkg.AddMealDBOperation;
import com.example.tsult.messmenegment.Home.DatabaseCreation;
import com.example.tsult.messmenegment.PreviousDataPkg.PreviousData;
import com.example.tsult.messmenegment.PreviousDataPkg.PreviousTable;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by tsult on 14-Jun-17.
 */

public class AddMemberDBOperation {

    private DatabaseCreation databaseCreation;
    private SQLiteDatabase sqLiteDatabase;
    private Member member;
    private Context context;
    public AddMemberDBOperation(Context context) {
        this.context = context;
        databaseCreation = new DatabaseCreation(context);
    }

    public void open(){
        sqLiteDatabase = databaseCreation.getWritableDatabase();
    }
    public void close(){
        sqLiteDatabase.close();
    }

    public boolean AddMemberList(Member member){
        this.open();
        ContentValues values = new ContentValues();

        values.put(AddMemberDatabaseHelper.MEMBER_NAME,member.getmName());
        values.put(AddMemberDatabaseHelper.MEMBER_PHONE, member.getmPhone());
        values.put(AddMemberDatabaseHelper.MEMBER_EMAIL, member.getnEmail());
        values.put(AddMemberDatabaseHelper.IDENTIFIER, member.getIdentifier());
        long id = sqLiteDatabase.insert(AddMemberDatabaseHelper.MEMBER_LIST_TABLE, null, values);
        this.close();

        if (id>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean UpdateMemberList(Member member){
        this.open();
        ContentValues values = new ContentValues();

        values.put(AddMemberDatabaseHelper.MEMBER_NAME,member.getmName());
        values.put(AddMemberDatabaseHelper.MEMBER_PHONE, member.getmPhone());
        values.put(AddMemberDatabaseHelper.MEMBER_EMAIL, member.getnEmail());
        values.put(AddMemberDatabaseHelper.IDENTIFIER, member.getIdentifier());
        long id = sqLiteDatabase.update(AddMemberDatabaseHelper.MEMBER_LIST_TABLE, values, AddMemberDatabaseHelper.MEMBER_ID+ " = "+ member.getmId(),null);
        this.close();

        if (id>0){
            return true;
        }else {
            return false;
        }
    }
    public ArrayList<Member> getMemberList(String identifier){
        ArrayList<Member> members = new ArrayList<>();
        this.open();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+AddMemberDatabaseHelper.MEMBER_LIST_TABLE+ " where "+AddMemberDatabaseHelper.IDENTIFIER+ " = '"+ identifier+ "'", null);
        cursor.moveToFirst();

        if (cursor.getCount()>0){
        for (int i =1; i<=cursor.getCount(); i++){
            int mId = cursor.getInt(cursor.getColumnIndex(AddMemberDatabaseHelper.MEMBER_ID));
            String mName = cursor.getString(cursor.getColumnIndex(AddMemberDatabaseHelper.MEMBER_NAME));
            String mPhone = cursor.getString(cursor.getColumnIndex(AddMemberDatabaseHelper.MEMBER_PHONE));
            String mEmail = cursor.getString(cursor.getColumnIndex(AddMemberDatabaseHelper.MEMBER_EMAIL));
            cursor.moveToNext();
            members.add(new Member(mId,mName,mPhone,mEmail, identifier, false));
        }

        }

        cursor.close();
        this.close();
        return members;
    }

    public boolean deleteMember(int mId, String identifier) {
        this.open();
        long id = sqLiteDatabase.delete(AddMemberDatabaseHelper.MEMBER_LIST_TABLE, AddMemberDatabaseHelper.MEMBER_ID+" = "+mId+ " and "+ AddMemberDatabaseHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteAllMember(String identifier) {
        this.open();
        long id = sqLiteDatabase.delete(AddMemberDatabaseHelper.MEMBER_LIST_TABLE, AddMemberDatabaseHelper.IDENTIFIER+" = '"+identifier+"'", null);
        this.close();
        if (id>0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<PreviousTable> getAllTables(String identifier){
        ArrayList<PreviousTable>previousTables = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select "+ AddMemberDatabaseHelper.IDENTIFIER + " from "+ AddMemberDatabaseHelper.MEMBER_LIST_TABLE+ " union "+"select "+ AddMemberDatabaseHelper.IDENTIFIER + " from "+ AddMemberDatabaseHelper.MEMBER_LIST_TABLE +" order by "+ AddMemberDatabaseHelper.IDENTIFIER +" desc",null);
        Assert.assertNotNull(cursor);
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            for (int i =1; i<=cursor.getCount(); i++){
                String table = cursor.getString(0);
                cursor.moveToNext();
                if (table.equals(MealInfo.getYear()+" - "+MealInfo.getMonth())){
                    continue;
                }else {
                    ArrayList<Member> members = new ArrayList<>();
                    members = getMemberList(table);
                    if (members.size()>0){
                        previousTables.add(new PreviousTable(table));
                    }else {
                        AddBazaarDBOperation addBazaarDBOperation = new AddBazaarDBOperation(0, context);
                        addBazaarDBOperation.deleteAllBazaar(identifier);

                        AddDepositDBOperation addDepositDBOperation = new AddDepositDBOperation(context, 0);
                        addDepositDBOperation.deleteAllDeposit(identifier);

                        AddExtraDBOperation addExtraDBOperation = new AddExtraDBOperation(context);
                        addExtraDBOperation.deleteExtra(identifier);

                        AddMealDBOperation addMealDBOperation = new AddMealDBOperation(context, null);
                        addMealDBOperation.deleteAllMeal(identifier);

                        AddMemberDBOperation addMemberDBOperation = new AddMemberDBOperation(context);
                        addMemberDBOperation.deleteAllMember(identifier);
                    }
                }
            }
        }
        cursor.close();
        this.close();
        return previousTables;
    }
}
