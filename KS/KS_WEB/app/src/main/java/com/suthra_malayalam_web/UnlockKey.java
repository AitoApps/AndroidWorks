package com.suthra_malayalam_web;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.PatternLockView.Dot;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class UnlockKey extends AppCompatActivity {
    ImageView back;
    final DataBase db = new DataBase(this);
    Typeface face;
    Button hide_app;
    PatternLockView patternLockView;
    TextView text;
    TextView textpattern;
    public String txt_pattern = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvy_unlocker);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text.setTypeface(face);
        back = (ImageView) findViewById(R.id.moveback);
        text.setText(Static_Veriable.changepattern);
        patternLockView = (PatternLockView) findViewById(R.id.patternview_lock);
        patternLockView.addPatternLockListener(mPatternLockViewListener);
        hide_app = (Button) findViewById(R.id.hideapp);
        textpattern = (TextView) findViewById(R.id.patterntext);
        textpattern.setText(Static_Veriable.writepatterchange);
        textpattern.setTypeface(face);
        hide_app.setText(Static_Veriable.setpattern);
        hide_app.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (txt_pattern.equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), Static_Veriable.writepattern, 0).show();
                } else if (txt_pattern.toString().length() < 3) {
                    Toasty.info(getApplicationContext(), Static_Veriable.lowpattern, 0).show();
                } else {
                    secur_quest();
                }
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        public void onStarted() {
        }

        public void onProgress(List<Dot> list) {
        }

        public void onComplete(List<Dot> pattern) {
            txt_pattern = PatternLockUtils.patternToString(patternLockView, pattern);
        }

        public void onCleared() {
        }
    };
    public void secur_quest() {
        final Dialog dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.sec_question);
        dialog3.setTitle("Security Questions");
        dialog3.setCancelable(false);
        TextView txtquestion1 = (TextView) dialog3.findViewById(R.id.txtquestion1);
        TextView questtxt = (TextView) dialog3.findViewById(R.id.txtquestion);
        final EditText question1 = (EditText) dialog3.findViewById(R.id.question1);
        Button verify = (Button) dialog3.findViewById(R.id.verify);
        questtxt.setTypeface(face);
        txtquestion1.setTypeface(face);
        questtxt.setText(Static_Veriable.secuirtytext);
        txtquestion1.setText(Static_Veriable.question1);
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (question1.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), Static_Veriable.enterquestion1, 0).show();
                    question1.requestFocus();
                    return;
                }
                db.add_keyopen(txt_pattern);
                db.add_quest(question1.getText().toString(), "NA");
                Toasty.info(getApplicationContext(), Static_Veriable.editpatternsucess, 0).show();
                dialog3.dismiss();
            }
        });
        dialog3.show();
    }
}
