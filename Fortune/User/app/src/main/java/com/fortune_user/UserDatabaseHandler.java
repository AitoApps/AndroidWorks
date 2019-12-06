package com.fortune_user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "udbfortuneuser";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String TABLE_name3 = "screenwidth";
    private static final String fcmid = "fcmid";
    private static final String pkey = "pkey";
    private static final String userid = "userid";
    private static final String screenwidth = "screenwidth";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_name1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+userid+" TEXT"+")";
    private static String CREATE_TABLE2= "CREATE TABLE " + TABLE_name2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+fcmid+" TEXT"+")";
    private static String CREATE_TABLE3= "CREATE TABLE " + TABLE_name3 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT"+")";


    public UserDatabaseHandler(Context context) {
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
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        sql.close();
        return link;
    }
    public void adduser(String userid1) {
        deleteuser();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userid, userid1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }
    public void deleteuser() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+TABLE_name1);
        db.close();
    }

    public String get_screenwidth() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM "+TABLE_name3, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        sql.close();
        return link;
    }
    public void addscreenwidth(String userid1) {
        deletescreenwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userid, userid1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }
    public void deletescreenwidth() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+TABLE_name3);
        db.close();
    }
}
