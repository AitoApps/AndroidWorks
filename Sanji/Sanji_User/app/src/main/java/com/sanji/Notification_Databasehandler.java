package com.sanji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class Notification_Databasehandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE noti(pkey INTEGER PRIMARY KEY AUTOINCREMENT,notitype TEXT,notititle TEXT,notimsg TEXT,notime TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE noticount(notimsg TEXT)";
    private static final String DATABASE_NAME = "tasteofponnani_noti";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_name1 = "noti";
    private static final String TABLE_name2 = "noticount";
    private static final String notime = "notime";
    private static final String notimsg = "notimsg";
    private static final String notititle = "notititle";
    private static final String notitype = "notitype";
    private static final String pkey = "pkey";

    public Notification_Databasehandler(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS noti");
        db.execSQL("DROP TABLE IF EXISTS noticount");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS noti");
        db.execSQL("DROP TABLE IF EXISTS noticount");
        onCreate(db);
    }
    public void addnoti(String notitype1, String notititle1, String notimsg1, String notime1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(notitype, notitype1);
        values.put(notititle, notititle1);
        values.put(notimsg, notimsg1);
        values.put(notime, notime1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public ArrayList<String> getnoti() {
        ArrayList<String> arraylist = new ArrayList<>();
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM noti", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        sql.close();
        return arraylist;
    }
    public void deletenoti() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from noti");
        db.close();
    }

    public void deletenoti(String pkey1) {
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("pkey=");
        sb.append(pkey1);
        sb.append("");
        db.delete(TABLE_name1, sb.toString(), null);
    }

    public ArrayList<String> getnoti1(String limit) {
        ArrayList<String> arraylist = new ArrayList<>();
        SQLiteDatabase sql = getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM noti order by pkey desc LIMIT ");
        sb.append(limit);
        sb.append(",30");
        Cursor c = sql.rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public void update_count(String notimsg1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(notimsg, notimsg1);
        db.update(TABLE_name2, newValues, null, null);
    }

    public String get_count() {
        String link = "";
        SQLiteDatabase sql = getReadableDatabase();
        Cursor c = sql.rawQuery("SELECT  * FROM noticount", null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        sql.close();
        return link;
    }
    public void addcount(String notimsg1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(notimsg, notimsg1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }
    public void deletecount() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from noticount");
        db.close();
    }
}
