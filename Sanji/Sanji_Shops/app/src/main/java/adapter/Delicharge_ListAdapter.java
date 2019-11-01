package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.DatabaseHandler;
import com.sanji_shop.R;
import com.sanji_shop.Shop_Details;
import data.DelichargeList_FeedItem;
import java.util.List;

public class Delicharge_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    DatabaseHandler db;
    Typeface face;
    public List<DelichargeList_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;

    public Delicharge_ListAdapter(Activity activity2, List<DelichargeList_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
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
            convertView = inflater.inflate(R.layout.custom_deliverychrg, null);
        }
        TextView toamt = (TextView) convertView.findViewById(R.id.toamt);
        TextView charge = (TextView) convertView.findViewById(R.id.charge);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        DelichargeList_FeedItem item = (DelichargeList_FeedItem) feedItems.get(position);
        String rupee = context.getResources().getString(R.string.Rs);
        toamt.setTypeface(face);
        charge.setTypeface(face);
        StringBuilder sb = new StringBuilder();
        sb.append(item.getToamt());
        sb.append(" - ");
        toamt.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(rupee);
        sb2.append(item.getCharge());
        charge.setText(sb2.toString());
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DelichargeList_FeedItem delichargeList_FeedItem = (DelichargeList_FeedItem) Delicharge_ListAdapter.feedItems.get(position);
                DelichargeList_FeedItem item = (DelichargeList_FeedItem) Delicharge_ListAdapter.feedItems.get(position);
                Delicharge_ListAdapter.pos = position;
                ((Shop_Details) Delicharge_ListAdapter.activity).removeitem(Delicharge_ListAdapter.pos);
            }
        });
        return convertView;
    }
}
