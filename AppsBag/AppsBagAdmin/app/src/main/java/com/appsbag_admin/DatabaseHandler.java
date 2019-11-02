package com.appsbag_admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "appsbagadmin";
    private static final int  DATABASE_VERSION= 1;
    private static final String TABLE_name1 = "appdetails";
    private static final String TABLE_name3 = "appviews";
    private static final String TABLE_name2 = "scwidth";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";


    private static final String appname = "appname";
    private static final String appurl = "appurl";
    private static final String title = "title";
    private static final String footer = "footer";


    private static final String photopath = "photopath";

    private static final String english = "english";
    private static final String malayalam = "malayalam";
    private static final String hindi = "hindi";
    private static final String tamil = "tamil";
    private static final String telugu = "telugu";
    private static final String datatid = "datatid";
    private static final String fbpath = "fbpath";

    private static String CREATE_TABLE2 = "CREATE TABLE " + TABLE_name2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT"+")";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_name1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+appname+" TEXT,"+appurl+" TEXT,"+title+" TEXT,"+footer+" TEXT,"+photopath+" TEXT"+")";
    private static String CREATE_TABLE3 = "CREATE TABLE " + TABLE_name3 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+english+" TEXT,"+malayalam+" TEXT,"+hindi+" TEXT,"+tamil+" TEXT,"+telugu+" TEXT,"+photopath+" TEXT,"+datatid+" TEXT,"+fbpath+" TEXT"+")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE3);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name3);
        onCreate(db);
    }
    public void addappview(String english1,String malayalam1,String hindi1,String tamil1,String telugu1,String photopath1,String datatid1,String fbpath1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(english, english1);
        values.put(malayalam, malayalam1);
        values.put(hindi, hindi1);
        values.put(tamil, tamil1);
        values.put(telugu, telugu1);
        values.put(photopath, photopath1);
        values.put(datatid,datatid1);
        values.put(fbpath,fbpath1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public void addappview_update(String pkey1, String english1,String malayalam1,String hindi1,String tamil1,String telugu1,String photopath1,String datatid1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(english, english1);
        values.put(malayalam, malayalam1);
        values.put(hindi, hindi1);
        values.put(tamil, tamil1);
        values.put(telugu, telugu1);
        values.put(photopath, photopath1);
        values.put(datatid,datatid1);
        db.update(TABLE_name3, values, "pkey = ?", new String[]{pkey1});
        db.close();
    }
    public ArrayList<String> getappview() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM appviews ORDER BY pkey ASC", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
            arraylist.add(c.getString(6));
            arraylist.add(c.getString(7));
            arraylist.add(c.getString(8));
        }
        c.close();
        return arraylist;
    }
    public ArrayList<String> getappview1() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM appviews ORDER BY pkey ASC", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
            arraylist.add(c.getString(6));
            arraylist.add(c.getString(7));
        }
        c.close();
        return arraylist;
    }
    public void deleteappview_byid(String id1) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("appviews", "pkey="+id1, null);
    }

    public void deleteappview() {
        getWritableDatabase().execSQL("delete from "+TABLE_name3);
    }


    public void addappdetails(String appname1,String appurl1,String title1,String footer1,String photopath1) {
        deleteappdetails();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(appname, appname1);
        values.put(appurl, appurl1);
        values.put(title, title1);
        values.put(footer, footer1);
        values.put(photopath,photopath1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String getappname() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public String getappurl() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }



    public String gettitle() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String getfooter() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(4);
        }
        c.close();
        return link;
    }


    public String getphotopath() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            link = c.getString(5);
        }
        c.close();
        return link;
    }

    public void deleteappdetails() {
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
