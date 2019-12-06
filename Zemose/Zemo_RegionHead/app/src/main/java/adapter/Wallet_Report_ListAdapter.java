package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zemose.regionadmin.ChatDB;
import com.zemose.regionadmin.ConnectionDetecter;
import com.zemose.regionadmin.R;
import com.zemose.regionadmin.Temp_Variable;
import com.zemose.regionadmin.Wallets_Report;

import java.util.List;

import data.Wallet_Report_FeedItem;

public class Wallet_Report_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;

    public AppCompatActivity activity;
    public ConnectionDetecter cd;
    private Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;

    public List<Wallet_Report_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String userid = "";

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        ImageView image;
        RelativeLayout layout;
        RelativeLayout lytcall;
        TextView name;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.lytcall = (RelativeLayout) itemView.findViewById(R.id.lytcall);
            this.layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Wallet_Report_ListAdapter(AppCompatActivity activity2, List<Wallet_Report_FeedItem> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.cd = new ConnectionDetecter(this.context);
        this.db = new ChatDB(this.context);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "font/proxibold.otf");
        this.face1 = Typeface.createFromAsset(this.context.getAssets(), "font/proximanormal.ttf");
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.walletreport_custom, parent, false));
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

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                Wallet_Report_FeedItem item = (Wallet_Report_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.name.setText(item.getname());
                String rupee = this.context.getResources().getString(R.string.Rs);
                RequestManager with = Glide.with(this.context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append("images/shopImages/");
                sb.append(item.getuserid());
                sb.append(".jpg");
                with.load(sb.toString()).into(viewHolder2.image);
                TextView textView = viewHolder2.amount;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(rupee);
                sb2.append(item.getamount());
                textView.setText(sb2.toString());
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Wallet_Report_ListAdapter.this.feedItems.get(position);
                        Wallet_Report_FeedItem item = (Wallet_Report_FeedItem) Wallet_Report_ListAdapter.this.feedItems.get(position);
                        Wallet_Report_ListAdapter.this.userid = item.getuserid();
                        Wallet_Report_ListAdapter wallet_Report_ListAdapter = Wallet_Report_ListAdapter.this;
                        wallet_Report_ListAdapter.pos = position;
                        ((Wallets_Report) wallet_Report_ListAdapter.activity).show_supplier(item.getname(), item.getuserid(), position);
                    }
                });
                viewHolder2.lytcall.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Wallet_Report_ListAdapter.this.feedItems.get(position);
                        ((Wallets_Report) Wallet_Report_ListAdapter.this.activity).call(((Wallet_Report_FeedItem) Wallet_Report_ListAdapter.this.feedItems.get(position)).getcontact());
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
