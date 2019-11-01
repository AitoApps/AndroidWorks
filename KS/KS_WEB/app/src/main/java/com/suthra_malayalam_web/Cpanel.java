package com.suthra_malayalam_web;

import adapter.Parent_Adapter;
import adapter.Parent_Icons;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.PatternLockView.Dot;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.gms.ads.MobileAds;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cpanel extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    ImageView about_us;
    Button app_lock;
    ScrollView content;
    DataBase_POS da;
    final DataBase db = new DataBase(this);
    Typeface face;
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        public void onStarted() {
        }

        public void onProgress(List<Dot> list) {
        }

        public void onComplete(List<Dot> pattern) {
            txt_pattern = PatternLockUtils.patternToString(patternLockView, pattern);
            if (db.get_keyopen().equalsIgnoreCase(txt_pattern)) {
                pattern_lyt.setVisibility(View.GONE);
                app_lock.setText(Static_Veriable.changepattern);
            }
        }

        public void onCleared() {
        }
    };
    NetConnect nc;
    PatternLockView patternLockView;
    RelativeLayout pattern_lyt;
    TextView patterntext;
    ProgressDialog pd;
    ImageView share;
    TextView text;
    String txt_pattern = "";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_cpanel);
        String[] PERMISSIONS = {android.Manifest.permission.INTERNET, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_NETWORK_STATE};

        MobileAds.initialize(this, "ca-app-pub-8933294539595122~5710315462");

        if (!check_permission(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        List<Parent_Icons> items = new ArrayList<>();
        items.add(new Parent_Icons(R.drawable.img_position));
        items.add(new Parent_Icons(R.drawable.img_album1));
        items.add(new Parent_Icons(R.drawable.img_mathrtham));
        items.add(new Parent_Icons(R.drawable.img_arivukal));
        items.add(new Parent_Icons(R.drawable.img_food));
        items.add(new Parent_Icons(R.drawable.img_rogangal));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new Parent_Adapter(this, items, this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        app_lock = (Button) findViewById(R.id.hideapp);
        pattern_lyt = (RelativeLayout) findViewById(R.id.patternlyt);
        patterntext = (TextView) findViewById(R.id.patterntext);
        if (db.get_scrwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            db.add_widthscreen(width+"");
        }
        if (db.get_counts().equalsIgnoreCase("")) {
            db.add_counts("5");
        }
        if (db.get_keyopen().equalsIgnoreCase("")) {
            app_lock.setText(Static_Veriable.setpattern);
            patterntext.setText(Static_Veriable.pattertntext1);
        } else {
            app_lock.setText(Static_Veriable.forgotpattern);
            patterntext.setText(Static_Veriable.patterntext2);
        }
        share = (ImageView) findViewById(R.id.appshare);
        content = (ScrollView) findViewById(R.id.content);
        about_us = (ImageView) findViewById(R.id.aboutus);
        patternLockView = (PatternLockView) findViewById(R.id.patternview_lock);
        patternLockView.addPatternLockListener(mPatternLockViewListener);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text = (TextView) findViewById(R.id.text);
        text.setText(Static_Veriable.appname);
        text.setTypeface(face);
        nc = new NetConnect(this);
        pd = new ProgressDialog(this);
        da = new DataBase_POS(this, "chithram.sqlite");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getFilesDir()+"/"+Static_Veriable.foldername);
        File folder = new File(sb2.toString());
        if (!folder.exists()) {
            folder.mkdir();
            try {
                new File(getFilesDir()+"/"+Static_Veriable.foldername+"/.nomedia").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pattern_lyt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        app_lock.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = "";
                if (db.get_keyopen().equalsIgnoreCase(str)) {
                    if (txt_pattern.equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), Static_Veriable.writepattern, 0).show();
                    } else if (txt_pattern.toString().length() < 3) {
                        Toasty.info(getApplicationContext(), Static_Veriable.lowpattern, 0).show();
                    } else {
                        question_secure();
                    }
                } else if (pattern_lyt.getVisibility() == View.GONE) {
                    startActivity(new Intent(getApplicationContext(), UnlockKey.class));
                } else {
                    forgot_ques();
                }
            }
        });
        share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Intent sharingIntent = new Intent("android.intent.action.SEND");
                    sharingIntent.setType("text/plain");
                    String shareBody = Static_Veriable.shareapp;
                    sharingIntent.putExtra("android.intent.extra.SUBJECT", Static_Veriable.appname);
                    sharingIntent.putExtra("android.intent.extra.TEXT", shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } catch (Exception e) {
                }
            }
        });
        about_us.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    startActivity(new Intent(getApplicationContext(), Act_About.class));
                } catch (Exception e) {
                }
            }
        });

    }

    public static boolean check_permission(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void question_secure() {
        final Dialog dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.sec_question);
        dialog3.setTitle("Security Questions");
        dialog3.setCancelable(false);
        TextView txt_ques = (TextView) dialog3.findViewById(R.id.txtquestion);
        TextView txtquestion1 = (TextView) dialog3.findViewById(R.id.txtquestion1);
        final EditText question1 = (EditText) dialog3.findViewById(R.id.question1);
        Button verify = (Button) dialog3.findViewById(R.id.verify);
        txt_ques.setText(Static_Veriable.secuirtytext);
        txtquestion1.setText(Static_Veriable.question1);
        txt_ques.setTypeface(face);
        txtquestion1.setTypeface(face);
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (question1.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), Static_Veriable.enterquestion1, 0).show();
                    question1.requestFocus();
                    return;
                }
                db.add_keyopen(txt_pattern);
                db.add_quest(question1.getText().toString(), "NA");
                Toasty.info(getApplicationContext(), Static_Veriable.patternlocksucess, 0).show();
                pattern_lyt.setVisibility(View.GONE);
                app_lock.setText(Static_Veriable.changepattern);
                dialog3.dismiss();
            }
        });
        dialog3.show();
    }

    public void forgot_ques() {
        Dialog dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.frget_question);
        dialog3.setTitle("Answer Questions");
        TextView txtquestion = (TextView) dialog3.findViewById(R.id.txtquestion);
        TextView txtquestion1 = (TextView) dialog3.findViewById(R.id.txtquestion1);
        final EditText question1 = (EditText) dialog3.findViewById(R.id.question1);
        Button verify = (Button) dialog3.findViewById(R.id.verify);
        final TextView lockkey = (TextView) dialog3.findViewById(R.id.lockkey);
        txtquestion.setText(Static_Veriable.forgottext);
        txtquestion1.setTypeface(face);
        lockkey.setTypeface(face);
        txtquestion1.setText(Static_Veriable.question1);
        txtquestion.setTypeface(face);
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (question1.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), Static_Veriable.enterquestion1, 0).show();
                    question1.requestFocus();
                    return;
                }
                ArrayList<String> id1 = db.get_quest();
                String[] c = (String[]) id1.toArray(new String[id1.size()]);
                if (c.length <= 0) {
                    Toasty.info(getApplicationContext(), Static_Veriable.tmpproblem, 0).show();
                } else if (question1.getText().toString().equalsIgnoreCase(c[0])) {
                    String key = "";
                    for (int i = 0; i < db.get_keyopen().length(); i++) {
                        char d = db.get_keyopen().charAt(i);
                        key = (Character.getNumericValue(d) + 1) + "";
                    }
                    lockkey.setText(Static_Veriable.forgot1 + " " + key + Static_Veriable.forgot2);
                    lockkey.setBackgroundColor(Color.WHITE);
                } else {
                    Toasty.info(getApplicationContext(), Static_Veriable.answr_check, 0).show();
                }
            }
        });
        dialog3.show();
    }






}