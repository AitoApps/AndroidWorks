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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.Do_Payment;
import com.zemose.admin.R;
import com.zemose.admin.Temp_Variable;
import data.Supplierlist_FeedItem;
import java.util.List;

public class Supplier_List_ForPayment_Adapter extends Adapter<ViewHolder> {
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
    public List<Supplierlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public String userid = "";

    public class viewHolder extends ViewHolder {
        TextView contact;
        ImageView image;
        Button payment;
        TextView place;
        TextView shopname;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.shopname = (TextView) itemView.findViewById(R.id.shopname);
            this.place = (TextView) itemView.findViewById(R.id.place);
            this.contact = (TextView) itemView.findViewById(R.id.contact);
            this.payment = (Button) itemView.findViewById(R.id.payment);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Supplier_List_ForPayment_Adapter(Activity activity2, List<Supplierlist_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.supplier_list_payment_customelayout, parent, false));
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
                Supplierlist_FeedItem item = (Supplierlist_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.shopname.setText(item.getsupname());
                viewHolder2.place.setText(item.getsupplace());
                viewHolder2.contact.setText(item.getsupmobile());
                RequestManager with = Glide.with(this.context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append(item.getimgpath());
                with.load(sb.toString()).into(viewHolder2.image);
                viewHolder2.payment.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = Supplier_List_ForPayment_Adapter.this.feedItems.get(position);
                        Supplierlist_FeedItem item = (Supplierlist_FeedItem) Supplier_List_ForPayment_Adapter.this.feedItems.get(position);
                        Temp_Variable.supplierid = item.getsupid();
                        ((Do_Payment) Supplier_List_ForPayment_Adapter.this.activity).showpayment(item.getsupname(), item.getsupmobile(), "");
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
