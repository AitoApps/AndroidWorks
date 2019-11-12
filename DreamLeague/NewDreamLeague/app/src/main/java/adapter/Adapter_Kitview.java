package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlkitmaker_feeds.DB;
import com.dlkitmaker_feeds.R;

import java.util.List;

import data.Feed_KitView;
import es.dmoral.toasty.Toasty;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class Adapter_Kitview extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Feed_KitView> feedItems;
    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NULL = 2;
    ProgressDialog pb;
    public DB db;
    public Adapter_Kitview(Activity activity, List<Feed_KitView> feedItems) {
        this.activity = activity;
        context=activity.getApplicationContext();
        this.feedItems = feedItems;
        pb=new ProgressDialog(activity);
        db=new DB(context);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.kitview_customlayout, parent, false);
            return new viewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(context).inflate(R.layout.footerview, parent, false);
            return new viewHolderFooter(v);
        }
        else if (viewType == TYPE_NULL) {
            View v = LayoutInflater.from(context).inflate(R.layout.fullloaded, parent, false);
            return new viewHolderFooter(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == feedItems.size() && feedItems.size()>10)
            return TYPE_FOOTER;
        else if(position < feedItems.size())
            return TYPE_ITEM;
        else
            return TYPE_NULL;
    }

    @Override
    public int getItemCount() {
        return feedItems.size()+1;
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView kittype;
        RelativeLayout layout;
        ImageView image;
        Button copyurl;
        public viewHolder(View itemView) {
            super(itemView);
            kittype=itemView.findViewById(R.id.kittype);
              layout=itemView.findViewById(R.id.layout);
              image=itemView.findViewById(R.id.image);
              copyurl=itemView.findViewById(R.id.copyurl);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = itemView.findViewById(R.id.layout1);

        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {

            try {
                final Feed_KitView item = feedItems.get(position);
                final Adapter_Kitview.viewHolder viewHolder = (Adapter_Kitview.viewHolder) holder;

                viewHolder.kittype.setText(item.getkitname());
                float ogwidth=(Float.valueOf(db.getscreenwidth()));
                viewHolder.image.getLayoutParams().height=Math.round(ogwidth);

                Glide.with(context).load(item.getimgurl()).transition(withCrossFade()).into(viewHolder.image);

               viewHolder.copyurl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try
                        {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("salmansuhi", item.getimgurl());
                            clipboard.setPrimaryClip(clip);
                            Toasty.success(context, "Kit Link Copied", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception a)
                        {

                        }
                    }
                });



            } catch (Exception a) {


            }

        }
    }


}
