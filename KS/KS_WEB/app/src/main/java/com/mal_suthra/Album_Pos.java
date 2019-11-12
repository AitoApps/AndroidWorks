package com.mal_suthra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class Album_Pos extends AppCompatActivity {
    public static ImageView moveback;
    public static ImageView movenext;
    ImageView addtofvrts;
    TextView amount;
    ImageView back;
    Button card_atm;
    TextView count;
    final DataBase dataBase = new DataBase(this);
    final DataBase db = new DataBase(this);
    DataBase_POS dbHelper;
    Button ecreacharge;
    Typeface face;
    Typeface face2;
    ImageView fvrts;
    TextView helptext;
    Zoomable_ImageView image;
    ScrollView layoutlock;
    ImageView loading;
    NetConnect nc;
    TextView paymenttext;
    Button paytm;
    ProgressDialog pd;
    ImageView remove_ads;
    RelativeLayout slidehelp;
    TextView text;
    Button upiid;
    NetConnect cd;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.avty_album);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }
        cd=new NetConnect(this);
        pd = new ProgressDialog(this);
        upiid = (Button) findViewById(R.id.upiid);
        helptext = (TextView) findViewById(R.id.help_text);
        slidehelp = (RelativeLayout) findViewById(R.id.slidehelp);
        back = (ImageView) findViewById(R.id.moveback);
        amount = (TextView) findViewById(R.id.amount);
        ecreacharge = (Button) findViewById(R.id.ecreacharge);
        card_atm = (Button) findViewById(R.id.atmcard);
        paytm = (Button) findViewById(R.id.paytm);
        nc = new NetConnect(this);
        layoutlock = (ScrollView) findViewById(R.id.locklyt);
        paymenttext = (TextView) findViewById(R.id.pymenttext);
        loading = (ImageView) findViewById(R.id.loading);
        image = (Zoomable_ImageView) findViewById(R.id.image);
        remove_ads = (ImageView) findViewById(R.id.removeads);
        moveback = (ImageView) findViewById(R.id.move_back);
        movenext = (ImageView) findViewById(R.id.movenext);
        count = (TextView) findViewById(R.id.count);
        fvrts = (ImageView) findViewById(R.id.fvrts);
        addtofvrts = (ImageView) findViewById(R.id.addtofvrts);
        dbHelper = new DataBase_POS(this, "chithram.sqlite");
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        face2 = Typeface.createFromAsset(getAssets(), "app_fonts/rupee.ttf");
        text = (TextView) findViewById(R.id.text);
        text.setText(Static_Veriable.albmtitle);
        text.setTypeface(face);
        helptext.setText(Static_Veriable.zoomtext);
        helptext.setTypeface(face);
        paymenttext.setText("35 രൂപ പേയ്‌മെന്റ് ചെയ്താല്‍ താങ്കള്‍ക്ക് എല്ലാ പൊസിഷനുകളും ആല്‍ബവും പരസ്യങ്ങളില്ലാതെ കാണാവുന്നതാണ്. താഴെയുള്ള ഏത് വഴി ഉപയോഗിച്ചും പേയ്‌മെന്റ് ചെയ്യാവുന്നതാണ്‌");
        paymenttext.setTypeface(face);

        amount.setText("`35");
        amount.setTypeface(face2);
        amount.setTextColor(Color.RED);
        ecreacharge.setText(Static_Veriable.eacyrecharge);
        ecreacharge.setTypeface(face);
        card_atm.setText(Static_Veriable.atmcard);
        card_atm.setTypeface(face);
        paymenttext.setTypeface(face);
        paytm.setText(Static_Veriable.paytm);
        paytm.setTypeface(face);
        upiid.setText(Static_Veriable.upiid);
        upiid.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addtofvrts.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dataBase.drop_albm_fvrt(Static_Veriable.albumid+"");
                dataBase.addalbum_fvrt(Static_Veriable.albumid+"");
                Toasty.info(getApplicationContext(), Static_Veriable.addtofvrt, 0).show();
            }
        });
        fvrts.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Album_Pos_Fvrt.class));
            }
        });
        layoutlock.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        ecreacharge.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {
                    Static_Veriable.clickedmethod = 6;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });
        paytm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Static_Veriable.clickedmethod = 3;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                } catch (Exception e) {
                }
            }
        });
        card_atm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {
                    Static_Veriable.clickedmethod = 5;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });
        upiid.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Static_Veriable.clickedmethod = 4;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                } catch (Exception e) {
                }
            }
        });
        slidehelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dataBase.add_slidehelp("1");
                slidehelp.setVisibility(View.GONE);
            }
        });
        remove_ads.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                layoutlock.setVisibility(View.VISIBLE);
            }
        });
        moveback.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Static_Veriable.albumid != 1 && loading.getVisibility() != View.VISIBLE) {
                    Static_Veriable.albumid--;
                    reloaddata1();
                }
            }
        });
        movenext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Static_Veriable.albumid != 815 && loading.getVisibility() != View.VISIBLE) {
                    Static_Veriable.albumid++;

                    reloaddata1();

                }
            }
        });
    }

    public void onResume() {
        String str = "";
        try {
            if (dataBase.get_slidehelp().equalsIgnoreCase(str)) {
                slidehelp.setVisibility(View.VISIBLE);
            } else {
                slidehelp.setVisibility(View.GONE);
            }
            if (dataBase.get_albm_bkmark().equalsIgnoreCase(str)) {
                dataBase.addbkmrk_album("1");
            }
            Static_Veriable.albumid = Integer.parseInt(dataBase.get_albm_bkmark());


            reloaddata1();


        } catch (Exception e) {
        }
        super.onResume();
    }

    public void reloaddata1()
    {
        if(db.get_shouldshow().equalsIgnoreCase("1") && db.get_purchase().equalsIgnoreCase(""))
        {
            startActivity(new Intent(getApplicationContext(),Lock_Layout.class));
        }
        else
        {
            if (!db.get_purchase().equalsIgnoreCase("")) {
                dataBase.addbkmrk_album(Static_Veriable.albumid+"");
                reload_data();
            } else if (cd.isConnectingToInternet()) {

                if(Integer.parseInt(db.get_showedads())>=10)
                {
                    db.add_shouldshow("1");
                    startActivity(new Intent(getApplicationContext(),Lock_Layout.class));
                }
                else
                {
                    db.add_showedads((Integer.parseInt(db.get_showedads())+1)+"");
                    dataBase.addbkmrk_album(Static_Veriable.albumid+"");
                    reload_data();
                }

            } else {
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        }
    }
    public void reload_data() {

        try {
            if (!db.get_purchase().equalsIgnoreCase("")) {
                layoutlock.setVisibility(View.GONE);
                count.setText(Static_Veriable.albumid+"");
                DataBase_POS dataBase_POS = dbHelper;
                byte[] decodedString = Base64.decode(dataBase_POS.getpic(Static_Veriable.albumid+""), 0);
                Options options = new Options();
                options.inPurgeable = true;
                image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options));
                alpha_animation();
            } else if (nc.isConnectingToInternet()) {
                layoutlock.setVisibility(View.GONE);
                count.setText(Static_Veriable.albumid+"");
                byte[] decodedString2 = Base64.decode(dbHelper.getpic(Static_Veriable.albumid+""), 0);
                Options options2 = new Options();
                options2.inPurgeable = true;
                image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length, options2));
                alpha_animation();
            } else {
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        } catch (Exception e) {

           // Log.w("Error",Log.getStackTraceString(e));
        }
    }

    public void alpha_animation() {
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1000);
        animation1.setFillAfter(true);
        image.startAnimation(animation1);
    }

    public void onBackPressed() {
        try {
            if (layoutlock.getVisibility() == View.VISIBLE) {
                layoutlock.setVisibility(View.GONE);
                return;
            }
            super.onBackPressed();
        } catch (Exception e2) {
        }
    }
}
