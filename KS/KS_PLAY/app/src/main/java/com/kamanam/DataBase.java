package com.kamanam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE posslidehelp(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE screenwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";

    private static final String DATABASE_NAME = "db_sksssa";
    private static final int DATABASE_VERSION = 13;

    private static final String TABLE_name1 = "posslidehelp";
    private static final String title = "title";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        onCreate(db);
    }


    public void add_widthscreen(String screenwidth1) {
        drop_scrwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String str = "screenwidth";
        values.put(str, screenwidth1);
        db.insert(str, null, values);
        db.close();
    }

    public String get_scrwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM screenwidth", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_scrwidth() {
        getWritableDatabase().execSQL("delete from screenwidth");
    }

    public void add_poshelp_slide(String title1) {
        drop_poshelp_slide();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String get_poshlp_slide() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_poshelp_slide() {
        getWritableDatabase().execSQL("delete from "+TABLE_name1);
    }
}
