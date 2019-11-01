package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.sanji.DatabaseHandler;
import com.sanji.Product_List;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Productcat_Feeditem;
import java.util.List;

public class Productcat_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");

    public List<Productcat_Feeditem> feedItems;
    private LayoutInflater inflater;
    UserDatabaseHandler udb = new UserDatabaseHandler(context);

    public class viewHolder extends ViewHolder {
        TextView catogeryname;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
            catogeryname = (TextView) itemView.findViewById(R.id.catogeryname);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Productcat_ListAdapter(Activity activity2, List<Productcat_Feeditem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.catogerylist, parent, false));
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
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.catogeryname.setText(((Productcat_Feeditem) feedItems.get(position)).getproductname());
                viewHolder2.catogeryname.setTypeface(face);
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Productcat_Feeditem productcat_Feeditem = (Productcat_Feeditem) Productcat_ListAdapter.feedItems.get(position);
                            Productcat_Feeditem item = (Productcat_Feeditem) Productcat_ListAdapter.feedItems.get(position);
                            Temp.catogeryname = item.getproductname();
                            Productcat_ListAdapter.db.addproductcat(item.getsn());
                            Intent i = new Intent(Productcat_ListAdapter.context, Product_List.class);
                            i.setFlags(268435456);
                            Productcat_ListAdapter.context.startActivity(i);
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
