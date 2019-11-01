package com.dailybill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "billmaza1";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_name1 = "townid";
    private static final String TABLE_name2 = "scwidth";
    private static final String TABLE_name3 = "locationtable";

    private static final String common = "common";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";

    private static final String address = "address";
    private static final String latitude = "latitude";
    private static final String locavia = "locavia";
    private static final String longtitude = "longtitude";
    private static final String pincode = "pincode";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_name1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+common+" TEXT"+")";
    private static String CREATE_TABLE2 = "CREATE TABLE " + TABLE_name2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT"+")";
    private static String CREATE_TABLE3 = "CREATE TABLE " + TABLE_name3 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+latitude+" TEXT,"+longtitude+" TEXT,"+address+" TEXT,"+locavia+" TEXT,"+pincode+" TEXT"+")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        onCreate(db);
    }

    public void addlocation(String latitude1, String longtitude1, String address1, String locavia1, String pincode1) {
        deletelocation();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(latitude, latitude1);
        values.put(longtitude, longtitude1);
        values.put(address, address1);
        values.put(locavia, locavia1);
        values.put(pincode, pincode1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public String getpincode() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(5);
        }
        c.close();
        return link;
    }

    public String getlocavia() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(4);
        }
        c.close();
        return link;
    }

    public String getaddress() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String getlongtitude() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String getlatitude() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletelocation() {
        getWritableDatabase().execSQL("delete from "+TABLE_name3);
    }


    public void addtownid(String common1) {
        deletetownid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(common, common1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String gettownid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM townid", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletetownid() {
        getWritableDatabase().execSQL("delete from townid");
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
