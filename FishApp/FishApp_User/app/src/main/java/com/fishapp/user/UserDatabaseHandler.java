package com.fishapp.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fishappuserudb";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String TABLE_name3 = "areainfo";
    private static final String TABLE_name4 = "screenwidth";
    private static final String TABLE_name5 = "otpid";
    private static final String areaid = "areaid";
    private static final String areaname = "areaname";
    private static final String deliverytime = "deliverytime";
    private static final String fcmid = "fcmid";
    private static final String mobile = "mobile";
    private static final String name = "name";
    private static final String otpnumber = "otpnumber";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";
    private static final String sn = "sn";

    private String CREATE_videoid_TABLE4 = "CREATE TABLE screenwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private String CREATE_videoid_TABLE5 = "CREATE TABLE otpid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,otpnumber TEXT)";
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE user(pkey INTEGER PRIMARY KEY AUTOINCREMENT,sn TEXT,name TEXT,mobile TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,fcmid TEXT)";
    private static String CREATE_videoid_TABLE3 = "CREATE TABLE areainfo(pkey INTEGER PRIMARY KEY AUTOINCREMENT,areaid TEXT,areaname TEXT,deliverytime TEXT)";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_videoid_TABLE1);
            db.execSQL(CREATE_videoid_TABLE2);
            db.execSQL(CREATE_videoid_TABLE3);
            db.execSQL(CREATE_videoid_TABLE4);
            db.execSQL(CREATE_videoid_TABLE5);
        } catch (Exception e) {
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS areainfo");
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        db.execSQL("DROP TABLE IF EXISTS otpid");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS areainfo");
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        db.execSQL("DROP TABLE IF EXISTS otpid");
        onCreate(db);
    }

    public void addotpnumber(String otpnumber1) {
        deleteotpnumber();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(otpnumber, otpnumber1);
        db.insert(TABLE_name5, null, values);
        db.close();
    }

    public String getotpnumber() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM otpid", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deleteotpnumber() {
        getWritableDatabase().execSQL("delete from otpid");
    }

    public void addscreenwidth(String screenwidth1) {
        deletescreenwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(screenwidth, screenwidth1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public String getscreenwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name4, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletescreenwidth() {
        getWritableDatabase().execSQL("delete from "+TABLE_name4);
    }

    public void addarea(String areaid1, String areaname1, String deliverytime1) {
        try {
            deletearea();
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(areaid, areaid1);
            values.put(areaname, areaname1);
            values.put(deliverytime, deliverytime1);
            db.insert(TABLE_name3, null, values);
            db.close();
        } catch (Exception e) {
        }
    }

    public void addarea_update(String areaid1, String areaname1, String deliverytime1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(areaname, areaname1);
        values.put(deliverytime, deliverytime1);
        db.update(TABLE_name3, values, "areaid = ?", new String[]{areaid1});
        db.close();
    }

    public String getareaid() {
        String link = "";
        try {
            Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM areainfo", null);
            while (c.moveToNext()) {
                link = c.getString(1);
            }
            c.close();
        } catch (Exception a) {

        }
        return link;
    }

    public String getareaname() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM areainfo", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String getdelitime() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM areainfo", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public void deletearea() {
        getWritableDatabase().execSQL("delete from areainfo");
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

    public String get_userid() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        sql.close();
        return link;
    }

    public String get_name() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        sql.close();
        return link;
    }

    public String get_mobile() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        sql.close();
        return link;
    }
    public void adduser(String sn1, String name1, String mobile1) {
        deleteuser();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(sn, sn1);
        values.put(name, name1);
        values.put(mobile, mobile1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }
    public void deleteuser() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from user");
        db.close();
    }
}
