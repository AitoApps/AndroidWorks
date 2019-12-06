package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zemose.admin.ChatDB;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;
import data.ChatHistory_FeedItem;
import java.util.List;

public class ChatHistory_ListAdapter extends Adapter<ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    private List<ChatHistory_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;

    public class viewHolder extends ViewHolder {
        RelativeLayout adminchat;
        TextView adminchattime;
        TextView adminmsg;
        TextView chattime;
        ImageView image;
        TextView msg;
        RelativeLayout userchat;

        public viewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.userchat = (RelativeLayout) itemView.findViewById(R.id.userchat);
            this.adminchat = (RelativeLayout) itemView.findViewById(R.id.adminchat);
            this.msg = (TextView) itemView.findViewById(R.id.msg);
            this.chattime = (TextView) itemView.findViewById(R.id.chattime);
            this.adminmsg = (TextView) itemView.findViewById(R.id.adminmsg);
            this.adminchattime = (TextView) itemView.findViewById(R.id.adminchattime);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public ChatHistory_ListAdapter(Activity activity2, List<ChatHistory_FeedItem> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.cd = new ConnectionDetecter(this.context);
        this.db = new ChatDB(this.context);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "font/proxibold.otf");
        this.face1 = Typeface.createFromAsset(this.context.getAssets(), "font/proximanormal.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.custom_chathistory, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(this.context).inflate(R.layout.footerview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(this.context).inflate(R.layout.fullloaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if (position < this.feedItems.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return this.feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof viewHolder) {
            try {
                ChatHistory_FeedItem item = (ChatHistory_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                if (item.getchattype().equalsIgnoreCase("1")) {
                    viewHolder2.adminchat.setVisibility(0);
                    viewHolder2.userchat.setVisibility(8);
                    viewHolder2.adminmsg.setText(item.getmsg());
                    viewHolder2.adminchattime.setText(item.getchattime());
                } else if (item.getchattype().equalsIgnoreCase("2")) {
                    viewHolder2.adminchat.setVisibility(8);
                    viewHolder2.userchat.setVisibility(0);
                    viewHolder2.chattime.setText(item.getchattime());
                    viewHolder2.msg.setText(item.getmsg());
                }
            } catch (Exception e) {
            }
        }
    }
}
