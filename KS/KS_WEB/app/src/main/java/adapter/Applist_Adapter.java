package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.mal_suthra.App_Deatils;
import com.mal_suthra.NetConnect;
import com.mal_suthra.R;
import com.mal_suthra.Static_Veriable;

import java.util.List;

import data.Applist_Feed;
import es.dmoral.toasty.Toasty;

public class Applist_Adapter extends BaseAdapter {

    public AppCompatActivity activity;
    public NetConnect cd;
    public Context context;
    public List<Applist_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public Applist_Adapter(AppCompatActivity activity2, List<Applist_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        cd=new NetConnect(context);
    }

    public int getCount() {
        return feedItems.size();
    }

    public Object getItem(int location) {
        return feedItems.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_applist, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        Button install=(Button)convertView.findViewById(R.id.install);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        Applist_Feed item = (Applist_Feed) feedItems.get(position);

        title.setText(item.getAppname());

        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Static_Veriable.weblink+"applogo/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {

                    Static_Veriable.appid = item.getSn();
                    Static_Veriable.task_appname=item.getAppname();
                    Static_Veriable.task_appurl=item.getApplink();
                    Static_Veriable.task_appheader=item.getHeader();
                    Static_Veriable.task_appfooter=item.getFooter();
                    Intent i = new Intent(context, App_Deatils.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else
                {
                    Toasty.info(context, Static_Veriable.nonet, 0).show();
                }

            }
        });
        install.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {

                    Static_Veriable.appid = item.getSn();
                    Static_Veriable.task_appname=item.getAppname();
                    Static_Veriable.task_appurl=item.getApplink();
                    Static_Veriable.task_appheader=item.getHeader();
                    Static_Veriable.task_appfooter=item.getFooter();
                    Intent i = new Intent(context, App_Deatils.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else
                {
                    Toasty.info(context, Static_Veriable.nonet, 0).show();
                }
            }
        });
        return convertView;
    }
}
