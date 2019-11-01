package com.suhi_chintha;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataDB2 extends SQLiteOpenHelper {

 	private static final int DATABASE_VERSION =35;
	private static final String DATABASE_NAME = "chinathdb56";
	private static final String table1 = "disbalenoti";
	private static final String table14 = "disbalenotibld";
	private static final String table2 = "commentreplay";
	private static final String table4 = "replaystore";
	private static final String table5 = "disablereply";
	private static final String table6 = "coincount";
	private static final String table7 = "fcmid";
	private static final String table8 = "tempuser";
	private static final String table9 = "commentcoutn";
	private static final String table10 = "replyvisible";
	private static final String table11 = "scwidth";
	private static final String table12 = "addtofvrt";
	private static final String table13 = "addtofvrtlist";

	private static final String status_id = "status_id";
	private static final String name = "name";
    private static final String comment_id="commentid";
	private static final String commentdate = "commentdate";
	private static final String screenwidth = "screenwidth";
	private static final String cmnt_imgsigid="cmnt_imgsigid";
	private static final String statusid = "statusid";
	private static final String status= "status";
	private static final String fcmid = "fcmid";
	private static final String commentcount = "commentcount";

	private static final String showmobile = "showmobile";
	private static final String pkey= "pkey";
	private static final String cmnt_statustype = "cmnt_statustype";
	private static final String cmnt_photourl = "cmnt_photourl";
	private static final String cmnt_photodim = "cmnt_photodim";
    private static final String msg = "msg";
	private static final String userid="userid";
	private static final String username="username";
	private static final String mobile="mobile";
	private static final String countrycode="countrycode";
	private static final String profilpic="profilepic";
	private static final String comments="comments";
	private static final String coins="coins";
	private static final String replyvisi="replyvisi";
	public DataDB2(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	private static String CREATE_videoid_TABLE7 = "CREATE TABLE " + table7 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+fcmid+" TEXT"+")";
	private static String CREATE_videoid_TABLE8 = "CREATE TABLE " + table8 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+username+" TEXT,"+mobile+" TEXT,"+countrycode+" TEXT"+")";
	private static String CREATE_videoid_TABLE9 = "CREATE TABLE " + table9 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+commentdate+" TEXT,"+commentcount+" TEXT"+")";
	private static String CREATE_videoid_TABLE10 = "CREATE TABLE " + table10 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+replyvisi+" TEXT"+")";
	private static String CREATE_videoid_TABLE11 = "CREATE TABLE " + table11 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+screenwidth+" TEXT"+")";
	private static String CREATE_videoid_TABLE12 = "CREATE TABLE " + table12 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+statusid +" TEXT,"+userid +" TEXT,"+name +" TEXT,"+mobile+" TEXT,"+status +" TEXT,"+showmobile +" TEXT,"+cmnt_statustype +" TEXT,"+cmnt_photourl +" TEXT,"+cmnt_photodim+" TEXT"+")";
	private static String CREATE_videoid_TABLE13 = "CREATE TABLE " + table13 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+cmnt_statustype+" TEXT,"+cmnt_photourl+" TEXT,"+cmnt_photodim+" TEXT,"+msg+" TEXT"+")";
	private static String CREATE_videoid_TABLE14 = "CREATE TABLE " + table14 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+status_id+" TEXT"+")";

	private static String CREATE_videoid_TABLE1 = "CREATE TABLE " + table1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+status_id+" TEXT"+")";
	private static String CREATE_videoid_TABLE2 = "CREATE TABLE " + table2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+comment_id+" TEXT,"+userid+" TEXT,"+username+" TEXT,"+profilpic+" TEXT,"+comments+" TEXT,"+cmnt_imgsigid+" TEXT"+")";
	private static String CREATE_videoid_TABLE4 = "CREATE TABLE " + table4 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+comment_id+" TEXT,"+comments+" TEXT"+")";
	private static String CREATE_videoid_TABLE5 = "CREATE TABLE " + table5 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+status_id+" TEXT"+")";
	private static String CREATE_videoid_TABLE6 = "CREATE TABLE " + table6 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+coins+" TEXT"+")";

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_videoid_TABLE1);
		db.execSQL(CREATE_videoid_TABLE2);
		db.execSQL(CREATE_videoid_TABLE4);
		db.execSQL(CREATE_videoid_TABLE5);

		db.execSQL(CREATE_videoid_TABLE12);
		db.execSQL(CREATE_videoid_TABLE13);
		db.execSQL(CREATE_videoid_TABLE14);

		db.execSQL(CREATE_videoid_TABLE6);
		db.execSQL(CREATE_videoid_TABLE7);
		db.execSQL(CREATE_videoid_TABLE8);
		db.execSQL(CREATE_videoid_TABLE9);
		db.execSQL(CREATE_videoid_TABLE10);
		db.execSQL(CREATE_videoid_TABLE11);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table1);
		db.execSQL("DROP TABLE IF EXISTS " + table2);
		db.execSQL("DROP TABLE IF EXISTS " + table4);
		db.execSQL("DROP TABLE IF EXISTS " + table5);

		db.execSQL("DROP TABLE IF EXISTS " + table12);
		db.execSQL("DROP TABLE IF EXISTS " + table13);
		db.execSQL("DROP TABLE IF EXISTS " + table14);
		db.execSQL("DROP TABLE IF EXISTS " + table6);
		db.execSQL("DROP TABLE IF EXISTS " + table7);
		db.execSQL("DROP TABLE IF EXISTS " + table8);
		db.execSQL("DROP TABLE IF EXISTS " + table9);
		db.execSQL("DROP TABLE IF EXISTS " + table10);
		db.execSQL("DROP TABLE IF EXISTS " + table11);
        onCreate(db);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + table1);
		db.execSQL("DROP TABLE IF EXISTS " + table2);
		db.execSQL("DROP TABLE IF EXISTS " + table4);
		db.execSQL("DROP TABLE IF EXISTS " + table5);

		db.execSQL("DROP TABLE IF EXISTS " + table11);
		db.execSQL("DROP TABLE IF EXISTS " + table12);
		db.execSQL("DROP TABLE IF EXISTS " + table13);
		db.execSQL("DROP TABLE IF EXISTS " + table14);
		db.execSQL("DROP TABLE IF EXISTS " + table6);
		db.execSQL("DROP TABLE IF EXISTS " + table7);
		db.execSQL("DROP TABLE IF EXISTS " + table8);
		db.execSQL("DROP TABLE IF EXISTS " + table9);
		db.execSQL("DROP TABLE IF EXISTS " + table10);
		onCreate(db);
		
	}


	public void add_publicmsg(String cmnt_statustype1, String cmnt_photourl1, String cmnt_photodim1, String msg1)
	{
		drop_pubmsg();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(cmnt_statustype,cmnt_statustype1);
		values.put(cmnt_photourl,cmnt_photourl1);
		values.put(cmnt_photodim,cmnt_photodim1);
        values.put(msg,msg1);
		db.insert(table13,null, values);
		db.close();
	}
	public ArrayList<String> get_pubmsg(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table13;
		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			arraylist.add(c.getString(1));
			arraylist.add(c.getString(2));
			arraylist.add(c.getString(3));
            arraylist.add(c.getString(4));
		}
		c.close();
		return arraylist;
	}

	public void drop_pubmsg()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table13);
	}

		public void add_rplyvisible(String replyvisi1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(replyvisi,replyvisi1);
		db.insert(table10,null, values);
		db.close();
	}
	public String get_rplyvisible(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table10;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void drop_rplyvisible()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table10);
	}




	public void addfcmid(String fcmid1)
	{
		deletefcmid();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(fcmid,fcmid1);
		db.insert(table7,null, values);
		db.close();
	}
	public String getfcmid(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table7;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void deletefcmid()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table7);

	}



	public void add_rplydisable(String status_id1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(status_id,status_id1);
		db.insert(table5,null, values);
		db.close();
	}


	public String get_disablerply(String status_id1){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table5 +" WHERE status_id='"+status_id1+"'";

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}



	public void del_disablerply(String status_id1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String[] whereArgs = new String[] { status_id1 };
		db.delete(table5, status_id+"=?", whereArgs);
	}
	public void add_storecmnt(String status_id1, String status_status1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(comment_id,status_id1);
		values.put(comments,status_status1);
		db.insert(table4,null, values);
		db.close();
	}


	public String getcommentstore1(String comment_id){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table4 +" WHERE commentid="+comment_id;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}

	public ArrayList<String> get_storecmnt(String comment_id){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table4 +" WHERE commentid="+comment_id;
		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			arraylist.add(c.getString(1));
			arraylist.add(c.getString(2));
		}
		c.close();
		return arraylist;
	}



	public void add_replycmnt(String comment_id1, String userid1, String username1, String profilepic1, String comments1, String cmnt_imgsigid1)
	{
		try {
			deletecmntreplay();
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(comment_id,comment_id1);
			values.put(userid,userid1);
			values.put(username,username1);
			values.put(profilpic,profilepic1);
			values.put(comments,comments1);
			values.put(cmnt_imgsigid,cmnt_imgsigid1);
			db.insert(table2,null, values);
			db.close();
		}
		catch (Exception a)
		{
			Log.e("Error",Log.getStackTraceString(a));
		}

	}
	public ArrayList<String> get_rplycmnt(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table2;
		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			arraylist.add(c.getString(1));
			arraylist.add(c.getString(2));
			arraylist.add(c.getString(3));
			arraylist.add(c.getString(4));
			arraylist.add(c.getString(5));
			arraylist.add(c.getString(6));
		}
		c.close();
		return arraylist;
	}

	void deletecmntreplay()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table2);
	}

	public void add_usertmp(String username1, String mobile1, String countrycode1)
	{
		get_usertmp();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(username,username1);
		values.put(mobile,mobile1);
		values.put(countrycode,countrycode1);
		db.insert(table8,null, values);
		db.close();
	}
	public String get_usrtmp(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table8;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public String get_mobiletmp(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table8;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(2);
		}
		c.close();
		return link;
	}
	public String get_codetemp(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table8;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(3);
		}
		c.close();
		return link;
	}
	public void get_usertmp()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table8);

	}

	public void add_disablenoti(String status_id1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(status_id,status_id1);
		db.insert(table1,null, values);
		db.close();
	}


	public String get_notidisabled(String status_id1){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table1 +" WHERE status_id='"+status_id1+"'";

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}

public void del_notidisabled(String status_id1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String[] whereArgs = new String[] { status_id1 };
		db.delete(table1, status_id+"=?", whereArgs);
	}


	public void addtofvrt(String statusid1, String userid1, String name1, String mobile1, String status1, String showmobile1, String cmnt_statustype1, String cmnt_photourl1, String cmnt_photodim1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(statusid,statusid1);
		values.put(userid,userid1);
		values.put(name,name1);
		values.put(mobile,mobile1);
		values.put(status,status1);
		values.put(showmobile,showmobile1);
		values.put(cmnt_statustype,cmnt_statustype1);
		values.put(cmnt_photourl,cmnt_photourl1);
		values.put(cmnt_photodim,cmnt_photodim1);
		db.insert(table12,null, values);
		db.close();
	}
	public ArrayList<String> get_fvrt1(String limit){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table12 +" order by pkey desc LIMIT "+limit+",30";

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
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

	public void deletefvrt(String statusid1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table12, statusid+"='"+statusid1+"'", null);

	}


	public String get_fvrt(String id){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + table12 +" WHERE statusid=?", new String[]{id});
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void deletefvrt()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table12);
	}

	public void add_screenwidth(String screenwidth1)
	{
		drop_screenwidth();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(screenwidth,screenwidth1);
		db.insert(table11,null, values);
		db.close();
	}
	public String get_screenwidth(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table11;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void drop_screenwidth()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table11);
	}


}