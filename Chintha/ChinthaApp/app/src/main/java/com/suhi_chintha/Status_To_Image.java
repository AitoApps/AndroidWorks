package com.suhi_chintha;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.suhi_chintha.ColorPickerDialog.OnColorPickerListener;
import es.dmoral.toasty.Toasty;
import java.util.Calendar;

public class Status_To_Image extends AppCompatActivity {
    static final int MAX_DURATION = 500;
    public static int RESULT_LOAD_IMAGE = 1;
    public static String imgPath = "none";
    public AdView adView1;
    ImageView addimage;
    ImageView addtext;
    AdRequest adreq1;
    ImageView back;
    ImageView bgcolor;
    Zoomable_image bgimage;
    int clickCount = 0;
    Image_Compressing cmp;
    RelativeLayout content;
    int count = 0;
    public TextView currentview;
    ImageView decrement;
    long duration;
    Typeface face;
    ImageView forcolor;
    ImageView increment;
    ImageView save;
    public Image_Save saveimage;
    ImageView setfont;
    long startTime;
    TextView statustext;
    TextView text;
    public float txtfontsize = 20.0f;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.status_to_image_actvty);
        adView1 = (AdView) findViewById(R.id.adView1);
        adreq1 = new Builder().build();
        back = (ImageView) findViewById(R.id.moveback);
        statustext = (TextView) findViewById(R.id.statustext);
        addimage = (ImageView) findViewById(R.id.addimage);
        bgcolor = (ImageView) findViewById(R.id.bgcolor);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
        text.setTypeface(face);
        cmp = new Image_Compressing();
        bgimage = (Zoomable_image) findViewById(R.id.bgimage);
        content = (RelativeLayout) findViewById(R.id.content);
        addtext = (ImageView) findViewById(R.id.addtext);
        save = (ImageView) findViewById(R.id.save);
        forcolor = (ImageView) findViewById(R.id.forcolor);
        increment = (ImageView) findViewById(R.id.increment);
        decrement = (ImageView) findViewById(R.id.decrement);
        setfont = (ImageView) findViewById(R.id.setfont);
        saveimage = new Image_Save();
        try {
            adView1.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                    try {
                        if (count <= 10) {
                            adView1.loadAd(adreq1);
                            count++;
                        }
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {
        }
        statustext.setOnTouchListener(new OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            PointF DownPT = new PointF();
            PointF StartPT = new PointF();
            private long startClickTime;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        DownPT.x = event.getX();
                        DownPT.y = event.getY();
                        StartPT = new PointF(statustext.getX(), statustext.getY());
                        currentview = statustext;
                        startTime = System.currentTimeMillis();
                        clickCount++;
                        break;
                    case 1:
                        long time = System.currentTimeMillis() - startTime;
                        duration += time;
                        if (clickCount == 2) {
                            if (duration <= 500) {
                                edit_status(statustext.getText().toString());
                            }
                            clickCount = 0;
                           duration = 0;
                            break;
                        }
                        break;
                    case 2:
                        PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                        statustext.setX((float) ((int) (StartPT.x + mv.x)));
                        statustext.setY((float) ((int) (StartPT.y + mv.y)));
                        StartPT = new PointF(statustext.getX(), statustext.getY());
                        break;
                }
                return true;
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        addimage.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
                } catch (Exception e) {
                }
            }
        });
        if (!TextUtils.isEmpty(Static_Variable.sm_text)) {
            try {
                statustext.setText(Static_Variable.sm_text);
            } catch (Exception e2) {
            }
        } else {
            statustext.setText("Enter Status");
        }
        forcolor.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    forcolor_picker();
                } catch (Exception e) {
                }
            }
        });
        addtext.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                final TextView text = new TextView(Status_To_Image.this);
                text.setText("Enter Text here");
                LayoutParams layoutParams = new LayoutParams(-2, -2);
                layoutParams.leftMargin = 50;
                layoutParams.topMargin = 50;
                layoutParams.bottomMargin = -250;
                layoutParams.rightMargin = -250;
                text.setLayoutParams(layoutParams);
                content.addView(text);
                text.setOnTouchListener(new OnTouchListener() {
                    private static final int MAX_CLICK_DURATION = 200;
                    PointF DownPT = new PointF();
                    PointF StartPT = new PointF();
                    private long startClickTime;

                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
                                startClickTime = Calendar.getInstance().getTimeInMillis();
                                DownPT.x = event.getX();
                                DownPT.y = event.getY();
                                StartPT = new PointF(text.getX(), text.getY());
                                currentview = text;
                                startTime = System.currentTimeMillis();
                                clickCount++;
                                break;
                            case 1:
                                long time = System.currentTimeMillis() - startTime;
                                duration += time;
                                if (clickCount == 2) {
                                    if (duration <= 500) {
                                        edit_status(text.getText().toString());
                                    }
                                    clickCount = 0;
                                    duration = 0;
                                    break;
                                }
                                break;
                            case 2:
                                PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                                text.setX((float) ((int) (StartPT.x + mv.x)));
                                text.setY((float) ((int) (StartPT.y + mv.y)));
                                StartPT = new PointF(text.getX(), text.getY());
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        bgcolor.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    colorpickerbg();
                } catch (Exception e) {
                }
            }
        });
        increment.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    txtfontsize += 1.0f;
                    currentview.setTextSize(txtfontsize);
                } catch (Exception e) {
                }
            }
        });
        decrement.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    txtfontsize -= 1.0f;
                    currentview.setTextSize(txtfontsize);
                } catch (Exception e) {
                }
            }
        });
        save.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (saveimage.Imagesave(content, getApplicationContext())) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Successfully Saved , Please check your gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) "Unable to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setfont.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                font_settings();
            }
        });
    }
    public void onResume() {
        super.onResume();
        count = 0;
        try {
            adView1.loadAd(adreq1);
        } catch (Exception e) {
        }
    }

    public void colorpickerbg() {
        new ColorPickerDialog(this, -16777216, new OnColorPickerListener() {
            public void onCancel(ColorPickerDialog dialog) {
            }

            public void onOk(ColorPickerDialog dialog, int color) {
                bgimage. setVisibility(View.GONE);
                content.setBackgroundColor(color);
            }
        }).show();
    }

    public void forcolor_picker() {
        new ColorPickerDialog(this, -16777216, new OnColorPickerListener() {
            public void onCancel(ColorPickerDialog dialog) {
            }

            public void onOk(ColorPickerDialog dialog, int color) {
                try {
                    currentview.setTextColor(color);
                } catch (Exception e) {
                }
            }
        }).show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == -1 && data != null) {
            try {
                Uri selectedImage = data.getData();
                Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
                if (cursor == null) {
                    imgPath = selectedImage.getPath();
                } else {
                    cursor.moveToFirst();
                    imgPath = cursor.getString(cursor.getColumnIndex("_data"));
                }
                Bitmap myBitmap = BitmapFactory.decodeFile(imgPath);
                bgimage.setVisibility(View.VISIBLE);
                bgimage.setImageBitmap(myBitmap);
                bgimage.setScaleType(ScaleType.FIT_XY);
                bgimage.setAdjustViewBounds(true);
            } catch (Exception e) {
            }
        }
    }

    public void font_settings() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.fontstyle);
        final Typeface faceanjali = Typeface.createFromAsset(getAssets(), "asset_fonts/Anjali.ttf");
        final Typeface facechilanka = Typeface.createFromAsset(getAssets(), "asset_fonts/font_chilanka.ttf");
        final Typeface facekarumbi = Typeface.createFromAsset(getAssets(), "asset_fonts/font_karumbi.ttf");
        final Typeface facekeraleeyam = Typeface.createFromAsset(getAssets(), "asset_fonts/font_keraleeyam.ttf");
        final Typeface facemanjari = Typeface.createFromAsset(getAssets(), "asset_fonts/font_manjari.ttf");
        final Typeface facemeera = Typeface.createFromAsset(getAssets(), "asset_fonts/font_Meera.ttf");
        final Typeface facerachana = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
        TextView anjali = (TextView) dialog.findViewById(R.id.anjali);
        TextView chilanka = (TextView) dialog.findViewById(R.id.chilanka);
        TextView karumbi = (TextView) dialog.findViewById(R.id.karumbi);
        TextView keraleeyam = (TextView) dialog.findViewById(R.id.keraleeyam);
        TextView manjari = (TextView) dialog.findViewById(R.id.manjari);
        TextView meera = (TextView) dialog.findViewById(R.id.meera);
        TextView rachana = (TextView) dialog.findViewById(R.id.rachana);
        anjali.setText("അഞ്ചലി ");
        chilanka.setText("ചിലങ്ക ");
        karumbi.setText("കറുമ്പി ");
        keraleeyam.setText("കേരളീയം ");
        manjari.setText("മഞ്ചരി ");
        meera.setText("മീര ");
        rachana.setText("രചന ");
        anjali.setTypeface(faceanjali);
        chilanka.setTypeface(facechilanka);
        karumbi.setTypeface(facekarumbi);
        keraleeyam.setTypeface(facekeraleeyam);
        manjari.setTypeface(facemanjari);
        meera.setTypeface(facemeera);
        rachana.setTypeface(facerachana);
        TextView rachana2 = rachana;
        chilanka.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(facechilanka);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        karumbi.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(facekarumbi);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        keraleeyam.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(facekeraleeyam);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        anjali.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(faceanjali);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        manjari.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(facemanjari);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        meera.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(facemeera);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        Typeface typeface = faceanjali;
        rachana2.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    currentview.setTypeface(facerachana);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        dialog.show();
    }

    public void edit_status(String text2) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialogedittext);
        final EditText status = (EditText) dialog.findViewById(R.id.chintha);
        Button update = (Button) dialog.findViewById(R.id.rpt_update);
        status.setText(text2);
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                currentview.setText(status.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
