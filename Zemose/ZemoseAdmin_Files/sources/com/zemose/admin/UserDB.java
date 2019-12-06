package com.zemose.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE user(pkey INTEGER PRIMARY KEY AUTOINCREMENT,common TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,common TEXT)";
    private static final String DATABASE_NAME = "zemose_admin";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String common = "common";
    private static final String pkey = "pkey";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS fcmid");
        onCreate(db);
    }

    public void addfcmid(String fcmid1) {
        deletefcm();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(common, fcmid1);
        db.insert(TABLE_name2, null, values);
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

    /* access modifiers changed from: 0000 */
    public void deletefcm() {
        getWritableDatabase().execSQL("delete from fcmid");
    }

    /* access modifiers changed from: 0000 */
    public void adduser(String userid1) {
        deleteuser();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(common, userid1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String getuserid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    /* access modifiers changed from: 0000 */
    public void deleteuser() {
        getWritableDatabase().execSQL("delete from user");
    }
}
