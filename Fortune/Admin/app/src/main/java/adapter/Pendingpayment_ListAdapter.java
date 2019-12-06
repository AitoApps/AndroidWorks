package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fortune_admin.ConnectionDetecter;
import com.fortune_admin.R;

import java.util.List;

import data.PaymentHistory_FeedItem;

public class Pendingpayment_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    Typeface face;
    public List<PaymentHistory_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public Pendingpayment_ListAdapter(Activity activity2, List<PaymentHistory_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_payment_history, null);
        }
        TextView paymentid=convertView.findViewById(R.id.paymentid);
        TextView amount=convertView.findViewById(R.id.amount);
        PaymentHistory_FeedItem item = (PaymentHistory_FeedItem) feedItems.get(position);
        paymentid.setTypeface(face);
        amount.setTypeface(face);
        paymentid.setText(item.getPaymentdate());
        amount.setText(item.getAmount());

        return convertView;
    }
}
