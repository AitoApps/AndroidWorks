package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
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
import com.hellokhd.ExpandableTextView;
import com.hellokhd.R;
import com.hellokhd.Shops;
import com.hellokhd.Temp;
import com.hellokhd.UserDatabaseHandler;

import java.util.List;

import data.SchoolSearch_FeedItem;
import data.ShopsList_FeedItem;

public class ShopsList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    float ogheight;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<ShopsList_FeedItem> feedItems;
    private LayoutInflater inflater;
    public ShopsList_Adapter(Activity activity2, List<ShopsList_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {

        TextView shopname;
        ExpandableTextView discription;
        ImageView shoppic,location,call;

        public viewHolder(View itemView) {
            super(itemView);
            shopname=itemView.findViewById(R.id.shopname);
            discription=itemView.findViewById(R.id.discription);
            shoppic=itemView.findViewById(R.id.shoppic);
            location=itemView.findViewById(R.id.location);
            call=itemView.findViewById(R.id.call);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_shops, parent, false));
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
                ShopsList_FeedItem item = (ShopsList_FeedItem)feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.shopname.setTypeface(face);
                viewHolder2.discription.setTypeface(face);

                viewHolder2.shopname.setText(item.getShopname());
                viewHolder2.discription.setText(item.getDiscription());
                ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
                ogheight *= 3.0f;

                viewHolder2.shoppic.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder2.shoppic.getLayoutParams().height=Math.round(ogheight);
                    }
                });
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"shop/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.shoppic);

                if(!item.getContact().equalsIgnoreCase("") && !item.getContact().toString().toLowerCase().equalsIgnoreCase("na") && !item.getContact().toString().toLowerCase().equalsIgnoreCase("nill"))
                {
                     viewHolder2.call.setVisibility(View.VISIBLE);
                }
                else
                {
                    viewHolder2.call.setVisibility(View.GONE);
                }

                viewHolder2.call.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ShopsList_FeedItem item= (ShopsList_FeedItem)feedItems.get(position);
                        if(!item.getContact().equalsIgnoreCase("") && !item.getContact().toString().toLowerCase().equalsIgnoreCase("na") && !item.getContact().toString().toLowerCase().equalsIgnoreCase("nill"))
                        {
                            Shops h=(Shops)activity;
                            h.call(item.getContact());
                        }

                    }
                });

                viewHolder2.location.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ShopsList_FeedItem item= (ShopsList_FeedItem)feedItems.get(position);
                        Shops h=(Shops)activity;
                        h.showmap(item.getLatitude()+","+item.getLongtitude());
                    }
                });


            } catch (Exception e) {

                Log.w("possssss",Log.getStackTraceString(e));
            }
        }
    }
}
