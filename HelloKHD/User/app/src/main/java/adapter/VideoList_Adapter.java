package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
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
import com.hellokhd.Shops;
import com.hellokhd.Temp;
import com.hellokhd.UserDatabaseHandler;
import com.hellokhd.Video_List;

import java.util.List;

import data.ShopsList_FeedItem;
import data.Videolist_FeedItem;

public class VideoList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    float ogheight;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<Videolist_FeedItem> feedItems;
    private LayoutInflater inflater;
    public VideoList_Adapter(Activity activity2, List<Videolist_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
         RelativeLayout lytvide;
         ImageView videopic,playicon;
         TextView title,duration;

        public viewHolder(View itemView) {
            super(itemView);
            lytvide=itemView.findViewById(R.id.lytvideo);
            videopic=itemView.findViewById(R.id.videopic);
            playicon=itemView.findViewById(R.id.playicon);
            title=itemView.findViewById(R.id.title);
            duration=itemView.findViewById(R.id.duration);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_videos, parent, false));
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
                Videolist_FeedItem item = (Videolist_FeedItem)feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.title.setTypeface(face);
                viewHolder2.duration.setTypeface(face);
                viewHolder2.title.setText(item.getTitle());
                viewHolder2.duration.setText(item.getDuration());

                ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
                ogheight *= 3.0f;
                viewHolder2.lytvide.getLayoutParams().height=Math.round(ogheight);


                Glide.with(context).load("https://img.youtube.com/vi/"+item.getVideoid()+"/0.jpg").transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.videopic);

                viewHolder2.videopic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        Video_List h=(Video_List)activity;

                        h.watchYoutubeVideo(item.getVideoid());
                    }
                });

            } catch (Exception e) {

            }
        }
    }
}
