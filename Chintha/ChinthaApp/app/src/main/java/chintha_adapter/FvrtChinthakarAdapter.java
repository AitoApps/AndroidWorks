package chintha_adapter;

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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.Favourite_UsersList;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Users_Chinthakal;

import java.util.List;

import chintha_data.ChinthakarFeed;

public class FvrtChinthakarAdapter extends BaseAdapter {

    public AppCompatActivity activity;

    public Context context;
    public DataDb dataDb = new DataDb(context);

    public List<ChinthakarFeed> feed;
    private LayoutInflater inflater;

    public FvrtChinthakarAdapter(AppCompatActivity activity2, List<ChinthakarFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
    }

    public int getCount() {
        return feed.size();
    }

    public Object getItem(int location) {
        return feed.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_fvrtchinthausers, null);
        }
        ImageView dppics = (ImageView) convertView.findViewById(R.id.img);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        ImageView chintha_history = (ImageView) convertView.findViewById(R.id.chintha_histroy);
        ImageView drop = (ImageView) convertView.findViewById(R.id.del);
        ChinthakarFeed item = (ChinthakarFeed) feed.get(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(item.getName());
        Glide.with(context).load(item.getDppic()).apply(new RequestOptions().placeholder((int) R.drawable.img_noimage)).into(dppics);
        dppics.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ChinthakarFeed item = (ChinthakarFeed) feed.get(position);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        drop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dataDb.del_fvrtusr(((ChinthakarFeed) feed.get(position)).getuserid());
                ((Favourite_UsersList) activity).clearitem(position);
            }
        });
        chintha_history.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChinthakarFeed item = (ChinthakarFeed) feed.get(position);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChinthakarFeed item = (ChinthakarFeed) feed.get(position);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
