package com.sanji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE user(pkey INTEGER PRIMARY KEY AUTOINCREMENT,sn TEXT,name TEXT,mobile TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,fcmid TEXT)";
    private static final String DATABASE_NAME = "sanji_udb";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String fcmid = "fcmid";
    private static final String mobile = "mobile";
    private static final String name = "name";
    private static final String pkey = "pkey";
    private static final String sn = "sn";
    private static final String townanme = "townanme";
    private static final String towncode = "towncode";
    private String CREATE_videoid_TABLE3 = "CREATE TABLE arealist(pkey INTEGER PRIMARY KEY AUTOINCREMENT,towncode TEXT,townanme TEXT)";
    private String CREATE_videoid_TABLE4 = "CREATE TABLE townids(pkey INTEGER PRIMARY KEY AUTOINCREMENT,townid TEXT,towntitle TEXT,townfcm TEXT)";
    private String CREATE_videoid_TABLE5 = "CREATE TABLE lasttownids(pkey INTEGER PRIMARY KEY AUTOINCREMENT,townfcm TEXT)";
    private String CREATE_videoid_TABLE6 = "CREATE TABLE tempuser(pkey INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,mobile TEXT,countrycode TEXT,androidid TEXT)";
    private final String TABLE_name3 = "arealist";
    private final String TABLE_name4 = "townids";
    private final String TABLE_name5 = "lasttownids";
    private final String TABLE_name6 = "tempuser";
    private final String androidid = "androidid";
    private final String countrycode = "countrycode";
    private final String townfcm = "townfcm";
    private final String townid = "townid";
    private final String towntitle = "towntitle";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE3);
        db.execSQL(CREATE_videoid_TABLE4);
        db.execSQL(CREATE_videoid_TABLE5);
        db.execSQL(CREATE_videoid_TABLE6);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS arealist");
        db.execSQL("DROP TABLE IF EXISTS townids");
        db.execSQL("DROP TABLE IF EXISTS lasttownids");
        db.execSQL("DROP TABLE IF EXISTS tempuser");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        db.execSQL("DROP TABLE IF EXISTS arealist");
        db.execSQL("DROP TABLE IF EXISTS townids");
        db.execSQL("DROP TABLE IF EXISTS lasttownids");
        db.execSQL("DROP TABLE IF EXISTS tempuser");
        onCreate(db);
    }

    public void addtempuser(String name1, String mobile1, String countrycode1, String androidid1) {
        deletetempuser();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(name, name1);
        values.put(mobile, mobile1);
        values.put("countrycode", countrycode1);
        values.put("androidid", androidid1);
        db.insert("tempuser", null, values);
        db.close();
    }

    public String gettempuser() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM tempuser", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public String gettempmobile() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM tempuser", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String gettempccode() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM tempuser", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String gettempandrodid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM tempuser", null);
        while (c.moveToNext()) {
            link = c.getString(4);
        }
        c.close();
        return link;
    }

    public void deletetempuser() {
        getWritableDatabase().execSQL("delete from tempuser");
    }

    public void addlasttownfcm(String townfcm1) {
        deletelasttownfcm();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("townfcm", townfcm1);
        db.insert("lasttownids", null, values);
        db.close();
    }

    public String getlasttownfcm() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM lasttownids", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletelasttownfcm() {
        getWritableDatabase().execSQL("delete from lasttownids");
    }

    public void addselecttown(String towncode1, String townanme1) {
        deleteselecttown();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(towncode, towncode1);
        values.put(townanme, townanme1);
        db.insert("arealist", null, values);
        db.close();
    }

    public String getselecttownid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM arealist", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public String getselecttownname() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM arealist", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public void deleteselecttown() {
        getWritableDatabase().execSQL("delete from arealist");
    }

    public void addtowns(String townid1, String towntitle1, String townfcm1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("townid", townid1);
        values.put("towntitle", towntitle1);
        values.put("townfcm", townfcm1);
        db.insert("townids", null, values);
        db.close();
    }

    public ArrayList<String> gettowns() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM townids", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
        }
        c.close();
        return arraylist;
    }

    public void deletetowns() {
        getWritableDatabase().execSQL("delete from townids");
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
