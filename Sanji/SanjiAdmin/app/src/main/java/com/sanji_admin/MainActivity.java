package com.sanji_admin;

import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    String[] PERMISSIONS = {"android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    int PERMISSION_ALL = 1;
    ImageView addcatogery;
    ImageView appupdate;
    ImageView catogerychange;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    ImageView franchises;
    ImageView loadcatogery;
    ProgressDialog pd;
    ImageView productmanagement;
    ImageView products;
    ImageView productverification;
    ImageView shopcontroller;
    ImageView shopmanagmenet;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class changecatnoti extends AsyncTask<String, Void, String> {
        public changecatnoti() {
        }
        public void onPreExecute() {
            MainActivity.pd.setMessage("Please wait...");
            MainActivity.pd.setCancelable(false);
            MainActivity.pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("sendcatchangenotification.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Temp.areafcmid, "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                MainActivity.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(MainActivity.getApplicationContext(), "Notified", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(MainActivity.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }

    public class loadcatlist extends AsyncTask<String, Void, String> {
        public loadcatlist() {
        }
        public void onPreExecute() {
            MainActivity.pd.setMessage("Loading Catogery List....");
            MainActivity.pd.setCancelable(false);
            MainActivity.pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductcatogery_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode("", "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                MainActivity.pd.dismiss();
                if (result.contains(":%ok")) {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 2;
                    MainActivity.db.delete_catid();
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        int m2 = m + 1;
                        int a = m2;
                        m = m2 + 1;
                        MainActivity.db.add_catid(got[a], got[m]);
                    }
                    Toasty.info(MainActivity.getApplicationContext(), "Catogery List Updated", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toasty.info(MainActivity.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        productverification = (ImageView) findViewById(R.id.productverification);
        shopmanagmenet = (ImageView) findViewById(R.id.shopmanagmenet);
        shopcontroller = (ImageView) findViewById(R.id.shopcontroller);
        addcatogery = (ImageView) findViewById(R.id.addcatogery);
        loadcatogery = (ImageView) findViewById(R.id.loadcatogery);
        franchises = (ImageView) findViewById(R.id.franchises);
        productmanagement = (ImageView) findViewById(R.id.productmanagement);
        products = (ImageView) findViewById(R.id.products);
        catogerychange = (ImageView) findViewById(R.id.catogerychange);
        appupdate = (ImageView) findViewById(R.id.appupdate);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        if (udb.get_uid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }
        try {
            if (db.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                DatabaseHandler databaseHandler = db;
                StringBuilder sb = new StringBuilder();
                sb.append(width);
                sb.append("");
                databaseHandler.addscreenwidth(sb.toString());
            }
        } catch (Exception e) {
        }
        productmanagement.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Product_List.class));
            }
        });
        shopmanagmenet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Shop_management.class));
            }
        });
        productverification.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Product_verification.class));
            }
        });
        shopcontroller.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Shop_Expire_Controller.class));
            }
        });
        loadcatogery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.cd.isConnectingToInternet()) {
                    new loadcatlist().execute(new String[0]);
                } else {
                    Toasty.info(MainActivity.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        addcatogery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Product_Catogery.class));
            }
        });
        products.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Products_Reports.class));
            }
        });
        catogerychange.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.cd.isConnectingToInternet()) {
                    new changecatnoti().execute(new String[0]);
                } else {
                    Toasty.info(MainActivity.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        franchises.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Franchiesis.class));
            }
        });
        appupdate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!MainActivity.hasPermissions(MainActivity.this, MainActivity.PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, MainActivity.PERMISSIONS, MainActivity.PERMISSION_ALL);
                } else {
                    MainActivity.showalert_appupdate("Are you sure want to update this app ?");
                }
            }
        });
    }

    public void showalert_appupdate(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (!MainActivity.cd.isConnectingToInternet()) {
                    Toasty.info(MainActivity.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (MainActivity.cd.isConnectingToInternet()) {
                    MainActivity.download_app(Uri.parse("https://sanji.in/apps/sanji_admin.apk"));
                } else {
                    Toasty.info(MainActivity.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void download_app(Uri uri) {
        DownloadManager downloadManager = (DownloadManager) getSystemService("download");
        Request request = new Request(uri);
        request.setAllowedNetworkTypes(3);
        request.setTitle("sanji_admin.apk");
        request.setDescription("Downloading...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sanji_admin.apk");
        downloadManager.enqueue(request);
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
