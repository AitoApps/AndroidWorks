package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.dreamkitmaker_feeds.MainActivity;
import com.dreamkitmaker_feeds.R;
import com.dreamkitmaker_feeds.Temp;

import java.util.List;

import data.Colors_FeedItem;

public class ColorsList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;

    public List<Colors_FeedItem> feedItems;
    private LayoutInflater inflater;
    public ColorsList_Adapter(Activity activity2, List<Colors_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }
    public class viewHolder extends ViewHolder {

        View color_picker_view,border;
        public viewHolder(View itemView) {
            super(itemView);
            border=itemView.findViewById(R.id.border);
            color_picker_view = itemView.findViewById(R.id.color_picker_view);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.color_picker_item_list, parent, false));
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
                Colors_FeedItem item = (Colors_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;

                viewHolder2.color_picker_view.setBackgroundColor(Color.parseColor(item.getColorcode()));

                if(item.getIsselected().equalsIgnoreCase("1"))
                {
                    viewHolder2.border.setVisibility(View.VISIBLE);
                }
                else if(item.getIsselected().equalsIgnoreCase("0"))
                {
                    viewHolder2.border.setVisibility(View.GONE);
                }
                viewHolder2.color_picker_view.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Colors_FeedItem item = (Colors_FeedItem) feedItems.get(position);
                        Temp.color=item.getColorcode();
                        MainActivity m=(MainActivity)activity;
                        m.setbrushstyle();
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
