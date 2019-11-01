package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.DatabaseHandler;
import com.sanji.Shop_List;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.productCatogery_New_Feeditem;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class ProductCatogery_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
    Typeface face1 = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    Typeface face2 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");

    public List<productCatogery_New_Feeditem> feedItems;
    private LayoutInflater inflater;
    UserDatabaseHandler udb = new UserDatabaseHandler(context);

    public class viewHolder extends ViewHolder {
        ImageView catimg;
        TextView catogeryname;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
            catimg = (ImageView) itemView.findViewById(R.id.catimg);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            catogeryname = (TextView) itemView.findViewById(R.id.catogeryname);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public ProductCatogery_ListAdapter(Activity activity2, List<productCatogery_New_Feeditem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_productcat, parent, false));
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
                productCatogery_New_Feeditem item = (productCatogery_New_Feeditem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.catogeryname.setTypeface(face1);
                viewHolder2.catogeryname.setText(item.getCatogery());
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                RequestManager with = Glide.with(context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productcatogery/");
                sb.append(item.getSn());
                sb.append(".jpg");
                with.load(sb.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.catimg);
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        productCatogery_New_Feeditem productcatogery_new_feeditem = (productCatogery_New_Feeditem) ProductCatogery_ListAdapter.feedItems.get(position);
                        productCatogery_New_Feeditem item = (productCatogery_New_Feeditem) ProductCatogery_ListAdapter.feedItems.get(position);
                        Temp.shopcat = item.getSn();
                        Temp.catogeryname = item.getCatogery();
                        Intent i = new Intent(ProductCatogery_ListAdapter.context, Shop_List.class);
                        i.setFlags(268435456);
                        ProductCatogery_ListAdapter.context.startActivity(i);
                    }
                });
            } catch (Exception a) {
                Toasty.info(context, Log.getStackTraceString(a), 1).show();
            }
        }
    }
}
