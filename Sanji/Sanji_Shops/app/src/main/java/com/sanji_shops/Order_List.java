package com.sanji_shops;

import adapter.OrderList_ListAdapter;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
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
    final DatabaseHandler db = new DatabaseHandler(this);
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
    public int position = 0;
    TextView text;
    public String txtdeliverycharge = "";
    public String txtestitime = "";
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    TextView username;

    public class cancellorder extends AsyncTask<String, Void, String> {
        public cancellorder() {
        }
        public void onPreExecute() {
            Order_List.pd.setMessage("Please wait...");
            Order_List.pd.setCancelable(false);
            Order_List.pd.show();
            Order_List.timerDelayRemoveDialog(50000, Order_List.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("cancellorder_shopadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.ordergroupid);
                sb3.append(":%");
                sb3.append(Order_List.udb.get_shopid());
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
            if (Order_List.pd != null || Order_List.pd.isShowing()) {
                Order_List.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Order_List.getApplicationContext(), "Order Cancelled", Toast.LENGTH_SHORT).show();
                    Order_List.finish();
                    return;
                }
                Toasty.info(Order_List.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class confirmorder extends AsyncTask<String, Void, String> {
        public confirmorder() {
        }
        public void onPreExecute() {
            Order_List.pd.setMessage("Please wait...");
            Order_List.pd.setCancelable(false);
            Order_List.pd.show();
            Order_List.timerDelayRemoveDialog(50000, Order_List.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("confirmorder_shopadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.ordergroupid);
                sb3.append(":%");
                sb3.append(Order_List.txtestitime);
                sb3.append(":%");
                sb3.append(Order_List.txtdeliverycharge);
                sb3.append(":%");
                sb3.append(Order_List.udb.get_shopid());
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
            if (Order_List.pd != null || Order_List.pd.isShowing()) {
                Order_List.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Order_List.getApplicationContext(), "Order Confirmed", Toast.LENGTH_SHORT).show();
                    Order_List.finish();
                    return;
                }
                Toasty.info(Order_List.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Order_List.call1.setVisibility(View.GONE);
            Order_List.call2.setVisibility(View.GONE);
            Order_List.address.setVisibility(View.GONE);
            Order_List.username.setVisibility(View.GONE);
            Order_List.cancell.setVisibility(View.GONE);
            Order_List.confirm.setVisibility(View.GONE);
            Order_List.nointernet.setVisibility(View.GONE);
            Order_List.nodata.setVisibility(View.GONE);
            Order_List.list.setVisibility(View.GONE);
            Order_List.heart.setVisibility(View.VISIBLE);
            Order_List.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getorderlist_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.ordergroupid);
                sb3.append(":%");
                sb3.append(Order_List.udb.get_shopid());
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
            int i;
            String str = result;
            try {
                if (str.contains("%:ok#####")) {
                    Order_List.feedItems.clear();
                    String[] got1 = str.split("#####");
                    String[] got = got1[0].split("%:");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i2 = 1; i2 <= k; i2++) {
                        Order_List_FeedItem item = new Order_List_FeedItem();
                        int m2 = m + 1;
                        item.setorderid(got[m2]);
                        int m3 = m2 + 1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m3]));
                            item.setorderdate(Order_List.getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            Exception exc = e;
                            item.setorderdate(got[m3]);
                        }
                        int m4 = m3 + 1;
                        item.setproductid(got[m4]);
                        int m5 = m4 + 1;
                        item.setproductname(got[m5]);
                        int m6 = m5 + 1;
                        item.setproductprice(got[m6]);
                        int m7 = m6 + 1;
                        item.setproductqty(got[m7]);
                        int m8 = m7 + 1;
                        item.setproducttotal(got[m8]);
                        m = m8 + 1;
                        item.setimgsig(got[m]);
                        Order_List.feedItems.add(item);
                    }
                    String[] got2 = got1[1].split("%:");
                    if (got2[3].equalsIgnoreCase("0")) {
                        Order_List.call2.setVisibility(View.GONE);
                        i = 0;
                    } else {
                        i = 0;
                        Order_List.call2.setVisibility(View.VISIBLE);
                    }
                    Order_List.address.setVisibility(i);
                    Order_List.heart.setVisibility(View.GONE);
                    Order_List.list.setVisibility(View.VISIBLE);
                    Order_List.listAdapter.notifyDataSetChanged();
                    Order_List.call1.setVisibility(View.VISIBLE);
                    Order_List.username.setVisibility(View.VISIBLE);
                    Order_List.cancell.setVisibility(View.VISIBLE);
                    Order_List.confirm.setVisibility(View.VISIBLE);
                    try {
                        Order_List.username.setText(got2[0]);
                        Order_List.username.setTypeface(Order_List.face1);
                        Order_List.db.add_address(got2[0], got2[1], got2[2], got2[3], got2[4], got2[5], got2[6]);
                    } catch (Exception e2) {
                    }
                } else {
                    Order_List.nodata.setVisibility(View.VISIBLE);
                    Order_List.heart.setVisibility(View.GONE);
                }
            } catch (Exception e3) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_order__list);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        call1 = (ImageView) findViewById(R.id.call1);
        call2 = (ImageView) findViewById(R.id.call2);
        address = (ImageView) findViewById(R.id.address);
        cancell = (ImageView) findViewById(R.id.cancell);
        confirm = (ImageView) findViewById(R.id.confirm);
        username = (TextView) findViewById(R.id.username);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Order_List.onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        face1 = Typeface.createFromAsset(getAssets(), "font/heading.otf");
        String string = getResources().getString(R.string.Rs);
        TextView textView = text;
        StringBuilder sb = new StringBuilder();
        sb.append(Temp.grpid);
        sb.append(Temp.ordergroupid);
        textView.setText(sb.toString());
        text.setTypeface(face1);
        text.setSelected(true);
        address.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List.showaddress();
            }
        });
        call1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List.call(Order_List.db.get_adressmobile1());
            }
        });
        call2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List.call(Order_List.db.get_adressmobile2());
            }
        });
        feedItems = new ArrayList();
        listAdapter = new OrderList_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Order_List.cd.isConnectingToInternet()) {
                    Order_List.nointernet.setVisibility(View.GONE);
                    Order_List.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Order_List.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Order_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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
                    Order_List.showalert_cancellorder("Are you sure want to cancell this order ?");
                } catch (Exception e) {
                }
            }
        });
        confirm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Order_List.cd.isConnectingToInternet()) {
                    Order_List.showestimated();
                } else {
                    Toasty.info(Order_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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
        String str = "h:mm a";
        String str2 = "MMM d h:mm a";
        if (now.get(5) == smsTime.get(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateFormat.format("h:mm a", smsTime));
            sb.append("");
            return sb.toString();
        } else if (now.get(5) - smsTime.get(5) == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Yesterday ");
            sb2.append(DateFormat.format("h:mm a", smsTime));
            return sb2.toString();
        } else if (now.get(1) == smsTime.get(1)) {
            return DateFormat.format("MMM d h:mm a", smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }
    }

    public void changeitem(int position2, String t_orderid, String t_orderdate, String t_productid, String t_productname, String t_productprice, String t_productqty, String t_producttotal, String t_imgsig) {
        try {
            feedItems.remove(position2);
            listAdapter.notifyDataSetChanged();
            Order_List_FeedItem item = new Order_List_FeedItem();
            item.setorderid(t_orderid);
            item.setorderdate(t_orderdate);
            item.setproductid(t_productid);
            item.setproductname(t_productname);
            item.setproductprice(t_productprice);
            item.setproductqty(t_productqty);
            item.setproducttotal(t_producttotal);
            item.setimgsig(t_imgsig);
            feedItems.add(position2, item);
        } catch (Exception e) {
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
        landmark.setText(db.get_landmark());
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
        txtpincode.setTypeface(face1);
        mobile1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List.call(mobile1.getText().toString());
            }
        });
        mobile2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List.call(mobile2.getText().toString());
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
                    if (ContextCompat.checkSelfPermission(Order_List.this, "android.permission.CALL_PHONE") != 0) {
                        ActivityCompat.requestPermissions(Order_List.this, new String[]{"android.permission.CALL_PHONE"}, 1);
                    } else {
                        Intent callIntent = new Intent("android.intent.action.CALL");
                        StringBuilder sb = new StringBuilder();
                        sb.append("tel:");
                        sb.append(mob);
                        callIntent.setData(Uri.parse(sb.toString()));
                        Order_List.startActivity(callIntent);
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

    public void showalert_cancellorder(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Order_List.cd.isConnectingToInternet()) {
                    new cancellorder().execute(new String[0]);
                } else {
                    Toasty.info(Order_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void showestimated() {
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setContentView(R.layout.custom_estimatedtime);
        dialog2.setCancelable(true);
        final EditText estimaeddate = (EditText) dialog2.findViewById(R.id.estimaeddate);
        final EditText deliverycharge = (EditText) dialog2.findViewById(R.id.deliverycharge);
        deliverycharge.setText(Temp.deliverycharge);
        estimaeddate.requestFocus();
        Button update = (Button) dialog2.findViewById(R.id.update);
        estimaeddate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Calendar currentDate = Calendar.getInstance();
                final Calendar date = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Order_List.this, new OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(Order_List.this, new OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(11, hourOfDay);
                                date.set(12, minute);
                                estimaeddate.setText(new SimpleDateFormat("dd-MM-yyyy h:mm a").format(date.getTime()));
                            }
                        }, currentDate.get(11), currentDate.get(12), false);
                        timePickerDialog.show();
                    }
                }, currentDate.get(1), currentDate.get(2), currentDate.get(5));
                datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (estimaeddate.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Order_List.getApplicationContext(), "ഡെലിവറി ചെയ്യുന്ന ഏകദേശം സമയം നല്‍കുക ", Toast.LENGTH_SHORT).show();
                } else if (deliverycharge.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Order_List.getApplicationContext(), "ഡെലിവറി ചാര്‍ജ്ജ് നല്‍കുക ", Toast.LENGTH_SHORT).show();
                } else {
                    Order_List.txtestitime = estimaeddate.getText().toString();
                    Order_List.txtdeliverycharge = deliverycharge.getText().toString();
                    if (Order_List.cd.isConnectingToInternet()) {
                        Order_List.showalert_confirm("Are you sure want to confirm ?");
                        Order_List.dialog2.dismiss();
                        return;
                    }
                    Toasty.info(Order_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog2.show();
    }
}
