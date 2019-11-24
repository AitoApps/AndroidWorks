package com.sanji;

import adapter.Product_List_ByShops_ByCat_ListAdapter;
import adapter.Product_Listnew_ListAdapter;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Product_List_Byshops_ByCat_FeedItem;
import data.Productlist_new_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ProductList_New extends AppCompatActivity {
    private final long DELAY = 1000;
    ImageView back;
    ImageView carticon;
    RelativeLayout cartlyt;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    public List<Product_List_Byshops_ByCat_FeedItem> feedItems;
    View headerview;
    ImageView heart;
    String imgid="";
    String imgsig="";
    TextView itemcount;
    public int limit = 0;
    public Product_List_ByShops_ByCat_ListAdapter listAdapter;
    public MediaPlayer mediaPlayer;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    EditText search;
    public HeaderAndFooterRecyclerView searchrecylerview;
    public List<Productlist_new_FeedItem> srchfeedItems;
    public Product_Listnew_ListAdapter srchlistAdapter;
    public Timer timer = new Timer();
    public String txt_search;
    public UserDatabaseHandler udb;
    TextView viewcart;
    ImageView voicesearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list__new);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            itemcount = (TextView) findViewById(R.id.itemcount);
            viewcart = (TextView) findViewById(R.id.viewcart);
            carticon = (ImageView) findViewById(R.id.carticon);
            search = (EditText) findViewById(R.id.search);
            voicesearch = (ImageView) findViewById(R.id.voicesearch);
            cartlyt = (RelativeLayout) findViewById(R.id.cartlyt);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            searchrecylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.searchrecylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            itemcount.setTypeface(face);
            viewcart.setTypeface(face);
            feedItems = new ArrayList();
            listAdapter = new Product_List_ByShops_ByCat_ListAdapter(this, feedItems);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            recylerview.setLayoutManager(gridLayoutManager);
            recylerview.setAdapter(listAdapter);
            headerview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.headerview, recylerview.getHeaderContainer(), false);
            ImageView shopimg = (ImageView) headerview.findViewById(R.id.shopimg);
            TextView shopname = (TextView) headerview.findViewById(R.id.shopname);
            TextView placename = (TextView) headerview.findViewById(R.id.placename);
            TextView km = (TextView) headerview.findViewById(R.id.km);
            ImageView location = (ImageView) headerview.findViewById(R.id.location);
            ImageView website = (ImageView) headerview.findViewById(R.id.website);
            ImageView facebook = (ImageView) headerview.findViewById(R.id.facebook);
            ImageView instagram = (ImageView) headerview.findViewById(R.id.instagram);
            ImageView pinterest = (ImageView) headerview.findViewById(R.id.pinterest);
            ImageView youtube = (ImageView) headerview.findViewById(R.id.youtube);
            RelativeLayout socialcontroller = (RelativeLayout) headerview.findViewById(R.id.socialcontroller);
            recylerview.addHeaderView(headerview);
            shopname.setTypeface(face);
            placename.setTypeface(face1);
            km.setTypeface(face1);
            float ogheight = Float.valueOf(db.getscreenwidth()).floatValue();
            float calheight = 0.75f * ogheight;
            GridLayoutManager gridLayoutManager2 = gridLayoutManager;
            float f = ogheight;
            shopimg.getLayoutParams().height = Math.round(calheight);
            float f2 = calheight;
            RequestOptions rep = new RequestOptions().signature(new ObjectKey(Temp.shopimgsig));
            Glide.with(this).load(Temp.weblink+"shoppics/"+Temp.shopid+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

            shopname.setText(Temp.shopname);
            placename.setText(Temp.shopplace);
            km.setText(" , "+Temp.shopkm);

            website.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    openwebsite(Temp.shopweb);
                }
            });
            instagram.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    openinstagram(Temp.shopinstagram);
                }
            });
            facebook.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    openfacebook(Temp.shop_facebook);
                }
            });
            pinterest.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    openpinterest(Temp.shop_pinterst);
                }
            });
            youtube.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    openyoutube(Temp.shop_youtube);
                }
            });


            if ((Temp.shopinstagram.equalsIgnoreCase("") || Temp.shopinstagram.equalsIgnoreCase("NA")) && ((Temp.shop_facebook.equalsIgnoreCase("") || Temp.shop_facebook.equalsIgnoreCase("NA")) && (Temp.shop_pinterst.equalsIgnoreCase("") || Temp.shop_pinterst.equalsIgnoreCase("NA")))) {
                socialcontroller.setVisibility(View.GONE);

            }
            else {

                socialcontroller.setVisibility(View.VISIBLE);
                if (!Temp.shopweb.equalsIgnoreCase("") || Temp.shopweb.equalsIgnoreCase("NA")) {
                    website.setVisibility(View.GONE);
                } else {
                    website.setVisibility(View.VISIBLE);
                }
                if (!Temp.shopinstagram.equalsIgnoreCase("") || Temp.shopinstagram.equalsIgnoreCase("NA")) {
                    instagram.setVisibility(View.GONE);
                } else {
                    instagram.setVisibility(View.VISIBLE);
                }
                if (!Temp.shop_facebook.equalsIgnoreCase("") || Temp.shop_facebook.equalsIgnoreCase("NA")) {
                    facebook.setVisibility(View.GONE);
                } else {
                    facebook.setVisibility(View.VISIBLE);
                }
                if (!Temp.shop_pinterst.equalsIgnoreCase("") || Temp.shop_pinterst.equalsIgnoreCase("NA")) {
                    pinterest.setVisibility(View.GONE);
                } else {
                    pinterest.setVisibility(View.VISIBLE);
                }
                if (!Temp.shop_youtube.equalsIgnoreCase("") || Temp.shop_youtube.equalsIgnoreCase("NA")) {
                    youtube.setVisibility(View.GONE);
                } else {
                    youtube.setVisibility(View.VISIBLE);
                }




            }


               location.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        showmap(Temp.shop_latittude+","+Temp.shop_longtitude, Temp.shopname);
                    }
                });
                search.setTypeface(face1);
                srchfeedItems = new ArrayList();
                srchlistAdapter = new Product_Listnew_ListAdapter(this, srchfeedItems);
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 2);
                searchrecylerview.setLayoutManager(gridLayoutManager1);
                searchrecylerview.setAdapter(srchlistAdapter);
                back.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                nointernet.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        if (cd.isConnectingToInternet()) {
                            nointernet.setVisibility(View.GONE);
                            limit = 0;
                            new loadstatus().execute(new String[0]);
                            return;
                        }
                        nointernet.setVisibility(View.VISIBLE);
                        Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
                    }
                });
                viewcart.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                    }
                });
                carticon.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                    }
                });
                cartlyt.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                    }
                });
                Glide.with(this).load(R.drawable.loading).into(heart);
                if (!cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    GridLayoutManager gridLayoutManager3 = gridLayoutManager1;
                } else {
                    nointernet.setVisibility(View.VISIBLE);
                    GridLayoutManager gridLayoutManager4 = gridLayoutManager1;
                    Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
                }
                search.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (timer != null) {
                            timer.cancel();
                        }
                    }

                    public void afterTextChanged(Editable editable) {
                        if (editable.length() >= 3) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            searchrecylerview.setVisibility(View.VISIBLE);
                                            recylerview.setVisibility(View.GONE);
                                            txt_search = search.getText().toString();
                                            new loadproductsearch().execute(new String[0]);
                                        }
                                    });
                                }
                            }, 1000);
                            return;
                        }
                        searchrecylerview.setVisibility(View.GONE);
                        recylerview.setVisibility(View.VISIBLE);
                    }
                });
                voicesearch.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                        intent.putExtra("android.speech.extra.PROMPT", "Voice searching...");
                        try {
                            startActivityForResult(intent, 1400);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Oops! Your device doesn't support voice search", 0).show();
                        }
                    }
                });
            search.setTypeface(face1);
            srchfeedItems = new ArrayList();
            srchlistAdapter = new Product_Listnew_ListAdapter(this, srchfeedItems);
            GridLayoutManager gridLayoutManager12 = new GridLayoutManager(getApplicationContext(), 2);
            searchrecylerview.setLayoutManager(gridLayoutManager12);
            searchrecylerview.setAdapter(srchlistAdapter);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nointernet.setVisibility(View.GONE);
                        limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
                }
            });
            viewcart.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            carticon.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            cartlyt.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
            if (!cd.isConnectingToInternet()) {
            }
            search.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (timer != null) {
                        timer.cancel();
                    }
                }

                public void afterTextChanged(Editable editable) {
                    if (editable.length() >= 3) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        searchrecylerview.setVisibility(View.VISIBLE);
                                        recylerview.setVisibility(View.GONE);
                                        txt_search = search.getText().toString();
                                        new loadproductsearch().execute(new String[0]);
                                    }
                                });
                            }
                        }, 1000);
                        return;
                    }
                    searchrecylerview.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                }
            });
            voicesearch.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                    intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                    intent.putExtra("android.speech.extra.PROMPT", "Voice searching...");
                    try {
                        startActivityForResult(intent, 1400);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Oops! Your device doesn't support voice search", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
        }
    }


    public class MainSliderAdapter extends SliderAdapter {
        public MainSliderAdapter() {
        }

        public int getItemCount() {
            return 3;
        }

        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            if (position == 0) {
                viewHolder.bindImageSlide(Temp.weblink+"productpics/"+imgid+"_1.jpg", R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                viewHolder.bindImageSlide(Temp.weblink+"productpics/"+imgid+"_2.jpg", R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                viewHolder.bindImageSlide(Temp.weblink+"productpics/"+imgid+"_3.jpg", R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadproductsearch extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recylerview.setVisibility(View.GONE);
            searchrecylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getproduct_search.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("1:%"+Temp.shopid+":%0:%"+txt_search+":%"+db.get_latitude()+":%"+db.get_longtitude()+":%"+limit, "UTF-8");
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
        public void onPostExecute(final String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) / 20;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    Productlist_new_FeedItem item2 = new Productlist_new_FeedItem();
                                    m=m+1;
                                    item2.setSn(got[m]);
                                    m=m+1;
                                    item2.setProductcat(got[m]);
                                    m=m+1;
                                    item2.setShopid(got[m]);
                                    m=m+1;
                                    item2.setItemname(got[m]);
                                    m=m+1;
                                    item2.setPrice(got[m]);
                                    m=m+1;
                                    item2.setOgprice(got[m]);
                                    m=m+1;
                                    item2.setItemdiscription(got[m]);
                                    m=m+1;
                                    item2.setMinorder(got[m]);
                                    m=m+1;
                                    item2.setUnittype(got[m]);
                                    m=m+1;
                                    item2.setImgsig1(got[m]);
                                    m=m+1;
                                    item2.setShopname(got[m]);
                                    m=m+1;
                                    item2.setShopplace(got[m]);
                                    m=m+1;
                                    item2.setShopmobile(got[m]);
                                    m=m+1;
                                    item2.setShoptime(got[m]);
                                    m=m+1;
                                    item2.setLocation(got[m]);
                                    m=m+1;
                                    item2.setDelicharge(got[m]);
                                    m=m+1;
                                    item2.setDelidisc(got[m]);
                                    m=m+1;
                                    item2.setMinordramt(got[m]);
                                    m=m+1;
                                    item2.setShopimgsig(got[m]);
                                    m=m+1;
                                    item2.setDistance(String.format("%.2f", Double.parseDouble(got[m]))+" KM");
                                    srchfeedItems.add(item2);
                                }
                            }
                        });
                        heart.setVisibility(View.GONE);
                        searchrecylerview.setVisibility(View.VISIBLE);
                        srchlistAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nodata.setVisibility(View.GONE);
            recylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {

            try {
                String link=Temp.weblink+"getproductlist_cats_byshopid_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.shopid, "UTF-8");
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
                if (result.contains("#:#ok")) {
                    try {
                        feedItems.clear();
                        String[] got = result.split("#:#");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Product_List_Byshops_ByCat_FeedItem item = new Product_List_Byshops_ByCat_FeedItem();
                            m=m+1;
                            item.setproducts(got[m]);
                            m=m+1;
                            item.setcatid(got[m]);
                            m=m+1;
                            item.setcatname(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    nodata.setVisibility(View.GONE);
                    heart.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                recylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), 1).show();
            }
        }
    }

    public void openwebsite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openfacebook(String username) {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/"+username)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+username)));
        }
    }

    public void openinstagram(String username) {
        Intent likeIng = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/"+username));
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+username)));
        }
    }

    public void openpinterest(String username) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("pinterest://www.pinterest.com/")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pinterest.com/"+username)));
        }
    }

    public void openyoutube(String channelname) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/user/"+channelname));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            intent2.setData(Uri.parse("https://www.youtube.com/user/"+channelname));
            startActivity(intent2);
        }
    }

    public void onBackPressed() {
        if (recylerview.getVisibility() != View.VISIBLE) {
            recylerview.setVisibility(View.VISIBLE);
            searchrecylerview.setVisibility(View.GONE);
            search.setText("");
            return;
        }
        super.onBackPressed();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 1400 && resultCode == -1 && data2 != null) {
            String str = "android.speech.extra.RESULTS";
            if (data2.getStringArrayListExtra(str).size() > 0) {
                search.setText((CharSequence) data2.getStringArrayListExtra(str).get(0));
                limit = 0;
                txt_search = search.getText().toString();
                EditText editText = search;
                editText.setSelection(editText.getText().toString().length());
                new loadproductsearch().execute(new String[0]);
            }
        }
    }

    public void showdiscription(String txtdiscription, String deli_type, String dlcharge, String ddsic) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.home_productdiscription);
        TextView discription = (TextView) dialog.findViewById(R.id.discription);
        RelativeLayout lytdeliverytype = (RelativeLayout) dialog.findViewById(R.id.lytdeliverytype);
        TextView deliverytype = (TextView) dialog.findViewById(R.id.deliverytype);
        TextView txtdeliverycharge = (TextView) dialog.findViewById(R.id.txtdeliverycharge);
        TextView deliverycharge = (TextView) dialog.findViewById(R.id.deliverycharge);
        TextView deliverydic = (TextView) dialog.findViewById(R.id.deliverydic);
        discription.setTypeface(face1);
        deliverytype.setTypeface(face);
        deliverycharge.setTypeface(face);
        deliverydic.setTypeface(face);
        txtdeliverycharge.setTypeface(face);
        discription.setText(fromHtml(txtdiscription));
        String rupee = getResources().getString(R.string.Rs);
        if (deli_type.equalsIgnoreCase("2")) {
            lytdeliverytype.setVisibility(View.VISIBLE);
            String str = "";
            if (dlcharge.equalsIgnoreCase(str) || dlcharge.equalsIgnoreCase("0")) {
                deliverycharge.setVisibility(View.INVISIBLE);
                txtdeliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setText("No Delivery Charge");
            } else {
                deliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setText("Delivery Charge : ");
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(dlcharge);
                deliverycharge.setText(sb.toString());
            }
            if (ddsic.equalsIgnoreCase(str)) {
                deliverydic.setVisibility(View.GONE);
            } else {
                deliverydic.setVisibility(View.VISIBLE);
                deliverydic.setText(ddsic);
            }
        } else {
            lytdeliverytype.setVisibility(View.GONE);
        }
        dialog.show();
    }
    public void call(String mob) {
        String str = "android.permission.CALL_PHONE";
        try {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{str}, 1);
                return;
            }
            Intent callIntent = new Intent("android.intent.action.CALL");
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(mob);
            callIntent.setData(Uri.parse(sb.toString()));
            startActivity(callIntent);
        } catch (Exception e) {
        }
    }
    public void web(String web) {
        Intent pagIntent = new Intent("android.intent.action.VIEW");
        pagIntent.setData(Uri.parse(web));
        try {
            startActivity(pagIntent);
        } catch (Exception e) {
        }
    }
    public void download(String link) {
        String str = "android.intent.action.VIEW";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("market://details?id=");
            sb.append(link);
            startActivity(new Intent(str, Uri.parse(sb.toString())));
        } catch (ActivityNotFoundException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://play.google.com/store/apps/details?id=");
            sb2.append(link);
            startActivity(new Intent(str, Uri.parse(sb2.toString())));
        }
    }

    public void showalertcall(String message, final String mob) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                call(mob);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalertweb(String message, final String link) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                web(link);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalertapp(String message, final String link) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                download(link);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    public void showmap(String gps, String shopname) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("http://maps.google.com/maps?q=loc:");
            sb.append(gps);
            sb.append(" (");
            sb.append(shopname);
            sb.append(")");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "ദയവായി താങ്കളുടെ ഫോണില്‍ ഗൂഗിള്‍ മാപ്പ് ഇന്‍സ്റ്റാള്‍ ചെയ്യുക ", 0).show();
        }
    }

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_maxqty1) {
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.addtocart);
            mediaPlayer.setVolume(0.1f, 0.1f);
            mediaPlayer.start();
        } catch (Exception e) {
        }
    }
    public void onResume() {
        super.onResume();
        refreshcart();
    }

    public void refreshcart() {
        String rupee = getResources().getString(R.string.Rs);
        float gtotal = db.get_cartgrandtotal();
        if (gtotal > 0.0f) {
            TextView textView = itemcount;
            StringBuilder sb = new StringBuilder();
            sb.append(db.get_totalqty());
            sb.append(" Items | ");
            sb.append(rupee);
            sb.append(String.format("%.2f", new Object[]{Float.valueOf(gtotal)}));
            textView.setText(sb.toString());
            return;
        }
        TextView textView2 = itemcount;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(db.get_totalqty());
        sb2.append(" items | ");
        sb2.append(rupee);
        sb2.append("0.00");
        textView2.setText(sb2.toString());
    }

    public void photoview(final String imgsigs, String imgids) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.photoviews);
        imgid = imgids;
        imgsig = imgsigs;
        Slider.init(new ImageLoadingService() {
            public void loadImage(String url, ImageView imageView) {
                Glide.with(getApplicationContext()).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(getApplicationContext()).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(getApplicationContext()).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });
        ((Slider) dialog.findViewById(R.id.banner_slider1)).setAdapter(new MainSliderAdapter());
        dialog.show();
    }

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_imgsig1, String cart_minqty1, String cart_unittype1, String cart_shopname1, String cart_delicharge1, String cart_delidisc1, String minordramt, String imgsig1) {
        String str = cart_shopid1;
        String str2 = cart_productid1;
        final String str3 = cart_productname1;
        final String str4 = cart_price1;
        final String str5 = cart_imgsig1;
        final String str6 = cart_minqty1;
        final String str7 = cart_unittype1;
        final String str8 = cart_shopname1;
        final String str9 = cart_delicharge1;
        final String str10 = cart_delidisc1;
        final String str11 = minordramt;
        final String str12 = imgsig1;
        Dialog dialog1 = new Dialog(this);
        final Dialog dialog2 = dialog1;
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.addtocart);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);
        EditText qty = (EditText) dialog1.findViewById(R.id.qty);
        EditText editText = qty;
        TextView unittype = (TextView) dialog1.findViewById(R.id.unittype);
        TextView price = (TextView) dialog1.findViewById(R.id.price);
        final TextView textView = price;
        Button buy = (Button) dialog1.findViewById(R.id.buy);
        ImageView adminclose = (ImageView) dialog1.findViewById(R.id.adminclose);
        String rupee = getResources().getString(R.string.Rs);
        Button buy2 = buy;
        final String str13 = rupee;
        itemname.setText(cart_productname1);
        qty.setText(cart_minqty1);
        Button buy3 = buy2;
        qty.setSelection(cart_minqty1.length());
        TextView textView2 = itemname;
        unittype.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(cart_unittype1)));
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        TextView textView3 = unittype;
        sb.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(cart_minqty1) * Float.parseFloat(cart_price1))}));
        price.setText(sb.toString());
        Button buy4 = buy3;
        final EditText editText2 = qty;
        ImageView adminclose2 = adminclose;
        String rupee2 = rupee;
        final String rupee3 = cart_minqty1;
        String str14 = str2;
        EditText qty2 = qty;
        final TextView textView4 = price;
        String str15 = str;
        AnonymousClass21 r10 = r0;
        final String str16 = rupee2;
        EditText editText3 = editText;
        final Dialog dialog12 = dialog1;
        final String str17 = cart_price1;
        AnonymousClass21 r0 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                if (!editText2.getText().toString().equalsIgnoreCase("") && Float.valueOf(editText2.getText().toString()).floatValue() >= 0.0f) {
                    float qty1 = Float.parseFloat(editText2.getText().toString()) / Float.parseFloat(rupee3);
                    TextView textView = textView4;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str16);
                    sb.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(str17) * qty1)}));
                    textView.setText(sb.toString());
                }
            }
        };
        qty2.addTextChangedListener(r10);
        adminclose2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog12.dismiss();
            }
        });
        ImageView imageView = adminclose2;
        EditText editText4 = qty2;
        Dialog dialog13 = dialog12;
        final String str18 = str14;
        final String str19 = str15;
        final EditText editText5 = editText3;
        AnonymousClass23 r7 = new OnClickListener(this) {


            public void onClick(View view) {
                String str = "";
                if (!this$0.db.getcartexist(str18, str19).equalsIgnoreCase(str)) {
                    this$0.db.addcart_exitupdate(editText5.getText().toString(), textView.getText().toString().replace(str13, str), str18);
                    Toasty.info(this$0.getApplicationContext(), "Cart Updated", 0).show();
                } else {
                    this$0.db.addcart(str19, str18, str3, str4, editText5.getText().toString(), textView.getText().toString().replace(str13, str), str5, str6, str7, str8, str9, str10, "0", str11, str12);
                }
                try {
                    this$0.mediaPlayer = MediaPlayer.create(this$0.getApplicationContext(), R.raw.addtocart);
                    this$0.mediaPlayer.setVolume(0.1f, 0.1f);
                    this$0.mediaPlayer.start();
                } catch (Exception e) {
                }
                dialog2.dismiss();
                this$0.refreshcart();
            }
        };
        buy4.setOnClickListener(r7);
        dialog13.show();
    }
}
