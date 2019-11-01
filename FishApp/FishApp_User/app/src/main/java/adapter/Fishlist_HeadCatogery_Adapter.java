package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.fishapp.user.Fish_List;
import com.fishapp.user.R;
import com.fishapp.user.Temp;

import java.util.List;

import data.FishCatogery_FeedItem;

public class Fishlist_HeadCatogery_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;

    public List<FishCatogery_FeedItem> feedItems;
    private LayoutInflater inflater;
    public Fishlist_HeadCatogery_Adapter(Activity activity2, List<FishCatogery_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
        TextView title;
        ImageView catpic;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            title = (TextView) itemView.findViewById(R.id.title);
            catpic = (ImageView) itemView.findViewById(R.id.catpic);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_fish_catogery, parent, false));
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
                FishCatogery_FeedItem item = (FishCatogery_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.title.setText(item.getCatogery());
                viewHolder2.title.setTypeface(face,Typeface.BOLD);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"fishcatogery/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.catpic);

                if(item.getSn().equalsIgnoreCase(Temp.clientcatid))
                {
                    viewHolder2.title.setTextColor(Color.parseColor("#000000"));
                }
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        FishCatogery_FeedItem item = (FishCatogery_FeedItem) feedItems.get(position);
                        Temp.clientcatid = item.getSn();
                        Temp.clientcatname = item.getCatogery();
                        Temp.movepos=position;
                        Fish_List f=(Fish_List)activity;
                        f.reload();
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
