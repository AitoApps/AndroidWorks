package com.hellokhd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hellokhddb";
    private static final int DATABASE_VERSION =6;

    private static final String TABLE_name1 = "startupads";
    private static final String TABLE_name2 = "fullscreen";
    private static final String TABLE_name3 = "featured";
    private static final String TABLE_name4 = "banner";
    private static final String TABLE_name5 = "location";

    private static final String pkey = "pkey";
    private static final String adsn = "adsn";
    private static final String linktype = "linktype";
    private static final String reference = "reference";
    private static final String imgsig = "imgsig";

    private static final String latitude = "latitude";
    private static final String longitude = "longtitude";

    private static String CREATE_videoid_TABLE1 = "CREATE TABLE "+TABLE_name1+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+adsn+" TEXT,"+linktype+" TEXT,"+reference+" TEXT,"+imgsig+" TEXT"+")";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE "+TABLE_name2+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+adsn+" TEXT,"+linktype+" TEXT,"+reference+" TEXT,"+imgsig+" TEXT"+")";
    private static String CREATE_videoid_TABLE3 = "CREATE TABLE "+TABLE_name3+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+adsn+" TEXT,"+linktype+" TEXT,"+reference+" TEXT,"+imgsig+" TEXT"+")";
    private static String CREATE_videoid_TABLE4 = "CREATE TABLE "+TABLE_name4+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+adsn+" TEXT,"+linktype+" TEXT,"+reference+" TEXT,"+imgsig+" TEXT"+")";
    private static String CREATE_videoid_TABLE5 = "CREATE TABLE "+TABLE_name5+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+latitude+" TEXT,"+longitude+" TEXT"+")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE3);
        db.execSQL(CREATE_videoid_TABLE4);
        db.execSQL(CREATE_videoid_TABLE5);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name5);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name5);
        onCreate(db);
    }

    public String get_longtitude() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name5, null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        sql.close();
        return link;
    }

    public String get_latitude() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name5, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        sql.close();
        return link;
    }
    public void addlocation(String latitude1,String longtitude1) {
        deletelocation();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(latitude,latitude1);
        values.put(longitude,longtitude1);
        db.insert(TABLE_name5, null, values);
        db.close();
    }
    public void deletelocation() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+TABLE_name5);
        db.close();
    }

    public void addbanner(String adsn1,String linktype1,String reference1,String imgsig1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(adsn,adsn1);
        values.put(linktype,linktype1);
        values.put(reference, reference1);
        values.put(imgsig, imgsig1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public ArrayList<String> getbanner() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name4, null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public void deletebanner() {
        getWritableDatabase().execSQL("delete from "+TABLE_name4);
    }

    public void addfeatured(String adsn1,String linktype1,String reference1,String imgsig1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(adsn,adsn1);
        values.put(linktype,linktype1);
        values.put(reference, reference1);
        values.put(imgsig, imgsig1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public ArrayList<String> getfeatured() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public void deletefeatured() {
        getWritableDatabase().execSQL("delete from "+TABLE_name3);
    }

    public void addfullscreen(String adsn1,String linktype1,String reference1,String imgsig1) {
        deletefullscreen();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(adsn,adsn1);
        values.put(linktype,linktype1);
        values.put(reference, reference1);
        values.put(imgsig, imgsig1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }

    public ArrayList<String> getfullscreen() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name2, null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public void deletefullscreen() {
        getWritableDatabase().execSQL("delete from "+TABLE_name2);
    }

    public void addstartupads(String adsn1,String linktype1,String reference1,String imgsig1) {
        deletestartupads();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(adsn,adsn1);
        values.put(linktype,linktype1);
        values.put(reference, reference1);
        values.put(imgsig, imgsig1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public ArrayList<String> getstartupads() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public void deletestartupads() {
        getWritableDatabase().execSQL("delete from "+TABLE_name1);
    }
}
