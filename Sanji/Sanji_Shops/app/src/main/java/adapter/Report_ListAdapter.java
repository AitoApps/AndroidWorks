package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.DatabaseHandler;
import com.sanji_shop.R;
import com.sanji_shop.Temp;
import data.Report_FeedItem;
import java.util.List;

public class Report_ListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    DatabaseHandler db;
    Typeface face;
    private List<Report_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtproductid = "";

    public Report_ListAdapter(Activity activity2, List<Report_FeedItem> feedItems2) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_report, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView itemname = (TextView) convertView.findViewById(R.id.itemname);
        TextView voucher_pending = (TextView) convertView.findViewById(R.id.voucher_pending);
        TextView voucher_sold = (TextView) convertView.findViewById(R.id.voucher_sold);
        TextView voucher_cancelled = (TextView) convertView.findViewById(R.id.voucher_cancelled);
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.layout);
        Report_FeedItem item = (Report_FeedItem) feedItems.get(position);
        String string = context.getResources().getString(R.string.Rs);
        itemname.setText(item.getitemname());
        itemname.setText(item.getitemname());
        StringBuilder sb = new StringBuilder();
        sb.append(item.getvcount_pedning());
        sb.append(" Pending");
        voucher_pending.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(item.getvcount_sales());
        sb2.append(" Sold");
        voucher_sold.setText(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(item.getvcount_cancelled());
        sb3.append(" Cancelled");
        voucher_cancelled.setText(sb3.toString());
        itemname.setTypeface(face);
        voucher_pending.setTypeface(face);
        voucher_sold.setTypeface(face);
        voucher_cancelled.setTypeface(face);
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        RequestManager with = Glide.with(context);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(Temp.weblink);
        sb4.append("productpicsmall/");
        sb4.append(item.getitemid());
        sb4.append("_1.jpg");
        with.load(sb4.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        return convertView;
    }
}
