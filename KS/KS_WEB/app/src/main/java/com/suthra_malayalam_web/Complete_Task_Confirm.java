package com.suthra_malayalam_web;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import es.dmoral.toasty.Toasty;

public class Complete_Task_Confirm extends AppCompatActivity {
    public String str_date = "";
    ImageView back;
    EditText date;
    final DataBase db = new DataBase(this);
    Typeface face;
    NetConnect nc;
    TextView payment_text;
    ProgressDialog pd;
    TextView text;
    TextView txtdate;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete__task__confirm);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        back = (ImageView) findViewById(R.id.moveback);
        payment_text = (TextView) findViewById(R.id.pymenttext);
        txtdate = (TextView) findViewById(R.id.txtdate);
        text = (TextView) findViewById(R.id.text);
        verify = (Button) findViewById(R.id.verify);
        date = (EditText) findViewById(R.id.date);
        pd = new ProgressDialog(this);
        nc = new NetConnect(this);

        payment_text.setText("ഇന്\u200Dസ്റ്റാള്\u200D ചെയ്ത ആപ്പുകളില്\u200D താങ്കള്\u200D രജിസ്ട്രര്\u200D ചെയ്യുവാനുപയോഗിച്ച മൊബൈല്\u200D നമ്പര്\u200D താഴെയുള്ള ബോക്\u200Cസില്\u200D ടൈപ്പ് ചെയ്യുക ");
        text.setText("കംപ്ലീറ്റ് ടാസ്\u200Cക് ");
        text.setTypeface(face);
        payment_text.setTypeface(face);
        txtdate.setText("മൊബൈല്\u200D നമ്പര്\u200D");
        verify.setText(Static_Veriable.verifybtn);
        txtdate.setTypeface(face);
        verify.setTypeface(face);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {

                    if (date.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), Static_Veriable.enterrechargetime, 0).show();
                        date.requestFocus();
                        return;
                    }
                    db.add_veriid(((int) (Math.random() * 9000) + 1000)+"");
                    str_date = date.getText().toString();
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

                String link= Static_Veriable.weblink +"taskcomplete1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.get_veriid()+":%"+str_date+":%"+"task", "UTF-8");
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
                if (result.contains("ok")) {
                    alert_show(Static_Veriable.afterpost);
                }
                else
                {
                    db.drop_veriid();
                    Toasty.info(getApplicationContext(), Static_Veriable.tmpproblem, 1).show();
                }

            } catch (Exception e) {
            }
        }
    }

}

