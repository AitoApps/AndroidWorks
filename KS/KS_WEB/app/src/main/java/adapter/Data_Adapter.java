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

import com.mal_suthra.Act_ReadView;
import com.mal_suthra.DataBase;
import com.mal_suthra.NetConnect;
import com.mal_suthra.R;
import com.mal_suthra.Static_Veriable;

import java.util.List;

import data.Data_Feed;
import es.dmoral.toasty.Toasty;

public class Data_Adapter extends BaseAdapter {
    private AppCompatActivity activity;
    public NetConnect cd;
    public Context context;
    public DataBase dataBase;
    Typeface face;
    public List<Data_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public Data_Adapter(AppCompatActivity activity2, List<Data_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        dataBase=new DataBase(context);
        cd=new NetConnect(context);


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
        Data_Feed item = (Data_Feed) feedItems.get(position);
        title.setTag(Integer.valueOf(position));
        lyt.setTag(Integer.valueOf(position));
        title.setText(item.gettitle());
        title.setTypeface(face);
        lyt.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    int position = ((Integer) arg0.getTag()).intValue();
                    Data_Feed item = (Data_Feed) feedItems.get(position);
                    dataBase.add_subcat(item.getid());
                    Static_Veriable.tempheading = item.gettitle();
                    Intent i = new Intent(context, Act_ReadView.class);
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
