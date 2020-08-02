package com.moni.expenser;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.moni.expenser.DataContract.*;

public class  DataDBHelper extends SQLiteOpenHelper {
    public static final String DATABSE_NAME = "expenseData.db";
    public static final int DATABASE_VERSION = 1;
    public DataDBHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE "+
                TableEntry.TABLE_NAME+" ("+
                TableEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TableEntry.COLUMN_DATE+" TEXT NOT NULL, "+
                TableEntry.COLUMN_CATEGORY+" TEXT NOT NULL, "+
                TableEntry.COLUMN_AMOUNT+" REAL NOT NULL, "+
                TableEntry.COLUMN_DESC+" TEXT NOT NULL"+
                ");";
        final String SQL_CREATE_INCOME_TABLE = "CREATE TABLE "+
                TableEntry.TABLE_NAME_INCOME+" ("+
                TableEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TableEntry.COLUMN_DATE+" TEXT NOT NULL, "+
                TableEntry.COLUMN_CATEGORY+" TEXT NOT NULL, "+
                TableEntry.COLUMN_AMOUNT+" REAL NOT NULL, "+
                TableEntry.COLUMN_DESC+" TEXT NOT NULL"+
                ");";
        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
        db.execSQL(SQL_CREATE_INCOME_TABLE);
    }
    public void addInfo(SQLiteDatabase db,String date,String category,Float amount,String description)
    {
        ContentValues values = new ContentValues();
        values.put(TableEntry.COLUMN_DATE,date);
        values.put(TableEntry.COLUMN_CATEGORY,category);
        values.put(TableEntry.COLUMN_AMOUNT,amount);
        values.put(TableEntry.COLUMN_DESC,description);
        db.insert(TableEntry.TABLE_NAME,null,values);
        Log.d("Database operation","one row inserted...");
    }
    public void addInfoIncome(SQLiteDatabase db,String date,String category,Float amount,String description)
    {
        ContentValues values = new ContentValues();
        values.put(TableEntry.COLUMN_DATE,date);
        values.put(TableEntry.COLUMN_CATEGORY,category);
        values.put(TableEntry.COLUMN_AMOUNT,amount);
        values.put(TableEntry.COLUMN_DESC,description);
        db.insert(TableEntry.TABLE_NAME_INCOME,null,values);
        Log.d("Database operation","one row inserted...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TableEntry.TABLE_NAME_INCOME);
        onCreate(db);
    }
}
