package com.dlkitmaker_feeds;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class WebResources extends AppCompatActivity {
    WebView webview;
    TextView text;
    ProgressBar pb;
    Typeface face;
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_webresources);
        text=(TextView)findViewById(R.id.title);
        webview =(WebView)findViewById(R.id.webview);
        pb=findViewById(R.id.pb);
        back=findViewById(R.id.moveback);
        face = Typeface.createFromAsset(getAssets(), "fonts/heading.otf");
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptCookie(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
        text.setText(Temp.resourcetitle);
        text.setTypeface(face);
        adView1=findViewById(R.id.adView1);
        intestrial = new InterstitialAd(WebResources.this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
        adreq = new AdRequest.Builder().build();
        adreq1 = new AdRequest.Builder().build();

        page_render();


        try
        {
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    try
                    {
                        if(count<=20)
                        {
                            adView1.loadAd(adreq1);
                            count++;
                        }


                    }
                    catch (Exception a)
                    {

                    }

                }
            });

            intestrial.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {

                    if(intcount<=20) {
                        intestrial.loadAd(adreq);
                        intcount++;
                    }

                }
            });

        }
        catch (Exception a)
        {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        count=0;
        intcount=0;

        try
        {
            adView1.loadAd(adreq1);
            intestrial.loadAd(adreq);
        }
        catch (Exception a)
        {

        }
    }

    @Override
    public void onBackPressed() {

        if (webview.canGoBack()) {
            webview.goBack();
        } else {

            if(intestrial.isLoaded())
            {
                intestrial.show();
            }

            super.onBackPressed();
        }
    }

    protected void page_render(){
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                pb.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url){
            }
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                pb.setVisibility(View.VISIBLE);
                view.loadUrl(request.getUrl().toString());
                return true;
            }

        });
        webview.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                pb.setProgress(newProgress);
                if(newProgress == 100){
                    pb.setVisibility(View.GONE);
                }
            }
        });

        webview.loadUrl(Temp.resourcelink);
    }


}
