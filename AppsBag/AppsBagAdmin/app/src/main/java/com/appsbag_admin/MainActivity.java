package com.appsbag_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button loanapps,gameapps,video;
    Typeface face;
    List<String> lst_language = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.text);
        loanapps=findViewById(R.id.loanapps);
        gameapps=findViewById(R.id.gameapps);
        video=findViewById(R.id.video);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");

        loanapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.appcatogery="1";
                startActivity(new Intent(getApplicationContext(),Apps_List.class));

            }
        });

        gameapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.appcatogery="2";
                startActivity(new Intent(getApplicationContext(),Apps_List.class));
            }
        });


        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changelanguage();

            }
        });
    }


    public void changelanguage() {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog3.setContentView(R.layout.custom_languages);
            dialog3.setCancelable(true);
            final Spinner language=dialog3.findViewById(R.id.language);
            Button update=dialog3.findViewById(R.id.select);
            update.setTypeface(face);

            lst_language.clear();
            lst_language.add("Choose Language");
            lst_language.add("English");
            lst_language.add("Malayalam");
            lst_language.add("Hindi");
            lst_language.add("Tamil");
            lst_language.add("Telungu");




            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, lst_language) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }
            };
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            language.setAdapter(dataAdapter2);
            language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(language.getSelectedItemPosition()<=0)
                    {
                        Toast.makeText(getApplicationContext(),"Please select language",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Temp.language=language.getSelectedItemPosition()+"";
                        startActivity(new Intent(getApplicationContext(),Video_List.class));
                        dialog3.dismiss();
                    }

                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }
}
