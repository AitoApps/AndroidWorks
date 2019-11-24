package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.hellokhd.R;
import com.hellokhd.Temp;

import java.util.List;

import data.NowRunning_FeedItem;
import data.StageList_FeedItem;

public class NowRunning_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    public Context context;
    Typeface face;
    public List<NowRunning_FeedItem> feedItems;
    private LayoutInflater inflater;
    public NowRunning_Adapter(Activity activity2, List<NowRunning_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
        ImageView location;
        RelativeLayout layout;
        TextView programname,programtime,stagename,km,stagenumber;
        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            location=itemView.findViewById(R.id.location);
            stagename=itemView.findViewById(R.id.stagename);
            km=itemView.findViewById(R.id.km);
            stagenumber=itemView.findViewById(R.id.stagenumber);
            programname=itemView.findViewById(R.id.programname);
            programtime=itemView.findViewById(R.id.programtime);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_nowrunning, parent, false));
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
                NowRunning_FeedItem item = (NowRunning_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.stagename.setText(item.getStagename());
                viewHolder2.km.setText(item.getDistance() +" KM");

                viewHolder2.stagename.setTypeface(face);
                viewHolder2.km.setTypeface(face);
                viewHolder2.programname.setTypeface(face);
                viewHolder2.programtime.setTypeface(face);
                viewHolder2.stagenumber.setTypeface(face);

                viewHolder2.stagenumber.setText("Stage : "+item.getStagenumber());
                viewHolder2.programname.setText(item.getCurrentprogram());
                viewHolder2.programtime.setText(item.getProgramtime());
                viewHolder2.location.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NowRunning_FeedItem item = (NowRunning_FeedItem) feedItems.get(position);
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+item.getLatitude()+","+item.getLongtitude()+"&title="+item.getStagename()+"&mode=d");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(mapIntent);
                    }
                });
            } catch (Exception e) {

            }
        }
    }
}
