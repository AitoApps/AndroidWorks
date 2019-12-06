package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.payumoney.core.PayUmoneyConstants;
import com.zemose.admin.Add_Region_Head;
import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;
import com.zemose.admin.Regional_Heads;
import com.zemose.admin.Temp_Variable;
import data.RegionsHeads_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class RegionsHeadList_ListAdapter extends Adapter<ViewHolder> {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    /* access modifiers changed from: private */
    public Activity activity;
    public ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    /* access modifiers changed from: private */
    public List<RegionsHeads_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtregionid = "";
    public String userid = "";

    public class viewHolder extends ViewHolder {
        ImageView delete;
        ImageView edit;
        TextView headname;
        TextView password;
        TextView username;

        public viewHolder(View itemView) {
            super(itemView);
            this.headname = (TextView) itemView.findViewById(R.id.headname);
            this.username = (TextView) itemView.findViewById(R.id.username);
            this.password = (TextView) itemView.findViewById(R.id.password);
            this.delete = (ImageView) itemView.findViewById(R.id.delete);
            this.edit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public RegionsHeadList_ListAdapter(Activity activity2, List<RegionsHeads_FeedItem> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.cd = new ConnectionDetecter(this.context);
        this.db = new ChatDB(this.context);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "font/proxibold.otf");
        this.face1 = Typeface.createFromAsset(this.context.getAssets(), "font/proximanormal.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.custom_regionsheads, parent, false));
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

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                RegionsHeads_FeedItem item = (RegionsHeads_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.headname.setText(item.getHeadname());
                TextView textView = viewHolder2.username;
                StringBuilder sb = new StringBuilder();
                sb.append("Username : ");
                sb.append(item.getUsername());
                textView.setText(sb.toString());
                TextView textView2 = viewHolder2.password;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Password : ");
                sb2.append(item.getPassword());
                textView2.setText(sb2.toString());
                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            RegionsHeads_FeedItem regionsHeads_FeedItem = (RegionsHeads_FeedItem) RegionsHeadList_ListAdapter.this.feedItems.get(position);
                            RegionsHeads_FeedItem item = (RegionsHeads_FeedItem) RegionsHeadList_ListAdapter.this.feedItems.get(position);
                            RegionsHeadList_ListAdapter.this.txtregionid = item.getRegionheadId();
                            RegionsHeadList_ListAdapter.this.pos = position;
                            RegionsHeadList_ListAdapter.this.showalert_delete("Are you sure want to delete this region head ?");
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.edit.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = RegionsHeadList_ListAdapter.this.feedItems.get(position);
                        RegionsHeads_FeedItem item = (RegionsHeads_FeedItem) RegionsHeadList_ListAdapter.this.feedItems.get(position);
                        Temp_Variable.regionhededit = 1;
                        Temp_Variable.regionheadid = item.getRegionheadId();
                        Temp_Variable.regionheadname = item.getHeadname();
                        Temp_Variable.regionheadusername = item.getUsername();
                        Temp_Variable.regionheadpassword = item.getPassword();
                        Intent i = new Intent(RegionsHeadList_ListAdapter.this.context, Add_Region_Head.class);
                        i.setFlags(268435456);
                        RegionsHeadList_ListAdapter.this.context.startActivity(i);
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
                if (RegionsHeadList_ListAdapter.this.cd.isConnectingToInternet()) {
                    RegionsHeadList_ListAdapter.this.deleteregion();
                } else {
                    Toasty.info(RegionsHeadList_ListAdapter.this.context, Temp_Variable.nointernet, 0).show();
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
                jo.put("regionheadId", this.txtregionid);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/deleteregionhead");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    RegionsHeadList_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            RegionsHeadList_ListAdapter.this.pd.dismiss();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    RegionsHeadList_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                RegionsHeadList_ListAdapter.this.pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(RegionsHeadList_ListAdapter.this.context, "Please contact admin", 0).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(RegionsHeadList_ListAdapter.this.context, "deleted", 0).show();
                                    ((Regional_Heads) RegionsHeadList_ListAdapter.this.activity).removeitem(RegionsHeadList_ListAdapter.this.pos);
                                } else {
                                    Toasty.info(RegionsHeadList_ListAdapter.this.context, "Please try later", 0).show();
                                }
                            } catch (Exception e) {
                                Toasty.info(RegionsHeadList_ListAdapter.this.context, "Please try later", 0).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
