package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal.ConnectionDetecter;
import com.daydeal.DatabaseHandler;
import com.daydeal.Product_List;
import com.daydeal.R;
import com.daydeal.Temp;
import data.Catogery_FeedItem;
import java.util.List;
import okhttp3.MediaType;

public class Catogery_ListAdapter extends Adapter<ViewHolder> {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    private Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler db;
    Typeface face;
    public List<Catogery_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;

    public Catogery_ListAdapter(Activity activity2, List<Catogery_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        db = new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }


    public class viewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_catogery, parent, false));
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
        if ((position != feedItems.size() || feedItems.size() <= 10) && position >= feedItems.size()) {
            return 2;
        }
        return 0;
    }

    public int getItemCount() {
        return feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                Catogery_FeedItem item = (Catogery_FeedItem) feedItems.get(position);
                final viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.image.post(new Runnable() {
                    public void run() {
                        Float ogheight = Float.valueOf(Float.valueOf(Float.parseFloat(db.getscreenwidth()) / 4.0f).floatValue() * 2.0f);
                        viewHolder2.image.getLayoutParams().height = Math.round(ogheight.floatValue());
                    }
                });
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"productcatogery/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Catogery_FeedItem item = (Catogery_FeedItem) feedItems.get(position);
                        Temp.catogeryid = item.getSn();
                        Temp.catogeryname = item.getCatname();
                        Intent i = new Intent(context, Product_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception a) {
              //  Log.w("Resukr", Log.getStackTraceString(a));
            }
        }
    }
}
