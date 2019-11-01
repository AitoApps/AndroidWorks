package com.sanji_admin;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Add_Franchiesis extends AppCompatActivity {
    EditText address;
    EditText agrementid;
    EditText area;
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    Typeface face1;
    EditText mobile;
    EditText name;
    ProgressDialog pd;
    TextView text;
    public String txt_address = "";
    public String txt_agrementid = "";
    public String txt_area = "";
    public String txt_mobile = "";
    public String txt_name = "";
    TextView txtaddress;
    TextView txtagrementid;
    TextView txtarea;
    TextView txtmobile;
    TextView txtname;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button update;

    public class updatefranch extends AsyncTask<String, Void, String> {
        public updatefranch() {
        }
        public void onPreExecute() {
            Add_Franchiesis.pd.setMessage("Please Wait.....");
            Add_Franchiesis.pd.setCancelable(false);
            Add_Franchiesis.pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("addnewfranchisis.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.fr_sn);
                sb3.append(":%");
                sb3.append(Temp.fr_sn);
                sb3.append(":%");
                sb3.append(Add_Franchiesis.txt_name);
                sb3.append(":%");
                sb3.append(Add_Franchiesis.txt_area);
                sb3.append(":%");
                sb3.append(Add_Franchiesis.txt_mobile);
                sb3.append(":%");
                sb3.append(Add_Franchiesis.txt_address);
                sb3.append(":%");
                sb3.append(Add_Franchiesis.txt_agrementid);
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                Add_Franchiesis.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Add_Franchiesis.getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    Add_Franchiesis.finish();
                } else if (result.contains("exist")) {
                    Toasty.info(Add_Franchiesis.getApplicationContext(), "Sorry ! This Mobile Number already exist", Toast.LENGTH_SHORT).show();
                } else if (result.contains("userexi")) {
                    Toasty.info(Add_Franchiesis.getApplicationContext(), "Sorry ! This Mobile Number already exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(Add_Franchiesis.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__franchiesis);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        update.setTypeface(face);
        name = (EditText) findViewById(R.id.name);
        area = (EditText) findViewById(R.id.area);
        mobile = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        agrementid = (EditText) findViewById(R.id.agrementid);
        txtname = (TextView) findViewById(R.id.txtname);
        txtarea = (TextView) findViewById(R.id.txtarea);
        txtmobile = (TextView) findViewById(R.id.txtmobile);
        txtaddress = (TextView) findViewById(R.id.txtaddress);
        txtagrementid = (TextView) findViewById(R.id.txtagrementid);
        txtname.setTypeface(face);
        txtarea.setTypeface(face);
        txtmobile.setTypeface(face);
        txtaddress.setTypeface(face);
        txtagrementid.setTypeface(face);
        name.setTypeface(face1);
        area.setTypeface(face1);
        mobile.setTypeface(face1);
        address.setTypeface(face1);
        agrementid.setTypeface(face1);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Add_Franchiesis.onBackPressed();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (Add_Franchiesis.name.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Franchiesis.getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                        Add_Franchiesis.name.requestFocus();
                    } else if (Add_Franchiesis.area.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Franchiesis.getApplicationContext(), "Please enter area", Toast.LENGTH_SHORT).show();
                        Add_Franchiesis.area.requestFocus();
                    } else if (Add_Franchiesis.mobile.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Franchiesis.getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    } else if (Add_Franchiesis.cd.isConnectingToInternet()) {
                        Add_Franchiesis.txt_name = Add_Franchiesis.name.getText().toString();
                        Add_Franchiesis.txt_area = Add_Franchiesis.area.getText().toString();
                        Add_Franchiesis.txt_mobile = Add_Franchiesis.mobile.getText().toString();
                        Add_Franchiesis.txt_address = Add_Franchiesis.address.getText().toString();
                        Add_Franchiesis.txt_agrementid = Add_Franchiesis.agrementid.getText().toString();
                        new updatefranch().execute(new String[0]);
                    } else {
                        Toasty.info(Add_Franchiesis.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.frachedit == 1) {
            name.setText(Temp.fr_name);
            area.setText(Temp.fr_area);
            mobile.setText(Temp.fr_mobile);
            address.setText(Temp.fr_address);
            agrementid.setText(Temp.fr_agrementid);
        }
    }
}
