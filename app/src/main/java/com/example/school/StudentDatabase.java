package com.example.school;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class StudentDatabase extends SQLiteOpenHelper {


    public static final String DB_NAME ="School";

    public static final String TABLE_NAME = "Login";

    public static final int DB_VERSION=9;

    public static final String COLLUM_ID = "Id";
    public static final String COLLUM_USER="user_name";
    public static final String COLLUM_PWD="pass_word";



    public StudentDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    String sql ="create table "+ TABLE_NAME + "(" + StudentDatabase.COLLUM_ID + " Integer PRIMARY KEY," + StudentDatabase.COLLUM_USER
        + " text," + StudentDatabase.COLLUM_PWD + " text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if EXISTs " +StudentDatabase.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
