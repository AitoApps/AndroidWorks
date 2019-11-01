package com.sanji;

import adapter.ProductCatogery_ListAdapter;
import adapter.Product_Listnew_Search_ListAdapter;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import data.Productlist_new_FeedItem;
import data.productCatogery_New_Feeditem;
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

public class Fragment_Search extends Fragment {
    private final long DELAY = 1000;
    ImageView carticon;
    RelativeLayout cartlyt;
    ConnectionDetecter cd;
    Context context;
    public DatabaseHandler db;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    private List<productCatogery_New_Feeditem> feedItems;
    ImageView heart;
    String imgid;
    String imgsig;
    TextView itemcount;
    int limit = 0;
    private ProductCatogery_ListAdapter listAdapter;
    TextView loctext;

    public MediaPlayer mediaPlayer;
    ImageView nointernet;
    RecyclerView recylerview;
    EditText search;
    RecyclerView searchrecylerview;

    public List<Productlist_new_FeedItem> srchfeedItems;

    public Product_Listnew_Search_ListAdapter srchlistAdapter;

    public Timer timer = new Timer();
    public String txt_search;
    TextView viewcart;
    ImageView voicesearch;

    public class MainSliderAdapter extends SliderAdapter {
        public MainSliderAdapter() {
        }

        public int getItemCount() {
            return 3;
        }

        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            String str = "productpics/";
            if (position == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append(str);
                sb.append(Fragment_Search.imgid);
                sb.append("_1.jpg");
                viewHolder.bindImageSlide(sb.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append(str);
                sb2.append(Fragment_Search.imgid);
                sb2.append("_2.jpg");
                viewHolder.bindImageSlide(sb2.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.weblink);
                sb3.append(str);
                sb3.append(Fragment_Search.imgid);
                sb3.append("_3.jpg");
                viewHolder.bindImageSlide(sb3.toString(), R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadcatogery extends AsyncTask<String, Void, String> {
        public loadcatogery() {
        }
        public void onPreExecute() {
            Fragment_Search.recylerview.setVisibility(View.GONE);
            Fragment_Search.heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductcatgory_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                sb2.append(URLEncoder.encode("", str));
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
                if (result.contains(":%ok")) {
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        Fragment_Search.db.deleteproductcat();
                        for (int i = 1; i <= k; i++) {
                            int m2 = m + 1;
                            int a = m2;
                            int m3 = m2 + 1;
                            int a1 = m3;
                            m = m3 + 1;
                            Fragment_Search.db.addproductcatlist(got[a].trim(), got[a1], got[m]);
                        }
                        Fragment_Search.loadcatogertlist();
                    } catch (Exception e) {
                    }
                } else {
                    Fragment_Search.heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }

    public class loadproductsearch extends AsyncTask<String, Void, String> {
        public loadproductsearch() {
        }
        public void onPreExecute() {
            Fragment_Search.recylerview.setVisibility(View.GONE);
            Fragment_Search.searchrecylerview.setVisibility(View.GONE);
            Fragment_Search.heart.setVisibility(View.VISIBLE);
            Fragment_Search.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            String str2 = ":%";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproduct_search.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append("3:%0:%0:%");
                sb3.append(Fragment_Search.txt_search);
                sb3.append(str2);
                sb3.append(Fragment_Search.db.get_latitude());
                sb3.append(str2);
                sb3.append(Fragment_Search.db.get_longtitude());
                sb3.append(str2);
                sb3.append(Fragment_Search.limit);
                sb2.append(URLEncoder.encode(sb3.toString(), str));
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
        public void onPostExecute(final String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        Fragment_Search.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) / 20;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    Productlist_new_FeedItem item2 = new Productlist_new_FeedItem();
                                    int m2 = m + 1;
                                    item2.setsn(got[m2]);
                                    int m3 = m2 + 1;
                                    item2.setproductcat(got[m3]);
                                    int m4 = m3 + 1;
                                    item2.setshopid(got[m4]);
                                    int m5 = m4 + 1;
                                    item2.setitemname(got[m5]);
                                    int m6 = m5 + 1;
                                    item2.setprice(got[m6]);
                                    int m7 = m6 + 1;
                                    item2.setogprice(got[m7]);
                                    int m8 = m7 + 1;
                                    item2.setitemdiscription(got[m8]);
                                    int m9 = m8 + 1;
                                    item2.setMinorder(got[m9]);
                                    int m10 = m9 + 1;
                                    item2.setUnittype(got[m10]);
                                    int m11 = m10 + 1;
                                    item2.setimgsig1(got[m11]);
                                    int m12 = m11 + 1;
                                    item2.setshopname(got[m12]);
                                    int m13 = m12 + 1;
                                    item2.setshopplace(got[m13]);
                                    int m14 = m13 + 1;
                                    item2.setshopmobile(got[m14]);
                                    int m15 = m14 + 1;
                                    item2.setshoptime(got[m15]);
                                    int m16 = m15 + 1;
                                    item2.setlocation(got[m16]);
                                    int m17 = m16 + 1;
                                    item2.setDelicharge(got[m17]);
                                    int m18 = m17 + 1;
                                    item2.setDelidisc(got[m18]);
                                    int m19 = m18 + 1;
                                    item2.setMinordramt(got[m19]);
                                    int m20 = m19 + 1;
                                    item2.setshopimgsig(got[m20]);
                                    m = m20 + 1;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(String.format("%.2f", new Object[]{Double.valueOf(Double.parseDouble(got[m]))}));
                                    sb.append(" KM");
                                    item2.setdistance(sb.toString());
                                    Fragment_Search.srchfeedItems.add(item2);
                                }
                            }
                        });
                        Fragment_Search.heart.setVisibility(View.GONE);
                        Fragment_Search.searchrecylerview.setVisibility(View.VISIBLE);
                        Fragment_Search.srchlistAdapter.notifyDataSetChanged();
                    } catch (Exception a) {
                        Toasty.info(Fragment_Search.context, Log.getStackTraceString(a), 1).show();
                    }
                    return;
                }
                Fragment_Search.heart.setVisibility(View.GONE);
            } catch (Exception a2) {
                Toasty.info(Fragment_Search.context, Log.getStackTraceString(a2), 1).show();
            }
        }
    }

    public Fragment_Search() {
        String str = "";
        txt_search = str;
        imgid = str;
        imgsig = str;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        context = getContext();
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        loctext = (TextView) view.findViewById(R.id.loctext);
        face = Typeface.createFromAsset(getActivity().getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getActivity().getAssets(), "font/proximanormal.ttf");
        recylerview = (RecyclerView) view.findViewById(R.id.recylerview);
        searchrecylerview = (RecyclerView) view.findViewById(R.id.searchrecylerview);
        nointernet = (ImageView) view.findViewById(R.id.nointernet);
        heart = (ImageView) view.findViewById(R.id.heart);
        search = (EditText) view.findViewById(R.id.search);
        itemcount = (TextView) view.findViewById(R.id.itemcount);
        viewcart = (TextView) view.findViewById(R.id.viewcart);
        carticon = (ImageView) view.findViewById(R.id.carticon);
        voicesearch = (ImageView) view.findViewById(R.id.voicesearch);
        cartlyt = (RelativeLayout) view.findViewById(R.id.cartlyt);
        Glide.with(getActivity()).load(Integer.valueOf(R.drawable.loading)).into(heart);
        if (db.get_locationaddress().equalsIgnoreCase("")) {
            change_location("Select Your Locality");
        } else {
            change_location(db.get_locationaddress());
        }
        loctext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Fragment_Search.startActivity(new Intent(Fragment_Search.getActivity(), Location_Picker.class));
            }
        });
        feedItems = new ArrayList();
        listAdapter = new ProductCatogery_ListAdapter(getActivity(), feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recylerview.setAdapter(listAdapter);
        srchfeedItems = new ArrayList();
        srchlistAdapter = new Product_Listnew_Search_ListAdapter(getActivity(), srchfeedItems, this);
        searchrecylerview.setLayoutManager(new GridLayoutManager(context, 2));
        searchrecylerview.setAdapter(srchlistAdapter);
        ArrayList<String> id6 = db.getproductcatlist();
        if (((String[]) id6.toArray(new String[id6.size()])).length > 0) {
            loadcatogertlist();
        } else {
            new loadcatogery().execute(new String[0]);
        }
        itemcount.setTypeface(face);
        viewcart.setTypeface(face);
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Fragment_Search.timer != null) {
                    Fragment_Search.timer.cancel();
                }
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 3) {
                    Fragment_Search.timer = new Timer();
                    Fragment_Search.timer.schedule(new TimerTask() {
                        public void run() {
                            Fragment_Search.getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Fragment_Search.searchrecylerview.setVisibility(View.VISIBLE);
                                    Fragment_Search.recylerview.setVisibility(View.GONE);
                                    Fragment_Search.txt_search = Fragment_Search.search.getText().toString();
                                    new loadproductsearch().execute(new String[0]);
                                }
                            });
                        }
                    }, 1000);
                    return;
                }
                Fragment_Search.searchrecylerview.setVisibility(View.GONE);
                Fragment_Search.recylerview.setVisibility(View.VISIBLE);
            }
        });
        voicesearch.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                intent.putExtra("android.speech.extra.PROMPT", "Voice searching...");
                try {
                    Fragment_Search.startActivityForResult(intent, 1400);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Fragment_Search.context, "Oops! Your device doesn't support voice search", 0).show();
                }
            }
        });
        viewcart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Fragment_Search.context, Cart.class);
                i.setFlags(268435456);
                Fragment_Search.startActivity(i);
            }
        });
        carticon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Fragment_Search.context, Cart.class);
                i.setFlags(268435456);
                Fragment_Search.startActivity(i);
            }
        });
        cartlyt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Fragment_Search.context, Cart.class);
                i.setFlags(268435456);
                Fragment_Search.startActivity(i);
            }
        });
        refreshcart();
        return view;
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

    public void change_location(String location) {
        loctext.setText(location);
    }

    public void loadcatogertlist() {
        recylerview.setVisibility(View.GONE);
        heart.setVisibility(View.VISIBLE);
        ArrayList<String> id6 = db.getproductcatlist();
        String[] c6 = (String[]) id6.toArray(new String[id6.size()]);
        feedItems.clear();
        if (c6.length > 0) {
            int a = c6.length / 3;
            int m = -1;
            for (int j = 1; j <= a; j++) {
                productCatogery_New_Feeditem item = new productCatogery_New_Feeditem();
                int m2 = m + 1;
                item.setSn(c6[m2]);
                int m3 = m2 + 1;
                item.setCatogery(c6[m3]);
                m = m3 + 1;
                item.setImgsig(c6[m]);
                feedItems.add(item);
            }
        }
        heart.setVisibility(View.GONE);
        recylerview.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();
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
        } else {
            TextView textView2 = itemcount;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(db.get_totalqty());
            sb2.append(" items | ");
            sb2.append(rupee);
            sb2.append("0.00");
            textView2.setText(sb2.toString());
        }
        try {
            ((Control_Panel) getActivity()).setcartcount();
        } catch (Exception e) {
        }
    }

    public void photoview(final String imgsigs, String imgids) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.photoviews);
        imgid = imgids;
        imgsig = imgsigs;
        Slider.init(new ImageLoadingService() {
            public void loadImage(String url, ImageView imageView) {
                Glide.with(Fragment_Search.context).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(Fragment_Search.context).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(Fragment_Search.context).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
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
        Dialog dialog1 = new Dialog(context);
        final Dialog dialog2 = dialog1;
        dialog1.requestWindowFeature(1);
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
        AnonymousClass8 r10 = r0;
        final String str16 = rupee2;
        EditText editText3 = editText;
        final Dialog dialog12 = dialog1;
        final String str17 = cart_price1;
        AnonymousClass8 r0 = new TextWatcher() {
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
        AnonymousClass10 r7 = new OnClickListener(this) {
            final /* synthetic */ Fragment_Search this$0;

            {
                this$0 = this$0;
            }

            public void onClick(View view) {
                String str = "";
                if (!this$0.db.getcartexist(str18, str19).equalsIgnoreCase(str)) {
                    this$0.db.addcart_exitupdate(editText5.getText().toString(), textView.getText().toString().replace(str13, str), str18);
                    Toasty.info(this$0.context, "Cart Updated", 0).show();
                } else {
                    this$0.db.addcart(str19, str18, str3, str4, editText5.getText().toString(), textView.getText().toString().replace(str13, str), str5, str6, str7, str8, str9, str10, "0", str11, str12);
                }
                try {
                    this$0.mediaPlayer = MediaPlayer.create(this$0.context, R.raw.addtocart);
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
