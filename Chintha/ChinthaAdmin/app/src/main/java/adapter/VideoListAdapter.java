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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.R;
import com.chintha_admin.Tempvariable;
import com.chintha_admin.Upload_Video_Full;
import data.Video_FeedItemItem;
import java.util.List;

public class VideoListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    private List<Video_FeedItemItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtvideoid = "";
    public View views;

    public VideoListAdapter(Activity activity2, List<Video_FeedItemItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
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
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.video_customlayout, null);
            }
            RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            final Video_FeedItemItem item = (Video_FeedItemItem) feedItems.get(position);
            ((TextView) convertView.findViewById(R.id.text)).setText(item.getfilename());
            layout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Tempvariable.playlink = item.getfilename();
                    Intent i = new Intent(context, Upload_Video_Full.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
        } catch (Exception e) {
        }
        return convertView;
    }
}
