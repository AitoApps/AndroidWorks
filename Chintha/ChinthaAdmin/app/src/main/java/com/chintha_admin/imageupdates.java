package com.chintha_admin;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class imageupdates {
    public ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler_Images db;
    int i = 0;
    public String pkey = "";
    public String title = "";

    public class fvrts extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"updaeteimageadmin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(title, "UTF-8");
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
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
            
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains("ok")) {
                    Toast.makeText(context, i+"", Toast.LENGTH_SHORT).show();
                    fvrtlist();
                }
            } catch (Exception e) {
            }
        }
    }

    public imageupdates(Context cx) {
        context = cx;
        db = new DatabaseHandler_Images(context);
        cd = new ConnectionDetecter(context);
    }

    public void fvrtlist() {
        try {
            ArrayList<String> id1 = db.getfcmids();
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            if (c.length > 0) {
                pkey = c[0];
                title = c[1];
                if (cd.isConnectingToInternet()) {
                    db.deletefcmid(pkey);
                    i++;
                    new fvrts().execute(new String[0]);
                    return;
                }
                return;
            }
            Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }
}
