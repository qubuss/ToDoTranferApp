package com.qubuss.todotransfer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by qubuss on 01.03.2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE = "create table "+DbContract.TABLE_NAME+"(id integer primary key autoincrement, "+DbContract.NAME+" text, "+DbContract.BANK_NAME+" text);";
    private static final String DROP_TABLE = "drop table if exists "+DbContract.TABLE_NAME;

    public DbHelper(Context context){
        super(context, DbContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveToLocalDataBase(String name, String bankName, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.NAME, name);
        contentValues.put(DbContract.BANK_NAME, bankName);

        database.insert(DbContract.TABLE_NAME, null, contentValues);

    }

    public Cursor readFromLocalDataBase(SQLiteDatabase database){

        String[] projection = {DbContract.ID, DbContract.NAME, DbContract.BANK_NAME};

        return (database.query(DbContract.TABLE_NAME, projection, null, null, null, null, null));
    }

    public void updateLocalDataBase(String name, String bankName, SQLiteDatabase database){

        ContentValues contentValue = new ContentValues();
        contentValue.put(DbContract.NAME, name);
        contentValue.put(DbContract.BANK_NAME, bankName);

        String selection = DbContract.NAME+" LIKE ?";
        String[] selectionArgs = {name, bankName};

        database.update(DbContract.TABLE_NAME, contentValue, selection, selectionArgs);
    }

    public int deleteFromLocalDataBase(String id, SQLiteDatabase database){
        String where = DbContract.ID+"=?";
        Log.e("WHERE", where+" "+id);
        String[] whereArgs = {id};
        return database.delete(DbContract.TABLE_NAME, where, whereArgs);
    }

}

