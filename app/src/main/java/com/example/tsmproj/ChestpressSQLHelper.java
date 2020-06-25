package com.example.tsmproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChestpressSQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chestpress.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CHESTPRESS_TABLE = "deadlift";
    public static final String CHESTPRESS_ID = "_id";
    public static final String CHESTPRESS_DATE = "date";
    public static final String CHESTPRESS_WEIGHT = "weight";
    public static final String CHESTPRESS_REPS = "reps";

    public ChestpressSQLHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + CHESTPRESS_TABLE +
                        "(" + CHESTPRESS_ID + " INTEGER PRIMARY KEY, " +
                        CHESTPRESS_DATE + " STRING, " +
                        CHESTPRESS_WEIGHT + " INTEGER, " +
                        CHESTPRESS_REPS + " INTEGER)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHESTPRESS_TABLE);
        onCreate(db);
    }


    public int getHighestVal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX("+CHESTPRESS_WEIGHT+") FROM "+CHESTPRESS_TABLE, null);
        c.moveToFirst();
        return c.getInt(0);
    }
    public int getInfoById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT "+CHESTPRESS_WEIGHT+" FROM "+CHESTPRESS_TABLE + " WHERE "+CHESTPRESS_ID+" = "+id,  null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public int getDateById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT "+CHESTPRESS_DATE+" FROM "+CHESTPRESS_TABLE + " WHERE "+CHESTPRESS_ID+" = "+id, null);
        c.moveToFirst();
        return c.getInt(0);
    }


    public Cursor getAllInfo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+CHESTPRESS_TABLE+" ORDER BY " + CHESTPRESS_DATE + " ASC",null);
        return res;
    }
    public boolean addRecord(String date, int weight, int reps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHESTPRESS_DATE, date);
        cv.put(CHESTPRESS_WEIGHT, weight);
        cv.put(CHESTPRESS_REPS, reps);
        long result  = db.insert(CHESTPRESS_TABLE,null,cv);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+CHESTPRESS_TABLE);
        onCreate(db);
    }

}
