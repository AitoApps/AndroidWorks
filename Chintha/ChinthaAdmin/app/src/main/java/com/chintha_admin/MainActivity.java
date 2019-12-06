package com.chintha_admin;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Button instagramtofacebook;
    Button instagramtoyoutube;
    Button ksrechargenew;
    Button ksusers;
    Button newusers;
    ProgressDialog pd;
    Button reportstatus;
    Button secretkey;
    Button uplaodtochintha;
    Button uplaodvideo;
    Button verification;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        uplaodtochintha = (Button) findViewById(R.id.uplaodtochintha);
        reportstatus = (Button) findViewById(R.id.reportstatus);
        newusers = (Button) findViewById(R.id.newusers);
        secretkey = (Button) findViewById(R.id.secretkey);
        verification = (Button) findViewById(R.id.verification);
        ksusers = (Button) findViewById(R.id.ksusers);
        uplaodvideo = (Button) findViewById(R.id.uplaodvideo);
        ksrechargenew = (Button) findViewById(R.id.ksrechargenew);
        instagramtofacebook = (Button) findViewById(R.id.instagramtofacebook);
        instagramtoyoutube = (Button) findViewById(R.id.instagramtoyoutube);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        if (db.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            db.addscreenwidth(width+"");
        }
        if (db.getuserid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
        }
        uplaodtochintha.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showalert1("Are you sure want to upload chintha ?");
            }
        });
        uplaodvideo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    startActivity(new Intent(getApplicationContext(), Status_Media_Upload.class));
                } catch (Exception e) {
                }
            }
        });
        ksrechargenew.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MK_EASY_RECHARGE_NEW.class));
            }
        });
        ksusers.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MK_Users.class));
            }
        });
        reportstatus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Report_Status.class));
            }
        });
        newusers.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), New_Users.class));
            }
        });
        verification.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Get_verificationcode.class));
            }
        });
        secretkey.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Get_Secretkey.class));
            }
        });
        instagramtofacebook.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InstagramToFacebook.class));
            }
        });
        instagramtoyoutube.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InstagramToYoutube.class));
            }
        });
    }

    public void showalert1(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new uploadtochintha().execute(new String[0]);
                } else {
                    Toast.makeText(getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public class uploadtochintha extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"uploadchinthapagefeedtoapp.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
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
            Log.w("Hello",result);
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
