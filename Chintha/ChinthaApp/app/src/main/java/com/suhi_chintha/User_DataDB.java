package com.suhi_chintha;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class User_DataDB extends SQLiteOpenHelper {

 	private static final int DATABASE_VERSION =1;
	private static final String DATABASE_NAME = "chinthadb10";
	private static final String table1 = "user";
	private static final String userid = "userid";
	private static final String name = "name";
	private static final String mobile = "mobile";
	private static final String showmobile = "showmobile";
	private static final String imagesig = "imagesig";
	private static final String pkey= "pkey";
	public static String message="message";
	public User_DataDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	private static String CREATE_videoid_TABLE1 = "CREATE TABLE " + table1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+userid +" TEXT,"+name +" TEXT,"+mobile +" TEXT,"+showmobile +" TEXT,"+imagesig+" TEXT"+")";
	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL(CREATE_videoid_TABLE1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table1);
        onCreate(db);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table1);
		onCreate(db);
		
	}

	public String get_usermobile(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(3);
		}
		c.close();
		sql.close();
		return link;
	}
	void add_user(String userid1, String name1, String mobile1, String showmobile1, String imgsig1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(userid,userid1);
		values.put(name,name1);
		values.put(mobile,mobile1);
		values.put(showmobile,showmobile1);
		values.put(imagesig,imgsig1);
		db.insert(table1,null, values);
		db.close(); 
	}
	public ArrayList<String> get_user(){
        
		   ArrayList<String> arraylist = new ArrayList<String>();
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table1;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			   arraylist.add(c.getString(1));
			   arraylist.add(c.getString(2));
			   arraylist.add(c.getString(3));
			   arraylist.add(c.getString(4));
		    }
		    c.close();
		sql.close();
		    return arraylist;
	}

	void drop_user()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table1);
		db.close();
	
	}
	void update_imgsig(String imgsig1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(imagesig,imgsig1);
		db.update(table1, newValues, null, null);
		db.close();
	}
	void update_user(String id, String name1, String status1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(name, name1);
		newValues.put(showmobile, status1);
		db.update(table1, newValues, "userid="+id, null);
		db.close();
	}

	public String get_imgsig(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(5);
		}
		c.close();
		sql.close();
		return link;
	}
	public String get_username(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(2);
		}
		c.close();
		sql.close();
		return link;
	}

	public String get_userid(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		sql.close();
		return link;
	}
}