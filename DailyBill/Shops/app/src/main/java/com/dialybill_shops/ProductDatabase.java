package com.dialybill_shops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProductDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "billmazaproducts";
    private static final int  DATABASE_VERSION= 2;
    private static final String TABLE_name1 = "products";
    private static final String pkey = "pkey";
    private static final String sn = "sn";
    private static final String itemid = "itemid";
    private static final String itemname = "itemname";
    private static final String price = "price";
    private static final String minimum= "minimum";
    private static final String minmumunit = "minmumunit";
    private static final String imgsig = "imgsig";
    private static final String status = "status";
    private static final String ogunit = "ogunit";

    private static String CREATE_TABLE1 = "CREATE TABLE " + TABLE_name1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+sn+" TEXT,"+itemid+" TEXT,"+itemname+" TEXT,"+price+" TEXT,"+minimum+" TEXT,"+minmumunit+" TEXT,"+imgsig+" TEXT,"+status+" TEXT,"+ogunit+" TEXT"+")";


    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_name1);
        onCreate(db);
    }

    public long getproductcount() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_name1);
    }

    public void product_basicinformupdate(String itemid1,String itemname1,String ogunit1,String imgsig1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(itemname,itemname1);
        values.put(imgsig,imgsig1);
        values.put(ogunit,ogunit1);
        db.update(TABLE_name1, values, itemid+"=?", new String[]{itemid1});
        db.close();
    }

    public void product_statusupdate(String sn1,String status1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(status,status1);
        db.update(TABLE_name1, values, sn+"=?", new String[]{sn1});
        db.close();
    }

    public void product_update(String sn1,String price1,String minimum1,String minmumunit1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(price,price1);
        values.put(minimum,minimum1);
        values.put(minmumunit,minmumunit1);
        db.update(TABLE_name1, values, sn+"=?", new String[]{sn1});
        db.close();
    }


    public void addproduct(String sn1,String itemid1,String itemname1,String price1,String minimum1,String minmumunit1,String imgsig1,String status1,String ogunit1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(sn, sn1);
        values.put(itemid, itemid1);
        values.put(itemname, itemname1);
        values.put(price, price1);
        values.put(minimum, minimum1);
        values.put(minmumunit, minmumunit1);
        values.put(imgsig, imgsig1);
        values.put(status, status1);
        values.put(ogunit,ogunit1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public ArrayList<String> getproducts() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM "+TABLE_name1, null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
            arraylist.add(c.getString(6));
            arraylist.add(c.getString(7));
            arraylist.add(c.getString(8));
            arraylist.add(c.getString(9));

        }
        c.close();
        return arraylist;
    }

    public void deleteproduct_byid(String id1) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_name1, "sn="+id1, null);
    }

    public void deleteproduct() {
        getWritableDatabase().execSQL("delete from "+TABLE_name1);
    }
}
