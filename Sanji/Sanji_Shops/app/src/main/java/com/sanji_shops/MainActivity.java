package com.sanji_shops;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] PERMISSIONS = {"android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    int PERMISSION_ALL = 1;
    ImageView appupdate;
    ImageView back;
    ConnectionDetecter cd;
    ImageView checkexpire;
    final DatabaseHandler db = new DatabaseHandler(this);
    ImageView deliveryreports;
    Typeface face;
    Typeface face1;
    ImageView loadcatogery;
    ImageView neworder;
    ProgressDialog pd;
    ImageView priceupdate;
    ImageView productmanagement;
    ImageView salsemanmanagement;
    ImageView shopdetails;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class getexpiredate extends AsyncTask<String, Void, String> {
        public getexpiredate() {
        }
        public void onPreExecute() {
            pd.setMessage("Getting Expire Date....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getexpiredate_shop.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(udb.get_shopid());
                sb3.append("");
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                pd.dismiss();
                if (result.contains(":%ok")) {
                    String[] date = result.split(":%")[0].split("-");
                    MainActivity mainActivity = this;
                    StringBuilder sb = new StringBuilder();
                    sb.append(date[2]);
                    sb.append("-");
                    sb.append(date[1]);
                    sb.append("-");
                    sb.append(date[0]);
                    show_expiredate(sb.toString());
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }

    public class loadcatlist extends AsyncTask<String, Void, String> {
        public loadcatlist() {
        }
        public void onPreExecute() {
            pd.setMessage("Loading Catogery List....");
            pd.setCancelable(false);
            pd.show();
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
                pd.dismiss();
                if (result.contains(":%ok")) {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 2;
                    db.delete_catid();
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        int m2 = m + 1;
                        int a = m2;
                        m = m2 + 1;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        productmanagement = (ImageView) findViewById(R.id.productmanagement);
        salsemanmanagement = (ImageView) findViewById(R.id.salsemanmanagement);
        shopdetails = (ImageView) findViewById(R.id.shopdetails);
        back = (ImageView) findViewById(R.id.back);
        priceupdate = (ImageView) findViewById(R.id.priceupdate);
        appupdate = (ImageView) findViewById(R.id.appupdate);
        checkexpire = (ImageView) findViewById(R.id.checkexpire);
        text = (TextView) findViewById(R.id.text);
        loadcatogery = (ImageView) findViewById(R.id.loadcatogery);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        neworder = (ImageView) findViewById(R.id.neworder);
        deliveryreports = (ImageView) findViewById(R.id.deliveryreports);
        if (udb.get_shopid().equalsIgnoreCase("")) {
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
        text.setText(udb.get_shopname());
        text.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ArrayList<String> id1 = db.getcatid();
        final String[] result = (String[]) id1.toArray(new String[id1.size()]);
        productmanagement.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (result.length > 0) {
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
        priceupdate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Price_Update.class));
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
        checkexpire.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    new getexpiredate().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        salsemanmanagement.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Salesman_List.class));
            }
        });
        neworder.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Order_Group.class));
            }
        });
        deliveryreports.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Delivery_Reports.class));
            }
        });
        appupdate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!hasPermissions(this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    showalert_appupdate("Are you sure want to update this app ?");
                }
            }
        });
    }

    public void show_expiredate(String expiredate1) {
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.alert_expiredate);
        dialog1.setCancelable(true);
        TextView txtexpiredate = (TextView) dialog1.findViewById(R.id.txtexpiredate);
        TextView expiredate = (TextView) dialog1.findViewById(R.id.expiredate);
        expiredate.setText(expiredate1);
        expiredate.setTypeface(face1);
        txtexpiredate.setTypeface(face);
        dialog1.show();
    }

    public void showalert_appupdate(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    download_app(Uri.parse("https://sanji.in/apps/sanji_shops.apk"));
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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
        request.setTitle("sanji_shops.apk");
        request.setDescription("Downloading...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sanji_shops.apk");
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
