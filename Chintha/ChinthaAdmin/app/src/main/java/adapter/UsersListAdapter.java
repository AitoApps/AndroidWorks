package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.R;

import data.User_FeedItemItem;
import java.util.List;

public class UsersListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    private List<User_FeedItemItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtclaimid = "";
    public String txtmobile = "";
    public View views;

    public UsersListAdapter(Activity activity2, List<User_FeedItemItem> feedItems2) {
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
                convertView = inflater.inflate(R.layout.user_customlayout, null);
            }
            views = convertView;
            TextView userid = (TextView) convertView.findViewById(R.id.userid);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView mobile = (TextView) convertView.findViewById(R.id.mobile);
            User_FeedItemItem item = (User_FeedItemItem) feedItems.get(position);
            mobile.setTag(Integer.valueOf(position));
            name.setTag(Integer.valueOf(position));
            userid.setTag(Integer.valueOf(position));
            mobile.setText(item.getmobile());
            userid.setText(item.getsn());
            name.setText(item.getname());
        } catch (Exception a) {
            Toast.makeText(context, Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }
}
