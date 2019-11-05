package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mal_suthra.DataBase;
import com.mal_suthra.NetConnect;
import com.mal_suthra.Position_View;
import com.mal_suthra.R;
import com.mal_suthra.Static_Veriable;

import java.util.List;

import data.Position_Feed;
import es.dmoral.toasty.Toasty;

public class Position_Adapter extends BaseAdapter {
    private AppCompatActivity activity;

    public Context context;
    public DataBase dataBase = new DataBase(context);
    Typeface face;

    public List<Position_Feed> feedItems;
    private LayoutInflater inflater;
    public NetConnect nc;
    ProgressDialog pd;

    public Position_Adapter(AppCompatActivity activity2, List<Position_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        nc=new NetConnect(context);
        face = Typeface.createFromAsset(context.getAssets(), "app_fonts/malfont.ttf");
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
            convertView = inflater.inflate(R.layout.custom_list, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        RelativeLayout lyt = (RelativeLayout) convertView.findViewById(R.id.layout);
        Position_Feed item = (Position_Feed) feedItems.get(position);
        title.setTag(Integer.valueOf(position));
        lyt.setTag(Integer.valueOf(position));
        title.setText(item.gettitle());
        title.setTypeface(face);
        lyt.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    if (nc.isConnectingToInternet()) {
                        int position = ((Integer) arg0.getTag()).intValue();
                        Position_Feed item = (Position_Feed) feedItems.get(position);
                        Static_Veriable.picid = position + 1;
                        Static_Veriable.posname = item.gettitle();
                        Intent i = new Intent(context, Position_View.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                        return;
                    }
                    Toasty.info(context, Static_Veriable.nonet, 0).show();
                } catch (Exception e) {

                }
            }
        });
        return convertView;
    }
}
