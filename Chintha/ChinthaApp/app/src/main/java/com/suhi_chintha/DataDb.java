package com.suhi_chintha;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataDb extends SQLiteOpenHelper {

 	private static final int DATABASE_VERSION =1;
	private static final String DATABASE_NAME = "chinthadb1";
	private static final String table3 = "addtofvrt";
	private static final String table9 = "fvrtcounts";
	private static final String table12 = "appvisible";
	private static final String table13 = "fvrtusers";
	private static final String table17 = "getfullcontact";
	private static final String userid = "userid";
	private static final String name = "name";
	private static final String mobile = "mobile";
	private static final String statusid = "statusid";
	private static final String status= "status";
	private static final String showmobile = "showmobile";
	private static final String pkey= "pkey";
	public static String message="message";
	public static String notification="noti";
	public static String fvr_status ="fvrstatus";
	public static String fvrstatususerid="statususerid";
	public static String fvr_userid ="fvruserid";
	public static String fvrusername="fvrusername";
	public static String fvrstatustext="fvrstatustext";
	private static final String common = "common";
	
	public DataDb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	private static String CREATE_videoid_TABLE3 = "CREATE TABLE " + table3 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+statusid +" TEXT,"+userid +" TEXT,"+name +" TEXT,"+mobile+" TEXT,"+status +" TEXT,"+showmobile+" TEXT"+")";
	private static String CREATE_videoid_TABLE9 = "CREATE TABLE " + table9 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ fvr_userid +" TEXT,"+ fvr_status +" TEXT,"+fvrstatususerid +" TEXT,"+fvrusername +" TEXT,"+fvrstatustext+" TEXT"+")";
	private static String CREATE_videoid_TABLE12 = "CREATE TABLE " + table12 + "("+notification+" TEXT"+")";
	private static String CREATE_videoid_TABLE13 = "CREATE TABLE " + table13 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+userid+" TEXT,"+name+" TEXT"+")";
	private static String CREATE_videoid_TABLE17 = "CREATE TABLE " + table17 + "("+common+" TEXT"+")";
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + table3);
		db.execSQL("DROP TABLE IF EXISTS " + table9);
		db.execSQL("DROP TABLE IF EXISTS " + table12);
		db.execSQL("DROP TABLE IF EXISTS " + table13);
		db.execSQL("DROP TABLE IF EXISTS " + table17);
        onCreate(db);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_videoid_TABLE3);
		db.execSQL(CREATE_videoid_TABLE9);
		db.execSQL(CREATE_videoid_TABLE12);
		db.execSQL(CREATE_videoid_TABLE13);
		db.execSQL(CREATE_videoid_TABLE17);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + table3);
		db.execSQL("DROP TABLE IF EXISTS " + table9);
		db.execSQL("DROP TABLE IF EXISTS " + table12);
		db.execSQL("DROP TABLE IF EXISTS " + table13);
		db.execSQL("DROP TABLE IF EXISTS " + table17);
		onCreate(db);
		
	}
	
	void add_vada(String message1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(common,message1);
		db.insert(table17,null, values);
		db.close(); 
	}
	public ArrayList<String> get_vada(){
        
		   ArrayList<String> arraylist = new ArrayList<String>();
		   SQLiteDatabase sql=this.getReadableDatabase();
			String query = "SELECT  * FROM " + table17;
		    Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			   arraylist.add(c.getString(0));
			 
		    }
		    c.close();
		    return arraylist;
	}

	void deletevada()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table17);
	}


	public void add_fvrtuser(String userid1, String name1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(userid,userid1);
		values.put(name,name1);
		db.insert(table13,null, values);
		db.close(); 
	}
	public ArrayList<String> get_fvrtusr1(String limit){
        
		   ArrayList<String> arraylist = new ArrayList<String>();
		   SQLiteDatabase sql=this.getReadableDatabase();
			String query = "SELECT  * FROM " + table13 +" order by pkey desc LIMIT "+limit+",30";
		    Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			   arraylist.add(c.getString(1));
			   arraylist.add(c.getString(2));
		    }
		    c.close();
		    return arraylist;
	}
	public ArrayList<String> get_fvrt(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table13 +" order by pkey desc";
		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			arraylist.add(c.getString(1));
		}
		c.close();
		return arraylist;
	}
	void deleteaddfvrtuser()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table13);
	}
	public void del_fvrtusr(String userid1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table13, userid+"='"+userid1+"'", null);
	
	}
	
    public String getfvr_usr(String id){
        
		String link="";	    
		SQLiteDatabase sql=this.getReadableDatabase();	
		Cursor c=sql.rawQuery("SELECT  * FROM " + table13 +" WHERE userid=?", new String[]{id});
	   while(c.moveToNext()) {
		   link=c.getString(1);
	    }
	    c.close();
	    return link;
	}



	void add_visible(String notification1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notification,notification1);
		db.insert(table12,null, values);
		db.close(); 
	}
	public String get_visible(){
           String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table12;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
	}

	void del_visible()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table12);
	}



}