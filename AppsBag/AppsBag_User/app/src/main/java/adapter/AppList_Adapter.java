package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.appsbag.App_Details;
import com.appsbag.App_List;
import com.appsbag.ConnectionDetecter;
import com.appsbag.R;
import com.appsbag.Temp;
import com.appsbag.UserDatabaseHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import data.AppCatogery_FeedItem;
import data.Applist_Feed;
import es.dmoral.toasty.Toasty;

public class AppList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<Applist_Feed> feedItems;
    private LayoutInflater inflater;
    ConnectionDetecter cd;
    public AppList_Adapter(Activity activity2, List<Applist_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        cd=new ConnectionDetecter(context);
        face=Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
        ImageView image;
        RelativeLayout layout;
        TextView title;
        TextView install;
        AdView adView1;
        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            image = (ImageView) itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            install=itemView.findViewById(R.id.install);
            adView1=itemView.findViewById(R.id.adView1);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_applist, parent, false));
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
                Applist_Feed item = (Applist_Feed) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;

                viewHolder2.title.setText(item.getAppname());

                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"applogo/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);

                AdRequest adreq1 = new AdRequest.Builder().build();
                viewHolder2.adView1.loadAd(adreq1);

                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        if (cd.isConnectingToInternet()) {

                            Temp.appid = item.getSn();
                            Temp.appname=item.getAppname();
                            Temp.appurl=item.getAppurl();
                            String[] p=item.getDisctiltle().split(":%");
                            Temp.appheader=p[1];
                            String[] p1=item.getDisctiltle().split(":%");
                            Temp.appfooter=p1[1];
                            Intent i = new Intent(context, App_Details.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                        else
                        {
                            Toasty.info(context, Temp.nointernet, 0).show();
                        }

                    }
                });
                viewHolder2.install.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cd.isConnectingToInternet()) {

                            Temp.appid = item.getSn();
                            Temp.appname=item.getAppname();
                            Temp.appurl=item.getAppurl();
                            String[] p=item.getDisctiltle().split(":%");
                            Temp.appheader=p[1];
                            String[] p1=item.getDisctiltle().split(":%");
                            Temp.appfooter=p1[1];
                            Intent i = new Intent(context, App_Details.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                        else
                        {
                            Toasty.info(context, Temp.nointernet, 0).show();
                        }
                    }
                });
            } catch (Exception e) {

            }
        }
    }
}
