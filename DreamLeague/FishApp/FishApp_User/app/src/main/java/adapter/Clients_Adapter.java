package adapter;

import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fishapp.user.Client_List;
import com.fishapp.user.Image_viewer;
import com.fishapp.user.R;
import com.fishapp.user.Temp;
import data.Clients_FeedItem;
import java.util.List;

public class Clients_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;

    public List<Clients_FeedItem> feedItems;
    private LayoutInflater inflater;
    public Clients_Adapter(Activity activity2, List<Clients_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }

    public class viewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout layout;
        TextView name;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            name = (TextView) itemView.findViewById(R.id.name);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_clients, parent, false));
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
                if (position == feedItems.size() - 1 && feedItems.size() > 40) {
                    if (Temp.isclientsearch == 0) {
                        ((Client_List) activity).loadmore();
                    } else if (Temp.isclientsearch == 1) {
                        ((Client_List) activity).loadmore_search();
                    }
                }
                Clients_FeedItem item = (Clients_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"advt/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.name.setText(item.getName());
                viewHolder2.name.setTypeface(face);
                viewHolder2.image.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {

                        Clients_FeedItem item = (Clients_FeedItem) feedItems.get(position);
                        Temp.img_title = item.getName();
                        Temp.imgsig = item.getImgsig();
                        StringBuilder sb = new StringBuilder();
                        sb.append(Temp.weblink);
                        sb.append("clients/");
                        sb.append(item.getSn());
                        sb.append(".jpg");
                        Temp.img_link = sb.toString();
                        Intent i = new Intent(context, Image_viewer.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
