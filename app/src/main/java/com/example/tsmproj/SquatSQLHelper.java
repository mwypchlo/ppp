package com.example.tsmproj;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SquatSQLHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "squat.db";
        private static final int DATABASE_VERSION = 1;

        public static final String SQUAT_TABLE = "squat";
        public static final String SQUAT_ID = "_id";
        public static final String SQUAT_DATE = "date";
        public static final String SQUAT_WEIGHT = "weight";
        public static final String SQUAT_REPS = "reps";

        public SquatSQLHelper(Context context) {
            super(context, DATABASE_NAME , null, DATABASE_VERSION);
        }


        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + SQUAT_TABLE +
                            "(" + SQUAT_ID + " INTEGER PRIMARY KEY, " +
                            SQUAT_DATE + " STRING, " +
                            SQUAT_WEIGHT + " INTEGER, " +
                            SQUAT_REPS + " INTEGER)"
            );
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SQUAT_TABLE);
            onCreate(db);
        }


        public int getHighestVal(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT MAX("+SQUAT_WEIGHT+") FROM "+SQUAT_TABLE, null);
            c.moveToFirst();
            return c.getInt(0);
        }
        public Cursor getAllInfo(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM "+SQUAT_TABLE+ " ORDER BY " + SQUAT_DATE + " ASC",null);
            return res;
        }
        public boolean addRecord(String date, int weight, int reps){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(SQUAT_DATE, date);
            cv.put(SQUAT_WEIGHT, weight);
            cv.put(SQUAT_REPS, reps);
            long result  = db.insert(SQUAT_TABLE,null,cv);
            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }

    public int getSumVal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(weight*reps) FROM "+SQUAT_TABLE, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+SQUAT_TABLE);
        onCreate(db);
    }
}
