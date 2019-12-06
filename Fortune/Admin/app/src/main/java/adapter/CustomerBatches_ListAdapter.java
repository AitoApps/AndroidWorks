package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fortune_admin.Add_Lucky_Draw_Types;
import com.fortune_admin.ConnectionDetecter;
import com.fortune_admin.Customers;
import com.fortune_admin.LuckyDrawPrice;
import com.fortune_admin.Lucky_Draw_Types;
import com.fortune_admin.R;
import com.fortune_admin.Temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.LuckyDrawBatches_FeedItem;
import es.dmoral.toasty.Toasty;

public class CustomerBatches_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    Typeface face;
    public List<LuckyDrawBatches_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_dateid="";
    public CustomerBatches_ListAdapter(Activity activity2, List<LuckyDrawBatches_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_customerbatches, null);
        }
        TextView batchname = (TextView) convertView.findViewById(R.id.batchname);
        RelativeLayout layout=convertView.findViewById(R.id.layout);
        LuckyDrawBatches_FeedItem item =feedItems.get(position);
        batchname.setTypeface(face);
        batchname.setText(item.getBatchname());

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.ltype_sn=item.getSn();
                Temp.ltype_batchname=item.getBatchname();
                Intent i = new Intent(context, Customers.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


        return convertView;
    }
}
