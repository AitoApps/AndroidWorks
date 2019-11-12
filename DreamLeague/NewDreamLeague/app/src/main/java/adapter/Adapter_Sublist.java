package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlkitmaker_feeds.KitView;
import com.dlkitmaker_feeds.R;
import com.dlkitmaker_feeds.Temp;

import java.util.List;
import data.Feed_Sublist;

public class Adapter_Sublist extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Feed_Sublist> feedItems;
    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NULL = 2;
    ProgressDialog pb;
    Typeface face;
    public Adapter_Sublist(Activity activity, List<Feed_Sublist> feedItems) {
        this.activity = activity;
        context=activity.getApplicationContext();
        this.feedItems = feedItems;
        pb=new ProgressDialog(activity);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.subkit_customlayout, parent, false);
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


        TextView title;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
              title=itemView.findViewById(R.id.title);
              layout=itemView.findViewById(R.id.layout);

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
                final Feed_Sublist item = feedItems.get(position);
                final Adapter_Sublist.viewHolder viewHolder = (Adapter_Sublist.viewHolder) holder;

                viewHolder.title.setText(item.getteamname());

                viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Feed_Sublist item = feedItems.get(position);
                        item = feedItems.get(position);
                        Temp.teamid=item.getteamid();
                        Intent i=new Intent(context, KitView.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });






            } catch (Exception a) {

               // Toasty.info(context, Log.getStackTraceString(a),Toast.LENGTH_SHORT).show();

            }

        }
    }


}
