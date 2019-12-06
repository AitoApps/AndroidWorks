package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zemose.admin.ChatDB;
import com.zemose.admin.Chat_History;
import com.zemose.admin.Chatting;
import com.zemose.admin.ConnectionDetecter;
import com.zemose.admin.R;
import com.zemose.admin.Temp_Variable;
import data.ChatHeads_FeedItem;
import java.util.List;

public class ChatHeads_ListAdapter extends Adapter<ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    private Activity activity;
    public ConnectionDetecter cd;
    /* access modifiers changed from: private */
    public Context context;
    public ChatDB db;
    Typeface face;
    Typeface face1;
    /* access modifiers changed from: private */
    public List<ChatHeads_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;

    public class viewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout layout;
        RelativeLayout lycount;
        ImageView medal;
        TextView msg;
        TextView txtcount;
        TextView username;

        public viewHolder(View itemView) {
            super(itemView);
            this.medal = (ImageView) itemView.findViewById(R.id.medal);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.username = (TextView) itemView.findViewById(R.id.username);
            this.msg = (TextView) itemView.findViewById(R.id.msg);
            this.lycount = (RelativeLayout) itemView.findViewById(R.id.lycount);
            this.txtcount = (TextView) itemView.findViewById(R.id.txtcount);
            this.layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            this.layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public ChatHeads_ListAdapter(Activity activity2, List<ChatHeads_FeedItem> feedItems2) {
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
            return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.custom_chat, parent, false));
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
        if (position == this.feedItems.size() && this.feedItems.size() > 10) {
            return 1;
        }
        if (position < this.feedItems.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return this.feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                ChatHeads_FeedItem item = (ChatHeads_FeedItem) this.feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.username.setText(item.getusername());
                viewHolder2.username.setTypeface(this.face);
                RequestManager with = Glide.with(this.context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp_Variable.baseurl);
                sb.append("images/customerImageThumb/");
                sb.append(item.getuserid());
                sb.append(".jpg");
                with.load(sb.toString()).into(viewHolder2.image);
                viewHolder2.msg.setText(this.db.get_lastmsg(item.getissupplier()));
                viewHolder2.msg.setTypeface(this.face);
                if (this.db.get_unreadcount(item.getissupplier()).equalsIgnoreCase("0")) {
                    viewHolder2.lycount.setVisibility(8);
                } else {
                    viewHolder2.lycount.setVisibility(0);
                    viewHolder2.txtcount.setText(this.db.get_unreadcount(item.getissupplier()));
                }
                if (item.getissupplier().contains("s_")) {
                    viewHolder2.medal.setVisibility(0);
                } else if (item.getissupplier().contains("c_")) {
                    viewHolder2.medal.setVisibility(8);
                }
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Object obj = ChatHeads_ListAdapter.this.feedItems.get(position);
                        ChatHeads_FeedItem item = (ChatHeads_FeedItem) ChatHeads_ListAdapter.this.feedItems.get(position);
                        Temp_Variable.chatuserid = item.getuserid();
                        Temp_Variable.chatusername = item.getusername();
                        Temp_Variable.issupplier = item.getissupplier();
                        ChatHeads_ListAdapter.this.db.updatereadcount(item.getuserid());
                        Intent i = new Intent(ChatHeads_ListAdapter.this.context, Chat_History.class);
                        i.setFlags(268435456);
                        ChatHeads_ListAdapter.this.context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        } else if (holder instanceof viewHolderFooter) {
            ViewHolder viewHolder3 = holder;
            if (this.feedItems.size() > 0) {
                ((Chatting) this.activity).loaddata();
            }
        }
    }
}
