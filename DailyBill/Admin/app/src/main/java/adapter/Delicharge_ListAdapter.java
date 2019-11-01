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

import com.dailybill_admin.Add_Shops;
import com.dailybill_admin.ConnectionDetecter;
import com.dailybill_admin.DatabaseHandler;
import com.dailybill_admin.R;
import java.util.List;
import data.DelichargeList_FeedItem;
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
            convertView = inflater.inflate(R.layout.custom_deliverychrg, null);
        }
        TextView toamt = (TextView) convertView.findViewById(R.id.toamt);
        TextView charge = (TextView) convertView.findViewById(R.id.charge);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        DelichargeList_FeedItem item = (DelichargeList_FeedItem) feedItems.get(position);
        String rupee = context.getResources().getString(R.string.Rs);
        toamt.setTypeface(face);
        charge.setTypeface(face);
        toamt.setText(item.getToamt()+" - ");
        charge.setText(rupee+item.getCharge());
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DelichargeList_FeedItem item = (DelichargeList_FeedItem) feedItems.get(position);
                pos = position;
                ((Add_Shops) activity).removeitem(pos);
            }
        });
        return convertView;
    }
}
