package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.payumoney.core.PayUmoneyConstants;
import com.zemose.admin.Add_Region;
import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;
import com.zemose.admin.Region_Management;
import com.zemose.admin.Regional_Heads;
import com.zemose.admin.Temp_Variable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import data.Regions_FeedItem;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegionsList_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;

    public AppCompatActivity activity;
    public ConnectionDetecter cd;

    public Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;

    public List<Regions_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtregionid = "";
    public String userid = "";

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView delete;
        ImageView edit;
        Button heads;
        TextView regionname;

        public viewHolder(View itemView) {
            super(itemView);
            this.regionname = (TextView) itemView.findViewById(R.id.regionname);
            this.heads = (Button) itemView.findViewById(R.id.heads);
            this.delete = (ImageView) itemView.findViewById(R.id.delete);
            this.edit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public RegionsList_ListAdapter(AppCompatActivity activity2, List<Regions_FeedItem> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.cd = new ConnectionDetecter(this.context);
        this.db = new ChatDB(this.context);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "font/proxibold.otf");
        this.face1 = Typeface.createFromAsset(this.context.getAssets(), "font/proximanormal.ttf");
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.custom_regions, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(this.context).inflate(R.layout.footerview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(this.context).inflate(R.layout.fullloaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if ((position != this.feedItems.size() || this.feedItems.size() <= 10) && position >= this.feedItems.size()) {
            return 2;
        }
        return 0;
    }

    public int getItemCount() {
        return this.feedItems.size() + 1;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.regionname.setText(((Regions_FeedItem) this.feedItems.get(position)).getRegionname());
                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Regions_FeedItem regions_FeedItem = (Regions_FeedItem) RegionsList_ListAdapter.this.feedItems.get(position);
                            Regions_FeedItem item = (Regions_FeedItem) RegionsList_ListAdapter.this.feedItems.get(position);
                            RegionsList_ListAdapter.this.txtregionid = item.getRegionid();
                            RegionsList_ListAdapter.this.pos = position;
                            RegionsList_ListAdapter.this.showalert_delete("Are you sure want to delete this region ?");
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.edit.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = RegionsList_ListAdapter.this.feedItems.get(position);
                        Regions_FeedItem item = (Regions_FeedItem) RegionsList_ListAdapter.this.feedItems.get(position);
                        Temp_Variable.regionedit = 1;
                        Temp_Variable.regionid = item.getRegionid();
                        Temp_Variable.regionname = item.getRegionname();
                        Intent i = new Intent(RegionsList_ListAdapter.this.context, Add_Region.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        RegionsList_ListAdapter.this.context.startActivity(i);
                    }
                });
                viewHolder2.heads.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = RegionsList_ListAdapter.this.feedItems.get(position);
                        Regions_FeedItem item = (Regions_FeedItem) RegionsList_ListAdapter.this.feedItems.get(position);
                        Temp_Variable.regionname = item.getRegionname();
                        Temp_Variable.regionid = item.getRegionid();
                        Intent i = new Intent(RegionsList_ListAdapter.this.context, Regional_Heads.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        RegionsList_ListAdapter.this.context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(this.activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (RegionsList_ListAdapter.this.cd.isConnectingToInternet()) {
                    RegionsList_ListAdapter.this.deleteregion();
                } else {
                    Toasty.info(RegionsList_ListAdapter.this.context, Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
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

    public void deleteregion() {
        try {
            this.pd.setMessage("Please wait..");
            this.pd.setCancelable(false);
            this.pd.show();
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("regionid", this.txtregionid);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/deleteregion");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    RegionsList_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            RegionsList_ListAdapter.this.pd.dismiss();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    RegionsList_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                RegionsList_ListAdapter.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(RegionsList_ListAdapter.this.context, "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(RegionsList_ListAdapter.this.context, "deleted", Toast.LENGTH_SHORT).show();
                                    ((Region_Management) RegionsList_ListAdapter.this.activity).removeitem(RegionsList_ListAdapter.this.pos);
                                } else {
                                    Toasty.info(RegionsList_ListAdapter.this.context, "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toasty.info(RegionsList_ListAdapter.this.context, "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
