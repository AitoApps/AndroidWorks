package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.fishapp.user.DatabaseHandler;
import com.fishapp.user.My_Order;
import com.fishapp.user.R;
import com.fishapp.user.Temp;
import data.OrderProductList_History_Feed;
import data.Order_History_Feed;
import java.util.ArrayList;
import java.util.List;

public class Order_History_listadapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    private Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face;
    Typeface face1;
    private List<Order_History_Feed> feedItems;
    private LayoutInflater inflater;
    public Order_History_listadapter(Activity activity2, List<Order_History_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
    }
    public class viewHolder extends ViewHolder {
        TextView deliverycharge;
        TextView groupid;
        ListView list;
        TextView orderdate;
        TextView status;
        TextView total;
        TextView txtdeliverycharge;
        TextView txtgroupid;
        TextView txttotal;

        public viewHolder(View itemView) {
            super(itemView);
            list = (ListView) itemView.findViewById(R.id.list);
            txtgroupid = (TextView) itemView.findViewById(R.id.txtgroupid);
            groupid = (TextView) itemView.findViewById(R.id.groupid);
            orderdate = (TextView) itemView.findViewById(R.id.orderdate);
            status = (TextView) itemView.findViewById(R.id.status);
            txttotal = (TextView) itemView.findViewById(R.id.txttoal);
            total = (TextView) itemView.findViewById(R.id.total);
            txtdeliverycharge = (TextView) itemView.findViewById(R.id.txtdeliverycharge);
            deliverycharge = (TextView) itemView.findViewById(R.id.deliverycharge);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_orderhistory, parent, false));
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
                if (position == feedItems.size() - 1 && feedItems.size() > 4) {
                    ((My_Order) activity).loadmore();
                }
                Order_History_Feed item = (Order_History_Feed) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                List<OrderProductList_History_Feed> feedItems1 = new ArrayList<>();
                OrderProductList_History_ListAdapter listAdapter = new OrderProductList_History_ListAdapter(activity, feedItems1);
                viewHolder2.list.setAdapter(listAdapter);
                feedItems1.clear();
                String[] got = item.getitemdetails().split("::");
                int p = 0;
                while (p < got.length) {
                    OrderProductList_History_Feed item2 = new OrderProductList_History_Feed();
                    item2.setitemname(got[p]);
                    p++;
                    item2.setqty(got[p]);
                    p++;
                    item2.settotalamount(got[p]);
                    feedItems1.add(item2);
                    p++;
                }
                viewHolder2.txttotal.setTypeface(face);
                viewHolder2.total.setTypeface(face);
                viewHolder2.txtgroupid.setTypeface(face);
                viewHolder2.groupid.setTypeface(face);
                viewHolder2.status.setTypeface(face);
                viewHolder2.orderdate.setTypeface(face);
                viewHolder2.txtdeliverycharge.setTypeface(face);
                viewHolder2.deliverycharge.setTypeface(face);
                viewHolder2.deliverycharge.setText(String.format("%.2f", Float.parseFloat(item.getdlcharge())));
                viewHolder2.total.setText(String.format("%.2f", Float.parseFloat(item.gettotalamount()) + Float.parseFloat(item.getdlcharge())));
                viewHolder2.list.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
                viewHolder2.orderdate.setText(item.getorderdate());
                viewHolder2.groupid.setText(Temp.orderprefix+"-"+item.getgroupid());
                if (item.getstatus().equalsIgnoreCase("0")) {
                    viewHolder2.status.setText("Please wait");
                    viewHolder2.status.setTextColor(Color.parseColor("#000000"));
                    return;
                }

                if (item.getstatus().equalsIgnoreCase("1")) {
                    viewHolder2.status.setText("Confirmed");
                    viewHolder2.status.setTextColor(Color.parseColor("#548347"));
                    return;
                }

                if (item.getstatus().equalsIgnoreCase("2")) {
                    viewHolder2.status.setText("Cancelled ");
                    viewHolder2.status.setTextColor(Color.parseColor("#e14141"));
                } else if (item.getstatus().equalsIgnoreCase("3")) {
                    viewHolder2.status.setText("Out to Delivery ");
                    viewHolder2.status.setTextColor(Color.parseColor("#e14141"));
                } else if (item.getstatus().equalsIgnoreCase("4")) {
                    viewHolder2.status.setText("Delivered ");
                    viewHolder2.status.setTextColor(Color.parseColor("#e14141"));
                } else if (item.getstatus().equalsIgnoreCase("5")) {
                    viewHolder2.status.setText("Delivery Returnd ");
                    viewHolder2.status.setTextColor(Color.parseColor("#e14141"));
                } else {
                    viewHolder2.status.setText("");
                }
            } catch (Exception e) {
            }
        }
    }
}
