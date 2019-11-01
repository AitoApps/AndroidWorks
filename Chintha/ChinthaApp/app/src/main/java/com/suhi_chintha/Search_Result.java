package com.suhi_chintha;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import chintha_adapter.SearchsAdapter;
import chintha_data.SearchsFeed;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search_Result extends AppCompatActivity {
    public static ArrayList<HashMap<String, String>> catlist = new ArrayList<>();
    public static String txtsearch = "";
    public AdView adView1;
    AdRequest adreq1;

    public SearchsAdapter apater;
    NetConnection cd;
    RelativeLayout content;
    int count = 0;
    final DataDb dataDb = new DataDb(this);
    final DataDB1 dataDb1 = new DataDB1(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    ImageView emptdata;
    Typeface face;

    public List<SearchsFeed> feed;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView listview;
    TextView loading;
    ImageView moveback;
    ImageView nonet;
    ProgressDialog progresspd;
    ImageView search;
    EditText searchbox;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.search_result_actvty);
        try {
            progresspd = new ProgressDialog(this);
            cd = new NetConnection(this);
            moveback = (ImageView) findViewById(R.id.moveback);
            searchbox = (EditText) findViewById(R.id.searchbox);
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            adView1 = (AdView) findViewById(R.id.adView1);
            adreq1 = new Builder().build();
            search = (ImageView) findViewById(R.id.search);
            listview = (ListView) findViewById(R.id.listview);
            content = (RelativeLayout) findViewById(R.id.content);
            feed = new ArrayList();
            apater = new SearchsAdapter(this, feed);
            listview.setAdapter(apater);
            loading = (TextView) findViewById(R.id.loading);
            loading.setText("തിരയുന്നു...കാത്തിരിക്കുക");
            loading.setTypeface(face);
            try {
                adView1.setAdListener(new AdListener() {
                    public void onAdFailedToLoad(int errorCode) {
                        try {
                            if (count <= 10) {
                                adView1.loadAd(adreq1);
                                count++;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
            searchbox.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    try {
                        if (!searchbox.getText().toString().equalsIgnoreCase("")) {
                            if (cd.isConnectingToInternet()) {
                                nonet. setVisibility(View.GONE);
                                limit = 0;
                                feed.clear();
                                apater.notifyDataSetChanged();
                                txtsearch = searchbox.getText().toString();
                                load_search();
                                return;
                            }
                            nonet.setVisibility(View.VISIBLE);
                            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    }
                }
            });
            search.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (searchbox.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) "Please enter search term", Toast.LENGTH_SHORT).show();
                        searchbox.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                        nonet. setVisibility(View.GONE);
                        limit = 0;
                        txtsearch = searchbox.getText().toString();
                        load_search();
                    } else {
                        nonet.setVisibility(View.VISIBLE);
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            moveback.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            listview = (ListView) findViewById(R.id.listview);
            emptdata = (ImageView) findViewById(R.id.emptydata);
            nonet = (ImageView) findViewById(R.id.nonets);
            footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
            listview.addFooterView(footerview);
            footerview. setVisibility(View.GONE);
            listview.setOnScrollListener(new OnScrollListener() {
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                        flag = false;
                        if (!cd.isConnectingToInternet()) {
                            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet_scroll, Toast.LENGTH_SHORT).show();
                        } else if (footerview.getVisibility() != View.VISIBLE) {
                            limit += 30;
                            searchload1();
                        }
                    }
                }

                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    if (arg1 == 2) {
                        flag = true;
                    }
                }
            });
            nonet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nonet. setVisibility(View.GONE);
                        limit = 0;
                        load_search();
                        return;
                    }
                    nonet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            if (cd.isConnectingToInternet()) {
                nonet. setVisibility(View.GONE);
                limit = 0;
                load_search();
            } else {
                nonet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
            }
            try {
                File f = new File(Environment.getExternalStorageDirectory()+File.separator+Static_Variable.foldername+"/bg/bg.png");
                if (f.exists()) {
                    try {
                        Glide.with(getApplicationContext()).asBitmap().load(f).into(new SimpleTarget<Bitmap>() {
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                content.setBackground(new BitmapDrawable(getResources(), bitmap));
                            }

                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                            }
                        });
                    } catch (Exception e4) {
                    }
                }
            } catch (Exception e3) {
            
            }
        } catch (Exception e3) {
        }
    }
    public void onResume() {
        super.onResume();
        count = 0;
        try {
            adView1.loadAd(adreq1);
        } catch (Exception e) {
        }
    }

    public void searchload1() {
        footerview.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url =Static_Variable.entypoint1+"getsearchlist.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+txtsearch).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (result.contains("%:ok")) {
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 2;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    SearchsFeed item = new SearchsFeed();
                                    m++;
                                    item.set_userid(got[m]);
                                    item.setDppic(Static_Variable.entypoint1 +"userphotosmall/"+got[m]+".jpg");
                                    m++;
                                    item.setName(got[m]);

                                    feed.add(item);
                                }
                                emptdata. setVisibility(View.GONE);
                                footerview. setVisibility(View.GONE);
                                apater.notifyDataSetChanged();
                                return;
                            }
                            footerview. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void load_search() {
        listview. setVisibility(View.GONE);
        nonet. setVisibility(View.GONE);
        emptdata. setVisibility(View.GONE);
        limit = 0;
        loading.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        String url =Static_Variable.entypoint1+"getsearchlist.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", limit+"%:"+txtsearch).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            loading. setVisibility(View.GONE);
                            if (result.contains("%:ok")) {
                                feed.clear();
                                String[] got = result.split("%:");
                                int k = (got.length - 1) / 2;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    SearchsFeed item = new SearchsFeed();
                                    m++;
                                    item.set_userid(got[m]);
                                    item.setDppic(Static_Variable.entypoint1 +"userphotosmall/"+got[m]+".jpg");
                                    m++;
                                    item.setName(got[m]);

                                    feed.add(item);
                                }
                                emptdata. setVisibility(View.GONE);
                                listview.setVisibility(View.VISIBLE);
                                apater.notifyDataSetChanged();
                                return;
                            }
                            emptdata.setVisibility(View.VISIBLE);
                            footerview. setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
}
