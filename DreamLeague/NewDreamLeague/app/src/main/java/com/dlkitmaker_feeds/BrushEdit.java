package com.dlkitmaker_feeds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedadeltito.photoeditorsdk.BrushDrawingView;
import com.ahmedadeltito.photoeditorsdk.PhotoEditorSDK;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;

import es.dmoral.toasty.Toasty;

public class BrushEdit extends AppCompatActivity {
    ImageView moveback,save,image_preview;
    RelativeLayout layout1;
    final DB db=new DB(this);
    PhotoEditorSDK photoEditorSDK;
    BrushDrawingView drawing_view;
    Typeface face;
    SeekBar seekBar;
    ImageView brushcolor,clearall;
    TextView text;
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    RelativeLayout eraserlyt;
    ImageView blackeraser,eraser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_brush_edit);
        drawing_view=findViewById(R.id.drawing_view);
        moveback =findViewById(R.id.back);
        save=findViewById(R.id.save);
        brushcolor=findViewById(R.id.brushcolor);
        text=findViewById(R.id.text);
        clearall=findViewById(R.id.clearall);
        image_preview=findViewById(R.id.image_preview);
        layout1=findViewById(R.id.layout1);
        seekBar=findViewById(R.id.seekBar);
        eraserlyt=findViewById(R.id.eraserlyt);
        blackeraser=findViewById(R.id.blackeraser);
        eraser=findViewById(R.id.eraser);
        moveback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        face= Typeface.createFromAsset(getAssets(), "fonts/heading.otf");
        text.setTypeface(face);
        loading_image();


        adView1=findViewById(R.id.adView1);
        intestrial = new InterstitialAd(BrushEdit.this);
        intestrial.setAdUnitId("ca-app-pub-2432830627480060/5272840655");
        adreq = new AdRequest.Builder().build();
        adreq1 = new AdRequest.Builder().build();


        try
        {
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    try
                    {
                        if(count<=20)
                        {
                            adView1.loadAd(adreq1);
                            count++;
                        }


                    }
                    catch (Exception a)
                    {

                    }

                }
            });

            intestrial.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {

                    if(intcount<=20) {
                        intestrial.loadAd(adreq);
                        intcount++;
                    }

                }
            });

        }
        catch (Exception a)
        {

        }

        photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(BrushEdit.this)
                .parentView(layout1)
                .childView(image_preview)
                .brushDrawingView(drawing_view)
                .buildPhotoEditorSDK();
        photoEditorSDK.setBrushDrawingMode(true);
        photoEditorSDK.setBrushSize(10);
        photoEditorSDK.setBrushColor(Color.RED);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                try {
                    photoEditorSDK.setBrushSize(i);
                }
                catch (Exception a)
                {

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    layout1.buildDrawingCache();
                    Bitmap bm = layout1.getDrawingCache();
                    try {
                        File f = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
                        FileOutputStream fileOutputStream = new FileOutputStream(f);
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 512, 512, true);
                        resized.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        finish();
                    } catch (Exception e) {

                    } finally {

                    }

                }
                catch (Exception a)
                {

                }
            }
        });
        brushcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickcolor();
            }
        });

        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoEditorSDK.clearBrushAllViews();
            }
        });

        eraserlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                blackeraser.setVisibility(View.VISIBLE);
                eraser.setVisibility(View.INVISIBLE);
                photoEditorSDK.setBrushEraserSize(seekBar.getProgress());
                photoEditorSDK.brushEraser();

            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                blackeraser.setVisibility(View.INVISIBLE);
                eraser.setVisibility(View.VISIBLE);
                photoEditorSDK.setBrushDrawingMode(true);
                return false;
            }
        });
    }

    public void loading_image()
    {
        if(!db.get_kittheme().equalsIgnoreCase(""))
        {
            File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
            if(file.exists())
            {
                float ogwidth=(Float.valueOf(db.getscreenwidth()));
                image_preview.getLayoutParams().height= Math.round(ogwidth);
                layout1.getLayoutParams().height= Math.round(ogwidth);

                Bitmap bm =  BitmapFactory.decodeFile(file.getAbsolutePath());
                image_preview.setImageBitmap(bm);

            }
            else
            {
                Toasty.info(getApplicationContext(),"Sorry ! Please try later", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
        {
            Toasty.info(getApplicationContext(),"Sorry ! Please try later", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    public void pickcolor()
    {
        ColorPickerDialog dialog = new ColorPickerDialog(this, 0xff000000, new ColorPickerDialog.OnColorPickerListener()
        {
            @Override
            public void onCancel(ColorPickerDialog dialog){

            }
            @Override
            public void onOk(ColorPickerDialog dialog, int color) {
                try
                {
                    photoEditorSDK.setBrushColor(color);

                }
                catch(Exception a)
                {

                }

            }

        });

        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        count=0;
        intcount=0;
        try
        {
            adView1.loadAd(adreq1);
            intestrial.loadAd(adreq);
        }
        catch (Exception a)
        {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(intestrial.isLoaded())
        {
            intestrial.show();
        }
    }
}
