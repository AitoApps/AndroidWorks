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
import com.hellokhd.MainActivity;
import com.hellokhd.R;
import com.hellokhd.Temp;

import java.util.List;

import data.FeaturedAds_FeedItem;
import data.StageList_FeedItem;

public class StageList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    public Context context;
    Typeface face;
    public List<StageList_FeedItem> feedItems;
    private LayoutInflater inflater;
    public StageList_Adapter(Activity activity2, List<StageList_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
        ImageView image,location;
        RelativeLayout layout;
        TextView stagename,km,place,stagenumber;
        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            image = itemView.findViewById(R.id.image);
            location=itemView.findViewById(R.id.location);
            stagename=itemView.findViewById(R.id.stagename);
            km=itemView.findViewById(R.id.km);
            stagenumber=itemView.findViewById(R.id.stagenumber);
            place=itemView.findViewById(R.id.place);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_stagelist, parent, false));
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
                StageList_FeedItem item = (StageList_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.stagename.setText(item.getStagename());
                viewHolder2.km.setText(item.getDistance() +" KM");
                viewHolder2.place.setText(item.getPlace());

                viewHolder2.stagename.setTypeface(face);
                viewHolder2.km.setTypeface(face);
                viewHolder2.place.setTypeface(face);
                viewHolder2.stagenumber.setTypeface(face);

                viewHolder2.stagenumber.setText("Stage : "+item.getStagenumber());
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"stagesmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);

                viewHolder2.location.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StageList_FeedItem item = (StageList_FeedItem) feedItems.get(position);
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
