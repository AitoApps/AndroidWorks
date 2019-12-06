package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;

import java.util.List;

import data.Wallet_History_FeedItem;

public class Wallet_History_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    private AppCompatActivity activity;
    public ConnectionDetecter cd;
    private Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    private List<Wallet_History_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String userid = "";

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        TextView date;
        TextView mode;
        TextView name;

        public viewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.mode = (TextView) itemView.findViewById(R.id.mode);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Wallet_History_ListAdapter(AppCompatActivity activity2, List<Wallet_History_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.wallethistory_customlayout, parent, false));
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

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            try {
                Wallet_History_FeedItem item = (Wallet_History_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.name.setText(item.getshopname());
                String rupee = this.context.getResources().getString(R.string.Rs);
                TextView textView = viewHolder2.amount;
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(item.getamount());
                textView.setText(sb.toString());
                viewHolder2.date.setText(item.gettimeStamp());
                viewHolder2.mode.setText(item.getpaymentMethod());
            } catch (Exception e) {
            }
        }
    }
}
