package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fortune_admin.ConnectionDetecter;
import com.fortune_admin.CustomerView;
import com.fortune_admin.Customers;
import com.fortune_admin.R;
import com.fortune_admin.Temp;

import java.util.List;

import data.Customers_FeedItem;
import data.LuckyDrawBatches_FeedItem;

public class Customer_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    Typeface face;
    public List<Customers_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_dateid="";
    public Customer_ListAdapter(Activity activity2, List<Customers_FeedItem> feedItems2) {
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
            convertView = inflater.inflate(R.layout.custom_customers, null);
        }
        ImageView image=convertView.findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView housename=convertView.findViewById(R.id.housename);
        TextView pending=convertView.findViewById(R.id.pending);
        Button viewall=convertView.findViewById(R.id.viewall);
        RelativeLayout layout=convertView.findViewById(R.id.layout);
        Customers_FeedItem item =feedItems.get(position);
        name.setTypeface(face);
        housename.setTypeface(face);
        pending.setTypeface(face);

        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"userimagesmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        name.setText(item.getName());
        housename.setText(item.getHousename());
        if(item.getPending().equalsIgnoreCase("0"))
        {
            pending.setText("");
        }
        else
        {
            pending.setText(item.getPending()+" Weeks");
            pending.setTextColor(Color.parseColor("#a82036"));
        }

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.lcustomerid=item.getSn();
                Intent i = new Intent(context, CustomerView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
