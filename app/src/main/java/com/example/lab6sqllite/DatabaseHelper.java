package com.example.lab6sqllite;



import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String TAG = "com.example.lab5.DatabaseHelper";

    private static final String TABLE_NAME = "student_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "MARKS";


    public DatabaseHelper(@Nullable Context context) {
        super(context,TABLE_NAME,null,1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, " + COL2 + " TEXT, " + COL3 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean addData(String[] student){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, student[0]);
        contentValues.put(COL2, student[1]);
        contentValues.put(COL3, student[2]);

        //Log.d(TAG, "addData :  Adding " + student[0] + " to " + TABLE_NAME);



        long result = db.insert(TABLE_NAME, null, contentValues);


        return !(result == -1);
    }

   public Cursor getData(String para_query){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = null;
        String db_query = null;

        if (para_query.equals("all")){
            db_query = "SELECT * FROM " + TABLE_NAME;
        }
        else{
            db_query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + para_query;
        }

        if (para_query != null){
            data = db.rawQuery(db_query, null);
        }

        return data;
    }
}


