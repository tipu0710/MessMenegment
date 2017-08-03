package com.example.tsult.messmenegment.AddMember;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;

/**
 * Created by tsult on 13-Jun-17.
 */

public class AddMemberDatabaseHelper {

    public static final String MEMBER_LIST_TABLE = "member_table";
    public static final String MEMBER_ID = "member_id";
    public static final String MEMBER_NAME = "member_name";
    public static final String MEMBER_PHONE = "member_phone";
    public static final String MEMBER_EMAIL = "member_email";
    public static final String IDENTIFIER = "identifier";

    public static final String TABLE_QUERY = "create table "+MEMBER_LIST_TABLE+" ("+
            MEMBER_ID+" integer primary key, "+
            MEMBER_NAME+" text, "+
            MEMBER_PHONE+" text, "+
            MEMBER_EMAIL+" text, "+
            IDENTIFIER+" text);";
}
