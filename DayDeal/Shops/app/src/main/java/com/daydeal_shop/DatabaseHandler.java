package com.daydeal_shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "daydealshopsdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "catids";
    private static final String TABLE_name2 = "scwidth";
    private static final String TABLE_name4 = "productcat";
    private static final String catid = "shopid";
    private static final String catname = "shopname";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";
    private static final String user_address = "user_address";
    private static final String user_landmark = "user_landmark";
    private static final String user_mobile1 = "user_mobile1";
    private static final String user_mobile2 = "user_mobile2";
    private static final String user_name = "user_name";
    private static final String user_pincode = "user_pincode";
    private static final String user_place = "user_place";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_name1+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+catid+" TEXT,"+catname+" TEXT"+")";
    private static String CREATE_TABLE2= "CREATE TABLE " + TABLE_name2+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT"+")";

    private static String CREATE_TABLE4= "CREATE TABLE " + TABLE_name4+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+catid+" TEXT"+")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE4);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name4);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name4);
        onCreate(db);
    }

    public void add_lastpcat(String catid1) {
        delete_lastpcat();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(catid, catid1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public String lastpcat() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name4, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_lastpcat() {
        getWritableDatabase().execSQL("delete from "+TABLE_name4);
    }

    public void add_catid(String catid1, String catname1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(catid, catid1);
        values.put(catname, catname1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public ArrayList<String> getcatid() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
        }
        c.close();
        return arraylist;
    }

    public void delete_catid() {
        getWritableDatabase().execSQL("delete from "+TABLE_name1);
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
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name2, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletescreenwidth() {
        getWritableDatabase().execSQL("delete from "+TABLE_name2);
    }
}
