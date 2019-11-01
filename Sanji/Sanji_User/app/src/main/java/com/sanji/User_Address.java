package com.sanji;

import adapter.Useraddress_ListAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;

import data.User_Address_Feeditem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class User_Address extends AppCompatActivity {
    ImageView addaddress;
    EditText address;
    public String addresssn;
    ImageView back;
    public String cart_delicharge;
    public String cart_miniqty;
    public String cart_nettotal;
    public String cart_price1;
    public String cart_productid1;
    public String cart_productname1;
    public String cart_qty1;
    public String cart_shopid;
    public String cart_totalprice1;
    public String cart_unittype;
    ConnectionDetecter cd;
    final DatabaseHandler db;
    public Dialog dialog1;
    Typeface face;
    Typeface face1;

    public List<User_Address_Feeditem> feedItems;
    ImageView heart;
    EditText landmark;
    ListView list;

    public Useraddress_ListAdapter listAdapter;
    EditText mobile1;
    EditText mobile2;
    EditText name;
    TextView nodata;
    ImageView nointernet;
    Button ordernow;
    ProgressDialog pd;
    EditText pincode;
    EditText place;
    TextView text;
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

    public class loadaddress extends AsyncTask<String, Void, String> {
        public loadaddress() {
        }
        public void onPreExecute() {
            User_Address.nointernet.setVisibility(View.GONE);
            User_Address.nodata.setVisibility(View.GONE);
            User_Address.list.setVisibility(View.GONE);
            User_Address.heart.setVisibility(View.VISIBLE);
            User_Address.uncheckall();
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getuseraddress.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(User_Address.udb.get_userid());
                sb3.append("");
                sb2.append(URLEncoder.encode(sb3.toString(), str));
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
                if (result.contains("%:ok")) {
                    User_Address.feedItems.clear();
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        User_Address_Feeditem item = new User_Address_Feeditem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setuser_name(got[m3]);
                        int m4 = m3 + 1;
                        item.setuser_place(got[m4]);
                        int m5 = m4 + 1;
                        item.setuser_mobile1(got[m5]);
                        int m6 = m5 + 1;
                        item.setuser_mobile2(got[m6]);
                        int m7 = m6 + 1;
                        item.setuser_address(got[m7]);
                        int m8 = m7 + 1;
                        item.setuser_landmark(got[m8]);
                        m = m8 + 1;
                        item.setpincode(got[m]);
                        User_Address.feedItems.add(item);
                    }
                    User_Address.heart.setVisibility(View.GONE);
                    User_Address.list.setVisibility(View.VISIBLE);
                    User_Address.listAdapter.notifyDataSetChanged();
                    return;
                }
                User_Address.nodata.setVisibility(View.VISIBLE);
                User_Address.heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    private class place_cart extends AsyncTask<Void, Integer, String> {
        private place_cart() {
        }
        public void onPreExecute() {
            try {
                User_Address.pd.setMessage("Please Wait.....");
                User_Address.pd.setCancelable(false);
                User_Address.pd.show();
                User_Address.ordernow.setEnabled(false);
            } catch (Exception e) {
            }
            super.onPreExecute();
        }
        public String doInBackground(Void... params) {
            return uploadFile();
        }

        private String uploadFile() {
            HttpClient httpclient = new DefaultHttpClient();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp.weblink);
            sb.append("placeorder.php");
            HttpPost httppost = new HttpPost(sb.toString());
            try {
                ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charset.forName("utf-8"));
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
                    public void transferred(long num) {
                    }
                });
                entity.addPart("userid", new StringBody(User_Address.udb.get_userid(), contentType));
                entity.addPart("shopid", new StringBody(User_Address.cart_shopid, contentType));
                entity.addPart("productid", new StringBody(User_Address.cart_productid1, contentType));
                entity.addPart("productname", new StringBody(User_Address.cart_productname1, contentType));
                entity.addPart("productprice", new StringBody(User_Address.cart_price1, contentType));
                entity.addPart("productqty", new StringBody(User_Address.cart_qty1, contentType));
                entity.addPart("producttotal", new StringBody(User_Address.cart_totalprice1, contentType));
                entity.addPart("minqty", new StringBody(User_Address.cart_miniqty, contentType));
                entity.addPart("unittype", new StringBody(User_Address.cart_unittype, contentType));
                entity.addPart("nettotal", new StringBody(User_Address.cart_nettotal, contentType));
                entity.addPart("delicharge", new StringBody(User_Address.cart_delicharge, contentType));
                entity.addPart("addresssn", new StringBody(User_Address.addresssn, contentType));
                StringBuilder sb2 = new StringBuilder();
                sb2.append(User_Address.db.get_latitude());
                sb2.append(",");
                sb2.append(User_Address.db.get_longtitude());
                entity.addPart("location", new StringBody(sb2.toString(), contentType));
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(r_entity);
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error occurred! Http Status Code: ");
                sb3.append(statusCode);
                return sb3.toString();
            } catch (ClientProtocolException e) {
                return e.toString();
            } catch (IOException e2) {
                return e2.toString();
            }
        }
        public void onPostExecute(String result) {
            User_Address.ordernow.setEnabled(true);
            User_Address.pd.dismiss();
            if (result.contains("404")) {
                Toasty.info(User_Address.getApplicationContext(), Temp.tempproblem, 0).show();
            } else if (result.contains("ok")) {
                User_Address.db.deletecart();
                User_Address.uploadsucess();
            } else {
                Toasty.info(User_Address.getApplicationContext(), Temp.tempproblem, 0).show();
            }
            super.onPostExecute(result);
        }
    }

    private class updateaddress extends AsyncTask<Void, Integer, String> {
        private updateaddress() {
        }
        public void onPreExecute() {
            User_Address.pd.setMessage("Please Wait.....");
            User_Address.pd.setCancelable(false);
            User_Address.pd.show();
            super.onPreExecute();
        }
        public String doInBackground(Void... params) {
            return uploadFile();
        }

        private String uploadFile() {
            HttpClient httpclient = new DefaultHttpClient();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp.weblink);
            sb.append("add_useraddress.php");
            HttpPost httppost = new HttpPost(sb.toString());
            try {
                ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charset.forName("utf-8"));
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
                    public void transferred(long num) {
                    }
                });
                if (Temp.isaddressedit == 1) {
                    entity.addPart("editid", new StringBody(User_Address.txtsn, contentType));
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.isaddressedit);
                sb2.append("");
                entity.addPart("isedit", new StringBody(sb2.toString(), contentType));
                entity.addPart("userid", new StringBody(User_Address.udb.get_userid(), contentType));
                entity.addPart("user_name", new StringBody(User_Address.name.getText().toString(), contentType));
                entity.addPart("user_place", new StringBody(User_Address.place.getText().toString(), contentType));
                entity.addPart("user_mobile1", new StringBody(User_Address.mobile1.getText().toString(), contentType));
                entity.addPart("user_mobile2", new StringBody(User_Address.mobile2.getText().toString(), contentType));
                entity.addPart("user_address", new StringBody(User_Address.address.getText().toString(), contentType));
                entity.addPart("user_pincode", new StringBody(User_Address.pincode.getText().toString(), contentType));
                entity.addPart("user_landmark", new StringBody(User_Address.landmark.getText().toString(), contentType));
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(r_entity);
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error occurred! Http Status Code: ");
                sb3.append(statusCode);
                return sb3.toString();
            } catch (ClientProtocolException e) {
                return e.toString();
            } catch (IOException e2) {
                return e2.toString();
            }
        }
        public void onPostExecute(String result) {
            User_Address.pd.dismiss();
            if (result.contains("404")) {
                Toasty.info(User_Address.getApplicationContext(), Temp.tempproblem, 0).show();
            } else if (result.contains("ok")) {
                new loadaddress().execute(new String[0]);
            } else {
                Toasty.info(User_Address.getApplicationContext(), Temp.tempproblem, 0).show();
            }
            super.onPostExecute(result);
        }
    }

    public User_Address() {
        String str = "";
        txtsn = str;
        db = new DatabaseHandler(this);
        addresssn = str;
        cart_shopid = str;
        cart_productid1 = str;
        cart_productname1 = str;
        cart_price1 = str;
        cart_qty1 = str;
        cart_totalprice1 = str;
        cart_unittype = str;
        cart_miniqty = str;
        cart_nettotal = str;
        cart_delicharge = str;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_user__address);
        cd = new ConnectionDetecter(this);
        heart = (ImageView) findViewById(R.id.heart);
        text = (TextView) findViewById(R.id.text);
        back = (ImageView) findViewById(R.id.back);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        list = (ListView) findViewById(R.id.list);
        nodata = (TextView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        addaddress = (ImageView) findViewById(R.id.addaddress);
        ordernow = (Button) findViewById(R.id.ordernow);
        nodata.setText("മുകളിലുള്ള പ്ലസ് ബട്ടണ്‍ പ്രസ്സ് ചെയ്ത് ഒരു അഡ്രസ്സ് നല്‍കുക ");
        nodata.setTypeface(face1);
        ordernow.setTypeface(face1);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                User_Address.onBackPressed();
            }
        });
        text.setText("Address ");
        text.setTypeface(face1);
        ordernow.setText("Order Now ");
        addaddress.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.isaddressedit = 0;
                User_Address.showuserdetails("", "", "", "", "", "", "", "");
            }
        });
        feedItems = new ArrayList();
        listAdapter = new Useraddress_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        ordernow.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (User_Address.cd.isConnectingToInternet()) {
                    String str = "";
                    if (!User_Address.db.getselectaddress().equalsIgnoreCase(str)) {
                        User_Address user_Address = User_Address.this;
                        user_Address.addresssn = user_Address.db.getselectaddress();
                        ArrayList<String> id1 = User_Address.db.getcart1();
                        String[] k = (String[]) id1.toArray(new String[id1.size()]);
                        int i = 0;
                        while (i < k.length) {
                            String str2 = ":%";
                            if (User_Address.cart_shopid.equalsIgnoreCase(str)) {
                                User_Address.cart_shopid = k[i];
                            } else {
                                User_Address user_Address2 = User_Address.this;
                                StringBuilder sb = new StringBuilder();
                                sb.append(User_Address.cart_shopid);
                                sb.append(str2);
                                sb.append(k[i]);
                                user_Address2.cart_shopid = sb.toString();
                            }
                            int i2 = i + 1;
                            if (User_Address.cart_productid1.equalsIgnoreCase(str)) {
                                User_Address.cart_productid1 = k[i2];
                            } else {
                                User_Address user_Address3 = User_Address.this;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(User_Address.cart_productid1);
                                sb2.append(str2);
                                sb2.append(k[i2]);
                                user_Address3.cart_productid1 = sb2.toString();
                            }
                            int i3 = i2 + 1;
                            if (User_Address.cart_productname1.equalsIgnoreCase(str)) {
                                User_Address.cart_productname1 = k[i3];
                            } else {
                                User_Address user_Address4 = User_Address.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(User_Address.cart_productname1);
                                sb3.append(str2);
                                sb3.append(k[i3]);
                                user_Address4.cart_productname1 = sb3.toString();
                            }
                            int i4 = i3 + 1;
                            if (User_Address.cart_price1.equalsIgnoreCase(str)) {
                                User_Address.cart_price1 = k[i4];
                            } else {
                                User_Address user_Address5 = User_Address.this;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(User_Address.cart_price1);
                                sb4.append(str2);
                                sb4.append(k[i4]);
                                user_Address5.cart_price1 = sb4.toString();
                            }
                            int i5 = i4 + 1;
                            if (User_Address.cart_qty1.equalsIgnoreCase(str)) {
                                User_Address.cart_qty1 = k[i5];
                            } else {
                                User_Address user_Address6 = User_Address.this;
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(User_Address.cart_qty1);
                                sb5.append(str2);
                                sb5.append(k[i5]);
                                user_Address6.cart_qty1 = sb5.toString();
                            }
                            int i6 = i5 + 1;
                            if (User_Address.cart_totalprice1.equalsIgnoreCase(str)) {
                                User_Address.cart_totalprice1 = k[i6];
                            } else {
                                User_Address user_Address7 = User_Address.this;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(User_Address.cart_totalprice1);
                                sb6.append(str2);
                                sb6.append(k[i6]);
                                user_Address7.cart_totalprice1 = sb6.toString();
                            }
                            int i7 = i6 + 1;
                            if (User_Address.cart_miniqty.equalsIgnoreCase(str)) {
                                User_Address.cart_miniqty = k[i7];
                            } else {
                                User_Address user_Address8 = User_Address.this;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append(User_Address.cart_miniqty);
                                sb7.append(str2);
                                sb7.append(k[i7]);
                                user_Address8.cart_miniqty = sb7.toString();
                            }
                            int i8 = i7 + 1;
                            if (User_Address.cart_unittype.equalsIgnoreCase(str)) {
                                User_Address.cart_unittype = k[i8];
                            } else {
                                User_Address user_Address9 = User_Address.this;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append(User_Address.cart_unittype);
                                sb8.append(str2);
                                sb8.append(k[i8]);
                                user_Address9.cart_unittype = sb8.toString();
                            }
                            int i9 = i8 + 1;
                            if (User_Address.cart_nettotal.equalsIgnoreCase(str)) {
                                User_Address.cart_nettotal = k[i9];
                            } else {
                                User_Address user_Address10 = User_Address.this;
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append(User_Address.cart_nettotal);
                                sb9.append(str2);
                                sb9.append(k[i9]);
                                user_Address10.cart_nettotal = sb9.toString();
                            }
                            int i10 = i9 + 1;
                            if (User_Address.cart_delicharge.equalsIgnoreCase(str)) {
                                User_Address.cart_delicharge = k[i10];
                            } else {
                                User_Address user_Address11 = User_Address.this;
                                StringBuilder sb10 = new StringBuilder();
                                sb10.append(User_Address.cart_delicharge);
                                sb10.append(str2);
                                sb10.append(k[i10]);
                                user_Address11.cart_delicharge = sb10.toString();
                            }
                            i = i10 + 1;
                        }
                        new place_cart().execute(new Void[0]);
                    } else if (User_Address.feedItems.size() <= 0) {
                        Temp.isaddressedit = 0;
                        User_Address.showuserdetails("", "", "", "", "", "", "", "");
                        Toasty.info(User_Address.getApplicationContext(), "ദയവായി ഒരു അഡ്രസ്സ് ചേര്‍ക്കുക ", 0).show();
                    } else {
                        Toasty.info(User_Address.getApplicationContext(), "ദയവായി ഒരു അഡ്രസ്സ് തിരഞ്ഞെടുക്കുക ", 1).show();
                    }
                } else {
                    Toasty.info(User_Address.getApplicationContext(), Temp.nointernet, 0).show();
                }
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (User_Address.cd.isConnectingToInternet()) {
                    User_Address.nointernet.setVisibility(View.GONE);
                    new loadaddress().execute(new String[0]);
                    return;
                }
                User_Address.nointernet.setVisibility(View.VISIBLE);
            }
        });
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
    }
    public void onResume() {
        super.onResume();
        try {
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                new loadaddress().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public void showuserdetails(String user_sn, String user_name, String user_place, String user_mobile1, String user_mobile2, String user_address, String user_landmark, String user_pincode) {
        dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.userdetails);
        dialog1.setCancelable(false);
        txtname = (TextView) dialog1.findViewById(R.id.txtname);
        txtplace = (TextView) dialog1.findViewById(R.id.txtplace);
        txtmobile1 = (TextView) dialog1.findViewById(R.id.txtmobile1);
        txtmobile2 = (TextView) dialog1.findViewById(R.id.txtmobile2);
        txtaddress = (TextView) dialog1.findViewById(R.id.txtaddress);
        txtlandmark = (TextView) dialog1.findViewById(R.id.txtlandmark);
        txtpincode = (TextView) dialog1.findViewById(R.id.txtpincode);
        name = (EditText) dialog1.findViewById(R.id.name);
        place = (EditText) dialog1.findViewById(R.id.place);
        mobile1 = (EditText) dialog1.findViewById(R.id.mobile1);
        mobile2 = (EditText) dialog1.findViewById(R.id.mobile2);
        update = (Button) dialog1.findViewById(R.id.update);
        pincode = (EditText) dialog1.findViewById(R.id.pincode);
        address = (EditText) dialog1.findViewById(R.id.address);
        landmark = (EditText) dialog1.findViewById(R.id.landmark);
        ImageView adminclose = (ImageView) dialog1.findViewById(R.id.adminclose);
        txtname.setText("പേര് ");
        txtplace.setText("സ്ഥലം ");
        txtmobile1.setText("മൊബൈല്‍ 1 ");
        txtmobile2.setText("മൊബൈല്‍ 2 ");
        txtaddress.setText("അഡ്രസ്സ് ");
        txtpincode.setText("പിന്‍കോഡ് ");
        txtlandmark.setText("സ്ഥലത്തെ അടയാളം ");
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
            txtsn = user_sn;
            name.setText(user_name);
            place.setText(user_place);
            mobile1.setText(user_mobile1);
            mobile2.setText(user_mobile2);
            pincode.setText(user_pincode);
            address.setText(user_address);
            landmark.setText(user_landmark);
        }
        adminclose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                User_Address.dialog1.dismiss();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (User_Address.cd.isConnectingToInternet()) {
                    String str = "";
                    if (User_Address.name.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(User_Address.getApplicationContext(), "enter name", 0).show();
                        User_Address.name.requestFocus();
                    } else if (User_Address.place.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(User_Address.getApplicationContext(), "enter place", 0).show();
                        User_Address.place.requestFocus();
                    } else if (User_Address.mobile1.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(User_Address.getApplicationContext(), "Enter your mobile", 0).show();
                        User_Address.mobile1.requestFocus();
                    } else if (User_Address.address.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(User_Address.getApplicationContext(), "Enter your addresss", 0).show();
                        User_Address.address.requestFocus();
                    } else if (User_Address.pincode.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(User_Address.getApplicationContext(), "Enter your pincode", 0).show();
                        User_Address.pincode.requestFocus();
                    } else {
                        new updateaddress().execute(new Void[0]);
                        User_Address.dialog1.dismiss();
                    }
                }
            }
        });
        dialog1.show();
    }

    public void uncheckall() {
        int itemsCount = list.getChildCount();
        for (int i = 0; i < itemsCount; i++) {
            ((RadioButton) list.getChildAt(i).findViewById(R.id.radio1)).setChecked(false);
        }
    }

    public void showalertsucuss(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                User_Address.exitsub();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(16908299)).setTypeface(face);
        } catch (Exception e) {
        }
    }

    public void exitsub() {
        if (db.getvouchergen().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), My_Orders.class));
            db.addvouchergen("OK");
        }
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void uploadsucess() {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(1);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog3.setContentView(R.layout.home_dlivery_ok);
            dialog3.setCancelable(false);
            TextView txtmsg = (TextView) dialog3.findViewById(R.id.txtmsg);
            txtmsg.setTypeface(face1);
            txtmsg.setText("വളരെ നന്ദി ! ഈ ഓര്‍ഡര്‍ ഞങ്ങള്‍ക്ക് ലഭിച്ചിരിക്കുന്നു.ഉറപ്പ് വരുത്തുവാനായി ചിലപ്പോള്‍ താങ്കള്‍ക്ക് അതാത് ഷോപ്പുകളില്‍ നിന്നും ഫോണ്‍ വിളിച്ചേക്കാം.");
            Button upload3 = (Button) dialog3.findViewById(R.id.upload);
            upload3.setTypeface(face1);
            upload3.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (User_Address.db.getvouchergen().equalsIgnoreCase("")) {
                        User_Address.startActivity(new Intent(User_Address.getApplicationContext(), My_Orders.class));
                        User_Address.db.addvouchergen("OK");
                    } else {
                        User_Address.finish();
                    }
                    dialog3.dismiss();
                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }
}
