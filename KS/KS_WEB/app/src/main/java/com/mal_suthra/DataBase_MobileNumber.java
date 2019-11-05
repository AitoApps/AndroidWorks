package com.mal_suthra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase_MobileNumber extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sk_mobile";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "mobileverify";
    private static final String mobile = "mobile";
    private static final String pkey = "pkey";
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE mobileverify(pkey INTEGER PRIMARY KEY AUTOINCREMENT,mobile TEXT)";

    public DataBase_MobileNumber(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mobileverify");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mobileverify");
        onCreate(db);
    }

    public String get_mob() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM mobileverify", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void add_mob(String mobile1) {
        drop_mob();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mobile, mobile1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public void drop_mob() {
        getWritableDatabase().execSQL("delete from mobileverify");
    }
}
