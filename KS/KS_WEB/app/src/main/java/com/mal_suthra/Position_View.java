package com.mal_suthra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.view.MotionEvent;
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
import java.io.InputStream;

public class Position_View extends AppCompatActivity implements gest.SimpleGestureListener {
    public static ImageView back_move;
    public static ImageView next_move;
    ImageView addto_fvrt;
    ImageView back;
    ImageView bookmark;
    Button completetask,payment;
    TextView textor;
    NetConnect cd;
    TextView content;
    final DataBase db = new DataBase(this);
    DataBase_POS dbHelper;
    public gest detector;
    Typeface face;
    Typeface face1;
    Typeface face2;
    TextView helptext;
    ImageView image;
    ScrollView loacklayout;
    ImageView loading;
    TextView payment_text;
    ProgressDialog pd;
    RelativeLayout sliderhelp;
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvty_pos_result);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }
        pd = new ProgressDialog(this);

        image = (ImageView) findViewById(R.id.image);
        content = (TextView) findViewById(R.id.content);
        text = (TextView) findViewById(R.id.text);
        completetask=findViewById(R.id.completetask);
        payment=findViewById(R.id.payment);
        textor=findViewById(R.id.textor);
        bookmark = (ImageView) findViewById(R.id.bookmark);
        loading = (ImageView) findViewById(R.id.loading);
        next_move = (ImageView) findViewById(R.id.movenext);
        back_move = (ImageView) findViewById(R.id.move_back);
        addto_fvrt = (ImageView) findViewById(R.id.addtofvrt);
        helptext = (TextView) findViewById(R.id.help_text);
        sliderhelp = (RelativeLayout) findViewById(R.id.slidehelp);
        loacklayout = (ScrollView) findViewById(R.id.locklyt);
        payment_text = (TextView) findViewById(R.id.pymenttext);
        cd = new NetConnect(this);
        face2 = Typeface.createFromAsset(getAssets(), "app_fonts/rupee.ttf");
        face = Typeface.createFromAsset(getAssets(), "app_fonts/heading.otf");
        face1 = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        dbHelper = new DataBase_POS(this, "chithram.sqlite");
        text.setTypeface(face1);
        helptext.setText(Static_Veriable.text_help);
        helptext.setTypeface(face1);
        payment_text.setText("ക്ഷമിക്കണം ! 5 പൊസിഷനുകള്‍ മാത്രമേ താങ്കള്‍ക്ക് സൗജന്യമായി കാണുവാന്‍ സാധിക്കുകയൊള്ളൂ.ബാക്കിയുള്ള പൊസിഷനുകളും ആല്‍ബവും കാണുവാന് ‍39 രൂപ പേയ്‌മെന്റ് ചെയ്യുകയോ ടാസ്‌ക് പൂര്‍ത്തിയാക്കുകയോ ചെയ്യുക.");
                text.setText(Static_Veriable.posname);
        text.setTypeface(face1);

        payment_text.setTypeface(face1);
        textor.setTypeface(face1);
        completetask.setTypeface(face1);
        payment.setTypeface(face1);

        textor.setText("അല്ലെങ്കില്\u200D ");
        completetask.setText("ടാസ്\u200Cക് പൂര്\u200Dത്തിയാക്കുക");
        payment.setText("പേയ്\u200Cമെന്റ് ചെയ്യുക");

        detector = new gest(this, this);
        back = (ImageView) findViewById(R.id.moveback);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addto_fvrt.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                db.deletefvrt(Static_Veriable.picid+"");
                db.addfvrt(Static_Veriable.picid+"");
                Toasty.info(getApplicationContext(), Static_Veriable.addtofvrt, 0).show();
            }
        });
        loacklayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), Act_Photoview_Pos.class));
            }
        });

        bookmark.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                db.addbkmrk(Static_Veriable.picid+"");
                Toasty.info(getApplicationContext(), Static_Veriable.addedbkmrk, 0).show();
            }
        });
        completetask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Complete_Task.class));
            }
        });
       payment.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    Static_Veriable.clickedmethod = 5;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });

        back_move.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (loading.getVisibility() != View.VISIBLE && Static_Veriable.picid != 1) {
                    refreshing_data(Static_Veriable.picid - 1);
                }
            }
        });
        next_move.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (loading.getVisibility() != View.VISIBLE && Static_Veriable.picid != 212) {
                    refreshing_data(Static_Veriable.picid + 1);
                }
            }
        });
        sliderhelp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                db.add_poshelp_slide("1");
                sliderhelp.setVisibility(View.GONE);
            }
        });
        try {
            back_move.setAlpha(1.0f);
            next_move.setAlpha(1.0f);
            animation_back();
            animation_next();
        } catch (Exception e2) {
        }
    }

    public boolean dispatchTouchEvent(MotionEvent me) {
        detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    public void onSwipe(int direction) {
        if (direction == 2) {
            return;
        }
        if (direction != 3) {
            if (direction == 4 && loading.getVisibility() != View.VISIBLE && Static_Veriable.picid != 1) {
                refreshing_data(Static_Veriable.picid - 1);
            }
        } else if (loading.getVisibility() != View.VISIBLE && Static_Veriable.picid != 212) {
            refreshing_data(Static_Veriable.picid + 1);
        }
    }

    public void onDoubleTap() {
    }

    public void refreshing_data(int move) {
        try {

            if(db.get_shouldshow().equalsIgnoreCase("1"))
            {
                startActivity(new Intent(getApplicationContext(),Lock_Layout.class));
            }
            else
            {
                if (!db.get_purchase().equalsIgnoreCase("")) {
                    loacklayout.setVisibility(View.GONE);
                    Static_Veriable.picid = move;
                    text.setText(File_Positions.positionlist[Static_Veriable.picid]);
                    new laoding_data().execute(new String[0]);
                } else if (cd.isConnectingToInternet()) {

                    if(Integer.parseInt(db.get_showedads())>=5)
                    {
                        db.add_shouldshow("1");
                        startActivity(new Intent(getApplicationContext(),Lock_Layout.class));
                    }
                    else
                    {
                        db.add_showedads((Integer.parseInt(db.get_showedads())+1)+"");
                        loacklayout.setVisibility(View.GONE);
                        Static_Veriable.picid = move;
                        text.setText(File_Positions.positionlist[Static_Veriable.picid]);
                        new laoding_data().execute(new String[0]);
                    }

                } else {
                    Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
                }
            }


        } catch (Exception e) {
        }
    }

    public static void animation_back() {
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(3000);
        animation1.setFillAfter(true);
        back_move.startAnimation(animation1);
    }

    public static void animation_next() {
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(3000);
        animation1.setFillAfter(true);
        next_move.startAnimation(animation1);
    }

    public void onResume() {
        super.onResume();
        if (db.get_poshlp_slide().equalsIgnoreCase("")) {
            sliderhelp.setVisibility(View.VISIBLE);
        } else {
            sliderhelp.setVisibility(View.GONE);
        }
        refreshing_data(Static_Veriable.picid);
    }

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    public void onBackPressed() {
        try {
            if (loacklayout.getVisibility() == View.VISIBLE) {
                loacklayout.setVisibility(View.GONE);
                return;
            }

            super.onBackPressed();
        } catch (Exception e2) {
        }
    }
    public class laoding_data extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            loading.setVisibility(View.VISIBLE);
        }


        public String doInBackground(String... arg0) {
            try {
                AssetManager assets = getAssets();
                InputStream is = assets.open("pos_list/"+Static_Veriable.picid+".txt");
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                return new String(buffer);
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }


        public void onPostExecute(String result) {
            String str = "";
            try {
                loading.setVisibility(View.GONE);
                content.setText(fromHtml(result));
                content.setTypeface(face1);
                if (db.get_purchase().equalsIgnoreCase(str)) {

                    byte[] decodedString = Base64.decode(dbHelper.getpic(Static_Veriable.picid+""), 0);
                    Options options = new Options();
                    options.inPurgeable = true;
                    image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options));
                } else {
                    loacklayout.setVisibility(View.GONE);

                    byte[] decodedString2 = Base64.decode(dbHelper.getpic(Static_Veriable.picid+""), 0);
                    Options options2 = new Options();
                    options2.inPurgeable = true;
                    image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length, options2));
                }
                back_move.setAlpha(1.0f);
                next_move.setAlpha(1.0f);
                animation_back();
                animation_next();
            } catch (Exception e) {
            }
        }
    }
}
