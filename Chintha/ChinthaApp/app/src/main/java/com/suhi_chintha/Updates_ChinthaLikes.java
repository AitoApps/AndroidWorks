package com.suhi_chintha;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Updates_ChinthaLikes {

	public Context context;
	public DataDb dataDb;
	public DataDB1 dataDb1;
	DataDB4 dataDb4;
	public User_DataDB userDataDB;
	public NetConnection cd;
	public String pkey="",imgsig="0";
	public Updates_ChinthaLikes(Context cx) {
	context=cx;
	dataDb =new DataDb(context);
	dataDb1 =new DataDB1(context);
	cd=new NetConnection(context);
	userDataDB =new User_DataDB(context);
	dataDb4 =new DataDB4(context);
	}

	public class like_update extends AsyncTask<String,Void,String>{

		protected void onPreExecute(){

		}
		@Override
		protected String doInBackground(String... arg0) {

			try{

				String link= Static_Variable.entypoint1 +"like.php";
				String data  = URLEncoder.encode("item", "UTF-8")
						+ "=" + URLEncoder.encode(Static_Variable.chintha_Id +":%"+ Static_Variable.statususerid+":%"+ Static_Variable.userid1+":%"+ Static_Variable.username1+":%"+ Static_Variable.chintha_text +":%"+imgsig, "UTF-8");
				URL url = new URL(link);
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter
						(conn.getOutputStream());
				wr.write(data);
				wr.flush();
				BufferedReader reader = new BufferedReader
						(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null)
				{
					sb.append(line);
				}
				return sb.toString();
			}catch(Exception e){
				return new String("Unable to connect server! Please check your internet connection");
			}
		}

		@Override
		protected void onPostExecute(String result){

			try
			{

				if(result.contains("ok"))
				{
					dataDb1.deletefvrtcoloum(pkey);
					likeupdate();
				}

			}
			catch(Exception a)
			{

			}


		}
	}


	public void likeupdate()
	{
		try
		{
			 ArrayList<String> id1=	dataDb1.get_fvrtstaus();
	     	 String[] c=id1.toArray(new String[id1.size()]);
	     
			if(c.length>0)
			{
				
				Static_Variable.userid1=c[1];
				Static_Variable.chintha_Id =c[2];
				Static_Variable.statususerid=c[3];
				Static_Variable.username1=c[4];
				if(c[5].length()>70)
				{
					Static_Variable.chintha_text =c[5].substring(0,70);
				}
				else
				{
					Static_Variable.chintha_text =c[5];
				}
				
				pkey=c[0];
				imgsig=c[6];
 
					if(cd.isConnectingToInternet())
					{
					 new like_update().execute();
					}
 
			}
		}
		catch(Exception a)
		{
			
		}
	
	}

}
