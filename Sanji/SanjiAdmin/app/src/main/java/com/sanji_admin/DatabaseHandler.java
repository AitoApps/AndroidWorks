package com.sanji_admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE townids(pkey INTEGER PRIMARY KEY AUTOINCREMENT,shopid TEXT,shopname TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE scwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private static String CREATE_videoid_TABLE3 = "CREATE TABLE catids(pkey INTEGER PRIMARY KEY AUTOINCREMENT,shopid TEXT,shopname TEXT)";
    private static String CREATE_videoid_TABLE4 = "CREATE TABLE lastcatids(pkey INTEGER PRIMARY KEY AUTOINCREMENT,shopid TEXT)";
    private static final String DATABASE_NAME = "sanji1";
    private static final int DATABASE_VERSION = 12;
    private static final String TABLE_name1 = "townids";
    private static final String TABLE_name2 = "scwidth";
    private static final String TABLE_name3 = "catids";
    private static final String TABLE_name4 = "lastcatids";
    private static final String catid = "shopid";
    private static final String catname = "shopname";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";
    private static final String townid = "shopid";
    private static final String townname = "shopname";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 12);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE3);
        db.execSQL(CREATE_videoid_TABLE4);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS townids");
        db.execSQL("DROP TABLE IF EXISTS catids");
        db.execSQL("DROP TABLE IF EXISTS lastcatids");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS catids");
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS townids");
        db.execSQL("DROP TABLE IF EXISTS lastcatids");
        onCreate(db);
    }

    public void add_lastpcat(String catid1) {
        delete_lastpcat();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("shopid", catid1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public String lastpcat() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM lastcatids", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_lastpcat() {
        getWritableDatabase().execSQL("delete from lastcatids");
    }

    public void add_catid(String catid1, String catname1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("shopid", catid1);
        values.put("shopname", catname1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public ArrayList<String> getcatid() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM catids", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
        }
        c.close();
        return arraylist;
    }

    public void delete_catid() {
        getWritableDatabase().execSQL("delete from catids");
    }

    public void add_townid(String townid1, String townname1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("shopid", townid1);
        values.put("shopname", townname1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public ArrayList<String> gettownid() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM townids", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
        }
        c.close();
        return arraylist;
    }

    public void delete_townid() {
        getWritableDatabase().execSQL("delete from townids");
    }

    public void addscreenwidth(String screenwidth1) {
        deletescreenwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(screenwidth, screenwidth1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }

    public String getscreenwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM scwidth", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletescreenwidth() {
        getWritableDatabase().execSQL("delete from scwidth");
    }
}
