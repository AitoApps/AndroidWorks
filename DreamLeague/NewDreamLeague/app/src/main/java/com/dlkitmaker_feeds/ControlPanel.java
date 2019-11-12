package com.dlkitmaker_feeds;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ControlPanel extends AppCompatActivity {

    RelativeLayout create,mykits,feeds,webresource;
    Dialog dialog;
    final DB db=new DB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        create=findViewById(R.id.create);
        mykits=findViewById(R.id.mykits);
        feeds=findViewById(R.id.feeds);
        webresource=findViewById(R.id.webresource);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_kitcat();

            }
        });
    }


    public void show_kitcat()
    {
        dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.catogerykits);
        final TextView txttitle=dialog.findViewById(R.id.txttitle);
        final Spinner spinner=dialog.findViewById(R.id.spinner);

        String[] p={"Choose","Normal","Goal Keeper"};
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, p){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView)v).setTextColor(Color.BLACK);
                ((TextView)v).setTextSize(16);
                //((TextView)v).setTypeface(face);
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView)v).setTextColor(Color.BLACK);
                ((TextView)v).setTextSize(16);
                //  ((TextView)v).setTypeface(face);
                return v;
            }
        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==0)
                {

                }
                else
                {
                    try
                    {

                        db.add_kittheme(arg2+"");
                        exittotheme();
                    }
                    catch (Exception a)
                    {

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialog.show();

    }


    public void exittotheme()
    {


        Intent i=new Intent(getApplicationContext(), Theme_Selection.class);
        startActivity(i);

    }
}
