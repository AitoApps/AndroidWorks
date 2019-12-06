package com.zemose.admin;

import adapter.RegionsHeadList_ListAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payumoney.core.PayUmoneyConstants;
import data.RegionsHeads_FeedItem;
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

public class Regional_Heads extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Button addnew;
    ImageView back;
    ConnectionDetecter cd;

    public List<RegionsHeads_FeedItem> feedItems;

    public RegionsHeadList_ListAdapter listAdapter;
    ImageView loading;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_regional__heads);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.pd = new ProgressDialog(this);
        this.addnew = (Button) findViewById(R.id.addnew);
        this.back = (ImageView) findViewById(R.id.back);
        this.text = (TextView) findViewById(R.id.text);
        this.pd.setMessage("Loading Countries...");
        this.pd.setCancelable(false);
        this.pd.show();
        this.text.setText(Temp_Variable.regionname);
        this.feedItems = new ArrayList();
        this.listAdapter = new RegionsHeadList_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(this.loading);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Regional_Heads.this.onBackPressed();
            }
        });
        this.addnew.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp_Variable.regionhededit = 0;
                Regional_Heads.this.startActivity(new Intent(Regional_Heads.this.getApplicationContext(), Add_Region_Head.class));
            }
        });
    }
    public void onResume() {
        super.onResume();
        getregionlist();
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
            this.nodata.setVisibility(View.GONE);
            this.recylerview.setVisibility(View.GONE);
            this.loading.setVisibility(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("regionId", Temp_Variable.regionid);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/getregionheadlist");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Regional_Heads.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Regional_Heads.this.nodata.setVisibility(View.VISIBLE);
                            Regional_Heads.this.loading.setVisibility(View.GONE);
                            Regional_Heads.this.recylerview.setVisibility(View.GONE);
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    Regional_Heads.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Regional_Heads.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Regional_Heads.this.nodata.setVisibility(View.VISIBLE);
                                    Regional_Heads.this.loading.setVisibility(View.GONE);
                                    Regional_Heads.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Regional_Heads.this.getApplicationContext(), "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("got")) {
                                    Regional_Heads.this.feedItems.clear();
                                    JSONArray jsonarray = jo.getJSONArray("data");
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        RegionsHeads_FeedItem h = new RegionsHeads_FeedItem();
                                        h.setRegionheadId(jsonobject.getString("regionheadId"));
                                        h.setHeadname(jsonobject.getString("headname"));
                                        h.setUsername(jsonobject.getString(PayUmoneyConstants.USER_NAME));
                                        h.setPassword(jsonobject.getString(PayUmoneyConstants.PASSWORD));
                                        Regional_Heads.this.feedItems.add(h);
                                    }
                                    Regional_Heads.this.loading.setVisibility(View.GONE);
                                    Regional_Heads.this.recylerview.setVisibility(View.VISIBLE);
                                    Regional_Heads.this.listAdapter.notifyDataSetChanged();
                                } else {
                                    Regional_Heads.this.nodata.setVisibility(View.VISIBLE);
                                    Regional_Heads.this.loading.setVisibility(View.GONE);
                                    Regional_Heads.this.recylerview.setVisibility(View.GONE);
                                    Toasty.info(Regional_Heads.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Regional_Heads.this.nodata.setVisibility(View.VISIBLE);
                                Regional_Heads.this.loading.setVisibility(View.GONE);
                                Regional_Heads.this.recylerview.setVisibility(View.GONE);
                                Toasty.info(Regional_Heads.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
