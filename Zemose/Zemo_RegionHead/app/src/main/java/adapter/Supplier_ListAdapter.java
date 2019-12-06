package adapter;

import android.app.ProgressDialog;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zemose.regionadmin.ChatDB;
import com.zemose.regionadmin.ConnectionDetecter;
import com.zemose.regionadmin.Product_List;
import com.zemose.regionadmin.R;
import com.zemose.regionadmin.Suppliers_List;
import com.zemose.regionadmin.Temp_Variable;

import java.util.List;

import data.Supplierlist_FeedItem;

public class Supplier_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    public AppCompatActivity activity;
    public ConnectionDetecter cd;
    public Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    public List<Supplierlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public String userid = "";
    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView call;
        TextView contact;
        ImageView image;
        RelativeLayout layout;
        ImageView location;
        TextView place;
        TextView shopname;
        TextView walletamount;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.shopname = (TextView) itemView.findViewById(R.id.shopname);
            this.place = (TextView) itemView.findViewById(R.id.place);
            this.contact = (TextView) itemView.findViewById(R.id.contact);
            this.call = (ImageView) itemView.findViewById(R.id.call);
            this.walletamount = (TextView) itemView.findViewById(R.id.walletamount);
            this.location = (ImageView) itemView.findViewById(R.id.location);
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

    public Supplier_ListAdapter(AppCompatActivity activity2, List<Supplierlist_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.supplier_list_customelayout, parent, false));
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
                Supplierlist_FeedItem item = (Supplierlist_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.shopname.setText(item.getsupname());
                viewHolder2.place.setText(item.getsupplace());
                viewHolder2.contact.setText(item.getsupmobile());
                String rupee = this.context.getResources().getString(R.string.Rs);
                TextView textView = viewHolder2.walletamount;
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(item.getwalletamount());
                textView.setText(sb.toString());
                RequestManager with = Glide.with(this.context);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp_Variable.baseurl);
                sb2.append(item.getimgpath());
                with.load(sb2.toString()).into(viewHolder2.image);
                viewHolder2.call.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Supplier_ListAdapter.this.feedItems.get(position);
                        ((Suppliers_List) Supplier_ListAdapter.this.activity).call(((Supplierlist_FeedItem) Supplier_ListAdapter.this.feedItems.get(position)).getsupmobile());
                    }
                });
                viewHolder2.location.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Supplier_ListAdapter.this.feedItems.get(position);
                        Supplierlist_FeedItem item = (Supplierlist_FeedItem) Supplier_ListAdapter.this.feedItems.get(position);
                        ((Suppliers_List) Supplier_ListAdapter.this.activity).showmap(item.getsupllocation(), item.getsupname());
                    }
                });
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Supplier_ListAdapter.this.feedItems.get(position);
                        Temp_Variable.supplierid = ((Supplierlist_FeedItem) Supplier_ListAdapter.this.feedItems.get(position)).getsupid();
                        Intent i = new Intent(Supplier_ListAdapter.this.context, Product_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Supplier_ListAdapter.this.context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
