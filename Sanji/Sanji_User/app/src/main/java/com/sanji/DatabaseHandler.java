package com.sanji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String CREATE_videoid_TABLE1 = "CREATE TABLE productcat(pkey INTEGER PRIMARY KEY AUTOINCREMENT,common TEXT)";
    private static String CREATE_videoid_TABLE2 = "CREATE TABLE scwidth(pkey INTEGER PRIMARY KEY AUTOINCREMENT,screenwidth TEXT)";
    private static final String DATABASE_NAME = "sanji1";
    private static final int DATABASE_VERSION = 21;
    private static final String TABLE_name1 = "productcat";
    private static final String TABLE_name2 = "scwidth";
    private static final String common = "common";
    private static final String pkey = "pkey";
    private static final String screenwidth = "screenwidth";
    private String CREATE_videoid_TABLE10;
    private String CREATE_videoid_TABLE11;
    private String CREATE_videoid_TABLE12;
    private String CREATE_videoid_TABLE3;
    private String CREATE_videoid_TABLE4;
    private String CREATE_videoid_TABLE5;
    private String CREATE_videoid_TABLE6;
    private String CREATE_videoid_TABLE7;
    private String CREATE_videoid_TABLE8;
    private String CREATE_videoid_TABLE9;
    private final String TABLE_name10 = "homeads";
    private final String TABLE_name11 = "carts";
    private final String TABLE_name12 = "locationtable";
    private final String TABLE_name3 = "maincatogery";
    private final String TABLE_name4 = "productdetails";
    private final String TABLE_name5 = "vouchergenerate";
    private final String TABLE_name6 = "selectaddtess";
    private final String TABLE_name7 = "myorderselecttab";
    private final String TABLE_name8 = "youtubeid";
    private final String TABLE_name9 = "offernoti";
    private final String adsid;
    private final String adsimgsig;
    private final String adsmessage;
    private final String adsofferid;
    private final String adstitle;
    private final String cart_delicharge;
    private final String cart_delidisc;
    private final String cart_finaldelicharge;
    private final String cart_imgsig;
    private final String cart_miniordramt;
    private final String cart_minqty;
    private final String cart_nettotal;
    private final String cart_price;
    private final String cart_productid;
    private final String cart_productname;
    private final String cart_qty;
    public final String cart_shopid;
    private final String cart_shopimgsig;
    private final String cart_shopname;
    private final String cart_totalprice;
    private final String cart_unittype;
    public final String cat_imgsig;
    public final String cat_sn;
    public final String cat_title;
    private final String commontitle;
    private final String latitude;
    private final String locaddress;
    private final String longtitude;
    private final String txt_dlcharge;
    private final String txt_dldisc;
    private final String txt_dltype;
    private final String txt_imgsig;
    private final String txt_intervel;
    private final String txt_itemdiscription;
    private final String txt_itemname;
    private final String txt_maxorder;
    private final String txt_minimumorder;
    private final String txt_orginalprice;
    private final String txt_price;
    private final String txt_productsubcat;
    private final String txt_shopid;
    private final String txt_sn;
    private final String txt_unittype;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 21);
        String str = "sn";
        cat_sn = str;
        cat_title = "title";
        String str2 = "imgsig";
        cat_imgsig = str2;
        txt_sn = str;
        txt_dltype = "dltype";
        txt_dlcharge = "dlcharge";
        txt_dldisc = "dldisc";
        txt_productsubcat = "productsubcat";
        txt_shopid = "shopid";
        txt_itemname = "itemname";
        txt_unittype = "unittype";
        String str3 = "price";
        txt_price = str3;
        txt_itemdiscription = "itemdiscription";
        txt_imgsig = str2;
        txt_orginalprice = "orginalprice";
        txt_minimumorder = "minimumorder";
        txt_maxorder = "maxorder";
        txt_intervel = "intervel";
        latitude = "latitude";
        longtitude = "longtitude";
        locaddress = "locaddress";
        commontitle = "commontitle";
        adsid = "adsid";
        adsofferid = "adsofferid";
        adstitle = "adstitle";
        adsmessage = "adsmessage";
        adsimgsig = "adsimgsig";
        cart_shopid = "cart_shopid";
        cart_productid = "productid";
        cart_productname = "productname";
        cart_price = str3;
        cart_qty = "qty";
        cart_totalprice = "totalprice";
        cart_imgsig = "cart_imgsig";
        cart_minqty = "cart_minqty";
        cart_unittype = "cart_unittype";
        cart_shopname = "cart_shopname";
        cart_delicharge = "cart_delicharge";
        cart_delidisc = "cart_delidisc";
        cart_nettotal = "cart_nettotal";
        cart_finaldelicharge = "cart_finaldelicharge";
        cart_miniordramt = "cart_miniordramt";
        cart_shopimgsig = "cart_shopimgsig";
        CREATE_videoid_TABLE3 = "CREATE TABLE maincatogery(pkey INTEGER PRIMARY KEY AUTOINCREMENT,sn TEXT,title TEXT,imgsig TEXT)";
        CREATE_videoid_TABLE4 = "CREATE TABLE productdetails(pkey INTEGER PRIMARY KEY AUTOINCREMENT,sn TEXT,productsubcat TEXT,shopid TEXT,itemname TEXT,unittype TEXT,price TEXT,itemdiscription TEXT,imgsig TEXT,orginalprice TEXT,minimumorder TEXT,maxorder TEXT,intervel TEXT,dltype TEXT,dlcharge TEXT,dldisc TEXT)";
        CREATE_videoid_TABLE5 = "CREATE TABLE vouchergenerate(pkey INTEGER PRIMARY KEY AUTOINCREMENT,common TEXT)";
        CREATE_videoid_TABLE6 = "CREATE TABLE selectaddtess(pkey INTEGER PRIMARY KEY AUTOINCREMENT,commontitle TEXT)";
        CREATE_videoid_TABLE7 = "CREATE TABLE myorderselecttab(commontitle TEXT)";
        CREATE_videoid_TABLE8 = "CREATE TABLE youtubeid(commontitle TEXT)";
        CREATE_videoid_TABLE9 = "CREATE TABLE offernoti(pkey INTEGER PRIMARY KEY AUTOINCREMENT,sn TEXT,title TEXT,imgsig TEXT)";
        CREATE_videoid_TABLE10 = "CREATE TABLE homeads(pkey INTEGER PRIMARY KEY AUTOINCREMENT,adsid TEXT,adsofferid TEXT,adstitle TEXT,adsmessage TEXT,adsimgsig TEXT)";
        CREATE_videoid_TABLE11 = "CREATE TABLE carts(pkey INTEGER PRIMARY KEY AUTOINCREMENT,cart_shopid TEXT,productid TEXT,productname TEXT,price TEXT,qty TEXT,totalprice TEXT,cart_imgsig TEXT,cart_minqty TEXT,cart_unittype TEXT,cart_shopname TEXT,cart_delicharge TEXT,cart_delidisc TEXT,cart_nettotal TEXT,cart_finaldelicharge TEXT,cart_miniordramt TEXT,cart_shopimgsig TEXT)";
        CREATE_videoid_TABLE12 = "CREATE TABLE locationtable(pkey INTEGER PRIMARY KEY AUTOINCREMENT,latitude TEXT,longtitude TEXT,locaddress TEXT)";
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_videoid_TABLE1);
        db.execSQL(CREATE_videoid_TABLE2);
        db.execSQL(CREATE_videoid_TABLE3);
        db.execSQL(CREATE_videoid_TABLE4);
        db.execSQL(CREATE_videoid_TABLE5);
        db.execSQL(CREATE_videoid_TABLE6);
        db.execSQL(CREATE_videoid_TABLE7);
        db.execSQL(CREATE_videoid_TABLE8);
        db.execSQL(CREATE_videoid_TABLE9);
        db.execSQL(CREATE_videoid_TABLE10);
        db.execSQL(CREATE_videoid_TABLE11);
        db.execSQL(CREATE_videoid_TABLE12);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productcat");
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS maincatogery");
        db.execSQL("DROP TABLE IF EXISTS productdetails");
        db.execSQL("DROP TABLE IF EXISTS vouchergenerate");
        db.execSQL("DROP TABLE IF EXISTS selectaddtess");
        db.execSQL("DROP TABLE IF EXISTS myorderselecttab");
        db.execSQL("DROP TABLE IF EXISTS youtubeid");
        db.execSQL("DROP TABLE IF EXISTS offernoti");
        db.execSQL("DROP TABLE IF EXISTS homeads");
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS locationtable");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productcat");
        db.execSQL("DROP TABLE IF EXISTS scwidth");
        db.execSQL("DROP TABLE IF EXISTS maincatogery");
        db.execSQL("DROP TABLE IF EXISTS productdetails");
        db.execSQL("DROP TABLE IF EXISTS vouchergenerate");
        db.execSQL("DROP TABLE IF EXISTS selectaddtess");
        db.execSQL("DROP TABLE IF EXISTS myorderselecttab");
        db.execSQL("DROP TABLE IF EXISTS youtubeid");
        db.execSQL("DROP TABLE IF EXISTS offernoti");
        db.execSQL("DROP TABLE IF EXISTS homeads");
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS locationtable");
        onCreate(db);
    }

    public void addlocation(String latitude1, String longtitude1, String locaddress1) {
        deletelocation();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("latitude", latitude1);
        values.put("longtitude", longtitude1);
        values.put("locaddress", locaddress1);
        db.insert("locationtable", null, values);
        db.close();
    }

    public String get_locationaddress() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM locationtable", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public String get_latitude() {
        String link = "0";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM locationtable", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public String get_longtitude() {
        String link = "0";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM locationtable", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public void deletelocation() {
        getWritableDatabase().execSQL("delete from locationtable");
    }

    public void updateloc_adress(String locaddress1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("locaddress", locaddress1);
        db.update("locationtable", values, null, null);
        db.close();
    }

    public void addcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_minqty1, String cart_unittype1, String cart_shopname1, String delicharge1, String delidisc1, String cart_finaldelicharge1, String cart_miniordramt1, String cart_shopimgsig1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cart_shopid", cart_shopid1);
        values.put("productid", cart_productid1);
        values.put("productname", cart_productname1);
        values.put("price", cart_price1);
        values.put("qty", cart_qty1);
        values.put("totalprice", cart_totalprice1);
        values.put("cart_imgsig", cart_imgsig1);
        values.put("cart_minqty", cart_minqty1);
        values.put("cart_unittype", cart_unittype1);
        values.put("cart_shopname", cart_shopname1);
        values.put("cart_delicharge", delicharge1);
        values.put("cart_delidisc", delidisc1);
        values.put("cart_nettotal", "0");
        values.put("cart_finaldelicharge", cart_finaldelicharge1);
        values.put("cart_miniordramt", cart_miniordramt1);
        values.put("cart_shopimgsig", cart_shopimgsig1);
        db.insert("carts", null, values);
        db.close();
    }

    public ArrayList<String> getcheck_minordrqty() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM carts group by cart_shopid", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(10));
            arraylist.add(c.getString(13));
            arraylist.add(c.getString(15));
        }
        c.close();
        return arraylist;
    }

    public void addcart_update(String cart_qty1, String total1, String productid1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("qty", cart_qty1);
        StringBuilder sb = new StringBuilder();
        sb.append(Float.parseFloat(total1));
        sb.append("");
        values.put("totalprice", sb.toString());
        db.update("carts", values, "productid = ?", new String[]{productid1});
        db.close();
    }

    public void addcart_exitupdate(String cart_qty1, String total1, String productid1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.parseInt(get_cartqty(productid1)) + Integer.parseInt(cart_qty1));
        String str = "";
        sb.append(str);
        values.put("qty", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Float.parseFloat(get_carttotalprice(productid1)) + Float.parseFloat(total1));
        sb2.append(str);
        values.put("totalprice", sb2.toString());
        db.update("carts", values, "productid = ?", new String[]{productid1});
        db.close();
    }

    public String get_cartqty(String productid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE productid=?", new String[]{productid1});
        while (c.moveToNext()) {
            link = c.getString(5);
        }
        c.close();
        return link;
    }

    public String get_carttotalprice(String productid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE productid=?", new String[]{productid1});
        while (c.moveToNext()) {
            link = c.getString(6);
        }
        c.close();
        return link;
    }

    public int get_totalqty() {
        int link = 0;
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM carts", null);
        while (c.moveToNext()) {
            link += Integer.parseInt(c.getString(5));
        }
        c.close();
        return link;
    }

    public ArrayList<String> getcart_byshopid(String shopid) {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE cart_shopid=?", new String[]{shopid});
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
            arraylist.add(c.getString(9));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<String> getmain_cart() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM carts group by cart_shopid", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(10));
            arraylist.add(c.getString(12));
            arraylist.add(c.getString(13));
            arraylist.add(c.getString(14));
            arraylist.add(c.getString(15));
            arraylist.add(c.getString(16));
        }
        c.close();
        return arraylist;
    }

    public ArrayList<String> getcart_fordelicalc() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  cart_shopid,cart_delicharge,sum(totalprice) as totalp FROM carts group by cart_shopid", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(0));
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
        }
        c.close();
        return arraylist;
    }

    public void update_finalcharges(String shopid1, String dcharge1, String nettotal1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cart_finaldelicharge", dcharge1);
        values.put("cart_nettotal", nettotal1);
        db.update("carts", values, "cart_shopid = ?", new String[]{shopid1});
        db.close();
    }

    public ArrayList<String> getcart1() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts ORDER BY cart_shopid ASC", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
            arraylist.add(c.getString(6));
            arraylist.add(c.getString(8));
            arraylist.add(c.getString(9));
            arraylist.add(c.getString(13));
            arraylist.add(c.getString(14));
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

    public String getcartexist(String productid1, String shopid1) {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts WHERE productid=? and cart_shopid=?", new String[]{productid1, shopid1});
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public float get_cartgrandtotal() {
        float link = 0.0f;
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM carts", null);
        while (c.moveToNext()) {
            link += Float.parseFloat(c.getString(6)) + Float.parseFloat(c.getString(14));
        }
        c.close();
        return link;
    }

    public void addhomeads(String adsid1, String adsofferid1, String adstitle1, String adsmessage1, String adsimgsig1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("adsid", adsid1);
        values.put("adsofferid", adsofferid1);
        values.put("adstitle", adstitle1);
        values.put("adsmessage", adsmessage1);
        values.put("adsimgsig", adsimgsig1);
        db.insert("homeads", null, values);
        db.close();
    }

    public ArrayList<String> get_homeads() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM homeads", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
            arraylist.add(c.getString(5));
        }
        c.close();
        return arraylist;
    }

    public void deletehomeads() {
        getWritableDatabase().execSQL("delete from homeads");
    }

    public void addoffernoti(String cat_sn1, String cat_title1, String cat_imgsig1) {
        deleteoffernoti();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sn", cat_sn1);
        values.put("title", cat_title1);
        values.put("imgsig", cat_imgsig1);
        db.insert("offernoti", null, values);
        db.close();
    }

    public String getoffer_id() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM offernoti", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public String getoffer_title() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM offernoti", null);
        while (c.moveToNext()) {
            link = c.getString(2);
        }
        c.close();
        return link;
    }

    public String getoffer_messaage() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM offernoti", null);
        while (c.moveToNext()) {
            link = c.getString(3);
        }
        c.close();
        return link;
    }

    public void deleteoffernoti() {
        getWritableDatabase().execSQL("delete from offernoti");
    }

    public void addyoutubelink(String commontitle1) {
        deleteyoutubelink();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commontitle", commontitle1);
        db.insert("youtubeid", null, values);
        db.close();
    }

    public String getyoutubelink() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM youtubeid", null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public void deleteyoutubelink() {
        getWritableDatabase().execSQL("delete from youtubeid");
    }

    public void addordertab(String commontitle1) {
        deleteordertab();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commontitle", commontitle1);
        db.insert("myorderselecttab", null, values);
        db.close();
    }

    public String getordertab() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM myorderselecttab", null);
        while (c.moveToNext()) {
            link = c.getString(0);
        }
        c.close();
        return link;
    }

    public void deleteordertab() {
        getWritableDatabase().execSQL("delete from myorderselecttab");
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

    public void addvouchergen(String common1) {
        deletevouchergen();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(common, common1);
        db.insert("vouchergenerate", null, values);
        db.close();
    }

    public String getvouchergen() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM vouchergenerate", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletevouchergen() {
        getWritableDatabase().execSQL("delete from vouchergenerate");
    }

    public void add_productdetails(String txt_sn1, String txt_productsubcat1, String txt_shopid1, String txt_itemname1, String txt_unittype1, String txt_price1, String txt_itemdiscription1, String txt_imgsig1, String txt_orginalprice1, String txt_minimumorder1, String txt_maxorder1, String txt_intervel1, String dltype1, String dlcharge1, String txt_dldisc1) {
        delete_productdetails();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sn", txt_sn1);
        values.put("productsubcat", txt_productsubcat1);
        values.put("shopid", txt_shopid1);
        values.put("itemname", txt_itemname1);
        values.put("unittype", txt_unittype1);
        values.put("price", txt_price1);
        values.put("itemdiscription", txt_itemdiscription1);
        values.put("imgsig", txt_imgsig1);
        values.put("orginalprice", txt_orginalprice1);
        values.put("minimumorder", txt_minimumorder1);
        values.put("maxorder", txt_maxorder1);
        values.put("intervel", txt_intervel1);
        values.put("dltype", dltype1);
        values.put("dlcharge", dlcharge1);
        values.put("dldisc", txt_dldisc1);
        db.insert("productdetails", null, values);
        db.close();
    }

    public ArrayList<String> get_productdetails() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM productdetails", null);
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
            arraylist.add(c.getString(10));
            arraylist.add(c.getString(11));
            arraylist.add(c.getString(12));
            arraylist.add(c.getString(13));
            arraylist.add(c.getString(14));
            arraylist.add(c.getString(15));
        }
        c.close();
        return arraylist;
    }

    public void delete_productdetails() {
        getWritableDatabase().execSQL("delete from productdetails");
    }

    public void addproductcatlist(String cat_sn1, String cat_title1, String cat_imgsig1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sn", cat_sn1);
        values.put("title", cat_title1);
        values.put("imgsig", cat_imgsig1);
        db.insert("maincatogery", null, values);
        db.close();
    }

    public ArrayList<String> getproductcatlist() {
        ArrayList<String> arraylist = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM maincatogery", null);
        while (c.moveToNext()) {
            arraylist.add(c.getString(1));
            arraylist.add(c.getString(2));
            arraylist.add(c.getString(3));
        }
        c.close();
        return arraylist;
    }

    public void deleteproductcatlist() {
        getWritableDatabase().execSQL("delete from maincatogery");
    }

    public void addproductcat(String common1) {
        deleteproductcat();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(common, common1);
        db.insert(TABLE_name1, null, values);
        db.close();
    }

    public String getproductcat() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM productcat", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deleteproductcat() {
        getWritableDatabase().execSQL("delete from productcat");
    }

    public void addscreenwidth(String screenwidth1) {
        deletescreenwidth();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(screenwidth, screenwidth1);
        db.insert(TABLE_name2, null, values);
        db.close();
    }

    public String getscreenwidth() {
        String link = "";
        Cursor c = getReadableDatabase().rawQuery("SELECT  * FROM scwidth", null);
        while (c.moveToNext()) {
            link = c.getString(1);
        }
        c.close();
        return link;
    }

    public void deletescreenwidth() {
        getWritableDatabase().execSQL("delete from scwidth");
    }
}
