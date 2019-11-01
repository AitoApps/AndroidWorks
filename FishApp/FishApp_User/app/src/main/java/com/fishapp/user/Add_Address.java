package com.fishapp.user;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Add_Address extends AppCompatActivity {
    EditText address;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    Typeface face1;
    EditText landmark;
    EditText mobile1;
    EditText mobile2;
    EditText name;
    ProgressDialog pd;
    EditText pincode;
    EditText place;
    TextView text;
    String txt_address="";
    String txt_landmark="";
    String txt_mobile1="";
    String txt_mobile2="";
    String txt_name="";
    String txt_pincode="";
    String txt_place="";
    TextView txtaddress;
    TextView txtlandmark;
    TextView txtmobile1;
    TextView txtmobile2;
    TextView txtname;
    TextView txtpincode;
    TextView txtplace;
    public String txtsn;
    UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button update;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__address);
        cd = new ConnectionDetecter(this);
        text = (TextView) findViewById(R.id.text);
        back = (ImageView) findViewById(R.id.back);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        txtname = (TextView) findViewById(R.id.txtname);
        txtplace = (TextView) findViewById(R.id.txtplace);
        txtmobile1 = (TextView) findViewById(R.id.txtmobile1);
        txtmobile2 = (TextView) findViewById(R.id.txtmobile2);
        txtaddress = (TextView) findViewById(R.id.txtaddress);
        txtlandmark = (TextView) findViewById(R.id.txtlandmark);
        txtpincode = (TextView) findViewById(R.id.txtpincode);
        name = (EditText) findViewById(R.id.name);
        place = (EditText) findViewById(R.id.place);
        mobile1 = (EditText) findViewById(R.id.mobile1);
        mobile2 = (EditText) findViewById(R.id.mobile2);
        update = (Button) findViewById(R.id.update);
        pincode = (EditText) findViewById(R.id.pincode);
        address = (EditText) findViewById(R.id.address);
        landmark = (EditText) findViewById(R.id.landmark);
        text.setTypeface(face);
        update.setTypeface(face);
        txtname.setText("Name ");
        txtplace.setText("Place ");
        txtmobile1.setText("Mobile 1 ");
        txtmobile2.setText("Mobile 2 ");
        txtaddress.setText("Address ");
        txtpincode.setText("Pincode ");
        txtlandmark.setText("Landmark ");
        txtname.setTypeface(face1);
        txtplace.setTypeface(face1);
        txtmobile1.setTypeface(face1);
        txtmobile2.setTypeface(face1);
        txtaddress.setTypeface(face1);
        txtlandmark.setTypeface(face1);
        txtpincode.setTypeface(face1);
        name.setTypeface(face);
        place.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        update.setTypeface(face);
        pincode.setTypeface(face);
        address.setTypeface(face);
        landmark.setTypeface(face);
        if (Temp.isaddressedit == 1) {
            txtsn = Temp.user_sn;
            name.setText(Temp.user_name);
            place.setText(Temp.user_place);
            mobile1.setText(Temp.user_mobile1);
            mobile2.setText(Temp.user_mobile2);
            pincode.setText(Temp.user_pincode);
            address.setText(Temp.user_address);
            landmark.setText(Temp.user_landmark);
        }
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    String str = "";
                    if (name.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "enter name", Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (place.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "enter place", Toast.LENGTH_SHORT).show();
                        place.requestFocus();
                    } else if (mobile1.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Enter your mobile", Toast.LENGTH_SHORT).show();
                        mobile1.requestFocus();
                    } else if (address.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Enter your addresss", Toast.LENGTH_SHORT).show();
                        address.requestFocus();
                    } else if (pincode.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Enter your pincode", Toast.LENGTH_SHORT).show();
                        pincode.requestFocus();
                    } else {
                        txt_name = name.getText().toString();
                        txt_place = place.getText().toString();
                        txt_mobile1 =mobile1.getText().toString();
                        txt_mobile2 = mobile2.getText().toString();
                        txt_address =address.getText().toString();
                        txt_landmark =landmark.getText().toString();
                        txt_pincode =pincode.getText().toString();
                        new updateaddress().execute(new String[0]);
                    }
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class updateaddress extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Updating.....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"add_useraddress.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtsn+":%"+Temp.isaddressedit+":%"+udb.get_userid()+":%"+txt_name+":%"+txt_place+":%"+txt_mobile1+":%"+txt_mobile2+":%"+txt_address+":%"+txt_pincode+":%"+txt_landmark, "UTF-8");
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
                if (result.contains("404")) {
                    Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            } catch (Exception a) {
                // Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}
