package com.downly_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "downlydb";
    private static final int DATABASE_VERSION = 15;
    private static final String TABLE_name10 = "insta_helpshow";
    private static final String TABLE_name11 = "tikhelp_show";
    private static final String TABLE_name12 = "sharechat_show";
    private static final String TABLE_name13 = "share_chat";
    private static final String TABLE_name14 = "whichingwhat";
    private static final String TABLE_name15 = "fb_format";
    private static final String TABLE_name18 = "isupdated";
    private static final String TABLE_name2 = "fb";
    private static final String TABLE_name3 = "insta";
    private static final String TABLE_name4 = "tiktoks";
    private static final String TABLE_name5 = "downsid";
    private static final String TABLE_name6 = "ytquality";
    private static final String TABLE_name7 = "youtube";
    private static final String TABLE_name8 = "ythelpshow";
    private static final String TABLE_name9 = "fb_helpshow";

    private static final String TABLE_name19 = "downloadon";
    private static final String TABLE_name20 = "notifications";
    private static final String TABLE_name21 = "fcmtopic";

    private static final String downid = "downid";
    private static final String downname = "downname";
    private static final String downpath = "downpath";
    private static final String downtype = "downtype";
    private static final String downurl = "downurl";
    private static final String newdownname = "newdownname";
    private static final String audioreq = "audioreq";
    private static final String ctime = "ctime";
    private static final String isshow= "isshow";

    private static final String pkey = "pkey";
    private static final String title = "arecode";
    private static final String notitype = "notitype";

    private static String CREATE_videoid_TABLE10 = "CREATE TABLE insta_helpshow(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE11 = "CREATE TABLE tikhelp_show(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE12 = "CREATE TABLE sharechat_show(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE13 = "CREATE TABLE share_chat(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE14 = "CREATE TABLE whichingwhat(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE15 = "CREATE TABLE fb_format(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE18 = "CREATE TABLE isupdated(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fb(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE3 = "CREATE TABLE insta(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE4 = "CREATE TABLE tiktoks(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE5 = "CREATE TABLE downsid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,downid TEXT,downtype TEXT,downname TEXT,downpath TEXT,downurl TEXT,newdownname TEXT,audioreq TEXT,ctime TEXT,isshow TEXT)";
    private static String CREATE_videoid_TABLE6 = "CREATE TABLE ytquality(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE7 = "CREATE TABLE youtube(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE8 = "CREATE TABLE ythelpshow(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE9 = "CREATE TABLE fb_helpshow(pkey INTEGER PRIMARY KEY AUTOINCREMENT,arecode TEXT)";
    private static String CREATE_videoid_TABLE19 = "CREATE TABLE "+ TABLE_name19+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+title+" TEXT)";
    private static String CREATE_videoid_TABLE20 = "CREATE TABLE "+ TABLE_name20+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+title+" TEXT,"+notitype+" TEXT)";
    private static String CREATE_videoid_TABLE21 = "CREATE TABLE "+ TABLE_name21+"("+pkey+" INTEGER PRIMARY KEY AUTOINCREMENT,"+title+" TEXT)";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE9);
        db.execSQL(CREATE_videoid_TABLE10);
        db.execSQL(CREATE_videoid_TABLE11);
        db.execSQL(CREATE_videoid_TABLE12);
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE3);
        db.execSQL(CREATE_videoid_TABLE4);
        db.execSQL(CREATE_videoid_TABLE5);
        db.execSQL(CREATE_videoid_TABLE13);
        db.execSQL(CREATE_videoid_TABLE14);
        db.execSQL(CREATE_videoid_TABLE15);
        db.execSQL(CREATE_videoid_TABLE18);
        db.execSQL(CREATE_videoid_TABLE6);
        db.execSQL(CREATE_videoid_TABLE7);
        db.execSQL(CREATE_videoid_TABLE8);
        db.execSQL(CREATE_videoid_TABLE19);
        db.execSQL(CREATE_videoid_TABLE20);
        db.execSQL(CREATE_videoid_TABLE21);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fb");
        db.execSQL("DROP TABLE IF EXISTS insta");
        db.execSQL("DROP TABLE IF EXISTS tiktoks");
        db.execSQL("DROP TABLE IF EXISTS downsid");
        db.execSQL("DROP TABLE IF EXISTS fb_format");
        db.execSQL("DROP TABLE IF EXISTS fb_helpshow");
        db.execSQL("DROP TABLE IF EXISTS insta_helpshow");
        db.execSQL("DROP TABLE IF EXISTS tikhelp_show");
        db.execSQL("DROP TABLE IF EXISTS sharechat_show");
        db.execSQL("DROP TABLE IF EXISTS share_chat");
        db.execSQL("DROP TABLE IF EXISTS whichingwhat");
        db.execSQL("DROP TABLE IF EXISTS isupdated");
        db.execSQL("DROP TABLE IF EXISTS ytquality");
        db.execSQL("DROP TABLE IF EXISTS youtube");
        db.execSQL("DROP TABLE IF EXISTS ythelpshow");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name19);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name20);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name21);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fb");
        db.execSQL("DROP TABLE IF EXISTS insta_helpshow");
        db.execSQL("DROP TABLE IF EXISTS tikhelp_show");
        db.execSQL("DROP TABLE IF EXISTS sharechat_show");
        db.execSQL("DROP TABLE IF EXISTS insta");
        db.execSQL("DROP TABLE IF EXISTS tiktoks");
        db.execSQL("DROP TABLE IF EXISTS downsid");
        db.execSQL("DROP TABLE IF EXISTS fb_helpshow");
        db.execSQL("DROP TABLE IF EXISTS share_chat");
        db.execSQL("DROP TABLE IF EXISTS whichingwhat");
        db.execSQL("DROP TABLE IF EXISTS fb_format");
        db.execSQL("DROP TABLE IF EXISTS isupdated");
        db.execSQL("DROP TABLE IF EXISTS ytquality");
        db.execSQL("DROP TABLE IF EXISTS youtube");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name19);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name20);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name21);
        onCreate(db);
    }

    public void add_isfcmupdate(String title1) {
        delete_isfcmupdate();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name21, null, values);
        db.close();
    }

    public String get_isfcmupdate() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name21, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_isfcmupdate() {
        getWritableDatabase().execSQL("delete from "+TABLE_name21);
    }


    //notitype 1-normal 2-update
    public void add_notimsg(String title1,String notitype1) {
        delete_notimsg();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        values.put(notitype,notitype1);
        db.insert(TABLE_name20, null, values);
        db.close();
    }

    public String get_notitype() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name20, null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String get_notimsg() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name20, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_notimsg() {
        getWritableDatabase().execSQL("delete from "+TABLE_name20);
    }



    public void add_downloadon(String title1) {
        delete_downloadon();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name19, null, values);
        db.close();
    }

    public String get_downloadon() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name19, null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_downloadon() {
        getWritableDatabase().execSQL("delete from "+TABLE_name19);
    }


    public void add_ythelpshow(String title1) {
        delete_ythelpshow();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name8, null, values);
        db.close();
    }

    public String get_ythelpshow() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM ythelpshow", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_ythelpshow() {
        getWritableDatabase().execSQL("delete from ythelpshow");
    }

    public void add_ytquality(String title1) {
        delete_ytquality();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name6, null, values);
        db.close();
    }

    public String get_ytquality() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM ytquality", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_ytquality() {
        getWritableDatabase().execSQL("delete from ytquality");
    }

    public void add_youtube(String title1) {
        delete_youtube();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name7, null, values);
        db.close();
    }

    public String get_youtube() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM youtube", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_youtube() {
        getWritableDatabase().execSQL("delete from youtube");
    }

    public void add_isupdate(String title1) {
        delete_isupdate();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name18, null, values);
        db.close();
    }

    public String get_isupdate() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM isupdated", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_isupdate() {
        getWritableDatabase().execSQL("delete from isupdated");
    }

    public void delete_whchwp() {
        getWritableDatabase().execSQL("delete from whichingwhat");
    }

    public void add_sharechat(String title1) {
        delete_sharechat();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name13, null, values);
        db.close();
    }

    public String get_ShareChats() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM share_chat", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_sharechat() {
        getWritableDatabase().execSQL("delete from share_chat");
    }

    public void add_sharechathelpshow(String title1) {
        delete_sharechathelpshow();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name12, null, values);
        db.close();
    }

    public String get_Help_ShareChat() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM sharechat_show", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_sharechathelpshow() {
        getWritableDatabase().execSQL("delete from sharechat_show");
    }

    public void add_Help_tikTokShow(String title1) {
        delete_tikhelpshow();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name11, null, values);
        db.close();
    }

    public String get_tikhelpshow() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM tikhelp_show", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_tikhelpshow() {
        getWritableDatabase().execSQL("delete from tikhelp_show");
    }

    public void add_help_InstaShow(String title1) {
        delete_instahelpshow();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name10, null, values);
        db.close();
    }

    public String get_instahelpshow() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM insta_helpshow", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_instahelpshow() {
        getWritableDatabase().execSQL("delete from insta_helpshow");
    }

    public void add_downloadList(String downid1, String downtype1, String downname1, String downpath1, String downurl1, String newdownname1,String audioreq1,String ctime1,String isshow1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(downid, downid1);
        values.put(downtype, downtype1);
        values.put(downname, downname1);
        values.put(downpath, downpath1);
        values.put(downurl, downurl1);
        values.put(newdownname, newdownname1);
        values.put(audioreq,audioreq1);
        values.put(ctime,ctime1);
        values.put(isshow,isshow1);
        db.insert(TABLE_name5, null, values);
        db.close();
    }

    public ArrayList<String> get_ListofDownloads() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM downsid order by pkey desc", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
            arraylist.add(c.getString(6));
            arraylist.add(c.getString(9));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<String> get_all_ctime(String ctime1) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT downid FROM downsid WHERE ctime='"+ctime1+"'", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
        }
        c.close();
        return arraylist;
    }

    public String getctime(String id1) {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT ctime FROM downsid WHERE downid="+id1, null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public String getaudioreq(String id1) {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT audioreq FROM downsid WHERE downid="+id1, null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }
    public String getdownid(String id1) {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  downpath FROM downsid WHERE downid="+id1, null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public String getdownname(String id1) {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT downname FROM downsid WHERE downid="+id1, null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public void delete_downid() {
        getWritableDatabase().execSQL("delete from downsid");
    }

    public void updatedownpathbyctime(String ctime1, String path1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(downpath, path1);
        db.update(TABLE_name5, newValues, "ctime = ?", new String[]{ctime1});
    }

    public void updatedownid(String dowid1, String path1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(downpath, path1);
        db.update(TABLE_name5, newValues, "downid = ?", new String[]{dowid1});
    }

    public void delete_file_bypath(String downpath1) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_name5, "downname='"+downpath1+"'", null);
    }

    public void delete_downid(String downid1) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_name5, "downid='"+downid1+"'", null);
    }

    public void delete_downid_byid(String pkey1) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_name5, "pkey="+pkey1, null);
    }

    public void add_facebook(String title1) {
        delete_facebook();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }

    public String get_facebook() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM fb", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_facebook() {
        getWritableDatabase().execSQL("delete from fb");
    }

    public void add_instagram(String title1) {
        delete_instagram();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name3, null, values);
        db.close();
    }

    public String get_instagram() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM insta", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_instagram() {
        getWritableDatabase().execSQL("delete from insta");
    }

    public void add_qualityFb(String title1) {
        delete_fbquality();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name15, null, values);
        db.close();
    }

    public String get_QualityFB() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM fb_format", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_fbquality() {
        getWritableDatabase().execSQL("delete from fb_format");
    }

    public void add_WpWhich(String title1) {
        delete_whchwp();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name14, null, values);
        db.close();
    }

    public String get_WpWhich() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM whichingwhat", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void add_tiktok(String title1) {
        delete_tiktok();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name4, null, values);
        db.close();
    }

    public String get_tiktok() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM tiktoks", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_tiktok() {
        getWritableDatabase().execSQL("delete from tiktoks");
    }

    public void add_HelpFBShow(String title1) {
        delete_fbhelpshow();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name9, null, values);
        db.close();
    }

    public String get_fbhelpshow() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM fb_helpshow", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void delete_fbhelpshow() {
        getWritableDatabase().execSQL("delete from fb_helpshow");
    }
}
