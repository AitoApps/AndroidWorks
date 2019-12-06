package com.zemose.admin;

import adapter.RegionsList_ListAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.Regions_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Region_Management extends AppCompatActivity {
    public static String txt_countrycode = "";
    public static String txt_countryid = "";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Button addnew;
    ConnectionDetecter cd;
    Spinner country;
    /* access modifiers changed from: private */
    public List<Regions_FeedItem> feedItems;
    /* access modifiers changed from: private */
    public RegionsList_ListAdapter listAdapter;
    ImageView loading;
    List<String> lst_countrycode = new ArrayList();
    List<String> lst_countryid = new ArrayList();
    List<String> lst_countryname = new ArrayList();
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_region__management);
        this.country = (Spinner) findViewById(R.id.country);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        this.addnew = (Button) findViewById(R.id.addnew);
        this.feedItems = new ArrayList();
        this.listAdapter = new RegionsList_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.addnew.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Region_Management.this.country.getSelectedItemPosition() > 0) {
                    Temp_Variable.regionedit = 0;
                    Region_Management.this.startActivity(new Intent(Region_Management.this.getApplicationContext(), Add_Region.class));
                    return;
                }
                Toast.makeText(Region_Management.this.getApplicationContext(), "Please select country list", 0).show();
            }
        });
        this.pd.setMessage("Loading Countries...");
        this.pd.setCancelable(false);
        this.pd.show();
        getcountrylist();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.lst_countryid.size() > 0) {
            getregionlist();
        }
    }

    public void removeitem(int position) {
        try {
            this.feedItems.remove(position);
            this.listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void getregionlist() {
        try {
            this.nodata.setVisibility(8);
            this.recylerview.setVisibility(8);
            this.loading.setVisibility(0);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("countryId", txt_countryid);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getregionlist_admin");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Region_Management.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Region_Management.this.nodata.setVisibility(0);
                            Region_Management.this.loading.setVisibility(8);
                            Region_Management.this.recylerview.setVisibility(8);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Region_Management.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Region_Management.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Region_Management.this.nodata.setVisibility(0);
                                    Region_Management.this.loading.setVisibility(8);
                                    Region_Management.this.recylerview.setVisibility(8);
                                    Toasty.info(Region_Management.this.getApplicationContext(), "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Region_Management.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Regions_FeedItem h = new Regions_FeedItem();
                                        h.setRegionid(jsonobject.getString("regionId"));
                                        h.setRegionname(jsonobject.getString("regionName"));
                                        Region_Management.this.feedItems.add(h);
                                    }
                                    Region_Management.this.loading.setVisibility(8);
                                    Region_Management.this.recylerview.setVisibility(0);
                                    Region_Management.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Region_Management.this.nodata.setVisibility(0);
                                    Region_Management.this.loading.setVisibility(8);
                                    Region_Management.this.recylerview.setVisibility(8);
                                    Toasty.info(Region_Management.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Region_Management.this.nodata.setVisibility(0);
                                Region_Management.this.loading.setVisibility(8);
                                Region_Management.this.recylerview.setVisibility(8);
                                Toasty.info(Region_Management.this.getApplicationContext(), "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void getcountrylist() {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("contact", "");
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getcountrylist");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Region_Management.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Region_Management.this.pd.dismiss();
                            Toasty.info(Region_Management.this.getApplicationContext(), "Please try later", 0).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Region_Management.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Region_Management.this.pd.dismiss();
                                String result = response.body().string();
                                Log.w("Resss", result);
                                JSONObject jo = new JSONObject(result);
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(Region_Management.this.getApplicationContext(), "Please check contact number", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    Region_Management.this.lst_countryid.clear();
                                    Region_Management.this.lst_countryname.clear();
                                    Region_Management.this.lst_countryid.add("0");
                                    Region_Management.this.lst_countrycode.add("0");
                                    Region_Management.this.lst_countryname.add("Select Country");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        Region_Management.this.lst_countryid.add(jsonobject.getString("countryId"));
                                        Region_Management.this.lst_countryname.add(jsonobject.getString("countryName"));
                                        Region_Management.this.lst_countrycode.add(jsonobject.getString("countryCode"));
                                    }
                                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Region_Management.this, 17367048, Region_Management.this.lst_countryname) {
                                        public View getView(int position, View convertView, ViewGroup parent) {
                                            View v = super.getView(position, convertView, parent);
                                            ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                                            ((TextView) v).setTextSize(16.0f);
                                            return v;
                                        }

                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View v = super.getDropDownView(position, convertView, parent);
                                            ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                                            ((TextView) v).setTextSize(16.0f);
                                            return v;
                                        }
                                    };
                                    dataAdapter2.setDropDownViewResource(17367049);
                                    Region_Management.this.country.setAdapter(dataAdapter2);
                                    Region_Management.this.country.setOnItemSelectedListener(new OnItemSelectedListener() {
                                        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                                            Region_Management.txt_countryid = (String) Region_Management.this.lst_countryid.get(arg2);
                                            Region_Management.txt_countrycode = (String) Region_Management.this.lst_countrycode.get(arg2);
                                            if (arg2 > 0) {
                                                Region_Management.this.getregionlist();
                                            }
                                        }

                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                } else {
                                    Toasty.info(Region_Management.this.getApplicationContext(), "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
