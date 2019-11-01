package com.daydeal_shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "daydealshopsudb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String fcmid = "fcmid";
    private static final String pkey = "pkey";
    private static final String shopid = "shopid";
    private static final String shopname = "shopname";
    public static final String trusted = "trusted";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_name1+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+shopid+" TEXT,"+shopname+" TEXT,"+trusted+" TEXT"+")";
    private static String CREATE_TABLE2= "CREATE TABLE " + TABLE_name2+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+fcmid+" TEXT"+")";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        onCreate(db);
    }

    public void addfcmid(String fcmid1) {
        deletefcmid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fcmid, fcmid1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }

    public String getfcmid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name2, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletefcmid() {
        getWritableDatabase().execSQL("delete from "+TABLE_name2);
    }

    public String get_shopid() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        sql.close();
        return link;
    }

    public String get_shopname() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        sql.close();
        return link;
    }

    public String get_trusted() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        sql.close();
        return link;
    }
    public void add_shops(String shopid1, String shopname1, String trusted1) {
        deleteshops();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(shopid, shopid1);
        values.put(shopname, shopname1);
        values.put(trusted, trusted1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }
    public void deleteshops() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+TABLE_name1);
        db.close();
    }
}
