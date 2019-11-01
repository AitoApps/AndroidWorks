package com.sanji_shops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE catids(pkey INTEGER PRIMARY KEY AUTOINCREMENT,shopid TEXT,shopname TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE scwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private static String CREATE_videoid_TABLE3 = "CREATE TABLE useraddress(pkey INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,user_place TEXT,user_mobile1 TEXT,user_mobile2 TEXT,user_address TEXT,user_pincode TEXT,user_landmark TEXT)";
    private static String CREATE_videoid_TABLE4 = "CREATE TABLE productcat(pkey INTEGER PRIMARY KEY AUTOINCREMENT,shopid TEXT)";
    private static final String DATABASE_NAME = "sanjihops1";
    private static final int DATABASE_VERSION = 12;
    private static final String TABLE_name1 = "catids";
    private static final String TABLE_name2 = "scwidth";
    private static final String TABLE_name3 = "useraddress";
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
        db.execSQL("DROP TABLE IF EXISTS catids");
        db.execSQL("DROP TABLE IF EXISTS useraddress");
        db.execSQL("DROP TABLE IF EXISTS productcat");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS catids");
        db.execSQL("DROP TABLE IF EXISTS useraddress");
        db.execSQL("DROP TABLE IF EXISTS productcat");
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
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM productcat", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_lastpcat() {
        getWritableDatabase().execSQL("delete from productcat");
    }

    public void add_address(String user_name1, String user_place1, String user_mobile1_1, String user_mobile2_1, String user_address1, String user_pincode1, String user_landmark1) {
        delete_address();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_name, user_name1);
        values.put(user_place, user_place1);
        values.put(user_mobile1, user_mobile1_1);
        values.put(user_mobile2, user_mobile2_1);
        values.put(user_address, user_address1);
        values.put(user_pincode, user_pincode1);
        values.put(user_landmark, user_landmark1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public String get_landmark() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(7);
        }
        c.close();
        return link;
    }

    public String get_pincode() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(6);
        }
        c.close();
        return link;
    }

    public String get_address() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(5);
        }
        c.close();
        return link;
    }

    public String get_adressmobile2() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(4);
        }
        c.close();
        return link;
    }

    public String get_adressmobile1() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String get_adressplace() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String get_adressname() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM useraddress", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_address() {
        getWritableDatabase().execSQL("delete from useraddress");
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
