package com.kamanam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class Position_View extends AppCompatActivity implements gesture.SimpleGestureListener {
    public static ImageView back_move;
    public static ImageView next_move;
    ImageView back;
    TextView content;
    public gesture detector;
    Typeface face;
    TextView helptext;
    ImageView image;
    ImageView loading;
    RelativeLayout sliderhelp;
    TextView text;
    final DataBase db = new DataBase(this);
    TextView help1,watchvideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position__view);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }

        watchvideo=findViewById(R.id.watchvideo);
        help1=findViewById(R.id.help1);
        image = (ImageView) findViewById(R.id.image);
        content = (TextView) findViewById(R.id.content);
        text = (TextView) findViewById(R.id.text);
        loading = (ImageView) findViewById(R.id.loading);
        next_move = (ImageView) findViewById(R.id.movenext);
        back_move = (ImageView) findViewById(R.id.move_back);
        helptext = (TextView) findViewById(R.id.help_text);
        sliderhelp = (RelativeLayout) findViewById(R.id.slidehelp);
        face = Typeface.createFromAsset(getAssets(), "fonts/proximanormal.ttf");
        text.setTypeface(this.face);
        helptext.setText(Temp.text_help);
        helptext.setTypeface(this.face);
        text.setText(Temp.posname);
        help1.setTypeface(face);
        watchvideo.setTypeface(face);
        watchvideo.setText("വീഡിയോ കാണാം");
        watchvideo.setPaintFlags(watchvideo.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        help1.setText("ക്ഷമിക്കണം ! പ്ലേസ്റ്റോര്\u200D അനുവദിക്കാത്തത് കാരണം ഈ ആപ്പില്\u200D ചിത്രങ്ങള്\u200D ഉള്\u200Dപ്പെടുത്തിയിട്ടില്ല.ചിത്രങ്ങള്\u200D ഉള്\u200Dപ്പെടുത്തിയ ഒറിജിനല്\u200D ആപ്പ് ഞങ്ങളുടെ വെബ്\u200Cസൈറ്റില്\u200D നിന്നും ഡൗണ്\u200Dലോഡ് ചെയ്\u200Cതെടുക്കാവുന്നതാണ്.");
        detector = new gesture(this, Position_View.this);
        back = (ImageView) findViewById(R.id.moveback);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        back_move.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Position_View.this.loading.getVisibility() != View.VISIBLE && Temp.picid != 1) {
                    Position_View.this.refreshing_data(Temp.picid - 1);
                }
            }
        });
        next_move.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Position_View.this.loading.getVisibility() != View.VISIBLE && Temp.picid != 212) {
                    Position_View.this.refreshing_data(Temp.picid + 1);
                }
            }
        });
        this.sliderhelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Position_View.this.db.add_poshelp_slide("1");
                Position_View.this.sliderhelp.setVisibility(View.GONE);
            }
        });

        watchvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6o4qd7lcaqU"));
                startActivity(browserIntent);
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
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    public void onSwipe(int direction) {
        if (direction == 2) {
            return;
        }
        if (direction != 3) {
            if (direction == 4 && this.loading.getVisibility() != View.VISIBLE && Temp.picid != 1) {
                refreshing_data(Temp.picid - 1);
            }
        } else if (this.loading.getVisibility() != View.VISIBLE && Temp.picid != 212) {
            refreshing_data(Temp.picid + 1);
        }
    }

    public void onDoubleTap() {
    }

    public void refreshing_data(int move) {
        try {
            Temp.picid = move;
            this.text.setText(PositionTitlls.positionlist[Temp.picid]);
            new laoding_data().execute(new String[0]);
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
        if (this.db.get_poshlp_slide().equalsIgnoreCase("")) {
            this.sliderhelp.setVisibility(View.VISIBLE);
        } else {
            this.sliderhelp.setVisibility(View.GONE);
        }
        refreshing_data(Temp.picid);
    }

    public static Spanned fromHtml(String html) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    public class laoding_data extends AsyncTask<String, Void, String> {


        public void onPreExecute() {
            Position_View.this.loading.setVisibility(View.VISIBLE);
        }


        public String doInBackground(String... arg0) {
            try {
                AssetManager assets = Position_View.this.getAssets();
                InputStream is = assets.open("positions/"+Temp.picid+".txt");
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
                Position_View.this.loading.setVisibility(View.GONE);
                Position_View.this.content.setText(Position_View.fromHtml(result));
                Position_View.this.content.setTypeface(Position_View.this.face);
                Position_View.back_move.setAlpha(1.0f);
                Position_View.next_move.setAlpha(1.0f);
                Position_View.animation_back();
                Position_View.animation_next();
            } catch (Exception e) {
            }
        }
    }

}
