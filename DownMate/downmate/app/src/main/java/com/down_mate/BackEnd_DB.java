package com.down_mate;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
public class BackEnd_DB extends SQLiteOpenHelper {
 	private static final int DATABASE_VERSION =2;
	private static final String DATABASE_NAME = "keraladown";
	private static final String Table2 = "fb";
	private static final String Table3 = "insta";
	private static final String Table18 = "isupdated";
	private static final String Table4 = "tiktoks";
	private static final String Table5 = "downsid";
	private static final String Table14 = "whichingwhat";
	private static final String Table15 = "fb_format";
	private static final String Table9 = "fb_helpshow";
	private static final String Table10 = "insta_helpshow";
	private static final String Table11 = "tikhelp_show";
	private static final String Table12 = "sharechat_show";
	private static final String Table13 = "share_chat";
 	private static final String pkey= "pkey";
	private static final String CommonTitle ="commontitle";
	private static final String downid="downid";
    private static final String downname="downname";
	private static final String newdownname="newdownname";
	private static final String downtype="downtype";
	private static final String downpath="downpath";
	private static final String downurl="downurl";
	public BackEnd_DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static String create_table14 = "CREATE TABLE " + Table14 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table15 = "CREATE TABLE " + Table15 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table2 = "CREATE TABLE " + Table2 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table3 = "CREATE TABLE " + Table3 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";

	//This is data rable

	private static String create_table4 = "CREATE TABLE " + Table4 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table5 = "CREATE TABLE " + Table5 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+downid +" TEXT,"+downtype +" TEXT,"+downname +" TEXT,"+downpath +" TEXT,"+downurl +" TEXT,"+newdownname+" TEXT"+")";
	private static String create_table9 = "CREATE TABLE " + Table9 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table10 = "CREATE TABLE " + Table10 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table11 = "CREATE TABLE " + Table11 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table12 = "CREATE TABLE " + Table12 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table18 = "CREATE TABLE " + Table18 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";
	private static String create_table13 = "CREATE TABLE " + Table13 + "("+pkey +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CommonTitle +" TEXT"+")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(create_table9);
		db.execSQL(create_table10);
		db.execSQL(create_table11);
		db.execSQL(create_table14);
		db.execSQL(create_table15);
		db.execSQL(create_table18);
		db.execSQL(create_table12);
		db.execSQL(create_table2);
		db.execSQL(create_table3);
		db.execSQL(create_table4);
		db.execSQL(create_table5);
		db.execSQL(create_table13);
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Table2);
		db.execSQL("DROP TABLE IF EXISTS " + Table10);
		db.execSQL("DROP TABLE IF EXISTS " + Table13);
		db.execSQL("DROP TABLE IF EXISTS " + Table14);
		db.execSQL("DROP TABLE IF EXISTS " + Table15);
		db.execSQL("DROP TABLE IF EXISTS " + Table18);
		db.execSQL("DROP TABLE IF EXISTS " + Table11);
		db.execSQL("DROP TABLE IF EXISTS " + Table12);
		db.execSQL("DROP TABLE IF EXISTS " + Table3);
		db.execSQL("DROP TABLE IF EXISTS " + Table4);
		db.execSQL("DROP TABLE IF EXISTS " + Table5);
		db.execSQL("DROP TABLE IF EXISTS " + Table9);
		onCreate(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Table2);
		db.execSQL("DROP TABLE IF EXISTS " + Table3);
		db.execSQL("DROP TABLE IF EXISTS " + Table12);
		db.execSQL("DROP TABLE IF EXISTS " + Table13);
		db.execSQL("DROP TABLE IF EXISTS " + Table14);
		db.execSQL("DROP TABLE IF EXISTS " + Table18);
		db.execSQL("DROP TABLE IF EXISTS " + Table4);
		db.execSQL("DROP TABLE IF EXISTS " + Table5);
		db.execSQL("DROP TABLE IF EXISTS " + Table15);
		db.execSQL("DROP TABLE IF EXISTS " + Table9);
		db.execSQL("DROP TABLE IF EXISTS " + Table10);
		db.execSQL("DROP TABLE IF EXISTS " + Table11);
        onCreate(db);
	}

	public void add_isupdate(String title1)
	{
		delete_isupdate();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table18,null, values);
		db.close();
	}
	public String get_isupdate(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table18;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void delete_isupdate()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table18);
	}
	public void delete_whchwp()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table14);
	}
	public void add_chatshare(String title1)
	{
		del_sharechat();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table13,null, values);
		db.close();
	}
	public String get_chatshare(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table13;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_sharechat()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table13);
	}

	public void add_sharehelp(String title1)
	{
		delete_sharechathelpshow();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table12,null, values);
		db.close();
	}
	public String get_sharehelp(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table12;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void delete_sharechathelpshow()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table12);
	}

	public void add_tickhelp(String title1)
	{
		del_tikhelp();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table11,null, values);
		db.close();
	}
	public String get_tikhelp(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table11;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_tikhelp()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table11);
	}


	public void add_instahelp(String title1)
	{
		del_instahelp();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table10,null, values);
		db.close();
	}
	public String get_instahelp(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table10;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_instahelp()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table10);
	}

	public void add_downlaodlist(String downid1, String downtype1, String downname1, String downpath1, String downurl1, String newdownname1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(downid,downid1);
		values.put(downtype,downtype1);
		values.put(downname,downname1);
		values.put(downpath,downpath1);
		values.put(downurl,downurl1);
		values.put(newdownname,newdownname1);
		db.insert(Table5,null, values);
		db.close();
	}

	public ArrayList<String> get_downloadlist(){

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table5 +" order by pkey desc";
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

	public String get_Downid(String id1){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  downpath FROM " + Table5 +" WHERE downid="+id1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}
	public String get_downname(String id1){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT downname FROM " + Table5 +" WHERE downid="+id1;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(0);
		}
		c.close();
		return link;
	}
	public void del_downid()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table5);
	}

	public void update_downid(String dowid1, String path1)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put(downpath,path1);
		db.update(Table5, newValues, downid + " = ?",new String[]{dowid1});
	}

    public void del_downid(String pkey1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table5, pkey+"="+pkey1, null);
    }

	public void add_fb(String title1)
	{
		del_fb();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table2,null, values);
		db.close();
	}
	public String get_fb(){

		 String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table2;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_fb()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table2);
	}

	public void add_insta(String title1)
	{
		del_insta();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table3,null, values);
		db.close();
	}
	public String get_insta(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table3;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_insta()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table3);
	}

	public void add_fbquality(String title1)
	{
		del_fbquality();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table15,null, values);
		db.close();
	}
	public String get_fbquality(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table15;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_fbquality()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table15);
	}

	public void add_whichwp(String title1)
	{
		delete_whchwp();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table14,null, values);
		db.close();
	}
	public String get_whichwp(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table14;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}

	public void add_ticks(String title1)
	{
		del_tiktok();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table4,null, values);
		db.close();
	}
	public String get_tiks(){
		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table4;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_tiktok()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table4);
	}

	public void add_fbhelp(String title1)
	{
		del_fbhelp();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CommonTitle,title1);
		db.insert(Table9,null, values);
		db.close();
	}
	public String get_fphelp(){

		String link="";
		SQLiteDatabase sql=this.getReadableDatabase();
		String query = "SELECT  * FROM " + Table9;

		Cursor c=sql.rawQuery(query,null);
		while(c.moveToNext()) {
			link=c.getString(1);
		}
		c.close();
		return link;
	}
	public void del_fbhelp()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ Table9);
	}
}