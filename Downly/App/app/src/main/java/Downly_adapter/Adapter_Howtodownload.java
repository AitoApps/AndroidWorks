package Downly_adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.downly_app.R;
import com.downly_app.YoutubeFormat_Window;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Downly_Data.Feed_Formats;
import Downly_Data.Feed_Howtodownload;
import es.dmoral.toasty.Toasty;

public class Adapter_Howtodownload extends BaseAdapter {
    public Activity activity;
    public Context context;
    Typeface face;
    public List<Feed_Howtodownload> feedItems;
    private LayoutInflater inflater;

    public Adapter_Howtodownload(Activity activity2, List<Feed_Howtodownload> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "commonfont.otf");
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
            convertView = inflater.inflate(R.layout.custom_list_howtodownload, null);
        }
        ImageView image=(ImageView)convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        Feed_Howtodownload item = (Feed_Howtodownload) feedItems.get(position);

        title.setTypeface(face);
        title.setText((position+1)+"."+item.getTitle());
        image.setImageDrawable(item.getImage());


        return convertView;
    }

}
