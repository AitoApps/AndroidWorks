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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;
import com.zemose.admin.Temp_Variable;
import com.zemose.admin.Transfered_Report;
import data.Sna_FeedItem;
import java.util.List;

public class Transfered_ListAdapter extends Adapter<ViewHolder> {
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
    public List<Sna_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String userid = "";

    public class viewHolder extends ViewHolder {
        TextView customer;
        TextView date;
        ImageView image;
        TextView name;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.customer = (TextView) itemView.findViewById(R.id.customer);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Transfered_ListAdapter(Activity activity2, List<Sna_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.sna_customlayout, parent, false));
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
                Sna_FeedItem item = (Sna_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.name.setText(item.getproductname());
                viewHolder2.date.setText(item.getorderdate());
                viewHolder2.customer.setText(item.getcustomername());
                String string = this.context.getResources().getString(R.string.Rs);
                RequestManager with = Glide.with(this.context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append(item.getproductimage());
                with.load(sb.toString()).into(viewHolder2.image);
                viewHolder2.customer.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Transfered_ListAdapter.this.feedItems.get(position);
                        ((Transfered_Report) Transfered_ListAdapter.this.activity).call(((Sna_FeedItem) Transfered_ListAdapter.this.feedItems.get(position)).getcustomercontact());
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
