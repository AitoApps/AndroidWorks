package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.payumoney.core.PayUmoneyConstants;
import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;
import com.zemose.admin.Regional_Heads;
import com.zemose.admin.Temp_Variable;
import com.zemose.admin.Transfered_Report;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import data.Sna_FeedItem;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Transfered_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public AppCompatActivity activity;
    public ConnectionDetecter cd;
    private Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;

    public List<Sna_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String userid = "";
    String ids="";
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView customer;
        TextView date;
        ImageView image,delete;
        TextView name;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.date = (TextView) itemView.findViewById(R.id.date);
            delete=itemView.findViewById(R.id.delete);
            this.customer = (TextView) itemView.findViewById(R.id.customer);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Transfered_ListAdapter(AppCompatActivity activity2, List<Sna_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.transferedorder_customlayout, parent, false));
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
                Sna_FeedItem item = (Sna_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.name.setText(item.getproductname());
                viewHolder2.date.setText(item.getorderdate());
                viewHolder2.customer.setText(item.getcustomername());
                String string = this.context.getResources().getString(R.string.Rs);
                RequestManager with = Glide.with(this.context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append(item.getproductimage());
                with.load(sb.toString()).into(viewHolder2.image);
                viewHolder2.customer.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ((Transfered_Report) Transfered_ListAdapter.this.activity).call(((Sna_FeedItem) Transfered_ListAdapter.this.feedItems.get(position)).getcustomercontact());
                    }
                });

                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ids=item.getorderid();
                        pos=position;
                        showalert_delete("Delete this transfered order ?");


                    }
                });
            } catch (Exception e) {
            }
        }
    }

    public void showalert_delete(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    deleteregion();
                } else {
                    Toasty.info(context, Temp_Variable.nointernet, Toast.LENGTH_SHORT).show();
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
                jo.put("orderId", ids);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/deletetransferedorder");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                           pd.dismiss();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                pd.dismiss();
                                JSONObject jo = new JSONObject(response.body().string());
                                if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                    Toasty.info(context, "Please contact admin", Toast.LENGTH_SHORT).show();
                                } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Update")) {
                                    Toasty.info(context, "deleted", Toast.LENGTH_SHORT).show();
                                    ((Transfered_Report) activity).removeitem(pos);
                                } else {
                                    Toasty.info(context, "Please try later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toasty.info(context, "Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }
}
