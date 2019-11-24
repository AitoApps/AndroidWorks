package com.hellokhd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hellokhdudb";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String TABLE_name3 = "scwidth";
    private static final String TABLE_name5 = "otpid";
    private static final String userid = "userid";
    private static final String username = "username";
    private static final String fcmid = "fcmid";
    private static final String screenwidth = "screenwidth";
    public static final String otpnumber="otpnumber";

    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,fcmid TEXT)";
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE user(pkey INTEGER PRIMARY KEY AUTOINCREMENT,"+userid+" TEXT,"+username+" TEXT"+")";
    private static String CREATE_videoid_TABLE3= "CREATE TABLE "+TABLE_name3+"(pkey INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT)";
    private String CREATE_videoid_TABLE5 = "CREATE TABLE otpid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,otpnumber TEXT)";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE3);
        db.execSQL(CREATE_videoid_TABLE5);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        db.execSQL("DROP TABLE IF EXISTS otpid");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
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
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public String getscreenwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletescreenwidth() {
        getWritableDatabase().execSQL("delete from "+TABLE_name3);
    }

    public void addfcmid(String fcmid1) {
        deletefcmid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fcmid, fcmid1);
        db.insert(fcmid, null, values);
        db.close();
    }

    public String getfcmid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM fcmid", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletefcmid() {
        getWritableDatabase().execSQL("delete from fcmid");
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
    public void adduserid(String userid1,String username1) {
        deleteuserid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userid, userid1);
        values.put(username,username1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }
    public void deleteuserid() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from user");
        db.close();
    }
}
