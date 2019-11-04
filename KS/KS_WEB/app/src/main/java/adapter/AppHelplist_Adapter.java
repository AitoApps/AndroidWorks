package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.suthra_malayalam_web.App_Deatils;
import com.suthra_malayalam_web.DataBase;
import com.suthra_malayalam_web.NetConnect;
import com.suthra_malayalam_web.R;
import com.suthra_malayalam_web.Static_Veriable;

import java.util.List;

import data.AppHelplist_Feed;
import data.Applist_Feed;
import es.dmoral.toasty.Toasty;

public class AppHelplist_Adapter extends BaseAdapter {

    public Activity activity;
    public NetConnect cd;
    public Context context;
    public List<AppHelplist_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public DataBase db;
    public AppHelplist_Adapter(Activity activity2, List<AppHelplist_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        db=new DataBase(context);
        cd=new NetConnect(context);
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
            convertView = inflater.inflate(R.layout.custom_apphelplist, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        AppHelplist_Feed item = (AppHelplist_Feed) feedItems.get(position);
        title.setText(item.getTitle());
        String[] k = item.getImagedim().split("x");
        float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(db.get_scrwidth()).floatValue() - 20.0f);
        image.getLayoutParams().height = Math.round(calheight);
        Glide.with(context).load(item.getImagesrc()).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        return convertView;
    }
}
