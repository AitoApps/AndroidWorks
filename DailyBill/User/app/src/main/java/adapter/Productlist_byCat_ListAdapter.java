package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dailybill.DatabaseHandler;
import com.dailybill.R;
import com.dailybill.Temp;

import data.Productlist_ByCatogery_FeedItem;
import java.util.List;

public class Productlist_byCat_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    private Context context;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    Typeface face2;
    private List<Productlist_ByCatogery_FeedItem> feedItems;
    private LayoutInflater inflater;
    public Productlist_byCat_ListAdapter(Activity activity2, List<Productlist_ByCatogery_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db=new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "Rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
        face2 = Typeface.createFromAsset(context.getAssets(), "proximanormal.ttf");

    }
    public class viewHolder extends ViewHolder {
        Button buy;
        ImageView image;
        RelativeLayout layout;
        TextView productname;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            productname = (TextView) itemView.findViewById(R.id.productname);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            buy = (Button) itemView.findViewById(R.id.buy);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_produtclist_bycatogery, parent, false));
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

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            try {
                Productlist_ByCatogery_FeedItem item = (Productlist_ByCatogery_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                try {
                    viewHolder2.productname.setText(item.getItemname().toUpperCase());
                } catch (Exception e) {
                    viewHolder2.productname.setText(item.getItemname());
                }
                viewHolder2.productname.setTypeface(face1);
                viewHolder2.buy.setTypeface(face1);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgisg()));
                Glide.with(context).load(Temp.weblink+"productimage/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.buy.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                    }
                });
                viewHolder2.image.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                    }
                });
            } catch (Exception e2) {
            }
        }
    }
}
