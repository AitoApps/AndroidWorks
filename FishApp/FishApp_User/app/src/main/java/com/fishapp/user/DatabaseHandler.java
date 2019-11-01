package com.fishapp.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iggluefishapp";
    private static final int DATABASE_VERSION = 3;
    private static final String pkey = "pkey";

    private final String TABLE_name1 = "carts";
    private final String TABLE_name2 = "selectaddtess";
    private final String TABLE_name3 = "orderfirstshow";
    public final String cart_fishid = "cart_fishid";
    private final String cart_fishname = "cart_fishname";
    private final String cart_imgsig = "cart_imgsig";
    private final String cart_ogprice = "cart_ogprice";
    private final String cart_ogqty = "cart_ogqty";
    private final String cart_ogunit = "cart_ogunit";
    private final String cart_qty = "cart_qty";
    private final String cart_totalprice = "cart_totalprice";
    public final String commontitle = "commontitle";

    private String CREATE_videoid_TABLE1 = "CREATE TABLE carts(pkey INTEGER PRIMARY KEY AUTOINCREMENT,cart_fishid TEXT,cart_fishname TEXT,cart_qty TEXT,cart_totalprice TEXT,cart_imgsig TEXT,cart_ogunit TEXT,cart_ogqty TEXT,cart_ogprice TEXT)";
    private String CREATE_videoid_TABLE2 = "CREATE TABLE selectaddtess(pkey INTEGER PRIMARY KEY AUTOINCREMENT,commontitle TEXT)";
    private String CREATE_videoid_TABLE3 = "CREATE TABLE orderfirstshow(pkey INTEGER PRIMARY KEY AUTOINCREMENT,commontitle TEXT)";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE3);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS selectaddtess");
        db.execSQL("DROP TABLE IF EXISTS orderfirstshow");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS selectaddtess");
        db.execSQL("DROP TABLE IF EXISTS orderfirstshow");
        onCreate(db);
    }

    public void addorderfirstshow(String common1) {
        deleteorderfirstshow();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commontitle", common1);
        db.insert("orderfirstshow", null, values);
        db.close();
    }

    public String getorderfirstshow() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM orderfirstshow", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deleteorderfirstshow() {
        getWritableDatabase().execSQL("delete from orderfirstshow");
    }

    public void addselectaddress(String commontitle1) {
        deleteselectaddress();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commontitle", commontitle1);
        db.insert("selectaddtess", null, values);
        db.close();
    }

    public String getselectaddress() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM selectaddtess", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deleteselectaddress() {
        getWritableDatabase().execSQL("delete from selectaddtess");
    }

    public void addcart(String cart_fishid1, String cart_fishname1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_ogunit1, String cart_ogqty1, String cart_ogprice1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cart_fishid", cart_fishid1);
        values.put("cart_fishname", cart_fishname1);
        values.put("cart_qty", cart_qty1);
        values.put("cart_totalprice", cart_totalprice1);
        values.put("cart_imgsig", cart_imgsig1);
        values.put("cart_ogunit", cart_ogunit1);
        values.put("cart_ogqty", cart_ogqty1);
        values.put("cart_ogprice", cart_ogprice1);
        db.insert("carts", null, values);
        db.close();
    }

    public void addcart_update(String pkey1, String cart_qty1, String cart_totalprice1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cart_qty", cart_qty1);
        values.put("cart_totalprice", cart_totalprice1);
        db.update("carts", values, "pkey = ?", new String[]{pkey1});
        db.close();
    }

    public void addcart_existupdate(String fishid1, String cart_qty1, String cart_totalprice1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cart_qty",Float.parseFloat(get_cartqty(fishid1)) + Float.parseFloat(cart_qty1)+"");
        values.put("cart_totalprice",Float.parseFloat(get_carttotalprice(fishid1)) + Float.parseFloat(cart_totalprice1)+"");
        db.update("carts", values, "cart_fishid = ?", new String[]{fishid1});
        db.close();
    }

    public String get_cartexist(String fishid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE cart_fishid=?", new String[]{fishid1});
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public String get_cartqty(String fishid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE cart_fishid=?", new String[]{fishid1});
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String get_carttotalprice(String fishid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE cart_fishid=?", new String[]{fishid1});
        while (c.moveToNext()) {
            link = c.getString(4);
        }
        c.close();
        return link;
    }

    public ArrayList<String> getcart_pkey(String pkey1) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE pkey=?", new String[]{pkey1});
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

    public ArrayList<String> getcart() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts ORDER BY pkey DESC", null);
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

    public ArrayList<String> getcart1() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts ORDER BY pkey ASC", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
        }
        c.close();
        return arraylist;
    }

    public long getcartcount() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), "carts");
    }

    public void deletecart() {
        getWritableDatabase().execSQL("delete from carts");
    }

    public void deletecart_byid(String id1) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("carts", "pkey="+id1, null);
    }

    public float get_cartgrandtotal() {
        float link = 0.0f;
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts", null);
        while (c.moveToNext()) {
            link += Float.parseFloat(c.getString(4));
        }
        c.close();
        return link;
    }
}
