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
import com.kamanam.DataBase;
import com.kamanam.InternetConnection;
import com.kamanam.R;
import java.util.List;

import data.AppHelplist_Feed;

public class AppHelpList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;
    public DataBase udb;
    public List<AppHelplist_Feed> feedItems;
    private LayoutInflater inflater;
    InternetConnection cd;
    public AppHelpList_Adapter(Activity activity2, List<AppHelplist_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new DataBase(context);
        cd=new InternetConnection(context);
        face=Typeface.createFromAsset(context.getAssets(), "fonts/proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout layout;
        TextView title;
        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            image = (ImageView) itemView.findViewById(R.id.image);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_apphelplist, parent, false));
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
                AppHelplist_Feed item = (AppHelplist_Feed) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;


                viewHolder2.title.setText(item.getMalayalam());
                String[] k = item.getFbdim().split("x");
                float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(udb.get_scrwidth()).floatValue() - 20.0f);
                viewHolder2.image.getLayoutParams().height = Math.round(calheight);
                Glide.with(context).load(item.getImageid()).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);

            } catch (Exception e) {

            }
        }
    }
}
