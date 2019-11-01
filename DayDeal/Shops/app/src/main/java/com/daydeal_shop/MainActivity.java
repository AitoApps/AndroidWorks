package com.daydeal_shop;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET 
            };
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    Typeface face1;
    ImageView loadcatogery;
    ProgressDialog pd;
    Button product;
    Button shopdetails;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        text = (TextView) findViewById(R.id.text);
        loadcatogery = (ImageView) findViewById(R.id.loadcatogery);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        product = (Button) findViewById(R.id.product);
        shopdetails = (Button) findViewById(R.id.shopdetails);
        if (udb.get_shopid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }
        try {
            if (db.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                db.addscreenwidth(width+"");
            }
        } catch (Exception e) {
        }
        text.setText(udb.get_shopname());
        text.setTypeface(face);
        product.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ArrayList<String> id1 = db.getcatid();
                if (((String[]) id1.toArray(new String[id1.size()])).length > 0) {
                    startActivity(new Intent(getApplicationContext(), Product_Management.class));
                } else if (cd.isConnectingToInternet()) {
                    new loadcatlist().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        shopdetails.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Shop_Details.class));
            }
        });
        loadcatogery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    new loadcatlist().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class loadcatlist extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Loading Catogery List....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"getproductcatogery_shops.php";
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
            try {
                pd.dismiss();
                if (result.contains(":%ok")) {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 2;
                    int m = -1;
                    db.delete_catid();
                    for (int i = 1; i <= k; i++) {
                        m=m+1;
                        int a = m;
                        m=m+1;
                        db.add_catid(got[a], got[m]);
                    }
                    Toasty.info(getApplicationContext(), "Catogery List Updated", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
