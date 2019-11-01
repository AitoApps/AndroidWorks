package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.sanji.DatabaseHandler;
import com.sanji.Productist_ByCatogery_FromShops;
import com.sanji.R;
import com.sanji.Temp;
import data.Product_List_Byshops_ByCat_FeedItem;
import data.Productlist_new_FeedItem;
import java.util.ArrayList;
import java.util.List;

public class Product_List_ByShops_ByCat_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public Activity activity;
    public Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Product_List_Byshops_ByCat_FeedItem> feedItems;
    private LayoutInflater inflater;

    public Product_List_ByShops_ByCat_ListAdapter(Activity activity2, List<Product_List_Byshops_ByCat_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
        face2 = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
    }
    
    public class viewHolder extends ViewHolder {
        TextView catogeryname;
        RelativeLayout heading;
        RelativeLayout layout;
        RecyclerView recylerview;
        TextView viewall;

        public viewHolder(View itemView) {
            super(itemView);
            recylerview = (RecyclerView) itemView.findViewById(R.id.recylerview);
            catogeryname = (TextView) itemView.findViewById(R.id.catogeryname);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            heading = (RelativeLayout) itemView.findViewById(R.id.heading);
            viewall = (TextView) itemView.findViewById(R.id.viewall);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_productlist_byshops, parent, false));
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
                final Product_List_Byshops_ByCat_FeedItem item = (Product_List_Byshops_ByCat_FeedItem) feedItems.get(position);
                final viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.heading.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (viewHolder2.recylerview.getVisibility() == View.GONE) {
                            viewHolder2.recylerview.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder2.recylerview.setVisibility(View.GONE);
                        }
                    }
                });
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        List<Productlist_new_FeedItem> feedItems2 = new ArrayList<>();
                        Product_Listnew_ListAdapter listAdapter2 = new Product_Listnew_ListAdapter(activity, feedItems2);
                        viewHolder2.recylerview.setLayoutManager(new GridLayoutManager(context, 2));
                        viewHolder2.recylerview.setAdapter(listAdapter2);
                        feedItems2.clear();
                        String[] got = item.getProducts().split(":%");
                        for(int m=0;m<got.length;m++)
                        {
                            Productlist_new_FeedItem item2 = new Productlist_new_FeedItem();
                            item2.setSn(got[m]);
                            m=m+1;
                            item2.setProductcat(got[m]);
                            m=m+1;
                            item2.setShopid(got[m]);
                            m=m+1;
                            item2.setItemname(got[m]);
                            m=m+1;
                            item2.setPrice(got[m]);
                            m=m+1;
                            item2.setOgprice(got[m]);
                            m=m+1;
                            item2.setItemdiscription(got[m]);
                            m=m+1;
                            item2.setMinorder(got[m]);
                            m=m+1;
                            item2.setUnittype(got[m]);
                            m=m+1;
                            item2.setImgsig1(got[m]);
                            m=m+1;
                            item2.setShopname(got[m]);
                            m=m+1;
                            item2.setShopplace(got[m]);
                            m=m+1;
                            item2.setShopmobile(got[m]);
                            m=m+1;
                            item2.setShoptime(got[m]);
                            m=m+1;
                            item2.setLocation(got[m]);
                            m=m+1;
                            item2.setDelicharge(got[m]);
                            m=m+1;
                            item2.setDelidisc(got[m]);
                            m=m+1;
                            item2.setMinordramt(got[m]);
                            m=m+1;
                            item2.setShopimgsig(got[m]);
                            feedItems2.add(item2);
                        }
                       
                        viewHolder2.recylerview.setVisibility(View.VISIBLE);
                        listAdapter2.notifyDataSetChanged();
                    }
                });
                viewHolder2.catogeryname.setText(item.getCatname());
                viewHolder2.catogeryname.setTypeface(face1);
                viewHolder2.viewall.setTypeface(face);
                viewHolder2.viewall.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_List_Byshops_ByCat_FeedItem item = (Product_List_Byshops_ByCat_FeedItem) feedItems.get(position);
                        Temp.catogeryname = item.getCatname();
                        Temp.shopcat = item.getCatid();
                        Intent i = new Intent(context, Productist_ByCatogery_FromShops.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
