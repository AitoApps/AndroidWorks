package com.suhi_chintha;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataDB4 extends SQLiteOpenHelper {

 	private static final int DATABASE_VERSION =2;
	private static final String DATABASE_NAME = "chinthadb9";
	private static final String table1 = "commentlock";
	private static final String table2 = "shortstatus";
	private static final String table3 = "iswpstatusopen";
	private static final String commonid = "commonid";

	private static final String pkey= "pkey";

	public DataDB4(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	private static String CREATE_videoid_TABLE1 = "CREATE TABLE " + table1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+commonid+" TEXT"+")";
	private static String CREATE_videoid_TABLE2 = "CREATE TABLE " + table2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+commonid+" TEXT"+")";
	private static String CREATE_videoid_TABLE3= "CREATE TABLE " + table3+ "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+commonid+" TEXT"+")";

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_videoid_TABLE1);
		db.execSQL(CREATE_videoid_TABLE2);
		db.execSQL(CREATE_videoid_TABLE3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table1);
		db.execSQL("DROP TABLE IF EXISTS " + table2);
		db.execSQL("DROP TABLE IF EXISTS " + table3);
        onCreate(db);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table1);
		db.execSQL("DROP TABLE IF EXISTS " + table2);
		db.execSQL("DROP TABLE IF EXISTS " + table3);
		onCreate(db);
		
	}
	public void add_short_status(String commonid1)
	{
		drop_shortstatus();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(commonid,commonid1);
		db.insert(table2,null, values);
		db.close();
	}
	public String get_short_status(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table2;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void drop_shortstatus()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table2);
	}

	public void add_lockcomments(String commonid1)
	{
		drop_lockcomnts();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(commonid,commonid1);
		db.insert(table1,null, values);
		db.close();
	}
	public String get_lockcomments(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void drop_lockcomnts()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table1);
	}


	public void add_wpstatusopen(String commonid1)
	{
		drop_wpstatusopen();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(commonid,commonid1);
		db.insert(table3,null, values);
		db.close();
	}
	public String get_wpstatusopen(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table3;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void drop_wpstatusopen()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table3);
	}


}