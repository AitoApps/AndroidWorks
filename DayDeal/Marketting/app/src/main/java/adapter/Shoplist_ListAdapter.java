package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal_marketting.Add_Shop;
import com.daydeal_marketting.ConnectionDetecter;
import com.daydeal_marketting.DatabaseHandler;
import com.daydeal_marketting.Image_Viewer;
import com.daydeal_marketting.R;
import com.daydeal_marketting.Temp;
import data.Shoplist_FeedItem;
import java.util.List;

public class Shoplist_ListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;

    public Context context;
    DatabaseHandler db;
    Typeface face;

    public List<Shoplist_FeedItem> feedItems;
    public String imgsig1;
    private LayoutInflater inflater;
    public String latitude1;
    public String longtitude1;
    public String ownername1;
    ProgressDialog pd;
    public String place1;
    public String shopname1;
    public String sn1;
    public String status1;
    public String t_mobile1;
    public String t_mobile2;
    public String trust;

    public Shoplist_ListAdapter(Activity activity2, List<Shoplist_FeedItem> feedItems2) {
        sn1 = "";
        shopname1 = "";
        ownername1 = "";
        t_mobile1 = "";
        t_mobile2 = "";
        place1 = "";
        latitude1 = "";
        longtitude1 = "";
        imgsig1 = "";
        status1 = "";
        trust = "";
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
            convertView = inflater.inflate(R.layout.custom_shoplist, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView shopname = (TextView) convertView.findViewById(R.id.shopname);
        TextView ownername = (TextView) convertView.findViewById(R.id.ownername);
        TextView mobile1 = (TextView) convertView.findViewById(R.id.mobile1);
        TextView mobile2 = (TextView) convertView.findViewById(R.id.mobile2);
        TextView place = (TextView) convertView.findViewById(R.id.place);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        shopname.setTypeface(face);
        ownername.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        place.setTypeface(face);
        Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
        shopname.setText(item.getshopname());
        mobile1.setText(item.getmobile1());
        mobile2.setText(item.getmobile2());
        place.setText(item.getplace());
        ownername.setText(item.getownername());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        Glide.with(context).load(Temp.weblink+"shoppicsmall/"+item.getsn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem shoplist_FeedItem = (Shoplist_FeedItem) feedItems.get(position);
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                Temp.img_title = item.getshopname();
                Temp.img_imgsig = item.getimgsig();
                Temp.img_link = Temp.weblink+"shoppics/"+item.getsn()+".jpg";
                Intent i = new Intent(context, Image_Viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem shoplist_FeedItem = (Shoplist_FeedItem) feedItems.get(position);
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                Temp.edit_shopid = item.getsn();
                Temp.edit_shopname = item.getshopname();
                Temp.edit_ownername = item.getownername();
                Temp.edit_mobile1 = item.getmobile1();
                Temp.edit_mobile2 = item.getmobile2();
                Temp.edit_place = item.getplace();
                Temp.edit_latitude = item.getlatitude();
                Temp.edit_longitude = item.getlongtitude();
                Temp.isshopedit = 1;
                Intent i = new Intent(context, Add_Shop.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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
