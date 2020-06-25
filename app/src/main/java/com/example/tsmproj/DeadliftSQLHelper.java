package com.example.tsmproj;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DeadliftSQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "deadlift.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DEADLIFT_TABLE = "deadlift";
    public static final String DEADLIFT_ID = "_id";
    public static final String DEADLIFT_DATE = "date";
    public static final String DEADLIFT_WEIGHT = "weight";
    public static final String DEADLIFT_REPS = "reps";

    public DeadliftSQLHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DEADLIFT_TABLE +
                        "(" + DEADLIFT_ID + " INTEGER PRIMARY KEY, " +
                        DEADLIFT_DATE + " STRING, " +
                        DEADLIFT_WEIGHT + " INTEGER, " +
                        DEADLIFT_REPS + " INTEGER)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEADLIFT_TABLE);
        onCreate(db);
    }


    public int getHighestVal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX("+DEADLIFT_WEIGHT+") FROM "+DEADLIFT_TABLE, null);
        c.moveToFirst();
        return c.getInt(0);
    }
    public Cursor getAllInfo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+DEADLIFT_TABLE + " ORDER BY " + DEADLIFT_DATE + " ASC",null);
        return res;
    }
    public boolean addRecord(String date, int weight, int reps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DEADLIFT_DATE, date);
        cv.put(DEADLIFT_WEIGHT, weight);
        cv.put(DEADLIFT_REPS, reps);
        long result  = db.insert(DEADLIFT_TABLE,null,cv);
        if(result == -1){
            return false;
        }
        else {
            return true;
        } }

        public int getSumVal(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT SUM(weight*reps) FROM "+DEADLIFT_TABLE, null);
            c.moveToFirst();
            return c.getInt(0);
        }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+DEADLIFT_TABLE);
        onCreate(db);
    }

}
