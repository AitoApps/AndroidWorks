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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.hellokhd.ExpandableTextView;
import com.hellokhd.News;
import com.hellokhd.R;
import com.hellokhd.Shops;
import com.hellokhd.Temp;
import com.hellokhd.UserDatabaseHandler;

import java.util.List;

import data.NewsList_FeedItem;
import data.ShopsList_FeedItem;

public class NewsList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    float ogheight;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<NewsList_FeedItem> feedItems;
    private LayoutInflater inflater;
    public NewsList_Adapter(Activity activity2, List<NewsList_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "proximanormal.ttf");
    }
    public class viewHolder extends ViewHolder {

        TextView heading;
        ImageView newspic;
        ExpandableTextView news;
        public viewHolder(View itemView) {
            super(itemView);
            news=itemView.findViewById(R.id.news);
            heading=itemView.findViewById(R.id.heading);
            newspic=itemView.findViewById(R.id. newspic);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_news, parent, false));
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

                if (position == feedItems.size() - 1) {
                    ((News) activity).loadmore();
                }

                NewsList_FeedItem item = (NewsList_FeedItem)feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.news.setTypeface(face);
                viewHolder2.heading.setTypeface(face);
                ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
                ogheight *= 3.0f;

                viewHolder2.news.setText(item.getNews());
                viewHolder2.heading.setText(item.getHeading());

                viewHolder2.newspic.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder2.newspic.getLayoutParams().height=Math.round(ogheight);
                    }
                });
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"news/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.newspic);

            } catch (Exception e) {

            }
        }
    }
}
