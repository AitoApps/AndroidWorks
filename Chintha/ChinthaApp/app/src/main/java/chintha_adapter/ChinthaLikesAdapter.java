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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Users_Chinthakal;

import java.util.List;

import chintha_data.LikeFeed;

public class ChinthaLikesAdapter extends BaseAdapter {
    private AppCompatActivity activity;

    public Context context;

    public List<LikeFeed> feed;
    private LayoutInflater inflater;

    public ChinthaLikesAdapter(AppCompatActivity activity2, List<LikeFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
    }

    public Object getItem(int location) {
        return feed.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getCount() {
        return feed.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chinthalikes_custom, null);
        }
        ImageView dippic = (ImageView) convertView.findViewById(R.id.img);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        ImageView chintha_hostory = (ImageView) convertView.findViewById(R.id.chintha_histroy);
        LikeFeed item = (LikeFeed) feed.get(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(item.getName());
        Glide.with(context).load(item.get_dppic()).apply(new RequestOptions().signature(new ObjectKey(item.get_imgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(dippic);
        chintha_hostory.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LikeFeed item = (LikeFeed) feed.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LikeFeed item = (LikeFeed) feed.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        dippic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                LikeFeed item = (LikeFeed) feed.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
    }
}
