package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fortune_admin.ConnectionDetecter;
import com.fortune_admin.LuckyDrawWinner;
import com.fortune_admin.R;
import com.fortune_admin.Temp;

import java.util.List;

import data.LuckyDrawBatches_FeedItem;
import data.Weeklist_FeedItem;

public class Weeklist_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    Typeface face;
    public List<Weeklist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_dateid="";
    public Weeklist_ListAdapter(Activity activity2, List<Weeklist_FeedItem> feedItems2) {
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
            convertView = inflater.inflate(R.layout.custom_weeks, null);
        }
        TextView weekid = (TextView) convertView.findViewById(R.id.weekid);
        TextView weekdate=convertView.findViewById(R.id.weekdate);
        RelativeLayout layout=convertView.findViewById(R.id.layout);
        Weeklist_FeedItem item =feedItems.get(position);
        weekid.setTypeface(face);
        weekdate.setTypeface(face);
        weekid.setText(item.getWeekid() +" Week");
        weekdate.setText(item.getWeekdate());

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.weekid=item.getWeekid();
                Intent i = new Intent(context, LuckyDrawWinner.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
