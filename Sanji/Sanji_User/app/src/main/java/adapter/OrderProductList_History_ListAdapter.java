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
import com.sanji.ConnectionDetecter;
import com.sanji.DatabaseHandler;
import com.sanji.UserDatabaseHandler;
import data.OrderProductList_History_Feed;
import java.util.List;

public class OrderProductList_History_ListAdapter extends BaseAdapter {
    private Activity activity;
    public String addressid = "";
    public ConnectionDetecter cd;
    private Context context;
    DatabaseHandler db;
    Typeface face;
    Typeface face1;
    private List<OrderProductList_History_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    UserDatabaseHandler udb;

    public OrderProductList_History_ListAdapter(Activity activity2, List<OrderProductList_History_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        udb = new UserDatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_orderproductlist, null);
        }
        TextView itemname = (TextView) convertView.findViewById(R.id.itemname);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        OrderProductList_History_Feed item = (OrderProductList_History_Feed) feedItems.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append(item.getitemname());
        sb.append(" - ");
        itemname.setText(sb.toString());
        qty.setText(item.getqty());
        amount.setText(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(item.gettotalamount()))}));
        itemname.setTypeface(face);
        qty.setTypeface(face);
        amount.setTypeface(face);
        return convertView;
    }
}
