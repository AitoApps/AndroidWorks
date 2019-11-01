package com.fishappadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbfiashapp";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_name1 = "fcmid";
    private static final String TABLE_name2 = "scwidth";
    private static final String TABLE_name3 = "useraddress";
    private static final String TABLE_name4 = "productcats";
    private static final String TABLE_name5 = "productsubcats";
    private static final String catid = "catid";
    private static final String cattitle = "cattitle";
    private static final String cattype = "cattype";
    private static final String fcmid = "fcmid";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";
    private static final String subcatid = "subcatid";
    private static final String title = "title";
    private static final String user_address = "user_address";
    private static final String user_landmark = "user_landmark";
    private static final String user_mobile1 = "user_mobile1";
    private static final String user_mobile2 = "user_mobile2";
    private static final String user_name = "user_name";
    private static final String user_pincode = "user_pincode";
    private static final String user_place = "user_place";

    private static String CREATE_videoid_TABLE1 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,fcmid TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE scwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private static String CREATE_videoid_TABLE3 = "CREATE TABLE useraddress(pkey INTEGER PRIMARY KEY AUTOINCREMENT,user_name TEXT,user_place TEXT,user_mobile1 TEXT,user_mobile2 TEXT,user_address TEXT,user_pincode TEXT,user_landmark TEXT)";
    private static String CREATE_videoid_TABLE4 = "CREATE TABLE productcats(pkey INTEGER PRIMARY KEY AUTOINCREMENT,cattype TEXT,catid TEXT,cattitle TEXT)";
    private static String CREATE_videoid_TABLE5 = "CREATE TABLE productsubcats(pkey INTEGER PRIMARY KEY AUTOINCREMENT,cattype TEXT,catid TEXT,subcatid TEXT,cattitle TEXT)";


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
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS useraddress");
        db.execSQL("DROP TABLE IF EXISTS productcats");
        db.execSQL("DROP TABLE IF EXISTS productsubcats");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS useraddress");
        db.execSQL("DROP TABLE IF EXISTS productcats");
        db.execSQL("DROP TABLE IF EXISTS productsubcats");
        onCreate(db);
    }

    public void add_productsubcat(String cattype1, String catid1, String subcatid1, String cattitle1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(cattype, cattype1);
        values.put(catid, catid1);
        values.put(cattitle, cattitle1);
        values.put(subcatid, subcatid1);
        db.insert(TABLE_name5, null, values);
        db.close();
    }

    public ArrayList<String> getproductsubcat(String cattype1, String catid1) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM productsubcats WHERE cattype=? and catid=?", new String[]{cattype1, catid1});
        while (c.moveToNext()) {
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public void deleteproductsubcat() {
        getWritableDatabase().execSQL("delete from productsubcats");
    }

    public void add_productcat(String cattype1, String catid1, String cattitle1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(cattype, cattype1);
        values.put(catid, catid1);
        values.put(cattitle, cattitle1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public String getproductcat_count() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM productcats", null);
        while (c.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            sb.append(link);
            sb.append(c.getString(1));
            link = sb.toString();
        }
        c.close();
        return link;
    }

    public ArrayList<String> getproductcat(String cattype1) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM productcats WHERE cattype=?", new String[]{cattype1});
        while (c.moveToNext()) {
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
        }
        c.close();
        return arraylist;
    }

    public void deleteproductcat() {
        getWritableDatabase().execSQL("delete from productcats");
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

    public void addfcmid(String fcmid1) {
        deletefcmid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String str = "fcmid";
        values.put(str, fcmid1);
        db.insert(str, null, values);
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
