package com.dlkitmaker_feeds;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Edit_Text extends AppCompatActivity {
    ImageView moveback,save,image_preview;
    RelativeLayout layout1;
    public TextView currentview;
    int clickCount = 0;
    long startTime;
    long duration;
    ImageView addtext,forcolor,setfont,increment,decrement, drop;
    static final int MAX_DURATION = 500;
    final DB db=new DB(this);
    TextView text;
    Typeface face;

    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_textedit);
        moveback =findViewById(R.id.back);
        forcolor=findViewById(R.id.forcolor);
        setfont=findViewById(R.id.setfont);
        increment=findViewById(R.id.increment);
        decrement=findViewById(R.id.decrement);
        save=findViewById(R.id.save);
        image_preview=findViewById(R.id.image_preview);
        addtext=findViewById(R.id.addtext);
        layout1=findViewById(R.id.layout1);
        drop =findViewById(R.id.delete);
        text=findViewById(R.id.text);
        face= Typeface.createFromAsset(getAssets(), "fonts/heading.otf");
        text.setTypeface(face);
        image_loading();

        adView1=findViewById(R.id.adView1);
        intestrial = new InterstitialAd(Edit_Text.this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    try
                    {
                        currentview.setPaintFlags(currentview.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                    }
                    catch(Exception a)
                    {

                    }

                    layout1.buildDrawingCache();
                    Bitmap bm = layout1.getDrawingCache();
                    try {

                        File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
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
        moveback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        addtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView text = new TextView(Edit_Text.this);
                text.setText("Please double tap here");
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 50;
                layoutParams.topMargin = 50;
                text.setLayoutParams(layoutParams);
                text.setTextColor(Color.CYAN);
                layout1.addView(text);
                try
                {
                    currentview.setPaintFlags(currentview.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                }
                catch(Exception a)
                {

                }
                currentview=text;
                currentview.setPaintFlags(currentview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                text.setOnTouchListener(new View.OnTouchListener()
                {

                    private static final int MAX_CLICK_DURATION = 200;
                    private long startClickTime;

                    PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
                    PointF StartPT = new PointF(); // Record Start Position of 'img'

                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {

                        int eid = event.getAction();
                        switch (eid)
                        {
                            case MotionEvent.ACTION_MOVE :
                                PointF mv = new PointF( event.getX() - DownPT.x, event.getY() - DownPT.y);
                                text.setX((int)(StartPT.x+mv.x));
                                text.setY((int)(StartPT.y+mv.y));
                                StartPT = new PointF(text.getX(),text.getY() );
                                break;
                            case MotionEvent.ACTION_DOWN :
                                startClickTime = Calendar.getInstance().getTimeInMillis();
                                DownPT.x = event.getX();
                                DownPT.y = event.getY();
                                StartPT = new PointF(text.getX(),text.getY() );
                                currentview=text;
                                startTime = System.currentTimeMillis();
                                clickCount++;
                                break;
                            case MotionEvent.ACTION_UP :
                                long time = System.currentTimeMillis() - startTime;
                                duration=  duration + time;
                                if(clickCount == 2)
                                {
                                    if(duration<= MAX_DURATION)
                                    {
                                        editstatus(text.getText().toString());
                                    }
                                    clickCount = 0;
                                    duration = 0;
                                    break;
                                }
                                break;
                            default :
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    layout1.removeView(currentview);
                }
                catch (Exception a)
                {

                }
            }
        });
        forcolor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    colorpickerfor();
                }
                catch(Exception a)
                {
                }
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try
                {
                    currentview.setTextSize(TypedValue.COMPLEX_UNIT_PX,currentview.getTextSize() + 1);
                }
                catch(Exception a)
                {

                }
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTextSize(TypedValue.COMPLEX_UNIT_PX,currentview.getTextSize() - 1);
                }
                catch(Exception a)
                {
                }

            }
        });
        setfont.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                setfont();

            }
        });
    }
    public void image_loading()
    {
        if(!db.get_kittheme().equalsIgnoreCase(""))
        {
            File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", "mykit.png");
            if(file.exists())
            {
                float ogwidth=(Float.valueOf(db.getscreenwidth()));
                image_preview.getLayoutParams().height= Math.round(ogwidth);

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

    public void setfont()
    {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.fontstyle);

        final TextView cowboyd=dialog.findViewById(R.id.cowboyd);
        final TextView deutschlander=dialog.findViewById(R.id.deutschlander);
        final TextView emilio=dialog.findViewById(R.id.emilio);
        final TextView enfatica=dialog.findViewById(R.id.enfatica);
        final TextView mexcellent3D=dialog.findViewById(R.id.mexcellent3D);
        final TextView mexcellentrg=dialog.findViewById(R.id.mexcellentrg);
        final TextView mionta=dialog.findViewById(R.id.mionta);
        final TextView octinsports=dialog.findViewById(R.id.octinsports);
        final TextView onick=dialog.findViewById(R.id.onick);
        final TextView rechargebd=dialog.findViewById(R.id.rechargebd);
        final TextView redseven=dialog.findViewById(R.id.redseven);
        final TextView sattermatter=dialog.findViewById(R.id.sattermatter);
        final TextView sfsportsnight=dialog.findViewById(R.id.sfsportsnight);
        final TextView sfssports=dialog.findViewById(R.id.sfssports);
        final TextView soccerLeague=dialog.findViewById(R.id.soccerLeague);
        final TextView suissnord=dialog.findViewById(R.id.suissnord);
        final TextView teamspiritnf=dialog.findViewById(R.id.teamspiritnf);


        final Typeface cowboyd1= Typeface.createFromAsset(getAssets(), "fonts/cowboyd.ttf");
        final Typeface deutschlander1= Typeface.createFromAsset(getAssets(), "fonts/deutschlander.otf");
        final Typeface emilio1= Typeface.createFromAsset(getAssets(), "fonts/emilio.ttf");
        final Typeface enfatica1= Typeface.createFromAsset(getAssets(), "fonts/enfatica.otf");
        final Typeface mexcellent3D1= Typeface.createFromAsset(getAssets(), "fonts/mexcellent3D.ttf");
        final Typeface mexcellentrg1= Typeface.createFromAsset(getAssets(), "fonts/mexcellentrg.ttf");
        final Typeface mionta1= Typeface.createFromAsset(getAssets(), "fonts/mionta.ttf");
        final Typeface octinsports1= Typeface.createFromAsset(getAssets(), "fonts/octinsports.ttf");
        final Typeface onick1= Typeface.createFromAsset(getAssets(), "fonts/onick.otf");
        final Typeface rechargebd1= Typeface.createFromAsset(getAssets(), "fonts/rechargebd.ttf");
        final Typeface redseven1= Typeface.createFromAsset(getAssets(), "fonts/redseven.otf");
        final Typeface sattermatter1= Typeface.createFromAsset(getAssets(), "fonts/sattermatter.ttf");
        final Typeface sfsportsnight1= Typeface.createFromAsset(getAssets(), "fonts/sfsportsnight.ttf");
        final Typeface sfssports1= Typeface.createFromAsset(getAssets(), "fonts/sfssports.ttf");
        final Typeface soccerLeague1= Typeface.createFromAsset(getAssets(), "fonts/soccerLeague.ttf");
        final Typeface suissnord1= Typeface.createFromAsset(getAssets(), "fonts/suissnord.otf");
        final Typeface teamspiritnf1= Typeface.createFromAsset(getAssets(), "fonts/teamspiritnf.ttf");


        cowboyd.setTypeface(cowboyd1);
        deutschlander.setTypeface(deutschlander1);
        emilio.setTypeface(emilio1);
        enfatica.setTypeface(enfatica1);
        mexcellent3D.setTypeface(mexcellent3D1);
        mexcellentrg.setTypeface(mexcellentrg1);
        mionta.setTypeface(mionta1);
        octinsports.setTypeface(octinsports1);
        onick.setTypeface(onick1);
        rechargebd.setTypeface(rechargebd1);
        redseven.setTypeface(redseven1);
        sattermatter.setTypeface(sattermatter1);
        sfsportsnight.setTypeface(sfsportsnight1);
        sfssports.setTypeface(sfssports1);
        soccerLeague.setTypeface(soccerLeague1);
        suissnord.setTypeface(suissnord1);
        teamspiritnf.setTypeface(teamspiritnf1);


        cowboyd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(cowboyd1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        deutschlander.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(deutschlander1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        emilio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(emilio1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        enfatica.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(enfatica1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        mexcellent3D.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(mexcellent3D1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        mexcellentrg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(mexcellentrg1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        mionta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(mionta1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        octinsports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(octinsports1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        onick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(onick1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        rechargebd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(rechargebd1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        redseven.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(redseven1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        sattermatter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(sattermatter1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        sfsportsnight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(sfsportsnight1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        sfssports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(sfssports1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        soccerLeague.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(soccerLeague1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        suissnord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(suissnord1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });
        teamspiritnf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {
                    currentview.setTypeface(teamspiritnf1);
                    dialog.dismiss();
                }
                catch(Exception a)
                {

                }


            }
        });




        dialog.show();

    }
    public void colorpickerfor()
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
                    currentview.setTextColor(color);
                }
                catch(Exception a)
                {

                }

            }

        });

        dialog.show();
    }


    public void editstatus(String text)
    {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialogedittext);
        final EditText status=(EditText)dialog.findViewById(R.id.status);
        Button update=(Button)dialog.findViewById(R.id.update);
        status.setText(text);

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                currentview.setText(status.getText().toString());
                dialog.dismiss();
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
