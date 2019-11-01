package com.fishapp.user;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    RelativeLayout cart;
    RelativeLayout clients;
    Typeface face;
    ImageView fishlyt;
    RelativeLayout myorder;
    TextView text;
    TextView txtcart;
    TextView txtclients;
    TextView txtmyorder;
    ImageView changeloc;
    ProgressDialog pd;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    List<String> lst_areaid = new ArrayList();
    List<String> lst_areaname = new ArrayList();
    List<String> lst_delitime = new ArrayList();
    public static String txt_areaid="";
    public static String txt_areaname="";
    public static String txt_delitime="";
    ConnectionDetecter cd;
    PowerMenu powerMenu;
    ImageView anchorview;
    ImageView option;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        fishlyt = findViewById(R.id.fishlyt);
        myorder = (RelativeLayout) findViewById(R.id.myorder);
        cart = (RelativeLayout) findViewById(R.id.cart);
        clients = (RelativeLayout) findViewById(R.id.clients);
        txtmyorder = (TextView) findViewById(R.id.txtmyorder);
        txtcart = (TextView) findViewById(R.id.txtcart);
        option=findViewById(R.id.option);
        anchorview=findViewById(R.id.anchorview);
        pd=new ProgressDialog(this);
        cd=new ConnectionDetecter(this);
        txtclients = (TextView) findViewById(R.id.txtclients);
        text = (TextView) findViewById(R.id.text);
        changeloc=findViewById(R.id.changeloc);
        txtmyorder.setTypeface(face);
        txtcart.setTypeface(face);
        txtclients.setTypeface(face);
        text.setTypeface(face);
        text.setText("Fishapp - "+udb.getareaname());
        try {
            if (udb.getareaid().equalsIgnoreCase("")) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
                finish();
                return;
            }
            if (udb.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                udb.addscreenwidth(width+"");
            }
            clients.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Client_Catogery.class));
                }
            });
            cart.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            myorder.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), My_Order.class));
                }
            });
            fishlyt.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {

                    Temp.feeditem.clear();
                    startActivity(new Intent(getApplicationContext(), Fish_Catogery.class));
                }
            });
            changeloc.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {


                    if (cd.isConnectingToInternet()) {
                        new featching_area().execute(new String[0]);
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
             powerMenu = new PowerMenu.Builder(this)
                    .addItem(new PowerMenuItem("Change Area",R.drawable.changeloc,false)) // add an item.
                    .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) // Animation start point (TOP | LEFT).
                    .setMenuRadius(10f)
                    .setMenuShadow(10f)
                    .setTextColor(Color.parseColor("#000000"))
                    .setTextGravity(Gravity.CENTER)
                    .setTextSize(16)
                    .setWidth(600)
                    .setTextTypeface(face)
                    .setSelectedTextColor(Color.WHITE)
                    .setMenuColor(Color.WHITE)
                    .setSelectedMenuColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    .setOnMenuItemClickListener(onMenuItemClickListener)
                    .build();

            option.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    powerMenu.showAsDropDown(anchorview);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            if (cd.isConnectingToInternet()) {
                new featching_area().execute(new String[0]);
            } else {
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
            // change selected item
            powerMenu.dismiss();
        }
    };
    public class featching_area extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Loading areas....");
            pd.setCancelable(false);
            pd.show();
        }

        public String doInBackground(String... arg0) {
            try {
                String link = Temp.weblink + "user_getarealist.php";
                String data = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
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
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }

        public void onPostExecute(String result) {
            String str = "0";

            pd.dismiss();
            if (result.contains(":%ok")) {
                try {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 3;
                    int m = -1;
                    lst_areaid.clear();
                    lst_areaname.clear();
                    lst_delitime.clear();
                    lst_areaid.add(str);
                    lst_delitime.add(str);
                    lst_areaname.add("Select Your Area");
                    for (int i = 1; i <= k; i++) {
                        m = m + 1;
                        lst_areaid.add(got[m].trim());
                        m = m + 1;
                        lst_areaname.add(got[m].trim());
                        m = m + 1;
                        lst_delitime.add(got[m].trim());
                    }

                    changearea();
                } catch (Exception e2) {
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please try later",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void changearea() {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(1);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog3.setContentView(R.layout.changearea);
            dialog3.setCancelable(true);
            TextView txtarea = (TextView) dialog3.findViewById(R.id.txtarea);
            Spinner area=dialog3.findViewById(R.id.area);
            Button update=dialog3.findViewById(R.id.update);
            txtarea.setTypeface(face);
            update.setTypeface(face);

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, lst_areaname) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((TextView) v).setTextSize(16.0f);
                    ((TextView) v).setTypeface(face);
                    return v;
                }
            };
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            area.setAdapter(dataAdapter2);
            area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                    txt_areaid = (String) lst_areaid.get(arg2);
                    txt_areaname = (String) lst_areaname.get(arg2);
                    txt_delitime = (String) lst_delitime.get(arg2);
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            try
            {
                area.setSelection(lst_areaid.indexOf(udb.getareaid()));
            }
            catch (Exception a)
            {

            }


            update.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    udb.addarea(txt_areaid, txt_areaname, txt_delitime);
                    Toast.makeText(getApplicationContext(),"Area Changed to "+txt_areaname,Toast.LENGTH_SHORT).show();
                    dialog3.dismiss();
                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }

}
