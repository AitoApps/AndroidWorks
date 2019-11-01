package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.suthra_malayalam_web.DataBase;
import com.suthra_malayalam_web.Fvrt_POS;
import com.suthra_malayalam_web.NetConnect;
import com.suthra_malayalam_web.Position_View;
import com.suthra_malayalam_web.R;
import com.suthra_malayalam_web.Static_Veriable;
import data.Fvrt_Feed;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class Fvrt_Adapter extends BaseAdapter {

    public Activity activity;
    public NetConnect cd;
    public Context context;
    public DataBase dataBase;
    public List<Fvrt_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public Fvrt_Adapter(Activity activity2, List<Fvrt_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        dataBase = new DataBase(context);
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
            convertView = inflater.inflate(R.layout.custom_fvrtlist, null);
        }
        ImageView drop = (ImageView) convertView.findViewById(R.id.delete);
        TextView data_title = (TextView) convertView.findViewById(R.id.title);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        Fvrt_Feed item = (Fvrt_Feed) feedItems.get(position);
        data_title.setTag(Integer.valueOf(position));
        layout.setTag(Integer.valueOf(position));
        drop.setTag(Integer.valueOf(position));
        data_title.setText(item.gettitle());
        drop.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    int position = ((Integer) arg0.getTag()).intValue();
                    Fvrt_Feed fvrt_Feed = (Fvrt_Feed) feedItems.get(position);
                    dataBase.deletefvrt(fvrt_Feed.getid());
                    ((Fvrt_POS) activity).removeitem(position);
                } catch (Exception e) {
                }
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    int position = ((Integer) arg0.getTag()).intValue();
                    Fvrt_Feed fvrt_Feed = (Fvrt_Feed) feedItems.get(position);
                    Static_Veriable.picid = Integer.parseInt(fvrt_Feed.getid());
                    Intent i = new Intent(context, Position_View.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    return;
                }
                Toasty.info(context, Static_Veriable.nonet, 0).show();
            }
        });
        return convertView;
    }
}
