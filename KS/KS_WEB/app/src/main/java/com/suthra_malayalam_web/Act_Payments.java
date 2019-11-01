package com.suthra_malayalam_web;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Act_Payments extends AppCompatActivity {
    final DataBase dataBase = new DataBase(this);
    Typeface face;
    ImageView heart;
    final DataBase_MobileNumber mdb = new DataBase_MobileNumber(this);
    ProgressDialog pd;
    TextView text;
    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvty_pymnt);
        heart = (ImageView) findViewById(R.id.heart_laoding);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text.setText(Static_Veriable.payment);
        text.setTypeface(face);
        webview = (WebView) findViewById(R.id.webview);
        pd = new ProgressDialog(this);
        webview.setWebViewClient(new webBrowser());
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String MyUA = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
        webview.getSettings().setUserAgentString(MyUA);
        webview.loadUrl("https://www.payumoney.com/paybypayumoney/#/C9123467E87DAA9E662E4847EA6184B0");
        Glide.with(this).load(R.drawable.img_loading).into(heart);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle((CharSequence) "Really Exit?").setMessage((CharSequence) Static_Veriable.backalert).setNegativeButton("No", (OnClickListener) null).setPositiveButton("Yes", (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Act_Payments.super.onBackPressed();
            }
        }).create().show();
    }

    public void activty_exit() {
        Intent intent = new Intent(getApplicationContext(), Cpanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public class pymnt_after extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Updating your payment...Please wait");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Static_Veriable.weblink +"afterpaymentsucuss.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(mdb.get_mob(), "UTF-8");
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
                activty_exit();
            } catch (Exception e) {
            }
        }
    }

    private class webBrowser extends WebViewClient {
        private webBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(24)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            heart.setVisibility(View.GONE);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            heart.setVisibility(View.VISIBLE);
            if (url.equals("http://sayyidsalman.info/pni/success.html")) {
                Toasty.info(getApplicationContext(), Static_Veriable.afterpaysuccess, 0).show();
                dataBase.add_purchase("1");
                new pymnt_after().execute(new String[0]);
            } else if (url.equals("http://sayyidsalman.info/pni/failure.html")) {
                Toasty.info(getApplicationContext(), Static_Veriable.afterpaycancel, 0).show();
                activty_exit();
            } else if (url.equals("http://sayyidsalman.info/pni/cancel.html")) {
                Toasty.info(getApplicationContext(), Static_Veriable.afterpaycancel, 0).show();
                activty_exit();
            }
        }
    }

}
