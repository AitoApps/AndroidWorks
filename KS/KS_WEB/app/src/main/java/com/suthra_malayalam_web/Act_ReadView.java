package com.suthra_malayalam_web;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import es.dmoral.toasty.Toasty;
import java.io.InputStream;

public class Act_ReadView extends AppCompatActivity {

    TextView article;
    TextView article_head;
    ImageView back;
    final DataBase dataBase = new DataBase(this);
    Typeface face;
    ImageView minus;
    public String path = "";
    ImageView plus;
    ImageView share;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.acty_readingview);
        article_head = (TextView) findViewById(R.id.articleheading);
        article = (TextView) findViewById(R.id.article);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        share = (ImageView) findViewById(R.id.appshare);
        plus = (ImageView) findViewById(R.id.zoom_in);
        minus = (ImageView) findViewById(R.id.zoom_out);
        back = (ImageView) findViewById(R.id.moveback);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    article_head.setTextSize(0, article_head.getTextSize() - 1.0f);
                    article.setTextSize(0, article.getTextSize() - 1.0f);

                    dataBase.add_fontsize(article.getTextSize()+"");
                } catch (Exception e) {
                }
            }
        });
        plus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    article_head.setTextSize(0, article_head.getTextSize() + 1.0f);
                    article.setTextSize(0, article.getTextSize() + 1.0f);
                    dataBase.add_fontsize(article.getTextSize()+"");
                } catch (Exception e) {
                }
            }
        });
        try {
            if (!dataBase.get_sizefont().equalsIgnoreCase("")) {
                article.setTextSize(0, Float.parseFloat(dataBase.get_sizefont()));
                article_head.setTextSize(0, Float.parseFloat(dataBase.get_sizefont()));
            }
        } catch (Exception e) {
        }
        share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String shareBody;
                String str = "";
                try {
                    String str2 = "...\n";
                    String str3 = "*\n\n";
                    String str4 = "*";
                    if (article.getText().toString().length() > 310) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str4);
                        sb.append(article_head.getText().toString());
                        sb.append(str3);
                        sb.append(article.getText().toString().substring(0, 300));
                        sb.append(str2);
                        sb.append(Static_Veriable.readmore);
                        shareBody = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str4);
                        sb2.append(article_head.getText().toString());
                        sb2.append(str3);
                        sb2.append(article.getText().toString());
                        sb2.append(str2);
                        sb2.append(Static_Veriable.readmore);
                        shareBody = sb2.toString();
                    }
                    dialog_share(shareBody);
                } catch (Exception e) {
                }
            }
        });
    }

    public class fetching_data extends AsyncTask<String, Void, String> {
        public fetching_data() {
        }


        public void onPreExecute() {
        }


        public String doInBackground(String... arg0) {
            try {
                InputStream is = getAssets().open(path);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                return new String(buffer);
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }


        public void onPostExecute(String result) {
            article.setText(Html.fromHtml(result));
            article.setTypeface(face);
        }
    }

    public void onResume() {
        try {
            text.setText(File_Positions.catogery[Integer.parseInt(dataBase.get_cat()) - 1]);
            if (dataBase.get_cat().equalsIgnoreCase("1")) {
                article_head.setText(File_Positions.motherhood[Integer.parseInt(dataBase.get_subcat()) + (Integer.parseInt(dataBase.get_subcat()) - 1)]);
                path = "mthrhood/"+dataBase.get_subcat()+".txt";
            } else if (dataBase.get_cat().equalsIgnoreCase("2")) {
                article_head.setText(Static_Veriable.tempheading);
                path = "knwldge/"+dataBase.get_subcat()+".txt";
            } else if (dataBase.get_cat().equalsIgnoreCase("3")) {
                article_head.setText(Static_Veriable.tempheading);
                path = "healthproblems/"+dataBase.get_subcat()+".txt";
            } else if (dataBase.get_cat().equalsIgnoreCase("4")) {
                article_head.setText(Static_Veriable.tempheading);
                path = "foods/"+dataBase.get_subcat()+".txt";
            }
            text.setTypeface(face);
            article_head.setTypeface(face, Typeface.BOLD);
            new fetching_data().execute(new String[0]);
        } catch (Exception e) {
        }
        super.onResume();
    }

    public void dialog_share(final String sharebody) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.share_layout);
        RelativeLayout lytwhatsapp = (RelativeLayout) dialog.findViewById(R.id.lytwhatsapp);
        RelativeLayout lytfacebook = (RelativeLayout) dialog.findViewById(R.id.lytfacebook);
        RelativeLayout lytwhatsappbus = (RelativeLayout) dialog.findViewById(R.id.lytwhatsappbus);
        TextView txtwhatsapp = (TextView) dialog.findViewById(R.id.txtwhatsapp);
        TextView txtfacebook = (TextView) dialog.findViewById(R.id.txtfacebook);
        TextView txtwhatsappbus = (TextView) dialog.findViewById(R.id.txtwhatsappbus);
        txtwhatsapp.setText("വാട്‌സ്ആപ്പ് ");
        txtfacebook.setText("ഫെയ്‌സ്ബുക്ക് ");
        txtwhatsappbus.setText("വാട്‌സ്ആപ്പ് ബിസിനസ്‌ ");
        txtwhatsapp.setTypeface(face);
        txtfacebook.setTypeface(face);
        txtwhatsappbus.setTypeface(face);
        lytwhatsapp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "android.permission.WRITE_EXTERNAL_STORAGE";
                if (ContextCompat.checkSelfPermission(Act_ReadView.this, str) != 0) {
                    ActivityCompat.requestPermissions(Act_ReadView.this, new String[]{str}, 1);
                    return;
                }
                onClickApp("com.whatsapp", sharebody);
                dialog.dismiss();
            }
        });
        lytfacebook.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "android.permission.WRITE_EXTERNAL_STORAGE";
                if (ContextCompat.checkSelfPermission(Act_ReadView.this, str) != 0) {
                    ActivityCompat.requestPermissions(Act_ReadView.this, new String[]{str}, 1);
                    return;
                }
                onClickApp("com.facebook.katana", sharebody);
                dialog.dismiss();
            }
        });
        lytwhatsappbus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "android.permission.WRITE_EXTERNAL_STORAGE";
                if (ContextCompat.checkSelfPermission(Act_ReadView.this, str) != 0) {
                    ActivityCompat.requestPermissions(Act_ReadView.this, new String[]{str}, 1);
                    return;
                }
                onClickApp("com.whatsapp.w4b", sharebody);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onClickApp(String pack, String shareBody) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(pack, PackageManager.GET_META_DATA);
            Intent waIntent = new Intent("android.intent.action.SEND");
            waIntent.setType("text/plain");
            waIntent.setPackage(pack);
            waIntent.putExtra("android.intent.extra.TEXT", shareBody);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (Exception e) {
            if (pack.equalsIgnoreCase("com.whatsapp")) {
                Toasty.info(getApplicationContext(), "ദയവായി ആദ്യം വാട്‌സാആപ്പ് ഇന്‍സ്റ്റാള്‍ ചെയ്യുക ", 0).show();
            } else if (pack.equalsIgnoreCase("com.facebook.katana")) {
                Toasty.info(getApplicationContext(), "ദയവായി ആദ്യം ഫെയ്‌സ്ബുക്ക് ഇന്‍സ്റ്റാള്‍ ചെയ്യുക ", 0).show();
            } else if (pack.equalsIgnoreCase("com.whatsapp.w4b")) {
                Toasty.info(getApplicationContext(), "ദയവായി ആദ്യം വാട്‌സ്ആപ്പ് ബിസിനസ്‌ ഇന്‍സ്റ്റാള്‍ ചെയ്യുക ", 0).show();
            }
        }
    }

}
