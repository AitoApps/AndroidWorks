package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Handler;
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
import com.zemose.regionadmin.ConnectionDetecter;
import com.zemose.regionadmin.Payment_Approve;
import com.zemose.regionadmin.R;
import com.zemose.regionadmin.Temp_Variable;
import data.PaymentApprove_FeedItem;
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

public class PaymentApprove_ListAdapter extends Adapter<ViewHolder> {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    /* access modifiers changed from: private */
    public Activity activity;
    public String amount = "";
    public ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public Context context;
    Typeface face;
    Typeface face1;
    /* access modifiers changed from: private */
    public List<PaymentApprove_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String payid = "";
    ProgressDialog pd;
    int pos = 0;
    public String shopId = "";

    public class viewHolder extends ViewHolder {
        TextView amount;
        ImageView approve;
        ImageView call;
        ImageView cancell;
        TextView cardnumber;
        TextView mode;
        TextView shopname;

        public viewHolder(View itemView) {
            super(itemView);
            this.shopname = (TextView) itemView.findViewById(R.id.shopname);
            this.mode = (TextView) itemView.findViewById(R.id.mode);
            this.approve = (ImageView) itemView.findViewById(R.id.approve);
            this.cancell = (ImageView) itemView.findViewById(R.id.cancell);
            this.cardnumber = (TextView) itemView.findViewById(R.id.cardnumber);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.call = (ImageView) itemView.findViewById(R.id.call);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public PaymentApprove_ListAdapter(Activity activity2, List<PaymentApprove_FeedItem> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.cd = new ConnectionDetecter(this.context);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "font/proxibold.otf");
        this.face1 = Typeface.createFromAsset(this.context.getAssets(), "font/proximanormal.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.custom_paymentapprove, parent, false));
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
        if (position < this.feedItems.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return this.feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                PaymentApprove_FeedItem item = (PaymentApprove_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.shopname.setText(item.getshopName());
                viewHolder2.shopname.setTypeface(this.face);
                TextView textView = viewHolder2.mode;
                StringBuilder sb = new StringBuilder();
                sb.append(item.gettxtnid());
                sb.append(",");
                sb.append(item.getmode());
                sb.append(",");
                sb.append(item.getbankcode());
                textView.setText(sb.toString());
                viewHolder2.cardnumber.setText(item.getcardnum());
                String rupee = this.context.getResources().getString(R.string.Rs);
                TextView textView2 = viewHolder2.amount;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(rupee);
                sb2.append("");
                sb2.append(item.getamount());
                textView2.setText(sb2.toString());
                viewHolder2.mode.setTypeface(this.face1);
                viewHolder2.cardnumber.setTypeface(this.face1);
                viewHolder2.amount.setTypeface(this.face1);
                viewHolder2.call.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = PaymentApprove_ListAdapter.this.feedItems.get(position);
                        Payment_Approve.mobile = ((PaymentApprove_FeedItem) PaymentApprove_ListAdapter.this.feedItems.get(position)).getshopContact();
                        ((Payment_Approve) PaymentApprove_ListAdapter.this.activity).call();
                    }
                });
                viewHolder2.approve.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = PaymentApprove_ListAdapter.this.feedItems.get(position);
                        PaymentApprove_FeedItem item = (PaymentApprove_FeedItem) PaymentApprove_ListAdapter.this.feedItems.get(position);
                        PaymentApprove_ListAdapter.this.payid = item.getpayid();
                        PaymentApprove_ListAdapter.this.amount = item.getamount();
                        PaymentApprove_ListAdapter.this.shopId = item.getshopId();
                        PaymentApprove_ListAdapter paymentApprove_ListAdapter = PaymentApprove_ListAdapter.this;
                        paymentApprove_ListAdapter.pos = position;
                        paymentApprove_ListAdapter.showalert_confirm("Are you sure want to approve this Payment ?");
                    }
                });
                viewHolder2.cancell.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            PaymentApprove_FeedItem paymentApprove_FeedItem = (PaymentApprove_FeedItem) PaymentApprove_ListAdapter.this.feedItems.get(position);
                            PaymentApprove_FeedItem item = (PaymentApprove_FeedItem) PaymentApprove_ListAdapter.this.feedItems.get(position);
                            PaymentApprove_ListAdapter.this.payid = item.getpayid();
                            PaymentApprove_ListAdapter.this.amount = item.getamount();
                            PaymentApprove_ListAdapter.this.shopId = item.getshopId();
                            PaymentApprove_ListAdapter.this.pos = position;
                            PaymentApprove_ListAdapter.this.showalert_delete("Are you sure want to Reject this Poucher ?");
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert_confirm(String message) {
        Builder builder = new Builder(this.activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (PaymentApprove_ListAdapter.this.cd.isConnectingToInternet()) {
                    PaymentApprove_ListAdapter.this.confirm_payment();
                } else {
                    Toasty.info(PaymentApprove_ListAdapter.this.context, Temp_Variable.nointernet, 0).show();
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

    public void confirm_payment() {
        try {
            this.pd.setMessage("Please wait..");
            this.pd.setCancelable(false);
            this.pd.show();
            timerDelayRemoveDialog(30000, this.pd);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("payid", this.payid);
                jo.put(PayUmoneyConstants.AMOUNT, this.amount);
                jo.put("shopid", this.shopId);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/approvepayment");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    PaymentApprove_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            PaymentApprove_ListAdapter.this.pd.dismiss();
                            Toasty.info(PaymentApprove_ListAdapter.this.context, "Please try later", 0).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    PaymentApprove_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                PaymentApprove_ListAdapter.this.pd.dismiss();
                                if (new JSONObject(response.body().string()).getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("approve")) {
                                    Toasty.info(PaymentApprove_ListAdapter.this.context, "Approved", 0).show();
                                    ((Payment_Approve) PaymentApprove_ListAdapter.this.activity).removeitem(PaymentApprove_ListAdapter.this.pos);
                                    return;
                                }
                                Toasty.info(PaymentApprove_ListAdapter.this.context, "Unable to approve ! Please try later", 0).show();
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            });
        } catch (Exception e2) {
        }
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(this.activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (PaymentApprove_ListAdapter.this.cd.isConnectingToInternet()) {
                    PaymentApprove_ListAdapter.this.delete_vendor();
                } else {
                    Toasty.info(PaymentApprove_ListAdapter.this.context, Temp_Variable.nointernet, 0).show();
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

    public void delete_vendor() {
        try {
            this.pd.setMessage("Please wait..");
            this.pd.setCancelable(false);
            this.pd.show();
            timerDelayRemoveDialog(30000, this.pd);
            OkHttpClient client = new OkHttpClient();
            JSONObject jo = new JSONObject();
            try {
                jo.put("payid", this.payid);
                jo.put(PayUmoneyConstants.AMOUNT, this.amount);
                jo.put("shopid", this.shopId);
            } catch (JSONException e) {
            }
            RequestBody body = RequestBody.create(this.JSON, jo.toString());
            Request.Builder builder = new Request.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp_Variable.baseurl);
            sb.append("appadmin/canellpayment");
            client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    PaymentApprove_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            PaymentApprove_ListAdapter.this.pd.dismiss();
                            Toasty.info(PaymentApprove_ListAdapter.this.context, "Please try later", 0).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) throws IOException {
                    PaymentApprove_ListAdapter.this.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                PaymentApprove_ListAdapter.this.pd.dismiss();
                                if (new JSONObject(response.body().string()).getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("rejected")) {
                                    Toasty.info(PaymentApprove_ListAdapter.this.context, "Rejected", 0).show();
                                    ((Payment_Approve) PaymentApprove_ListAdapter.this.activity).removeitem(PaymentApprove_ListAdapter.this.pos);
                                    return;
                                }
                                Toasty.info(PaymentApprove_ListAdapter.this.context, "Unable to reject ! Please try later", 0).show();
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
