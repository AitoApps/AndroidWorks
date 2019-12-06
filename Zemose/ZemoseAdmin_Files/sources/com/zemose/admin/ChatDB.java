package com.zemose.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class ChatDB extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE chatmessage(pkey INTEGER PRIMARY KEY AUTOINCREMENT,chattime TEXT,userid TEXT,username TEXT,message TEXT,chattype TEXT,readstatus TEXT,issupplier TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE isopened(pkey INTEGER PRIMARY KEY AUTOINCREMENT,common TEXT)";
    private static final String DATABASE_NAME = "zemose_chatting";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_name1 = "chatmessage";
    private static final String TABLE_name2 = "isopened";
    private static final String chattime = "chattime";
    private static final String chattype = "chattype";
    private static final String common = "common";
    private static final String issupplier = "issupplier";
    private static final String message = "message";
    private static final String pkey = "pkey";
    private static final String readstatus = "readstatus";
    private static final String userid = "userid";
    private static final String username = "username";

    public ChatDB(Context context) {
        super(context, DATABASE_NAME, null, 7);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS chatmessage");
        db.execSQL("DROP TABLE IF EXISTS isopened");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS chatmessage");
        db.execSQL("DROP TABLE IF EXISTS isopened");
        onCreate(db);
    }

    public void add_isopened(String fcmid1) {
        delete_isopened();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(common, fcmid1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }

    public String get_isopened() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM isopened", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    /* access modifiers changed from: 0000 */
    public void delete_isopened() {
        getWritableDatabase().execSQL("delete from isopened");
    }

    public void add_chatmsg(String chattime1, String userid1, String username1, String message1, String readstatus1, String chattype1, String issupplier1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(chattime, chattime1);
        values.put(userid, userid1);
        values.put("username", username1);
        values.put("message", message1);
        values.put(chattype, chattype1);
        values.put(readstatus, readstatus1);
        values.put(issupplier, issupplier1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public void updatereadstatus(String userid1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(readstatus, "1");
        db.update(TABLE_name1, newValues, "userid = ?", new String[]{userid1});
    }

    public String get_lastmsg(String userid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  message FROM chatmessage WHERE issupplier=?", new String[]{userid1});
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public String get_unreadcount(String issupplier1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  count(pkey) FROM chatmessage WHERE issupplier=? and readstatus='0'", new String[]{issupplier1});
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public void updatereadcount(String id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(readstatus, "1");
        db.update(TABLE_name1, newValues, "userid = ?", new String[]{id});
    }

    public String get_chatmsg() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM chatmessage", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public ArrayList<String> get_chatheads(String limit) {
        ArrayList<String> arraylist = new ArrayList<>();
        SQLiteDatabase sql = getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM chatmessage group by issupplier order by pkey desc LIMIT ");
        sb.append(limit);
        sb.append(",100");
        Cursor c = sql.rawQuery(sb.toString(), null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(7));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<String> get_chathistory_last(String userid1) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM chatmessage WHERE userid=? order by pkey desc limit 1", new String[]{userid1});
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<String> get_chathistory(String userid1) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM chatmessage WHERE issupplier=? order by pkey", new String[]{userid1});
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
        }
        c.close();
        return arraylist;
    }

    /* access modifiers changed from: 0000 */
    public void deletefcm() {
        getWritableDatabase().execSQL("delete from chatmessage");
    }
}
