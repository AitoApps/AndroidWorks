package com.suhi_chintha;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataDB1 extends SQLiteOpenHelper {

 	private static final int DATABASE_VERSION =102;
	private static final String DATABASE_NAME = "chinathdb1";
	private static final String table1 = "bststaus";
	private static final String table2 = "staus";
	private static final String table4 = "cmntVisible";
	private static final String table6 = "ogcomment";
	private static final String table7 = "statusnoti";
	private static final String table8 = "likenoti";
	private static final String table9 = "commentnoti";
	private static final String table10 = "notilist";
	private static final String table11 = "noticount";
	private static final String table12 = "adminmessgae";
	private static final String table13 = "legalnotice";
	private static final String table14 = "refreshhost";

	private static final String table15 = "commentdetails";

	private static final String table17 = "cmntpost";
	private static final String table18 = "fvrtcounts";

	private static final String userid = "userid";
	private static final String name = "name";
	private static final String mobile = "mobile";
	private static final String statusid = "statusid";
	private static final String status= "status";
	private static final String showmobile = "showmobile";
	private static final String varified = "varified";

	private static final String isread = "isread";
	private static final String fvrts = "fvrts";
	private static final String pkey= "pkey";
	private static final String statusdate = "statusdate";
	private static final String notification = "notification";
	private static final String notiogid = "notiogid";
	private static final String noti_type = "noti_type";
	private static final String noti_lastname = "noti_lastname";
	private static final String noti_count = "noti_count";
	private static final String noti_text = "noti_text";
	private static final String date_created="date_created";
	private static final String noti_userid = "noti_userid";
	private static final String adminmsg= "adminmsg";
	private static final String adminmsg_type = "adminmsg_type";  // 0- text 1-imageadvt


	private static final String cmnt_photodim = "cmnt_photodim";
	private static final String status_imgsigid = "status_imgsigid";
	private static final String status_id = "status_id";
	private static final String status_name = "status_name";
	private static final String status_status = "status_status";
	private static final String status_userid= "status_userid";
	private static final String status_username = "status_username";
	private static final String cmnt_statusid = "cmnt_statusid";
	private static final String cmnt_userid = "cmnt_userid";
	private static final String cmnt_username = "cmnt_username";
	private static final String cmnt_status = "cmnt_status";
	private static final String cmnt_imgsigid = "cmnt_imgsigid";
	private static final String cmnt_statustype = "cmnt_statustype";
	private static final String cmnt_photourl = "cmnt_photourl";

	public static String fvrstatus="fvrstatus";
	public static String fvrstatususerid="statususerid";
	public static String dvrt_userid ="fvruserid";
	public static String fvrusername="fvrusername";
	public static String fvrstatustext="fvrstatustext";
	public static String fvrstatusimgsig="fvrstatusimgsig";

	public DataDB1(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	private static String CREATE_videoid_TABLE1 = "CREATE TABLE " + table1 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+statusid +" TEXT,"+userid +" TEXT,"+name +" TEXT,"+mobile+" TEXT,"+status +" TEXT,"+fvrts +" TEXT,"+showmobile +" TEXT,"+varified+" TEXT"+")";
	private static String CREATE_videoid_TABLE2 = "CREATE TABLE " + table2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+statusid +" TEXT,"+userid +" TEXT,"+name +" TEXT,"+mobile+" TEXT,"+status +" TEXT,"+fvrts +" TEXT,"+showmobile +" TEXT,"+varified +" TEXT,"+statusdate+" TEXT"+")";
	private static String CREATE_videoid_TABLE4 = "CREATE TABLE " + table4 + "("+notification+" TEXT"+")";

	private static String CREATE_videoid_TABLE10 = "CREATE TABLE " + table10 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+notiogid +" TEXT,"+noti_type +" TEXT,"+noti_lastname +" TEXT,"+noti_count+" TEXT,"+noti_text+" TEXT,"+date_created+" DATETIME,"+noti_userid+" TEXT,"+isread+" TEXT,"+status_userid+" TEXT,"+status_username+" TEXT,"+status_imgsigid+" TEXT,"+cmnt_statustype+" TEXT,"+cmnt_photourl+" TEXT,"+cmnt_photodim+" TEXT"+")";
	private static String CREATE_videoid_TABLE11 = "CREATE TABLE " + table11 + "("+notification+" TEXT"+")";
	private static String CREATE_videoid_TABLE12 = "CREATE TABLE " + table12 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+adminmsg+" TEXT,"+adminmsg_type+" TEXT"+")";
	private static String CREATE_videoid_TABLE13 = "CREATE TABLE " + table13 + "("+notification+" TEXT"+")";
	private static String CREATE_videoid_TABLE14 = "CREATE TABLE " + table14 + "("+notification+" TEXT"+")";
	private static String CREATE_videoid_TABLE15 = "CREATE TABLE " + table15 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+cmnt_statusid +" TEXT,"+cmnt_userid +" TEXT,"+cmnt_username +" TEXT,"+cmnt_status +" TEXT,"+cmnt_imgsigid +" TEXT,"+cmnt_statustype +" TEXT,"+cmnt_photourl +" TEXT,"+cmnt_photodim+" TEXT"+")";
	private static String CREATE_videoid_TABLE17 = "CREATE TABLE " + table17 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+status_id +" TEXT,"+status_status+" TEXT"+")";
	private static String CREATE_videoid_TABLE18 = "CREATE TABLE " + table18 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ dvrt_userid +" TEXT,"+fvrstatus +" TEXT,"+fvrstatususerid +" TEXT,"+fvrusername +" TEXT,"+fvrstatustext+" TEXT,"+fvrstatusimgsig+" TEXT"+")";
	private static String CREATE_videoid_TABLE6 = "CREATE TABLE " + table6 + "("+statusid+" TEXT"+")";
	private static String CREATE_videoid_TABLE7 = "CREATE TABLE " + table7 + "("+notification+" TEXT"+")";
	private static String CREATE_videoid_TABLE8 = "CREATE TABLE " + table8 + "("+notification+" TEXT"+")";
	private static String CREATE_videoid_TABLE9 = "CREATE TABLE " + table9 + "("+notification+" TEXT"+")";
	@Override
	public void onCreate(SQLiteDatabase db) {
	  
	    db.execSQL(CREATE_videoid_TABLE1);
	    db.execSQL(CREATE_videoid_TABLE2);
	    db.execSQL(CREATE_videoid_TABLE4);
	    db.execSQL(CREATE_videoid_TABLE6);
	    db.execSQL(CREATE_videoid_TABLE7);

	    db.execSQL(CREATE_videoid_TABLE12);
	    db.execSQL(CREATE_videoid_TABLE13);
	    db.execSQL(CREATE_videoid_TABLE14);
		db.execSQL(CREATE_videoid_TABLE15);
		db.execSQL(CREATE_videoid_TABLE17);
		db.execSQL(CREATE_videoid_TABLE18);
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

		db.execSQL("DROP TABLE IF EXISTS " + table12);
		db.execSQL("DROP TABLE IF EXISTS " + table13);
		db.execSQL("DROP TABLE IF EXISTS " + table14);
		db.execSQL("DROP TABLE IF EXISTS " + table15);
		db.execSQL("DROP TABLE IF EXISTS " + table17);
		db.execSQL("DROP TABLE IF EXISTS " + table18);
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
		db.execSQL("DROP TABLE IF EXISTS " + table6);

		db.execSQL("DROP TABLE IF EXISTS " + table13);
		db.execSQL("DROP TABLE IF EXISTS " + table14);
		db.execSQL("DROP TABLE IF EXISTS " + table15);
		db.execSQL("DROP TABLE IF EXISTS " + table18);
		db.execSQL("DROP TABLE IF EXISTS " + table17);
		db.execSQL("DROP TABLE IF EXISTS " + table7);
		db.execSQL("DROP TABLE IF EXISTS " + table8);
		db.execSQL("DROP TABLE IF EXISTS " + table9);
		db.execSQL("DROP TABLE IF EXISTS " + table10);
		db.execSQL("DROP TABLE IF EXISTS " + table11);
		db.execSQL("DROP TABLE IF EXISTS " + table12);
		onCreate(db);
		
	}

	public void add_cmntdetails(String status_id1, String status_status1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(status_id,status_id1);
		values.put(status_status,status_status1);
		db.insert(table17,null, values);
		db.close();
	}


	public String get_commntdtls(String status_id){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table17 +" WHERE status_id="+status_id;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}

	public ArrayList<String> getcommentdetails(String status_id){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table17 +" WHERE status_id="+status_id;
		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			arraylist.add(c.getString(1));
			arraylist.add(c.getString(2));
		}
		c.close();
		return arraylist;
	}

	public void dropcmntdetails()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table17);
	}

	public void dropcmntdetails(String status_id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table17, status_id+"="+status_id, null);
	}



	public void add_cmntdtails(String cmnt_statusid1, String cmnt_userid1, String cmnt_username1, String cmnt_status1, String cmnt_imgsigid1, String cmnt_statustype1, String cmnt_photourl1, String cmnt_photodim1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(cmnt_statusid,cmnt_statusid1);
		values.put(cmnt_userid,cmnt_userid1);
		values.put(cmnt_username,cmnt_username1);
		values.put(cmnt_status,cmnt_status1);
		values.put(cmnt_imgsigid,cmnt_imgsigid1);
		values.put(cmnt_statustype,cmnt_statustype1);
		values.put(cmnt_photourl,cmnt_photourl1);
		values.put(cmnt_photodim,cmnt_photodim1);
		db.insert(table15,null, values);
		db.close();
	}
	public ArrayList<String> getcmntdetails(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table15;
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
		}
		c.close();
		return arraylist;
	}

	public void deletecmntdetails()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table15);
	}

	

	public void add_privacynoti(String notification1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notification,notification1);
		db.insert(table13,null, values);
		db.close(); 
	}
	public String get_privacynoti(){
           String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table13;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
	}

	public void deletelegalnotice()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table13);
	}
 
	void add_message(String adminmsg1, String adminmsg_type1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(adminmsg,adminmsg1);
		values.put(adminmsg_type,adminmsg_type1);
		 
		db.insert(table12,null, values);
		db.close(); 
	}
	public ArrayList<String> get_message(){
        
		   ArrayList<String> arraylist = new ArrayList<String>();
		   SQLiteDatabase sql=this.getReadableDatabase();
			String query = "SELECT  * FROM " + table12;
		    Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  
			   arraylist.add(c.getString(1));
			   arraylist.add(c.getString(2));
			   
		    }
		    c.close();
		    return arraylist;
	}

	void drop_message()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table12);
	}
	
	 
	public String get_noticount(){
		 String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  count(pkey) FROM " + table10 +" where isread=0";
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
		 
	}


	
	public void add_cmtnnoti(String notification1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notification,notification1);
		db.insert(table9,null, values);
		db.close(); 
	}
	public String get_cmntnoti(){
           String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table9;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
	}

	public void drop_cmntnoti()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table9);
	}
	
	public void add_notilike(String notification1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notification,notification1);
		db.insert(table8,null, values);
		db.close(); 
	}
	public String get_likenoti(){
           String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table8;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
	}

	public void drop_notilike()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table8);
	}
	
	
	public void add_notistatus(String notification1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notification,notification1);
		db.insert(table7,null, values);
		db.close(); 
	}
	public String get_notistatus(){
           String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table7;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
	}

	public void drop_statusnoti()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table7);
	}


	
	public void add_cmntvisible(String notification1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notification,notification1);
		db.insert(table4,null, values);
		db.close(); 
	}
	public String get_cmntvisible(){
           String link="";
			SQLiteDatabase sql=this.getReadableDatabase();
			  String query = "SELECT  * FROM " + table4;
				 
				 Cursor c=sql.rawQuery(query,null);
		   while(c.moveToNext()) {
			  link=c.getString(0);
		    }
		    c.close();
		    return link;
	}

	public void deletecmntvisible()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table4);
	}

	void add_notilist(String notiogid1, String noti_type1, String noti_lastname1, String noti_count1, String noti_text1, String noti_userid1, String isread1, String status_userid1, String status_username1, String status_imgsigid1, String cmnt_statustype1, String cmnt_photourl1, String cmnt_photodim1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(notiogid,notiogid1);
		values.put(noti_type,noti_type1);
		values.put(noti_lastname,noti_lastname1);
		values.put(noti_count,noti_count1);
		values.put(noti_text,noti_text1);
		values.put(date_created,getDateTime());
		values.put(noti_userid,noti_userid1);
		values.put(isread,isread1);
		values.put(status_userid,status_userid1);
		values.put(status_username,status_username1);
		values.put(status_imgsigid,status_imgsigid1);
		values.put(cmnt_statustype,cmnt_statustype1);
		values.put(cmnt_photourl,cmnt_photourl1);
		values.put(cmnt_photodim,cmnt_photodim1);
		db.insert(table10,null, values);
		db.close();
	}
	public ArrayList<String> get_notilist(String limit){
        
		   ArrayList<String> arraylist = new ArrayList<String>();
		   SQLiteDatabase sql=this.getReadableDatabase();
		   String query = "SELECT  * FROM " + table10 +" ORDER BY date_created desc LIMIT "+limit+",30";
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
			   arraylist.add(c.getString(0));
			   arraylist.add(c.getString(9));
			   arraylist.add(c.getString(10));
			   arraylist.add(c.getString(11));
			   arraylist.add(c.getString(12));
			   arraylist.add(c.getString(13));
			   arraylist.add(c.getString(14));
		    }
		    c.close();
		    return arraylist;
	}
	void deletenotilist()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ table10);
	}
public String getlikes(String id){
		String link="";	    
		SQLiteDatabase sql=this.getReadableDatabase();	
		Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='0' and notiogid=?", new String[]{id});
	   while(c.moveToNext()) {
		   link=c.getString(4);
	    }
	    c.close();
	    return link;
	}

public String getcomments(String id){
	String link="";
	SQLiteDatabase sql=this.getReadableDatabase();
	Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='1' and notiogid=?", new String[]{id});
   while(c.moveToNext()) {
	   link=c.getString(4);
    }
    c.close();
    return link;
}
	public String getcomments_bld(String id){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='3' and notiogid=?", new String[]{id});
		while(c.moveToNext()) {
			link=c.getString(4);
		}
		c.close();
		return link;
	}
	public String get_reply(String id){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='2' and notiogid=?", new String[]{id});
		while(c.moveToNext()) {
			link=c.getString(4);
		}
		c.close();
		return link;
	}
	public String getreplay_bld(String id){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='4' and notiogid=?", new String[]{id});
		while(c.moveToNext()) {
			link=c.getString(4);
		}
		c.close();
		return link;
	}
public String get_comments1(String id){

	String link="";
	SQLiteDatabase sql=this.getReadableDatabase();
	Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='1' and noti_userid=?", new String[]{id});
   while(c.moveToNext()) {
	   link=c.getString(5);
    }
    c.close();
    return link;
}

	public String getreply1(String id){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		Cursor c=sql.rawQuery("SELECT  * FROM " + table10 +" WHERE noti_type='2' and noti_userid=?", new String[]{id});
		while(c.moveToNext()) {
			link=c.getString(5);
		}
		c.close();
		return link;
	}


public void updatereadstatus(String pkey1)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues newValues = new ContentValues();
	newValues.put(isread,"1");
	db.update(table10, newValues, pkey + " = ?",new String[]{pkey1});
}
	public void updatereadstatusall()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(isread,"1");
		db.update(table10, newValues, null,null);
	}
public void update_likes(String id, String lastname, String userid1, String status_imgsigid1)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues newValues = new ContentValues();
	newValues.put(noti_lastname,lastname);
	newValues.put(noti_count,(Integer.parseInt(getlikes(id))+1)+"");
	newValues.put(date_created,getDateTime());
	newValues.put(noti_userid,userid1);
	newValues.put(status_imgsigid,status_imgsigid1);
	newValues.put(isread,"0");
	db.update(table10, newValues, notiogid + " = ? AND " + noti_type+ " = ?",new String[]{id, "0"});
}

public void update_cmnts(String id, String lastname, String noti_text1, String userid1, String status_userid1, String status_username1, String status_imgsigid1)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues newValues = new ContentValues();
	newValues.put(noti_lastname,lastname);
	newValues.put(noti_count,(Integer.parseInt(getcomments(id))+1)+"");
	newValues.put(date_created,getDateTime());
	newValues.put(noti_text,noti_text1);
	newValues.put(noti_userid,userid1);
	newValues.put(status_userid,status_userid1);
	newValues.put(status_username,status_username1);
	newValues.put(status_imgsigid,status_imgsigid1);
	newValues.put(isread,"0");
	db.update(table10, newValues, notiogid + " = ? AND " + noti_type+ " = ?",new String[]{id, "1"});
}

public void updatecomments1(String id,String lastname,String noti_text1,String status_userid1,String status_username1,String status_imgsigid1)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues newValues = new ContentValues();
	newValues.put(noti_lastname,lastname);
	newValues.put(date_created,getDateTime());
	newValues.put(noti_text,noti_text1);
	newValues.put(isread,"0");
	newValues.put(status_userid,status_userid1);
	newValues.put(status_username,status_username1);
	newValues.put(status_imgsigid,status_imgsigid1);
	db.update(table10, newValues, notiogid + " = ? AND " + noti_type+ " = ?",new String[]{id, "1"});
}

	public void updatereplay(String id,String lastname,String noti_text1,String userid1,String status_userid1,String status_username1,String status_imgsigid1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(noti_lastname,lastname);
		newValues.put(noti_count,(Integer.parseInt(get_reply(id))+1)+"");
		newValues.put(date_created,getDateTime());
		newValues.put(noti_text,noti_text1);
		newValues.put(noti_userid,userid1);
		newValues.put(status_userid,status_userid1);
		newValues.put(status_username,status_username1);
		newValues.put(isread,"0");
		newValues.put(status_imgsigid,status_imgsigid1);
		db.update(table10, newValues, notiogid + " = ? AND " + noti_type+ " = ?",new String[]{id, "2"});
	}

	public void updatereplay1(String id,String lastname,String noti_text1,String status_userid1,String status_username1,String status_imgsigid1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(noti_lastname,lastname);
		newValues.put(date_created,getDateTime());
		newValues.put(noti_text,noti_text1);
		newValues.put(isread,"0");
		newValues.put(status_userid,status_userid1);
		newValues.put(status_username,status_username1);
		newValues.put(status_imgsigid,status_imgsigid1);
		db.update(table10, newValues, notiogid + " = ? AND " + noti_type+ " = ?",new String[]{id, "2"});
	}

private String getDateTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    Date date = new Date();
    return dateFormat.format(date);
}


	public void add_fvrtstaus(String userid1, String fvrstatusid1, String fvrstatususerid1, String fvrusername1, String fvrstatustext1, String fvrstatusimgsig1)
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(dvrt_userid,userid1);
		values.put(fvrstatus,fvrstatusid1);
		values.put(fvrstatususerid,fvrstatususerid1);
		values.put(fvrusername,fvrusername1);
		values.put(fvrstatustext,fvrstatustext1);
		values.put(fvrstatusimgsig,fvrstatusimgsig1);
		db.insert(table18,null, values);
		db.close();
	}
	public ArrayList<String> get_fvrtstaus(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + table18;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			arraylist.add(c.getString(0));
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

	public void deletefvrtcoloum(String pkey1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(table18, pkey+"="+pkey1, null);

	}
}