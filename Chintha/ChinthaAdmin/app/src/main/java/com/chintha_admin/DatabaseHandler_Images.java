package com.chintha_admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler_Images extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE datas(pkey INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT)";
    private static final String DATABASE_NAME = "din";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_name1 = "datas";
    private static final String pkey = "pkey";
    private static final String title = "title";

    public DatabaseHandler_Images(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS datas");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS datas");
        onCreate(db);
    }

    public void addfcmid(String title1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, title1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String getfcmid() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM datas", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletefcmid() {
        getWritableDatabase().execSQL("delete from datas");
    }

    public ArrayList<String> getfcmids() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM datas", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
        }
        c.close();
        return arraylist;
    }

    public void deletefcmid(String pkey1) {
        SQLiteDatabase db = getWritableDatabase();
        String str = TABLE_name1;
        StringBuilder sb = new StringBuilder();
        sb.append("pkey=");
        sb.append(pkey1);
        db.delete(str, sb.toString(), null);
    }
}
