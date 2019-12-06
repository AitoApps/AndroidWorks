package com.zemose.regionadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE user(pkey INTEGER PRIMARY KEY AUTOINCREMENT,regionheadId TEXT,regionId TEXT,regionName TEXT,headname TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE fcmid(pkey INTEGER PRIMARY KEY AUTOINCREMENT,common TEXT)";
    private static final String DATABASE_NAME = "zemose_admin";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "user";
    private static final String TABLE_name2 = "fcmid";
    private static final String common = "common";
    private static final String headname = "headname";
    private static final String pkey = "pkey";
    private static final String regionId = "regionId";
    private static final String regionName = "regionName";
    private static final String regionheadId = "regionheadId";

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

    public void addregion(String regionheadId1, String regionId1, String headname1) {
        deleteregion();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(regionheadId, regionheadId1);
        values.put(regionId, regionId1);
        values.put(headname, headname1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String getheadname() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String getRegionId() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String getRegionheadId() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM user", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    /* access modifiers changed from: 0000 */
    public void deleteregion() {
        getWritableDatabase().execSQL("delete from user");
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
}
