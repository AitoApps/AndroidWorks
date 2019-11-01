package com.chintha_admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE userid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,userid TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE screenwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private static String CREATE_videoid_TABLE7 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,fcmid TEXT)";
    private static final String DATABASE_NAME = "salmanadmin";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_name1 = "userid";
    private static final String TABLE_name2 = "screenwidth";
    private static final String TABLE_name7 = "fcmid";
    private static final String cmnt_imgsigid = "cmnt_imgsigid";
    private static final String cmnt_photodim = "cmnt_photodim";
    private static final String cmnt_photourl = "cmnt_photourl";
    private static final String cmnt_statustype = "cmnt_statustype";
    private static final String coins = "coins";
    private static final String comment_id = "commentid";
    private static final String commentcount = "commentcount";
    private static final String commentdate = "commentdate";
    private static final String comments = "comments";
    private static final String countrycode = "countrycode";
    private static final String fcmid = "fcmid";
    private static final String mobile = "mobile";
    private static final String msg = "msg";
    private static final String name = "name";
    private static final String pkey = "pkey";
    private static final String profilpic = "profilepic";
    private static final String replyvisi = "replyvisi";
    private static final String screenwidth = "screenwidth";
    private static final String showmobile = "showmobile";
    private static final String status = "status";
    private static final String status_id = "status_id";
    private static final String statusid = "statusid";
    private static final String userid = "userid";
    private static final String username = "username";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE7);
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS userid");
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS userid");
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        onCreate(db);
    }

    public void addscreenwidth(String screenwidth1) {
        deletescreenwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("screenwidth", screenwidth1);
        db.insert("screenwidth", null, values);
        db.close();
    }

    public String getscreenwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM screenwidth", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletescreenwidth() {
        getWritableDatabase().execSQL("delete from screenwidth");
    }

    public void adduserid(String userid1) {
        deletefcmid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userid", userid1);
        db.insert("userid", null, values);
        db.close();
    }

    public String getuserid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM userid", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deleteuserid() {
        getWritableDatabase().execSQL("delete from userid");
    }

    public void addfcmid(String fcmid1) {
        deletefcmid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fcmid", fcmid1);
        db.insert("fcmid", null, values);
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
}
