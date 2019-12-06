package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fishapp.user.R;

import data.OrderProductList_History_Feed;
import java.util.List;

public class OrderProductList_History_ListAdapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    Typeface face;
    Typeface face1;
    private List<OrderProductList_History_Feed> feedItems;
    private LayoutInflater inflater;

    public OrderProductList_History_ListAdapter(Activity activity2, List<OrderProductList_History_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1= Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_orderproductlist, null);
        }
        TextView itemname = (TextView) convertView.findViewById(R.id.itemname);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        OrderProductList_History_Feed item = (OrderProductList_History_Feed) feedItems.get(position);
        itemname.setText(item.getitemname()+" - ");
        float kilograms = Float.parseFloat(item.getqty()) / Float.parseFloat("1000");
        qty.setText(String.format("%.2f",Float.parseFloat(kilograms+"")));
        amount.setText(String.format("%.2f", Float.parseFloat(item.gettotalamount())));
        itemname.setTypeface(face);
        qty.setTypeface(face);
        amount.setTypeface(face);
        return convertView;
    }
}
