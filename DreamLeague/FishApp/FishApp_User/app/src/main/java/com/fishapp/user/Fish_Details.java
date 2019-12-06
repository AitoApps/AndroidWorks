package com.fishapp.user;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Fish_Details extends AppCompatActivity {
    RelativeLayout add;
    ImageView back;
    ImageView carticon;
    RelativeLayout cartlyt;
    ConnectionDetecter cd;
    RelativeLayout content;
    final DatabaseHandler db = new DatabaseHandler(this);
    TextView discription;
    Typeface face;
    RelativeLayout header;
    ImageView heart;
    ImageView image;
    TextView itemcount;
    RelativeLayout lytdiscription;
    ImageView minus;
    TextView outofstock;
    ImageView plus;
    TextView price;
    Float priceflag=0.0f;
    TextView qty;
    String rupee = "";
    TextView text;
    Float totalgrms=0.0f;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    TextView unit;
    TextView viewcart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fish__details);
        cd = new ConnectionDetecter(this);
        rupee = getResources().getString(R.string.Rs);
        back = (ImageView) findViewById(R.id.back);
        image = (ImageView) findViewById(R.id.image);
        minus = (ImageView) findViewById(R.id.minus);
        plus = (ImageView) findViewById(R.id.plus);
        heart = (ImageView) findViewById(R.id.heart);
        text = (TextView) findViewById(R.id.text);
        qty = (TextView) findViewById(R.id.qty);
        unit = (TextView) findViewById(R.id.unit);
        price = (TextView) findViewById(R.id.price);
        header = (RelativeLayout) findViewById(R.id.header);
        content = (RelativeLayout) findViewById(R.id.content);
        outofstock = (TextView) findViewById(R.id.outofstock);
        add = (RelativeLayout) findViewById(R.id.add);
        lytdiscription = (RelativeLayout) findViewById(R.id.lytdiscription);
        discription = (TextView) findViewById(R.id.discription);
        itemcount = (TextView) findViewById(R.id.itemcount);
        viewcart = (TextView) findViewById(R.id.viewcart);
        carticon = (ImageView) findViewById(R.id.carticon);
        cartlyt = (RelativeLayout) findViewById(R.id.cartlyt);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               onBackPressed();
            }
        });
        text.setTypeface(face);
        qty.setTypeface(face);
        unit.setTypeface(face);
        price.setTypeface(face);
        outofstock.setTypeface(face);
        discription.setTypeface(face);
        itemcount.setTypeface(face);
        viewcart.setTypeface(face);
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
        if (Temp.isfromad == 0) {
            loaddata();
        } else {
            header.setVisibility(View.GONE);
            cartlyt.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            new loadstatus().execute(new String[0]);
        }
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.img_title = Temp.fish_fishname;
                Temp.imgsig = Temp.fish_imgsig;
                Temp.img_link = Temp.weblink+"fishpic/"+Temp.fish_sn+".jpg";
                Intent i = new Intent(getApplicationContext(), Image_viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(i);
            }
        });
        add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Float qtyingram = Float.valueOf(Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000"));
               add_tocart(Temp.fish_sn, Temp.fish_fishname, qtyingram+"",price.getText().toString().replace(rupee, ""), Temp.fish_imgsig, Temp.fish_unit, Temp.fish_qty, Temp.fish_price);
            }
        });
    }

    public void add_tocart(String cart_fishid1, String cart_fishname1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_ogunit1, String cart_ogqty1, String cart_ogprice1) {
        String str = cart_fishid1;
        if (!db.get_cartexist(cart_fishid1).equalsIgnoreCase("")) {
            db.addcart_existupdate(cart_fishid1, cart_qty1, cart_totalprice1);
        } else {

            db.addcart(cart_fishid1, cart_fishname1, cart_qty1, cart_totalprice1, cart_imgsig1, cart_ogunit1, cart_ogqty1, cart_ogprice1);
        }
        refreshcart();
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshcart();
    }

    public void refreshcart() {
        float gtotal = db.get_cartgrandtotal();

        if (gtotal > 0.0f) {
            itemcount.setText(db.getcartcount()+" Fish | "+rupee+String.format("%.2f", Float.valueOf(gtotal)));
            return;
        }
        itemcount.setText(db.getcartcount()+" Fish | "+rupee+"0.00");
    }

    public void loaddata() {
        String str = "%.2f";
        String str2 = "";
        header.setVisibility(View.VISIBLE);
        cartlyt.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
        text.setText(Temp.fish_fishname);
        if (Temp.fish_stock.equalsIgnoreCase("1")) {
            outofstock.setVisibility(View.INVISIBLE);
            qty.setVisibility(View.VISIBLE);
            unit.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            minus.setVisibility(View.VISIBLE);
            plus.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            try {
                totalgrms = Float.valueOf(0.0f);
                String str3 = "1000";
                if (Temp.fish_unit.equalsIgnoreCase("gm")) {
                    totalgrms = Float.valueOf(Float.parseFloat(Temp.fish_qty));
                } else if (Temp.fish_unit.equalsIgnoreCase("Kg")) {
                    totalgrms = Float.valueOf(Float.parseFloat(Temp.fish_qty) * Float.valueOf(str3).floatValue());
                }
                priceflag = Float.valueOf(Float.parseFloat(Temp.fish_price) / totalgrms.floatValue());
                float kilograms = totalgrms.floatValue() / Float.parseFloat(str3);
                qty.setText(String.format("%.2f",Float.parseFloat(kilograms+"")));
                price.setText(rupee+String.format("%.2f", Float.parseFloat((totalgrms.floatValue() * priceflag.floatValue())+"")));


                minus.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {

                            Float minusgram = Float.valueOf((Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000")) - 500.0f);
                            if (minusgram.floatValue() < totalgrms.floatValue()) {
                                float kilograms = totalgrms.floatValue() / Float.parseFloat("1000");
                                qty.setText(String.format("%.2f", Float.parseFloat(kilograms + "")));
                                price.setText(rupee + String.format("%.2f", Float.parseFloat((totalgrms.floatValue() * priceflag.floatValue()) + "")));

                            } else {
                                float kilograms = minusgram.floatValue() / Float.parseFloat("1000");
                                qty.setText(String.format("%.2f", Float.parseFloat(kilograms + "")));
                                price.setText(rupee + String.format("%.2f", Float.parseFloat((minusgram.floatValue() * priceflag.floatValue()) + "")));
                            }
                        } catch (Exception e) {
                        }
                    }
                });


                plus.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Float plusgram = Float.valueOf((Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000")) + 500.0f);
                            float kilograms = plusgram.floatValue() / Float.parseFloat("1000");
                            qty.setText(String.format("%.2f", Float.parseFloat(kilograms+"")));
                            price.setText(rupee+String.format("%.2f", Float.parseFloat((plusgram.floatValue() * priceflag.floatValue())+"")));

                        } catch (Exception e) {
                        }
                    }
                });

            } catch (Exception e) {
            }
        } else {
            outofstock.setVisibility(View.VISIBLE);
            qty.setVisibility(View.INVISIBLE);
            unit.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
            minus.setVisibility(View.INVISIBLE);
            plus.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        float calheight = 0.75f * Float.valueOf(udb.getscreenwidth()).floatValue();
        image.getLayoutParams().height = Math.round(calheight);
        discription.setText(Temp.fish_discription);
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(Temp.fish_imgsig));
        Glide.with(getApplicationContext()).load(Temp.weblink+"fishpic/"+Temp.fish_sn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {

            try {
                String link= Temp.weblink +"getfishlist_user_byfishid.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.fish_sn, "UTF-8");
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
                heart.setVisibility(View.GONE);
                if (result.trim().contains(":%")) {
                    String[] got = result.trim().split(":%");
                    Temp.fish_sn = got[0];
                    Temp.fish_fishname = got[1];
                    Temp.fish_discription = got[2];
                    Temp.fish_qty = got[3];
                    Temp.fish_unit = got[4];
                    Temp.fish_price = got[5];
                    Temp.fish_stock = got[6];
                    Temp.fish_imgsig = got[7];
                    loaddata();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
