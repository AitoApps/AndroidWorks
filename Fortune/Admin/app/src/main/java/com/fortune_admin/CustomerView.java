package com.fortune_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.LuckyDrawPrizes_ListAdapter;
import adapter.PaymentHistory_ListAdapter;
import data.LuckyDrawBatches_FeedItem;
import data.LuckyDrawPrizes_FeedItem;
import data.PaymentHistory_FeedItem;

public class CustomerView extends AppCompatActivity {

    ImageView back,call;
    TextView text;
    TextView txtbasicinfo,name,housename,streetlandmarkcity,districstatecountry,postpincode,txtcontactinfo,mobile;
    TextView txtbatchinfo,batchname,amount,txtaccount,fromto,totalpaid,paymenthistory;
    RelativeLayout lytcontactinfo;
    ListView accounthistory;
    ConnectionDetecter cd;
    ProgressDialog pd;
    RelativeLayout loading,contents;
    ImageView nodata;
    ImageView nointernet,heart;
    public List<PaymentHistory_FeedItem> feedItems;
    public PaymentHistory_ListAdapter listAdapter;
    public String callmobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        nodata=findViewById(R.id.nodata);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        loading=findViewById(R.id.loading);
        contents=findViewById(R.id.contents);
        pd=new ProgressDialog(this);
        cd=new ConnectionDetecter(this);
        back=findViewById(R.id.back);
        call=findViewById(R.id.call);
        text=findViewById(R.id.text);
        txtbasicinfo=findViewById(R.id.txtbasicinfo);
        name=findViewById(R.id.name);
        housename=findViewById(R.id.housename);
        streetlandmarkcity=findViewById(R.id.streetlandmarkcity);
        districstatecountry=findViewById(R.id.districstatecountry);
        postpincode=findViewById(R.id.postpincode);
        txtcontactinfo=findViewById(R.id.txtcontactinfo);
        mobile=findViewById(R.id.mobile);
        txtbatchinfo=findViewById(R.id.txtbatchinfo);
        batchname=findViewById(R.id.batchname);
        amount=findViewById(R.id.amount);
        txtaccount=findViewById(R.id.txtaccount);
        fromto=findViewById(R.id.fromto);
        totalpaid=findViewById(R.id.totalpaid);
        paymenthistory=findViewById(R.id.paymenthistory);
        lytcontactinfo=findViewById(R.id.lytcontactinfo);
        accounthistory=findViewById(R.id.accounthistory);
        Glide.with(this).load(R.drawable.loading).into(heart);
        feedItems = new ArrayList();
        listAdapter = new PaymentHistory_ListAdapter(this, feedItems);
        accounthistory.setAdapter(listAdapter);

        lytcontactinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(mobile.getText().toString());
            }
        });
        new loadstatus().execute();
    }


    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nointernet.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
            contents.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getcustomerdetailsfull_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.lcustomerid+"", "UTF-8");
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
                if (result.contains(":%ok")) {
                    heart.setVisibility(View.GONE);
                    String[] k=result.split(":%");

                    String data_batchname=k[0];
                    String data_price=k[1];
                    String data_name=k[2];
                    String data_mobile=k[3];
                    String data_housename=k[4];
                    String data_street=k[5];
                    String data_landmark=k[6];
                    String data_city=k[7];
                    String data_post=k[8];
                    String data_distric=k[9];
                    String data_state=k[10];
                    String data_country=k[11];
                    String data_pincode=k[12];
                    String data_startweek=k[13];
                    String data_endweek=k[14];



                    String data_paymenthostory=k[15];
                    int tmpamount=0;
                    int tmptotalpaid=0;
                    int tmptotalbalance=0;
                    if(!data_paymenthostory.equalsIgnoreCase(""))
                    {
                        String[] p=data_paymenthostory.split(":;");
                        feedItems.clear();
                        for(int i=0;i<p.length;i++)
                        {
                            PaymentHistory_FeedItem item=new PaymentHistory_FeedItem();
                            item.setPaiddate(p[i]);
                            i=i+1;
                            item.setCustid(p[i]);
                            i=i+1;
                            item.setWeekid(p[i]);
                            i=i+1;
                            item.setWeekdate(p[i]);
                            i=i+1;
                            item.setAmount(p[i]);
                            tmpamount=Integer.parseInt(p[i]);
                            i=i+1;
                            item.setPaidvia(p[i]);
                            i=i+1;
                            item.setRemarks(p[i]);
                            i=i+1;
                            item.setReference(p[i]);
                            i=i+1;
                            item.setRemarks(p[i]);
                            i=i+1;
                            item.setStatus(p[i]);

                            if(p[i].equalsIgnoreCase("1"))
                            {
                                tmptotalpaid=tmptotalpaid+tmpamount;
                            }
                            else if(p[i].equalsIgnoreCase("0"))
                            {
                                tmptotalbalance=tmptotalbalance+tmpamount;
                            }

                            feedItems.add(item);
                        }

                        listAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(accounthistory);
                    }
                    String rupee = getResources().getString(R.string.Rs);

                    name.setText(data_name);
                    housename.setText(data_housename);
                    streetlandmarkcity.setText(data_street+","+data_landmark+","+data_city);
                    districstatecountry.setText(data_distric+","+data_state+","+data_country);
                    postpincode.setText(data_post+","+data_pincode);
                    mobile.setText(data_mobile);
                    batchname.setText(data_batchname);
                    amount.setText(rupee+data_price);
                    fromto.setText(data_startweek+" -- "+data_endweek);
                    totalpaid.setText("Paid : "+rupee+tmptotalpaid+" | Balance : "+rupee+tmptotalbalance);

                    loading.setVisibility(View.GONE);
                    contents.setVisibility(View.GONE);

                }
                else
                {
                    nodata.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    contents.setVisibility(View.GONE);
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e) {
            }
        }
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void call(String mobile)
    {
        if (ContextCompat.checkSelfPermission(CustomerView.this, android.Manifest.permission.CALL_PHONE) != 0) {
            callmobile=mobile;
            ActivityCompat.requestPermissions(CustomerView.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+mobile));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(CustomerView.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        ActivityCompat.requestPermissions(CustomerView.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+callmobile));
                        startActivity(callIntent);
                    }

                } else {

                }
                return;
            }
        }
    }
}
