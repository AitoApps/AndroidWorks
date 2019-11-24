package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.hellokhd.Amb_Transportation;
import com.hellokhd.R;
import com.hellokhd.UserDatabaseHandler;

import java.util.List;

import data.AmbuTrans_FeedItem;
import data.Videolist_FeedItem;

public class AmbuTrasn_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    float ogheight;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<AmbuTrans_FeedItem> feedItems;
    private LayoutInflater inflater;
    public AmbuTrasn_Adapter(Activity activity2, List<AmbuTrans_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
          TextView title,contact;
          ImageView call;
        public viewHolder(View itemView) {
            super(itemView);
            call=itemView.findViewById(R.id.call);
            contact=itemView.findViewById(R.id.contact);
            title=itemView.findViewById(R.id.title);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_ambtrans, parent, false));
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
                AmbuTrans_FeedItem item = (AmbuTrans_FeedItem)feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.title.setTypeface(face);
                viewHolder2.contact.setTypeface(face);
                viewHolder2.title.setText(item.getTitle());
                viewHolder2.contact.setText(item.getContact());

                viewHolder2.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Amb_Transportation h=(Amb_Transportation)activity;
                        h.call(item.getContact());
                    }
                });

            } catch (Exception e) {

            }
        }
    }
}
