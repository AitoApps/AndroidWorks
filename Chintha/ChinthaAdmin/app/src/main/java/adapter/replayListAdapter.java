package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.DatabaseHandler;
import com.chintha_admin.R;

import data.replay_FeedItemItem;
import java.util.List;

public class replayListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    public DatabaseHandler db;
    private List<replay_FeedItemItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtsn;
    public View views;

    public replayListAdapter(Activity activity2, List<replay_FeedItemItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
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
                convertView = inflater.inflate(R.layout.replay_customlayout, null);
            }
            views = convertView;
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView commentid = (TextView) convertView.findViewById(R.id.commentid);
            replay_FeedItemItem item = (replay_FeedItemItem) feedItems.get(position);
            try {
                ((TextView) convertView.findViewById(R.id.replay)).setText(new String(Base64.decode(item.getreplay(), 0), "UTF-8"));
            } catch (Exception e) {
            }
            time.setText(item.gettime());
            commentid.setText(item.getcommentid());
        } catch (Exception a) {
            Toast.makeText(context, Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }
}
