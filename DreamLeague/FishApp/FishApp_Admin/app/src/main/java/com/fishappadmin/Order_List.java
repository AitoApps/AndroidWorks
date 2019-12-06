package com.fishappadmin;

import adapter.OrderList_ListAdapter;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import data.Order_List_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Order_List extends AppCompatActivity {
    ImageView address;
    ImageView back;
    ImageView call1;
    ImageView call2;
    ImageView cancell;
    ConnectionDetecter cd;
    ImageView confirm;
    final DatabaseHandler db=new DatabaseHandler(this);
    Dialog dialog2;
    Typeface face1;

    public List<Order_List_FeedItem> feedItems;
    ImageView heart;
    public int limit = 0;
    ListView list;

    public OrderList_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public int position=0;
    TextView text;
    TextView total;
    public String txtdelicharge="";
    public String txtdelitype="";
    public String txtestitime="";
    public String txtremarks="";
    TextView txttotal;
    TextView username;
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_order__list);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        txttotal = (TextView) findViewById(R.id.txttotal);
        total = (TextView) findViewById(R.id.total);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        call1 = (ImageView) findViewById(R.id.call1);
        call2 = (ImageView) findViewById(R.id.call2);
        address = (ImageView) findViewById(R.id.address);
        cancell = (ImageView) findViewById(R.id.cancell);
        confirm = (ImageView) findViewById(R.id.confirm);
        username = (TextView) findViewById(R.id.username);
        String rupee = getResources().getString(R.string.Rs);
        TextView textView = total;
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        sb.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(Temp.totalcharge))}));
        textView.setText(sb.toString());
        if (Temp.orderlisttype == 1) {
            cancell.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
        } else if (Temp.orderlisttype == 2) {
            cancell.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
        } else if (Temp.orderlisttype == 3) {
            cancell.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
        }
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        face1 = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        TextView textView2 = text;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Temp.grpid);
        sb2.append(Temp.ordergroupid);
        textView2.setText(sb2.toString());
        text.setTypeface(face1);
        text.setSelected(true);
        address.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showaddress();
            }
        });
        call1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                call(db.get_adressmobile1());
            }
        });
        call2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                call(db.get_adressmobile2());
            }
        });
        feedItems = new ArrayList();
        listAdapter = new OrderList_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        cancell.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    String str = "Are you sure want to cancell this order ?";
                    if (Temp.orderlisttype == 1) {
                        showalert_cancellorder(str);
                    } else if (Temp.orderlisttype == 2) {
                        showalert_cancellorder(str);
                    }
                } catch (Exception e) {
                }
            }
        });
        confirm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!cd.isConnectingToInternet()) {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (Temp.orderlisttype == 1) {
                    showestimated();
                } else if (Temp.orderlisttype == 2) {
                    showalert_outtodelivery("Are you sure want to out this delivery ?");
                } else if (Temp.orderlisttype == 3) {
                    showdeli_remarks();
                }
            }
        });
    }

    public void showalert_confirm(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new confirmorder().execute(new String[0]);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void refresh() {
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position2) {
        try {
            feedItems.remove(position2);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        final String timeFormatString = "h:mm a";
        final String dateTimeFormatString = "MMM d h:mm a";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return DateFormat.format(timeFormatString, smsTime)+"";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }

    }

    public void showaddress() {
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.addressview);
        dialog1.setCancelable(true);
        TextView name = (TextView) dialog1.findViewById(R.id.name);
        TextView place = (TextView) dialog1.findViewById(R.id.place);
        final TextView mobile1 = (TextView) dialog1.findViewById(R.id.mobile1);
        final TextView mobile2 = (TextView) dialog1.findViewById(R.id.mobile2);
        TextView address2 = (TextView) dialog1.findViewById(R.id.address);
        TextView landmark = (TextView) dialog1.findViewById(R.id.landmark);
        TextView pincode = (TextView) dialog1.findViewById(R.id.pincode);
        TextView txtname = (TextView) dialog1.findViewById(R.id.txtname);
        TextView txtplace = (TextView) dialog1.findViewById(R.id.txtplace);
        TextView txtmobile1 = (TextView) dialog1.findViewById(R.id.txtmobile1);
        TextView txtmobile2 = (TextView) dialog1.findViewById(R.id.txtmobile2);
        TextView txtaddress = (TextView) dialog1.findViewById(R.id.txtaddress);
        TextView txtlandmark = (TextView) dialog1.findViewById(R.id.txtlandmark);
        TextView txtpincode = (TextView) dialog1.findViewById(R.id.txtpincode);
        Dialog dialog12 = dialog1;
        name.setText(db.get_adressname());
        place.setText(db.get_adressplace());
        mobile1.setText(db.get_adressmobile1());
        mobile2.setText(db.get_adressmobile2());
        address2.setText(db.get_address());
        TextView txtpincode2 = txtpincode;
        if (db.get_landmark().equalsIgnoreCase("NA")) {
            landmark.setText("");
        } else {
            landmark.setText(db.get_landmark());
        }
        pincode.setText(db.get_pincode());
        name.setTypeface(face1);
        place.setTypeface(face1);
        mobile1.setTypeface(face1);
        mobile2.setTypeface(face1);
        address2.setTypeface(face1);
        landmark.setTypeface(face1);
        pincode.setTypeface(face1);
        txtname.setTypeface(face1);
        txtplace.setTypeface(face1);
        txtmobile1.setTypeface(face1);
        txtmobile2.setTypeface(face1);
        txtaddress.setTypeface(face1);
        txtlandmark.setTypeface(face1);
        txtpincode2.setTypeface(face1);
        mobile1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                call(mobile1.getText().toString());
            }
        });
        mobile2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                call(mobile2.getText().toString());
            }
        });
        dialog12.show();
    }

    public void call(final String mob) {
        try {
            Builder builder = new Builder(this);
            StringBuilder sb = new StringBuilder();
            sb.append("Are you sure want to call to ?");
            sb.append(mob);
            builder.setMessage(sb.toString()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    if (ContextCompat.checkSelfPermission(Order_List.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        ActivityCompat.requestPermissions(Order_List.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent("android.intent.action.CALL");
                        callIntent.setData(Uri.parse("tel:"+mob));
                        startActivity(callIntent);
                    }
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception e) {
        }
    }

    public void showalert_outtodelivery(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new outtodelivery().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalert_cancellorder(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new cancellorder().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showdeli_remarks() {
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setContentView(R.layout.custom_remarks);
        dialog2.setCancelable(true);
        final EditText remarks = (EditText) dialog2.findViewById(R.id.remarks);
        Button delireturn = (Button) dialog2.findViewById(R.id.delireturn);
        ((Button) dialog2.findViewById(R.id.delivery)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    txtremarks = remarks.getText().toString();
                    txtdelitype = "1";
                    showalert_deliveryconfirm("Delivered ?");
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        delireturn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    txtremarks = remarks.getText().toString();
                    txtdelitype = "2";
                    showalert_deliveryconfirm("Is returned ?");
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        dialog2.show();
    }

    public void showestimated() {
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setContentView(R.layout.custom_estimatedtime);
        dialog2.setCancelable(true);
        final EditText deliverycharge = (EditText) dialog2.findViewById(R.id.deliverycharge);
        ((Button) dialog2.findViewById(R.id.update)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (deliverycharge.getText().toString().equalsIgnoreCase("")) {
                    txtdelicharge = "0";
                } else {
                    txtdelicharge = deliverycharge.getText().toString();
                }
                if (cd.isConnectingToInternet()) {
                    showalert_confirm("Are you sure want to confirm ?");
                    dialog2.dismiss();
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        dialog2.show();
    }

    public void download(String url, String filename) {
        Request request = new Request(Uri.parse(url));
        request.setDescription("Downloading...");
        request.setTitle(filename);
        if (VERSION.SDK_INT >= 11) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
    }

    public void showalert_deliveryconfirm(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new deliconfirm().execute(new String[0]);
                } else {
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public class cancellorder extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {

            try {
                String link= Temp.weblink +"cancellorder_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.ordergroupid, "UTF-8");
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), "Order Cancelled", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class confirmorder extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {

            try {
                String link= Temp.weblink +"confirmorder_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.ordergroupid+":%"+txtdelicharge, "UTF-8");
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), "Order Confirmed", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class deliconfirm extends AsyncTask<String, Void, String> {
        public deliconfirm() {
        }

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {

            try {
                String link= Temp.weblink +"deliconfirm_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.ordergroupid+":%"+txtremarks+":%"+txtdelitype, "UTF-8");
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    if (txtdelitype.equalsIgnoreCase("1")) {
                        Toasty.info(getApplicationContext(), "Order Delivered", Toast.LENGTH_SHORT).show();
                    } else if (txtdelitype.equalsIgnoreCase("2")) {
                        Toasty.info(getApplicationContext(), "Order Returned", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            call1.setVisibility(View.GONE);
            call2.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            cancell.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            nointernet.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }


        public String doInBackground(String... arg0) {

            try {
                String link= Temp.weblink +"getorderlist_adminbygrpid.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.ordergroupid+":%"+Temp.orderlisttype, "UTF-8");
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
                if (result.contains("%:ok#####")) {
                    feedItems.clear();
                    String[] got1 = result.split("#####");
                    String[] got = got1[0].split("%:");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i2 = 1; i2 <= k; i2++) {
                        Order_List_FeedItem item = new Order_List_FeedItem();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setUserid(got[m]);
                        m=m+1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m]));
                            SimpleDateFormat simpleDateFormat = sdf;
                            item.setRegdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            item.setRegdate(got[m]);
                        }
                        m=m+1;
                        item.setFishid(got[m]);
                        m=m+1;
                        item.setQty(got[m]);
                        m=m+1;
                        item.setPrice(got[m]);
                        m=m+1;
                        item.setFishname(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        feedItems.add(item);
                    }
                    String[] got2 = got1[1].split("%:");
                    if (got2[3].equalsIgnoreCase("0")) {
                        call2.setVisibility(View.GONE);
                    } else {
                        call2.setVisibility(View.VISIBLE);
                    }
                    address.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    call1.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    cancell.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    try {
                        username.setText(got2[0]);
                        username.setTypeface(face1);
                        db.add_address(got2[0], got2[1], got2[2], got2[3], got2[4], got2[5], got2[6]);
                    } catch (Exception e2) {
                    }
                } else {
                    nodata.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception a) {
                //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class outtodelivery extends AsyncTask<String, Void, String> {
        public outtodelivery() {
        }


        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {

            try {
                String link= Temp.weblink +"outtodelivery_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.ordergroupid, "UTF-8");
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), "Out to delivery", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
