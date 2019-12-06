package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zemose.regionadmin.ChatDB;
import com.zemose.regionadmin.ConnectionDetecter;
import com.zemose.regionadmin.R;
import com.zemose.regionadmin.Temp_Variable;
import data.Productlist_FeedItem;
import java.util.List;

public class Product_ListAdapter extends Adapter<ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    private List<Productlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public String userid = "";

    public class viewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout layout;
        TextView productname;
        TextView qty;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.productname = (TextView) itemView.findViewById(R.id.productname);
            this.qty = (TextView) itemView.findViewById(R.id.qty);
            this.layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Product_ListAdapter(Activity activity2, List<Productlist_FeedItem> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.cd = new ConnectionDetecter(this.context);
        this.db = new ChatDB(this.context);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "font/proxibold.otf");
        this.face1 = Typeface.createFromAsset(this.context.getAssets(), "font/proximanormal.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.product_customlayout, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(this.context).inflate(R.layout.footerview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(this.context).inflate(R.layout.fullloaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if ((position != this.feedItems.size() || this.feedItems.size() <= 10) && position >= this.feedItems.size()) {
            return 2;
        }
        return 0;
    }

    public int getItemCount() {
        return this.feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            try {
                Productlist_FeedItem item = (Productlist_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                if (item.getname().contains(":%")) {
                    viewHolder2.productname.setText(item.getname().split(":%")[0]);
                } else {
                    viewHolder2.productname.setText(item.getname());
                }
                viewHolder2.qty.setText(item.getproductqty());
                RequestManager with = Glide.with(this.context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append(item.getimgpath());
                with.load(sb.toString()).into(viewHolder2.image);
            } catch (Exception e) {
            }
        }
    }
}
