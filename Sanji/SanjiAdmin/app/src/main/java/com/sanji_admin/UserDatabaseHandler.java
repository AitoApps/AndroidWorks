package com.sanji_admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE user(pkey INTEGER PRIMARY KEY AUTOINCREMENT,uid TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,fcmid TEXT)";
    private static final String DATABASE_NAME = "sanjadmin";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String fcmid = "fcmid";
    public static String message = "message";
    private static final String pkey = "pkey";
    private static final String uid = "uid";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 2);
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

    public String get_uid() {
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

    /* access modifiers changed from: 0000 */
    public void adduser(String uid1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(uid, uid1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    /* access modifiers changed from: 0000 */
    public void deleteuser() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from user");
        db.close();
    }
}
