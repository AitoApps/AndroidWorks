package com.dlkitmaker_feeds;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import es.dmoral.toasty.Toasty;

public class Theme_Selection extends AppCompatActivity {
    ImageView move_back,movenext,moveback,image;
    RelativeLayout lyt_themeselection;
    final DB db=new DB(this);
    int[] norml_list;
    TextView text,txtselectheme;
    int[] goallist;
    Typeface face;
    public int normalid=0,goalid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_themeselection);
        try
        {
            move_back =findViewById(R.id.back);
            movenext=findViewById(R.id.movenext);
            moveback=findViewById(R.id.moveback);

            text=findViewById(R.id.text);
            image=findViewById(R.id.image);
            txtselectheme=findViewById(R.id.txtselectheme);
            lyt_themeselection =findViewById(R.id.lytselecttheme);
            face= Typeface.createFromAsset(getAssets(), "fonts/heading.otf");

            txtselectheme.setTypeface(face);
            text.setTypeface(face);
            move_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onBackPressed();
                }
            });

            if(db.get_kittheme().equalsIgnoreCase("1"))
            {
                norml_list = new int[]{R.raw.normal_1, R.raw.normal_2, R.raw.normal_3, R.raw.normal_4, R.raw.normal_5, R.raw.normal_6};
            }
            else if(db.get_kittheme().equalsIgnoreCase("2"))
            {
                goallist= new int[]{R.raw.goal_1, R.raw.goal_2, R.raw.goal_3};
            }


            float ogwidth=(Float.valueOf(db.getscreenwidth()));
            image.getLayoutParams().height=Math.round(ogwidth);


            if(db.get_kittheme().equalsIgnoreCase("1"))
            {
                image.setImageResource(norml_list[normalid]);
            }
            if(db.get_kittheme().equalsIgnoreCase("2"))
            {
                image.setImageResource(goallist[goalid]);
            }

            movenext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(db.get_kittheme().equalsIgnoreCase("1"))
                    {
                        if(normalid>= norml_list.length-1)
                        {

                        }
                        else
                        {
                            normalid=normalid+1;
                            image.setImageResource(norml_list[normalid]);
                        }
                    }

                    if(db.get_kittheme().equalsIgnoreCase("2"))
                    {
                        if(goalid>=goallist.length-1)
                        {

                        }
                        else
                        {
                            goalid=goalid+1;
                            image.setImageResource(goallist[goalid]);
                        }
                    }

                }
            });
            moveback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(db.get_kittheme().equalsIgnoreCase("1"))
                    {
                        if(normalid==0)
                        {

                        }
                        else
                        {
                            normalid=normalid-1;
                            image.setImageResource(norml_list[normalid]);
                        }
                    }
                    if(db.get_kittheme().equalsIgnoreCase("2"))
                    {
                        if(goalid==0)
                        {

                        }
                        else
                        {
                            goalid=goalid-1;
                            image.setImageResource(goallist[goalid]);
                        }
                    }
                }
            });
            lyt_themeselection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try
                    {
                        if (ContextCompat.checkSelfPermission(Theme_Selection.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(Theme_Selection.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {

                            if(db.get_kittheme().equalsIgnoreCase("1"))
                            {
                                int resId = getResources().getIdentifier("raw/normal_"+(normalid+1), null, Theme_Selection.this.getPackageName());
                                Bitmap bm = BitmapFactory.decodeResource( getResources(), resId);
                                File file = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/", "mykit.png");
                                FileOutputStream outStream = new FileOutputStream(file);
                                Bitmap resized = Bitmap.createScaledBitmap(bm, 512, 512, true);
                                resized.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                                outStream.flush();
                                outStream.close();
                                Intent i=new Intent(getApplicationContext(), Kit_Creation.class);
                                startActivity(i);
                            }
                            else  if(db.get_kittheme().equalsIgnoreCase("2"))
                            {
                                int resId = getResources().getIdentifier("raw/goal_"+(goalid+1), null, Theme_Selection.this.getPackageName());
                                Bitmap bm = BitmapFactory.decodeResource( getResources(), resId);
                                File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
                                FileOutputStream outStream = new FileOutputStream(file);
                                Bitmap resized = Bitmap.createScaledBitmap(bm, 512, 512, true);
                                resized.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                                outStream.flush();
                                outStream.close();

                                Intent i=new Intent(getApplicationContext(), Kit_Creation.class);
                                startActivity(i);
                            }
                        }

                    }
                    catch (Exception a)
                    {
                    }

                }
            });

        }
        catch (Exception a)
        {

        }
    }
}
