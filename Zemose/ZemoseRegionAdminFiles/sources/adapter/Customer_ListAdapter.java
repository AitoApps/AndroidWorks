package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zemose.regionadmin.ChatDB;
import com.zemose.regionadmin.ConnectionDetecter;
import com.zemose.regionadmin.Customers_List;
import com.zemose.regionadmin.R;
import data.Customerlist_FeedItem;
import java.util.List;

public class Customer_ListAdapter extends Adapter<ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    /* access modifiers changed from: private */
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    /* access modifiers changed from: private */
    public List<Customerlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public String userid = "";

    public class viewHolder extends ViewHolder {
        ImageView call;
        TextView contact;
        TextView cusname;

        public viewHolder(View itemView) {
            super(itemView);
            this.call = (ImageView) itemView.findViewById(R.id.call);
            this.cusname = (TextView) itemView.findViewById(R.id.cusname);
            this.contact = (TextView) itemView.findViewById(R.id.contact);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Customer_ListAdapter(Activity activity2, List<Customerlist_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.customer_list_customelayout, parent, false));
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

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                Customerlist_FeedItem item = (Customerlist_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.cusname.setText(item.getcustname());
                viewHolder2.contact.setText(item.getcustmobile());
                viewHolder2.call.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Customer_ListAdapter.this.feedItems.get(position);
                        ((Customers_List) Customer_ListAdapter.this.activity).call(((Customerlist_FeedItem) Customer_ListAdapter.this.feedItems.get(position)).getcustmobile());
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
