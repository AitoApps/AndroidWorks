package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Pending_Payments;
import com.sanji_admin.Temp;
import data.Pendingpayment_FeedItem;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class PendingPayment_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Pendingpayment_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;

    public PendingPayment_ListAdapter(Activity activity2, List<Pendingpayment_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
    }

    public int getCount() {
        return feedItems.size();
    }

    public Object getItem(int location) {
        return feedItems.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_paymentpending, null);
        }
        TextView shopname = (TextView) convertView.findViewById(R.id.shopname);
        TextView shopmobile = (TextView) convertView.findViewById(R.id.shopmobile);
        TextView shopamount = (TextView) convertView.findViewById(R.id.amount);
        ImageView payicon = (ImageView) convertView.findViewById(R.id.payicon);
        Pendingpayment_FeedItem item = (Pendingpayment_FeedItem) feedItems.get(position);
        String string = context.getResources().getString(R.string.Rs);
        shopname.setText(item.getshopname());
        shopmobile.setText(item.getshopmobile());
        StringBuilder sb = new StringBuilder();
        sb.append("Balance : ");
        sb.append(item.getamount());
        shopamount.setText(sb.toString());
        shopmobile.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Pendingpayment_FeedItem pendingpayment_FeedItem = (Pendingpayment_FeedItem) PendingPayment_ListAdapter.feedItems.get(position);
                    ((Pending_Payments) PendingPayment_ListAdapter.activity).call(((Pendingpayment_FeedItem) PendingPayment_ListAdapter.feedItems.get(position)).getshopmobile());
                } catch (Exception e) {
                }
            }
        });
        payicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Pendingpayment_FeedItem pendingpayment_FeedItem = (Pendingpayment_FeedItem) PendingPayment_ListAdapter.feedItems.get(position);
                Pendingpayment_FeedItem item = (Pendingpayment_FeedItem) PendingPayment_ListAdapter.feedItems.get(position);
                PendingPayment_ListAdapter.pos = position;
                if (PendingPayment_ListAdapter.cd.isConnectingToInternet()) {
                    ((Pending_Payments) PendingPayment_ListAdapter.activity).showpayamount(PendingPayment_ListAdapter.pos, item.getshopid(), item.getsn(), item.getamount(), item.getshopname(), item.getshopmobile());
                    return;
                }
                Toasty.info(PendingPayment_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
}
