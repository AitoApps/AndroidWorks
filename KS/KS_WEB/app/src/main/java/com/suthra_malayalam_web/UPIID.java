package com.suthra_malayalam_web;

import android.app.AlertDialog;
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

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class UPIID extends AppCompatActivity {
    public static String str_date = "";
    ImageView back;
    EditText date;
    final DataBase db = new DataBase(this);
    Typeface face;
    NetConnect nc;
    ProgressDialog pd;
    TextView text;
    TextView txt_pymnt;
    TextView txtdate;
    Button verify;
    TextView wponly;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvty_upiid);
        txtdate = (TextView) findViewById(R.id.txtdate);
        wponly = (TextView) findViewById(R.id.wponly);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        back = (ImageView) findViewById(R.id.moveback);
        text = (TextView) findViewById(R.id.text);
        txt_pymnt = (TextView) findViewById(R.id.pymenttext);
        date = (EditText) findViewById(R.id.date);
        verify = (Button) findViewById(R.id.verify);
        pd = new ProgressDialog(this);
        nc = new NetConnect(this);
        String amount = "25";
        try {
            ArrayList<Integer> numbers = new ArrayList<>();
            numbers.add(Integer.valueOf(25));
            numbers.add(Integer.valueOf(25));
            Collections.shuffle(numbers);
            amount = String.valueOf(numbers.get(0));
        } catch (Exception e) {
        }
        if (db.get_amt_recharge().equalsIgnoreCase("")) {
            db.add_amt_recharge(amount);
        }
        txt_pymnt.setText(Static_Veriable.upitext1+" 35 "+Static_Veriable.paytmtext2);
        wponly.setTypeface(face);
        verify.setTypeface(face);
        text.setText(Static_Veriable.upiidtext);
        text.setTypeface(face);
        txt_pymnt.setTypeface(face);
        txtdate.setTypeface(face);
        txtdate.setText(Static_Veriable.pymnt_time);
        wponly.setText(Static_Veriable.paymenthelp);
        verify.setText(Static_Veriable.verifybtn);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               onBackPressed();
            }
        });
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {
                    String str = "";
                    if (date.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), Static_Veriable.enterpaymenttime, 0).show();
                       date.requestFocus();
                        return;
                    }
                    db.add_veriid(((int) (Math.random() * 9000) + 1000)+"");
                    str_date =date.getText().toString();
                    new updating_details().execute(new String[0]);
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });
    }

    public void window_exit() {
        Intent intent = new Intent(getApplicationContext(), Cpanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void alert_show(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) message).setCancelable(false).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                window_exit();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public class updating_details extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }


        public String doInBackground(String... arg0) {



            try {

                String link= Static_Veriable.weblink +"resgisterrecharge_upiid1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.get_veriid()+":%"+str_date+":%"+db.get_posbyapss(), "UTF-8");
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
                if (result.contains("ok")) {
                    alert_show(Static_Veriable.afterpost);
                    return;
                }
                db.drop_veriid();
                Toasty.info(getApplicationContext(), Static_Veriable.tmpproblem, Toasty.LENGTH_LONG).show();
            } catch (Exception e) {
            }
        }
    }
}
