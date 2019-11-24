package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.hellokhd.Announcement_List;
import com.hellokhd.R;
import com.hellokhd.UserDatabaseHandler;

import java.util.List;

import data.Anouncement_FeedItem;
import data.BusTrain_FeedItem;

public class BusTrain_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    float ogheight;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<BusTrain_FeedItem> feedItems;
    private LayoutInflater inflater;
    public BusTrain_Adapter(Activity activity2, List<BusTrain_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
         RelativeLayout layout;
         TextView busname,stationname,fromtostation,times;

        public viewHolder(View itemView) {
            super(itemView);
            busname=itemView.findViewById(R.id.busname);
            layout=itemView.findViewById(R.id.layout);
            stationname=itemView.findViewById(R.id.stationname);
            fromtostation=itemView.findViewById(R.id.fromtostation);
            times=itemView.findViewById(R.id.times);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_buslist, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.footerview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.fullloaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if (position < feedItems.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                BusTrain_FeedItem item = (BusTrain_FeedItem)feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.busname.setTypeface(face);
                viewHolder2.fromtostation.setTypeface(face);
                viewHolder2.stationname.setTypeface(face);
                viewHolder2.times.setTypeface(face);

                viewHolder2.busname.setText(item.getBusname());
                viewHolder2.fromtostation.setText(item.getFromstation()+" <=> "+item.getTostation());
                viewHolder2.stationname.setText(item.getStation());
                viewHolder2.times.setText(item.getTimes());

            } catch (Exception e) {

            }
        }
    }
}
