package com.mal_suthra;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import es.dmoral.toasty.Toasty;

public class Mobile_verification extends AppCompatActivity {
    public static String mobilenumber = "";
    ImageView back;
    NetConnect cd;
    final DataBase dataBase = new DataBase(this);
    EditText date;
    Typeface face;
    TextView helptext;
    final DataBase_MobileNumber mdb = new DataBase_MobileNumber(this);
    ProgressDialog pd;
    TextView text;
    TextView text_paymnet;
    Button verify;
    TextView wponly;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvy_mob_verification);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        back = (ImageView) findViewById(R.id.moveback);
        text = (TextView) findViewById(R.id.text);
        helptext = (TextView) findViewById(R.id.help_text);
        verify = (Button) findViewById(R.id.verify);
        date = (EditText) findViewById(R.id.date);
        text_paymnet = (TextView) findViewById(R.id.pymenttext);
        wponly = (TextView) findViewById(R.id.wponly);
        pd = new ProgressDialog(this);
        cd = new NetConnect(this);
        text.setText("പേയ്‌മെന്റ് ഐഡി ");
        text.setTypeface(face);
        text_paymnet.setTypeface(face);
        wponly.setText(Static_Veriable.paymenthelp);
        verify.setText(Static_Veriable.verifybtn);
        helptext.setText("എന്തിനാണ് മൊബൈല്‍ നമ്പറിന്റെ ആവിശ്യം ?");
        TextView textView = helptext;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        helptext.setTypeface(face);
        text_paymnet.setText("പേയ്‌മെന്റ് ചെയ്യുന്നതിന് മുമ്പായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ എന്റര്‍ ചെയ്യുക ");
        wponly.setTypeface(face);
        verify.setTypeface(face);
        if (!mdb.get_mob().equalsIgnoreCase("")) {
            date.setText(mdb.get_mob());
            date.setSelection(mdb.get_mob().toString().length());
        }
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        helptext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mobile_help();
            }
        });
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
                } else if (date.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), Static_Veriable.enterpaymenttime, 0).show();
                    date.requestFocus();
                } else {
                    Mobile_verification.mobilenumber = date.getText().toString();
                    new update_details().execute(new String[0]);
                }
            }
        });
    }

    public void mobile_help() {
        Dialog dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.mobile_req_help);
        dialog3.setTitle("Mobile Number ?");
        TextView inform = (TextView) dialog3.findViewById(R.id.inform);
        inform.setTypeface(face);
        inform.setText("താങ്കള്‍ പേയ്‌മെന്റ് നടത്തിയതിന് ശേഷം ആപ്പ് അണ്‍ ഇന്‍സ്റ്റാള്‍ ചെയ്ത് വീണ്ടും ഇന്‍സ്റ്റാള്‍ ചെയ്താല്‍ എല്ലാ പൊസിഷനുകളും കാണുവാന്‍ വീണ്ടും പേയ്‌മെന്റ് ചോദിക്കും.അപ്പോള്‍ നിങ്ങള്‍ ഇവിടെ നല്‍കുന്ന മൊബൈല്‍ നമ്പര്‍ തന്നെ നല്‍കിയാല്‍ ബാക്കിയുള്ള പൊസിഷനുകള്‍ പേയ്‌മെന്റ് ചെയ്യാതെ തന്നെ കാണാവുന്നതാണ്.ഇങ്ങനെ ഒരു പേയ്‌മെന്റില്‍ 5 തവണ വരെ ചെയ്യാം ");
        dialog3.show();
    }

    public class update_details extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Checking your mobile number...Please wait");
            pd.setCancelable(false);
            pd.show();
        }


        public String doInBackground(String... arg0) {


            try {

                String link= Static_Veriable.weblink +"payment_dbupdate.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Mobile_verification.mobilenumber, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }


        public void onPostExecute(String result) {
            try {
                pd.dismiss();
                if (!result.contains(":OK")) {
                    Toasty.info(getApplicationContext(), Static_Veriable.tmpproblem, 0).show();
                } else if (result.contains("3:")) {
                    mdb.add_mob(Mobile_verification.mobilenumber);
                    if (Static_Veriable.clickedmethod == 3) {
                        startActivity(new Intent(getApplicationContext(), Paytm.class));
                        finish();
                    } else if (Static_Veriable.clickedmethod == 4) {
                        startActivity(new Intent(getApplicationContext(), UPIID.class));
                        finish();
                    } else if (Static_Veriable.clickedmethod == 5) {
                        startActivity(new Intent(getApplicationContext(), Act_Pymnt_Card.class));
                        finish();
                    } else if (Static_Veriable.clickedmethod == 6) {
                        startActivity(new Intent(getApplicationContext(), Recharge.class));
                        finish();
                    }
                    else if (Static_Veriable.clickedmethod == 7) {
                        startActivity(new Intent(getApplicationContext(), Complete_Task.class));
                        finish();
                    }
                } else if (result.contains("2:")) {
                    alert_show();
                } else if (result.contains("1:")) {
                    showalert1();
                }
            } catch (Exception e) {
            }
        }
    }

    public void alert_show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) "ക്ഷമിക്കണം !!! ഈ മൊബൈല്‍ നമ്പര്‍ ഉപയോഗിച്ച് താങ്കള്‍ 5 തവണ ഈ ആപ്പ് ഉപയോഗിച്ചതാണ്.ദയവായി വീണ്ടും പേയ്‌മെന്റ് നടത്തുക.പേയ്‌മെന്റിന് ശേഷം വീണ്ടും 5 തവണ ഈ ആപ്പ് ഉപയോഗിക്കാവുന്നതാണ്.").setCancelable(false).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mdb.add_mob(Mobile_verification.mobilenumber);
                if (Static_Veriable.clickedmethod == 3) {
                    startActivity(new Intent(getApplicationContext(), Paytm.class));
                    finish();
                } else if (Static_Veriable.clickedmethod == 4) {
                    startActivity(new Intent(getApplicationContext(), UPIID.class));
                    finish();
                } else if (Static_Veriable.clickedmethod == 5) {
                    startActivity(new Intent(getApplicationContext(), Act_Pymnt_Card.class));
                    finish();
                } else if (Static_Veriable.clickedmethod == 6) {
                    startActivity(new Intent(getApplicationContext(), Recharge.class));
                    finish();
                }
                else if (Static_Veriable.clickedmethod == 7) {
                    startActivity(new Intent(getApplicationContext(), Complete_Task.class));
                    finish();
                }
                else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.text)).setTypeface(face);
        } catch (Exception e) {
        }
    }

    public void showalert1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) "വളരെ നന്ദി !!! താങ്കള്‍ ഒരിക്കല്‍ പേയ്‌മെന്റ് ചെയ്തതാണ് താങ്കള്‍ക്ക് എല്ലാ പൊസിഷനുകളും കാണാവുന്നതാണ്‌ ").setCancelable(false).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dataBase.add_purchase("1");
                Intent intent = new Intent(getApplicationContext(), Cpanel.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.text)).setTypeface(face);
        } catch (Exception e) {
        }
    }
}
