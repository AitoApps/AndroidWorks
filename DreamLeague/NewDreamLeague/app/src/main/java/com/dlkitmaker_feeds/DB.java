package com.dlkitmaker_feeds;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

 	private static final int DATABASE_VERSION =5;
	private static final String DATABASE_NAME = "dream_db";
	private static final String TABLE_name1 = "scwidth";
	private static final String TABLE_name2 = "teamlist";
	private static final String TABLE_name3= "kit_theme";
	private static final String TABLE_name4= "mykits";
	private static final String TABLE_name5= "topisss";

 	private static final String pkey= "pkey";
	private static final String screenwidth = "screenwidth";
	private static final String team_id = "teamid";
	private static final String team_kitname = "kitname";
	private static final String team_imgurl = "imgurl";
	private static final String common = "common";
	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	private static String CREATE_videoid_TABLE1 = "CREATE TABLE " + TABLE_name1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT"+")";
	private static String CREATE_videoid_TABLE2 = "CREATE TABLE " + TABLE_name2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+team_id +" TEXT,"+team_kitname +" TEXT,"+team_imgurl+" TEXT"+")";
	private static String CREATE_videoid_TABLE3 = "CREATE TABLE " + TABLE_name3+ "("+common+" TEXT"+")";
	private static String CREATE_videoid_TABLE4 = "CREATE TABLE " + TABLE_name4+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+common+" TEXT"+")";
	private static String CREATE_videoid_TABLE5 = "CREATE TABLE " + TABLE_name5+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+common+" TEXT"+")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_videoid_TABLE1);
		db.execSQL(CREATE_videoid_TABLE2);
		db.execSQL(CREATE_videoid_TABLE3);
		db.execSQL(CREATE_videoid_TABLE4);
		db.execSQL(CREATE_videoid_TABLE5);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name1);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name2);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name3);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name4);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name5);
        onCreate(db);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name1);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name2);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name3);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name4);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_name5);
		onCreate(db);
	}


	public void add_mykits(String common1)
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(common,common1);
		db.insert(TABLE_name4,null, values);
		db.close();
	}
	public ArrayList<String> get_mykits(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + TABLE_name4+" order by pkey desc",null);
		while(c.moveToNext()) {

			arraylist.add(c.getString(1));

		}
		c.close();
		return arraylist;
	}

	public void delete_mykits()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+TABLE_name4);
	}

	public void add_kittheme(String common1)
	{
		delete_kittheme();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(common,common1);
		db.insert(TABLE_name3,null, values);
		db.close();
	}
	public String get_kittheme(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + TABLE_name3;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}

	public void delete_kittheme()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+TABLE_name3);
	}

	public void add_teamlist(String team_cat1, String team_id1, String team_name1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(team_id,team_cat1);
		values.put(team_kitname,team_id1);
		values.put(team_imgurl,team_name1);
		db.insert(TABLE_name2,null, values);
		db.close();
	}
	public ArrayList<String> get_teamlist1(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + TABLE_name2,null);
		while(c.moveToNext()) {

			arraylist.add(c.getString(2));
			arraylist.add(c.getString(3));

		}
		c.close();
		return arraylist;
	}
	public ArrayList<String> get_teamlist(String teamid1){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + TABLE_name2 +" WHERE teamid=?", new String[]{teamid1});
		while(c.moveToNext()) {

			arraylist.add(c.getString(2));
			arraylist.add(c.getString(3));

		}
		c.close();
		return arraylist;
	}

	public void delete_teamlist()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_name2);
	}



	public void addscreenwidth(String screenwidth1)
	{
		deletescreenwidth();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(screenwidth,screenwidth1);
		db.insert(TABLE_name1,null, values);
		db.close();
	}
	public String getscreenwidth(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + TABLE_name1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void deletescreenwidth()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_name1);
	}


	public void addcommon(String common1)
	{
		deletecommon();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(common,common1);
		db.insert(TABLE_name5,null, values);
		db.close();
	}
	public String getcommon(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + TABLE_name5;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void deletecommon()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_name5);
	}
}