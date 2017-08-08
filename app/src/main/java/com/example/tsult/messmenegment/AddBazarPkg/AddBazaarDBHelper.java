package com.example.tsult.messmenegment.AddBazarPkg;

import android.content.Context;
import android.database.StaleDataException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.tsult.messmenegment.AddMember.AddMemberDatabaseHelper;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

/**
 * Created by tsult on 22-Jun-17.
 */

public class AddBazaarDBHelper{

    public static final String BAZAAR_TABLE = "add_bazaar_table"; //Table name

    public static final String BAZAAR_MEMBER_ID = "member_id";    //column name
    public static final String BAZAAR_MEMBER_NAME = "member_name";
    public static final String BAZAAR_DATE = "bazaar_date";
    public static final String BAZAAR_COST = "bazaar_cost";
    public static final String BAZAAR_MEMO = "bazaar_memo";
    public static final String BAZAAR_ID = "bazaar_id";
    public static final String IDENTIFIER = "table_identifier";

    public static final String BAZAAR_QUERY = "create table "+BAZAAR_TABLE+" ("+
            BAZAAR_ID+" integer primary key, "+
            BAZAAR_MEMBER_ID+ " integer, "+
            BAZAAR_MEMBER_NAME+" text, "+
            BAZAAR_DATE+" text, "+
            BAZAAR_COST+" integer, "+
            BAZAAR_MEMO+" BLOB, "+
            IDENTIFIER+ " text);";

}
