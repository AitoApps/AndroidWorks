package com.sanji;

import adapter.MainCart_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import data.MainCart_FeedItem;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    Typeface face1;
    private List<MainCart_FeedItem> feedItems;
    ListView list;
    private MainCart_ListAdapter listAdapter;

    public MediaPlayer mediaPlayer;
    ImageView nodata;
    Button ordernow;
    ProgressBar pb;
    ProgressDialog pd;
    TextView title;
    TextView totalcount;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_cart);
        back = (ImageView) findViewById(R.id.back);
        nodata = (ImageView) findViewById(R.id.nodata);
        title = (TextView) findViewById(R.id.title);
        totalcount = (TextView) findViewById(R.id.totalcount);
        list = (ListView) findViewById(R.id.list);
        ordernow = (Button) findViewById(R.id.ordernow);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        pb = (ProgressBar) findViewById(R.id.pb);
        feedItems = new ArrayList();
        listAdapter = new MainCart_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        title.setText("Cart");
        title.setTypeface(face1);
        ordernow.setText("Order Now");
        ordernow.setTypeface(face1);
        totalcount.setTypeface(face);
        ordernow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = "";
                String str2 = "";
                String str3 = "";
                int islow = 0;
                ArrayList<String> id1 = Cart.db.getcheck_minordrqty();
                String[] k = (String[]) id1.toArray(new String[id1.size()]);
                int i = 0;
                while (true) {
                    if (i >= k.length) {
                        break;
                    }
                    String shopname = k[i];
                    int i2 = i + 1;
                    String currntamt = k[i2];
                    int i3 = i2 + 1;
                    String minamunt = k[i3];
                    if (Float.parseFloat(minamunt) > Float.parseFloat(currntamt)) {
                        islow = 1;
                        Cart cart = Cart.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("ക്ഷമിക്കണം ! ");
                        sb.append(shopname);
                        sb.append("എന്ന ഷോപ്പിന്റെ മിനിമം ഓര്‍ഡര്‍ ");
                        sb.append(minamunt);
                        sb.append(" രൂപയുടേതാണ്. ദയവായി ഓര്‍ഡറില്‍ മാറ്റം വരുത്തുക\n");
                        cart.show_lowcart(sb.toString());
                        break;
                    }
                    i = i3 + 1;
                }
                if (islow == 0) {
                    Cart.startActivity(new Intent(Cart.getApplicationContext(), User_Address.class));
                }
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Cart.onBackPressed();
            }
        });
    }
    public void onResume() {
        super.onResume();
        try {
            calculate_delicharge();
        } catch (Exception a) {
            Toasty.info(getApplicationContext(), Log.getStackTraceString(a), 1).show();
        }
    }

    public void calculate_delicharge() {
        ArrayList<String> id1 = db.getcart_fordelicalc();
        String[] m = (String[]) id1.toArray(new String[id1.size()]);
        int i = 0;
        while (i < m.length) {
            String shopid = m[i];
            int i2 = i + 1;
            String shopdelicharge = m[i2];
            char c = 1;
            int i3 = i2 + 1;
            String shoptotalprice = m[i3];
            String str = "0";
            if (!shopdelicharge.equalsIgnoreCase(str) && !shopdelicharge.equalsIgnoreCase("NA")) {
                String str2 = "";
                if (!shopdelicharge.equalsIgnoreCase(str2)) {
                    String str3 = "#";
                    String str4 = "-";
                    if (!shopdelicharge.contains(str3) && shopdelicharge.contains(str4)) {
                        String[] p = shopdelicharge.split(str4);
                        Float charge = checkpricein(Float.parseFloat(p[0]), Float.parseFloat(shoptotalprice), Float.parseFloat(p[1]));
                        DatabaseHandler databaseHandler = db;
                        StringBuilder sb = new StringBuilder();
                        sb.append(charge);
                        sb.append(str2);
                        databaseHandler.update_finalcharges(shopid, sb.toString(), shoptotalprice);
                        i = i3 + 1;
                    } else if (!shopdelicharge.contains(str3) || !shopdelicharge.contains(":")) {
                        db.update_finalcharges(shopid, str, shoptotalprice);
                        i = i3 + 1;
                    } else {
                        String[] f = shopdelicharge.split(str3);
                        int y = 0;
                        while (true) {
                            if (y >= f.length) {
                                break;
                            }
                            String[] p2 = f[y].split(str4);
                            Float charge2 = checkpricein(Float.parseFloat(p2[0]), Float.parseFloat(shoptotalprice), Float.parseFloat(p2[c]));
                            if (charge2.floatValue() > 0.0f) {
                                DatabaseHandler databaseHandler2 = db;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(charge2);
                                sb2.append(str2);
                                databaseHandler2.update_finalcharges(shopid, sb2.toString(), shoptotalprice);
                                break;
                            }
                            y++;
                            c = 1;
                        }
                        i = i3 + 1;
                    }
                }
            }
            db.update_finalcharges(shopid, str, shoptotalprice);
            i = i3 + 1;
        }
        loadmaindata();
        calculatetotal();
    }

    public Float checkpricein(float to, float price, float dcharcge) {
        Float dprice = Float.valueOf(0.0f);
        if (price <= to) {
            return Float.valueOf(dcharcge);
        }
        return dprice;
    }

    public void loadmaindata() {
        nodata.setVisibility(View.GONE);
        feedItems.clear();
        ArrayList<String> id1 = db.getmain_cart();
        String[] k = (String[]) id1.toArray(new String[id1.size()]);
        int i = 0;
        while (i < k.length) {
            MainCart_FeedItem item = new MainCart_FeedItem();
            item.setShopid(k[i]);
            int i2 = i + 1;
            item.setShopname(k[i2]);
            int i3 = i2 + 1;
            item.setDelidisc(k[i3]);
            int i4 = i3 + 1;
            item.setTotalcharge(k[i4]);
            int i5 = i4 + 1;
            item.setDeliverycharge(k[i5]);
            int i6 = i5 + 1;
            item.setMiniordramt(k[i6]);
            int i7 = i6 + 1;
            item.setimgsig(k[i7]);
            feedItems.add(item);
            i = i7 + 1;
        }
        pb.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();
    }

    public void calculatetotal() {
        String rupee = getResources().getString(R.string.Rs);
        float gtotal = db.get_cartgrandtotal();
        if (gtotal > 0.0f) {
            TextView textView = totalcount;
            StringBuilder sb = new StringBuilder();
            sb.append(db.get_totalqty());
            sb.append(" Items | ");
            sb.append(rupee);
            sb.append(String.format("%.2f", new Object[]{Float.valueOf(gtotal)}));
            textView.setText(sb.toString());
            return;
        }
        TextView textView2 = totalcount;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(db.get_totalqty());
        sb2.append(" items | ");
        sb2.append(rupee);
        sb2.append("0.00");
        textView2.setText(sb2.toString());
    }

    public void cartupdate(String productid1, String cart_productname1, String cart_price1, String cart_minqty1, String cart_unittype1, String cur_qty) {
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.cartupdate_fromcart);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);
        EditText qty = (EditText) dialog1.findViewById(R.id.qty);
        TextView unittype = (TextView) dialog1.findViewById(R.id.unittype);
        TextView price = (TextView) dialog1.findViewById(R.id.price);
        Button buy = (Button) dialog1.findViewById(R.id.buy);
        ImageView adminclose = (ImageView) dialog1.findViewById(R.id.adminclose);
        String rupee = getResources().getString(R.string.Rs);
        itemname.setText(cart_productname1);
        qty.setText(cur_qty);
        qty.setSelection(cur_qty.length());
        unittype.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(cart_unittype1)));
        price.setText(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(cur_qty) * Float.parseFloat(cart_price1))}));


        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!qty.getText().toString().equalsIgnoreCase("") && Float.parseFloat(qty.getText().toString()) >= 0.0f) {
                    float qty1 = Float.parseFloat(qty.getText().toString()) / Float.parseFloat(cart_price1);
                    price.setText(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(qty.getText().toString()) * qty1)}));
                }

            }
        });
        adminclose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.db.addcart_update(qty.getText().toString(), price.getText().toString().replace(rupee, ""), productid1);
                try {
                    Cart.mediaPlayer = MediaPlayer.create(Cart.getApplicationContext(), R.raw.addtocart);
                    Cart.mediaPlayer.setVolume(0.1f, 0.1f);
                    Cart.mediaPlayer.start();
                } catch (Exception e) {
                }
                dialog1.dismiss();
                Cart.calculate_delicharge();
            }
        });

        dialog1.show();
    }

    public void show_lowcart(String message1) {
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.custom_cartamountlow);
        Button ok = (Button) dialog1.findViewById(R.id.ok);
        ((TextView) dialog1.findViewById(R.id.message)).setText(message1);
        ok.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
}
