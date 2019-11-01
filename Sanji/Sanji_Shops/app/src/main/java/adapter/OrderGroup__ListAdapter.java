package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.DatabaseHandler;
import com.sanji_shop.Order_Group;
import com.sanji_shop.Order_List;
import com.sanji_shop.R;
import com.sanji_shop.Temp;
import com.sanji_shop.UserDatabaseHandler;
import com.sanji_shops.R;

import data.OrderGroup_Feeditem;
import java.util.List;

public class OrderGroup__ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler db;
    Typeface face;
    public List<OrderGroup_Feeditem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    UserDatabaseHandler udb;

    public class viewHolder extends ViewHolder {
        RelativeLayout layout;
        TextView oid;
        TextView orderdate;
        TextView totalcost;
        TextView txtoid;
        TextView txtorderdate;

        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            txtoid = (TextView) itemView.findViewById(R.id.txtoid);
            oid = (TextView) itemView.findViewById(R.id.oid);
            txtorderdate = (TextView) itemView.findViewById(R.id.txtorderdate);
            orderdate = (TextView) itemView.findViewById(R.id.orderdate);
            totalcost = (TextView) itemView.findViewById(R.id.totalcost);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public OrderGroup__ListAdapter(Activity activity2, List<OrderGroup_Feeditem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db = new DatabaseHandler(context);
        udb = new UserDatabaseHandler(context);
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_ordergroup, parent, false));
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
                if (position == feedItems.size() - 1 && feedItems.size() > 10) {
                    ((Order_Group) activity).loadmore();
                }
                OrderGroup_Feeditem item = (OrderGroup_Feeditem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.txtoid.setTypeface(face);
                viewHolder2.oid.setTypeface(face);
                viewHolder2.txtorderdate.setTypeface(face);
                viewHolder2.orderdate.setTypeface(face);
                viewHolder2.totalcost.setTypeface(face);
                String rupee = context.getResources().getString(R.string.Rs);
                TextView textView = viewHolder2.oid;
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.grpid);
                sb.append("-");
                sb.append(item.getgroupid());
                textView.setText(sb.toString());
                viewHolder2.orderdate.setText(item.getorderdate());
                TextView textView2 = viewHolder2.totalcost;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(rupee);
                sb2.append(Float.parseFloat(item.gettotal()) + Float.parseFloat(item.getdelicharge()));
                textView2.setText(sb2.toString());
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        OrderGroup_Feeditem orderGroup_Feeditem = (OrderGroup_Feeditem) OrderGroup__ListAdapter.feedItems.get(position);
                        OrderGroup_Feeditem item = (OrderGroup_Feeditem) OrderGroup__ListAdapter.feedItems.get(position);
                        Temp.ordergroupid = item.getgroupid();
                        Temp.deliverycharge = item.getdelicharge();
                        Intent i = new Intent(OrderGroup__ListAdapter.context, Order_List.class);
                        i.setFlags(268435456);
                        OrderGroup__ListAdapter.context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
