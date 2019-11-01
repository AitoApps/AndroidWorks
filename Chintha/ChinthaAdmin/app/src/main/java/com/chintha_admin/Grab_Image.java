package com.chintha_admin;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Grab_Image extends AppCompatActivity {
    Button btnsearch;
    final DatabaseHandler_Images db = new DatabaseHandler_Images(this);
    ImageView finalveri;
    EditText search;
    WebView web;

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
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
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    class MyJavaScriptInterface {
        private Context ctx;

        MyJavaScriptInterface(Context ctx2) {
            ctx = ctx2;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            Elements links = Jsoup.parse(html).getElementsByTag("article");
            db.deletefcmid();
            Iterator it = links.iterator();
            while (it.hasNext()) {
                db.addfcmid(((Element) it.next()).attr("data-thumb-url"));
            }
            new imageupdates(getApplicationContext()).fvrtlist();
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_grab__image);
        search = (EditText) findViewById(R.id.search);
        btnsearch = (Button) findViewById(R.id.btnsearch);
        finalveri = (ImageView) findViewById(R.id.finalveri);
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        web.setWebViewClient(new MyBrowser());
        web.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        CookieManager.getInstance().setAcceptCookie(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
        btnsearch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                WebView webView = web;

                webView.loadUrl("https://www.gettyimages.in/photos/indian-actress?family=editorial&mediatype=photography&page="+search.getText().toString()+"&phrase=indian%20actress&sort=mostpopular");
            }
        });
        finalveri.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                web.loadUrl("javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
    }
}
