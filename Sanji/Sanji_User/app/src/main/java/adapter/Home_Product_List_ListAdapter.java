package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.sanji.DatabaseHandler;
import com.sanji.R;
import com.sanji.Shop_List;
import com.sanji.Temp;
import data.Home_Product_List_FeedItem;
import data.ShoplistHome_Feed;
import java.util.ArrayList;
import java.util.List;

public class Home_Product_List_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    public Context context;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Home_Product_List_FeedItem> feedItems;
    private LayoutInflater inflater;
    public Home_Product_List_ListAdapter(Activity activity2, List<Home_Product_List_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db=new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
        face2 = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
    }
    
    public class viewHolder extends ViewHolder {
        TextView catogeryname;
        RelativeLayout layout;
        RecyclerView recylerview;
        ImageView viewall;

        public viewHolder(View itemView) {
            super(itemView);
            recylerview = (RecyclerView) itemView.findViewById(R.id.recylerview);
            catogeryname = (TextView) itemView.findViewById(R.id.catogeryname);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            viewall = (ImageView) itemView.findViewById(R.id.viewall);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_homelist, parent, false));
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
                Home_Product_List_FeedItem item = (Home_Product_List_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                List<ShoplistHome_Feed> feedItems2 = new ArrayList<>();
                ShoplistHome_ListAdapter listAdapter2 = new ShoplistHome_ListAdapter(activity, feedItems2);
                viewHolder2.recylerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                viewHolder2.recylerview.setAdapter(listAdapter2);
                feedItems2.clear();
                String[] got2 = item.getShops().split("::");

                for(int p=0;p<got2.length;p++)
                {
                    ShoplistHome_Feed item2 = new ShoplistHome_Feed();
                    item2.setSn(got2[p]);
                    p=p+1;
                    item2.setShopname(got2[p]);
                    p=p+1;
                    item2.setShopimgisg(got2[p]);
                    p=p+1;
                    item2.setDistance(String.format("%.2f", Double.parseDouble(got2[p]))+" KM");
                    p=p+1;
                    item2.setPlace(got2[p]);
                    p=p+1;
                    item2.setWebsite(got2[p]);
                    p=p+1;
                    item2.setInstagram(got2[p]);
                    p=p+1;
                    item2.setFacebook(got2[p]);
                    p=p+1;
                    item2.setPinterest(got2[p]);
                    p=p+1;
                    item2.setYoutube(got2[p]);
                    p=p+1;
                    item2.setLatitude(got2[p]);
                    p=p+1;
                    item2.setLongtitude(got2[p]);
                    feedItems2.add(item2);
                }

                viewHolder2.recylerview.setVisibility(View.VISIBLE);
                listAdapter2.notifyDataSetChanged();
                viewHolder2.catogeryname.setText(item.getCatname());
                viewHolder2.catogeryname.setTypeface(face1);
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Home_Product_List_FeedItem item = (Home_Product_List_FeedItem) feedItems.get(position);
                        Temp.shopcat = item.getCatid();
                        Temp.catogeryname = item.getCatname();
                        Intent i = new Intent(context, Shop_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                viewHolder2.viewall.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Home_Product_List_FeedItem item = (Home_Product_List_FeedItem) feedItems.get(position);
                        Temp.shopcat = item.getCatid();
                        Temp.catogeryname = item.getCatname();
                        Intent i = new Intent(context, Shop_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
