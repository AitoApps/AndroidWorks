package com.suthra_malayalam_web;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import java.util.ArrayList;

public class Album_Pos_Fvrt extends AppCompatActivity {
    ImageView ads_remove;
    TextView amount;
    Button atmcard;
    ImageView back;
    ImageView back_move;
    String[] c;
    TextView count;
    final DataBase dataBase = new DataBase(this);
    final DataBase db = new DataBase(this);
    DataBase_POS dbHelper;
    Button ecreacharge;
    Typeface face;
    Typeface face2;
    ImageView fvrt_remove;
    ArrayList<String> fvrtlist = new ArrayList<>();
    ImageView image;
    ScrollView lyt_lock;
    NetConnect nc;
    ImageView next_move;
    ImageView nodata;
    Button paytm;
    ProgressDialog pd;
    public int pos = 0;
    TextView pymenttext;
    TextView text;
    TextView totalcount;
    Button upiid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.avty_fvrt_albm);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }
        back = (ImageView) findViewById(R.id.move_back);
        pd = new ProgressDialog(this);
        ads_remove = (ImageView) findViewById(R.id.removeads);
        next_move = (ImageView) findViewById(R.id.movenext);
        back_move = (ImageView) findViewById(R.id.move_back);
        nodata = (ImageView) findViewById(R.id.emptydata);
        lyt_lock = (ScrollView) findViewById(R.id.locklyt);
        pymenttext = (TextView) findViewById(R.id.pymenttext);
        totalcount = (TextView) findViewById(R.id.totalcount);
        count = (TextView) findViewById(R.id.count);
        image = (ImageView) findViewById(R.id.image);
        fvrt_remove = (ImageView) findViewById(R.id.removefvrt);
        nc = new NetConnect(this);
        amount = (TextView) findViewById(R.id.amount);
        ecreacharge = (Button) findViewById(R.id.ecreacharge);
        atmcard = (Button) findViewById(R.id.atmcard);
        paytm = (Button) findViewById(R.id.paytm);
        upiid = (Button) findViewById(R.id.upiid);
        dbHelper = new DataBase_POS(this, "chithram.sqlite");
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        face2 = Typeface.createFromAsset(getAssets(), "app_fonts/rupee.ttf");
        text = (TextView) findViewById(R.id.text);
        text.setText(Static_Veriable.fvrts);
        text.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ArrayList<String> id1 = dataBase.get_fvrt_albm();
        c = (String[]) id1.toArray(new String[id1.size()]);
        if (c.length > 0) {
            nodata.setVisibility(View.GONE);
            fvrtlist.clear();
            int i = 0;
            while (true) {
                String[] strArr = c;
                if (i >= strArr.length) {
                    break;
                }
                fvrtlist.add(strArr[i]);
                i++;
            }
            pos = 0;

            totalcount.setText(fvrtlist.size()+"");
            count.setText((pos + 1)+"");
            show_image(pos);
        } else {
            nodata.setVisibility(View.VISIBLE);
        }
        paytm.setText(Static_Veriable.paytm);
        paytm.setTypeface(face);
        ecreacharge.setText(Static_Veriable.eacyrecharge);
        ecreacharge.setTypeface(face);
        pymenttext.setTypeface(face);
        amount.setText("`35");
        amount.setTypeface(face2);
        amount.setTextColor(Color.RED);
        pymenttext.setText("35 രൂപ പേയ്‌മെന്റ് ചെയ്താല്‍ താങ്കള്‍ക്ക് എല്ലാ പൊസിഷനുകളും ആല്‍ബവും പരസ്യങ്ങളില്ലാതെ കാണാവുന്നതാണ്. താഴെയുള്ള ഏത് വഴി ഉപയോഗിച്ചും പേയ്‌മെന്റ് ചെയ്യാവുന്നതാണ്‌");
        pymenttext.setTypeface(face);
        atmcard.setText(Static_Veriable.atmcard);
        atmcard.setTypeface(face);
        upiid.setText(Static_Veriable.upiid);
        upiid.setTypeface(face);
        lyt_lock.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        atmcard.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {
                    Static_Veriable.clickedmethod = 5;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });
        upiid.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Static_Veriable.clickedmethod = 4;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                } catch (Exception e) {
                }
            }
        });
        ecreacharge.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {
                    Static_Veriable.clickedmethod = 6;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });
        paytm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Static_Veriable.clickedmethod = 3;
                    startActivity(new Intent(getApplicationContext(), Mobile_verification.class));
                } catch (Exception e) {
                }
            }
        });
        next_move.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (fvrtlist.size() <= 0) {
                        return;
                    }
                    if (pos != fvrtlist.size() - 1) {
                        pos++;
                        count.setText((pos + 1)+"");
                        show_image(pos);
                    }
                } catch (Exception e) {
                }
            }
        });
        ads_remove.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                lyt_lock.setVisibility(View.VISIBLE);
            }
        });
        back_move.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (c.length <= 0) {
                        return;
                    }
                    if (pos > 0) {
                        pos--;
                        count.setText((pos + 1)+"");
                        show_image(pos);
                    }
                } catch (Exception e) {
                }
            }
        });
        fvrt_remove.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    String str = "";
                    if (fvrtlist.size() > 0) {
                        dataBase.drop_albm_fvrt((String) fvrtlist.get(pos));
                        fvrtlist.remove(pos);
                        totalcount.setText(fvrtlist.size()+"");
                    }
                    try {
                        if (fvrtlist.size() == 0) {
                            nodata.setVisibility(View.VISIBLE);
                            image.setImageResource(0);
                            count.setText("0");
                        } else if (fvrtlist.size() == pos) {
                            pos--;
                            count.setText((pos + 1)+"");
                            show_image(pos);
                        } else {
                            if (fvrtlist.size() == 1) {
                                pos = 0;
                                count.setText("1");
                            }
                            show_image(pos);
                        }
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                }
            }
        });
    }


    public void onResume() {
        super.onResume();
        show_image(pos);
    }

    public void show_image(int pos2) {
        String str = "";
        try {
            if (!db.get_purchase().equalsIgnoreCase(str)) {
                lyt_lock.setVisibility(View.GONE);
                byte[] decodedString = Base64.decode(dbHelper.getpic(fvrtlist.get(pos2)), 0);
                Options options = new Options();
                options.inPurgeable = true;
                image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options));
                alpha_ani();
            } else if (nc.isConnectingToInternet()) {
                byte[] decodedString2 = Base64.decode(dbHelper.getpic(fvrtlist.get(pos2)), 0);
                Options options2 = new Options();
                options2.inPurgeable = true;
                image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length, options2));
                alpha_ani();
            } else {
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        } catch (Exception e) {
        }
    }

    public void alpha_ani() {
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1000);
        animation1.setFillAfter(true);
        image.startAnimation(animation1);
    }

}
