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
import com.hellokhd.Cinima;
import com.hellokhd.R;
import com.hellokhd.Shops;
import com.hellokhd.Temp;
import com.hellokhd.UserDatabaseHandler;

import java.util.List;

import data.Cinima_FeedItem;
import data.ShopsList_FeedItem;

public class CinimaList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    float ogheight;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<Cinima_FeedItem> feedItems;
    private LayoutInflater inflater;
    public CinimaList_Adapter(Activity activity2, List<Cinima_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {

        TextView theatorname,filmname,showtime,discription;
        ImageView cinimaposter,location;

        public viewHolder(View itemView) {
            super(itemView);
            theatorname=itemView.findViewById(R.id.theatorname);
            discription=itemView.findViewById(R.id.discription);
            filmname=itemView.findViewById(R.id.filmname);
            location=itemView.findViewById(R.id.location);
            showtime=itemView.findViewById(R.id.showtime);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_cinima, parent, false));
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
                Cinima_FeedItem item = (Cinima_FeedItem)feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.theatorname.setTypeface(face);
                viewHolder2.discription.setTypeface(face);
                viewHolder2.filmname.setTypeface(face);
                viewHolder2.showtime.setTypeface(face);

                viewHolder2.theatorname.setText(item.getName());
                viewHolder2.discription.setText(item.getDiscription());
                viewHolder2.filmname.setText(item.getFilmname());
                viewHolder2.showtime.setText(item.getShowtime());

                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"cinima/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.cinimaposter);

                viewHolder2.location.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Cinima_FeedItem item= (Cinima_FeedItem)feedItems.get(position);
                        Cinima h=(Cinima )activity;
                        h.showmap(item.getLatitude()+","+item.getLongtitude());
                    }
                });


            } catch (Exception e) {

            }
        }
    }
}
