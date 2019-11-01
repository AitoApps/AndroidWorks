package com.suthra_malayalam_web;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_suthra";
    private static final int DATABASE_VERSION = 101;
    private static final String TABLE_name1 = "fvrts";
    private static final String TABLE_name11 = "album_fvrts";
    private static final String TABLE_name12 = "album_bookmark";
    private static final String TABLE_name13 = "posslidehelp";
    private static final String TABLE_name14 = "albmslidehelp";
    private static final String TABLE_name16 = "fontsize";
    private static final String TABLE_name18 = "positionscount";
    private static final String TABLE_name20 = "verificationid";
    private static final String TABLE_name21 = "rechargeamt";
    private static final String TABLE_name22 = "downsql";
    private static final String TABLE_name23 = "passkey";
    private static final String TABLE_name24 = "securityquestion";
    private static final String TABLE_name25 = "adcounts";
    private static final String TABLE_name26 = "screenwidth";
    private static final String TABLE_name29 = "pos_bypass";
    private static final String TABLE_name30 = "isunlock";
    private static final String TABLE_name4 = "bookmark";
    private static final String TABLE_name5 = "ispurchased";
    private static final String TABLE_name6 = "firstdown";
    private static final String TABLE_name7 = "catogery";
    private static final String TABLE_name8 = "subcatogery";
    private static final String adcount = "question2";
    private static final String firstdown = "firstdown";
    private static final String pkey = "pkey";
    private static final String purchased = "purchased";
    private static final String question1 = "question1";
    private static final String question2 = "question2";
    private static final String screenwidth = "screenwidth";
    private static final String title = "title";

    private static String CREATE_videoid_TABLE1 = "CREATE TABLE fvrts(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE11 = "CREATE TABLE album_fvrts(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE12 = "CREATE TABLE album_bookmark(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE13 = "CREATE TABLE posslidehelp(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE14 = "CREATE TABLE albmslidehelp(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE16 = "CREATE TABLE fontsize(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE18 = "CREATE TABLE positionscount(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE20 = "CREATE TABLE verificationid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE21 = "CREATE TABLE rechargeamt(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE22 = "CREATE TABLE downsql(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE23 = "CREATE TABLE passkey(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE24 = "CREATE TABLE securityquestion(pkey INTEGER PRIMARY KEY AUTOINCREMENT,question1 TEXT,question2 TEXT)";
    private static String CREATE_videoid_TABLE25 = "CREATE TABLE adcounts(pkey INTEGER PRIMARY KEY AUTOINCREMENT,question2 TEXT)";
    private static String CREATE_videoid_TABLE26 = "CREATE TABLE screenwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private static String CREATE_videoid_TABLE29 = "CREATE TABLE pos_bypass(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE30 = "CREATE TABLE isunlock(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE4 = "CREATE TABLE bookmark(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE5 = "CREATE TABLE ispurchased(pkey INTEGER PRIMARY KEY AUTOINCREMENT,purchased TEXT)";
    private static String CREATE_videoid_TABLE6 = "CREATE TABLE firstdown(pkey INTEGER PRIMARY KEY AUTOINCREMENT,firstdown TEXT)";
    private static String CREATE_videoid_TABLE7 = "CREATE TABLE catogery(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static String CREATE_videoid_TABLE8 = "CREATE TABLE subcatogery(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE4);
        db.execSQL(CREATE_videoid_TABLE5);
        db.execSQL(CREATE_videoid_TABLE6);
        db.execSQL(CREATE_videoid_TABLE7);
        db.execSQL(CREATE_videoid_TABLE8);
        db.execSQL(CREATE_videoid_TABLE11);
        db.execSQL(CREATE_videoid_TABLE12);
        db.execSQL(CREATE_videoid_TABLE13);
        db.execSQL(CREATE_videoid_TABLE14);
        db.execSQL(CREATE_videoid_TABLE16);
        db.execSQL(CREATE_videoid_TABLE18);
        db.execSQL(CREATE_videoid_TABLE20);
        db.execSQL(CREATE_videoid_TABLE21);
        db.execSQL(CREATE_videoid_TABLE22);
        db.execSQL(CREATE_videoid_TABLE23);
        db.execSQL(CREATE_videoid_TABLE24);
        db.execSQL(CREATE_videoid_TABLE25);
        db.execSQL(CREATE_videoid_TABLE26);
        db.execSQL(CREATE_videoid_TABLE29);
        db.execSQL(CREATE_videoid_TABLE30);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fvrts");
        db.execSQL("DROP TABLE IF EXISTS bookmark");
        db.execSQL("DROP TABLE IF EXISTS ispurchased");
        db.execSQL("DROP TABLE IF EXISTS firstdown");
        db.execSQL("DROP TABLE IF EXISTS catogery");
        db.execSQL("DROP TABLE IF EXISTS subcatogery");
        db.execSQL("DROP TABLE IF EXISTS album_fvrts");
        db.execSQL("DROP TABLE IF EXISTS album_bookmark");
        db.execSQL("DROP TABLE IF EXISTS posslidehelp");
        db.execSQL("DROP TABLE IF EXISTS downsql");
        db.execSQL("DROP TABLE IF EXISTS passkey");
        db.execSQL("DROP TABLE IF EXISTS securityquestion");
        db.execSQL("DROP TABLE IF EXISTS adcounts");
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        db.execSQL("DROP TABLE IF EXISTS albmslidehelp");
        db.execSQL("DROP TABLE IF EXISTS fontsize");
        db.execSQL("DROP TABLE IF EXISTS positionscount");
        db.execSQL("DROP TABLE IF EXISTS verificationid");
        db.execSQL("DROP TABLE IF EXISTS rechargeamt");
        db.execSQL("DROP TABLE IF EXISTS pos_bypass");
        db.execSQL("DROP TABLE IF EXISTS isunlock");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fvrts");
        db.execSQL("DROP TABLE IF EXISTS bookmark");
        db.execSQL("DROP TABLE IF EXISTS ispurchased");
        db.execSQL("DROP TABLE IF EXISTS firstdown");
        db.execSQL("DROP TABLE IF EXISTS positionscount");
        db.execSQL("DROP TABLE IF EXISTS verificationid");
        db.execSQL("DROP TABLE IF EXISTS rechargeamt");
        db.execSQL("DROP TABLE IF EXISTS downsql");
        db.execSQL("DROP TABLE IF EXISTS passkey");
        db.execSQL("DROP TABLE IF EXISTS securityquestion");
        db.execSQL("DROP TABLE IF EXISTS adcounts");
        db.execSQL("DROP TABLE IF EXISTS screenwidth");
        db.execSQL("DROP TABLE IF EXISTS catogery");
        db.execSQL("DROP TABLE IF EXISTS subcatogery");
        db.execSQL("DROP TABLE IF EXISTS album_fvrts");
        db.execSQL("DROP TABLE IF EXISTS album_bookmark");
        db.execSQL("DROP TABLE IF EXISTS posslidehelp");
        db.execSQL("DROP TABLE IF EXISTS albmslidehelp");
        db.execSQL("DROP TABLE IF EXISTS fontsize");
        db.execSQL("DROP TABLE IF EXISTS pos_bypass");
        db.execSQL("DROP TABLE IF EXISTS isunlock");
        onCreate(db);
    }

    public void add_bypass_pos(String title1) {
        drop_posbypass();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name29, null, values);
        db.close();
    }

    public String get_posbyapss() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM pos_bypass", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_posbypass() {
        getWritableDatabase().execSQL("delete from pos_bypass");
    }

    public void add_widthscreen(String screenwidth1) {
        drop_scrwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String str = "screenwidth";
        values.put(str, screenwidth1);
        db.insert(str, null, values);
        db.close();
    }

    public String get_scrwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM screenwidth", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_scrwidth() {
        getWritableDatabase().execSQL("delete from screenwidth");
    }

    public void add_quest(String question1_1, String question2_1) {
        drop_quest();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(question1, question1_1);
        values.put("question2", question2_1);
        db.insert(TABLE_name24, null, values);
        db.close();
    }

    public ArrayList<String> get_quest() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM securityquestion", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
        }
        c.close();
        return arraylist;
    }

    public void drop_quest() {
        getWritableDatabase().execSQL("delete from securityquestion");
    }

    public void add_keyopen(String title1) {
        drop_keyopen();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name23, null, values);
        db.close();
    }

    public String get_keyopen() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM passkey", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_keyopen() {
        getWritableDatabase().execSQL("delete from passkey");
    }

    public void add_downsql(String title1) {
        drop_downsql();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name22, null, values);
        db.close();
    }

    public String get_downsql() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM downsql", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_downsql() {
        getWritableDatabase().execSQL("delete from downsql");
    }

    public void add_amt_recharge(String title1) {
        drop_amt_recharge();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name21, null, values);
        db.close();
    }

    public String get_amt_recharge() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM rechargeamt", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_amt_recharge() {
        getWritableDatabase().execSQL("delete from rechargeamt");
    }

    public void add_veriid(String title1) {
        drop_veriid();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name20, null, values);
        db.close();
    }

    public String get_veriid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM verificationid", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_veriid() {
        getWritableDatabase().execSQL("delete from verificationid");
    }

    public void addposcount1(String title1) {
        drop_poscount1();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name18, null, values);
        db.close();
    }

    public String get_poscount1() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM positionscount", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_poscount1() {
        getWritableDatabase().execSQL("delete from positionscount");
    }

    public void add_counts(String adcount1) {
        drop_counts();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question2", adcount1);
        db.insert(TABLE_name25, null, values);
        db.close();
    }

    public String get_counts() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM adcounts", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_counts() {
        getWritableDatabase().execSQL("delete from adcounts");
    }

    public void add_fontsize(String title1) {
        drop_sizefont();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name16, null, values);
        db.close();
    }

    public String get_sizefont() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM fontsize", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_sizefont() {
        getWritableDatabase().execSQL("delete from fontsize");
    }

    public void add_slidehelp(String title1) {
        delete_slidehelp();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name14, null, values);
        db.close();
    }

    public String get_slidehelp() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM albmslidehelp", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_slidehelp() {
        getWritableDatabase().execSQL("delete from albmslidehelp");
    }

    public void addalbum_fvrt(String title1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name11, null, values);
        db.close();
    }

    public ArrayList<String> get_fvrt_albm() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM album_fvrts", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
        }
        c.close();
        return arraylist;
    }

    public void drop_albm_fvrt(String id1) {
        getWritableDatabase().delete(TABLE_name11, "title=?", new String[]{id1});
    }

    public void add_poshelp_slide(String title1) {
        drop_poshelp_slide();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name13, null, values);
        db.close();
    }

    public String get_poshlp_slide() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM posslidehelp", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_poshelp_slide() {
        getWritableDatabase().execSQL("delete from posslidehelp");
    }

    public void addbkmrk_album(String title1) {
        drop_albm_bkmrk();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name12, null, values);
        db.close();
    }

    public String get_albm_bkmark() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM album_bookmark", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_albm_bkmrk() {
        getWritableDatabase().execSQL("delete from album_bookmark");
    }

    public void addbkmrk(String title1) {
        drop_bokmark();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public String get_bokmark() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM bookmark", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_bokmark() {
        getWritableDatabase().execSQL("delete from bookmark");
    }

    public void add_cat(String firstdown1) {
        drop_cat();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, firstdown1);
        db.insert(TABLE_name7, null, values);
        db.close();
    }

    public String get_cat() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM catogery", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_cat() {
        getWritableDatabase().execSQL("delete from catogery");
    }

    public void addfvrt(String title1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public ArrayList<String> getfvrt() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM fvrts", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
        }
        c.close();
        return arraylist;
    }

    public void deletefvrt(String id1) {
        getWritableDatabase().delete(TABLE_name1, "title=?", new String[]{id1});
    }

    public void add_firstdown(String firstdown1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String str = "firstdown";
        values.put(str, firstdown1);
        db.insert(str, null, values);
        db.close();
    }

    public String get_firstdown() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM firstdown", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_firstdown() {
        getWritableDatabase().execSQL("delete from firstdown");
    }

    public void add_purchase(String purchased1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(purchased, purchased1);
        db.insert(TABLE_name5, null, values);
        db.close();
    }

    public String get_purchase() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM ispurchased", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_purchase() {
        getWritableDatabase().execSQL("delete from ispurchased");
    }

    public void add_subcat(String firstdown1) {
        drop_subcat();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, firstdown1);
        db.insert(TABLE_name8, null, values);
        db.close();
    }

    public String get_subcat() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM subcatogery", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void drop_subcat() {
        getWritableDatabase().execSQL("delete from subcatogery");
    }
}
