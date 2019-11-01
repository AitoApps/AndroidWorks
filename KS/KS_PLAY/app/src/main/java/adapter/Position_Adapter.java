package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kamanam.Position_View;
import com.kamanam.R;
import com.kamanam.Temp;
import java.util.List;
import data.Position_Feed;
public class Position_Adapter extends BaseAdapter {
    private Activity activity;
    public Context context;
    Typeface face;
    public List<Position_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public Position_Adapter(Activity activity2, List<Position_Feed> feedItems2) {
        this.activity = activity2;
        this.feedItems = feedItems2;
        this.context = activity2.getApplicationContext();
        this.pd = new ProgressDialog(activity2);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "fonts/proximanormal.ttf");
    }

    public int getCount() {
        return this.feedItems.size();
    }

    public Object getItem(int location) {
        return this.feedItems.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.custom_list, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        RelativeLayout lyt = (RelativeLayout) convertView.findViewById(R.id.layout);
        Position_Feed item = (Position_Feed) this.feedItems.get(position);
        title.setTag(Integer.valueOf(position));
        lyt.setTag(Integer.valueOf(position));
        title.setText(item.gettitle());
        title.setTypeface(this.face);
        lyt.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {

                        int position = ((Integer) arg0.getTag()).intValue();
                        Position_Feed position_Feed = (Position_Feed) Position_Adapter.this.feedItems.get(position);
                        Position_Feed item = (Position_Feed) Position_Adapter.this.feedItems.get(position);
                        Temp.picid = position + 1;
                        Temp.posname = item.gettitle();
                        Intent i = new Intent(Position_Adapter.this.context, Position_View.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Position_Adapter.this.context.startActivity(i);
                        return;


                } catch (Exception e) {


                }
            }
        });
        return convertView;
    }
}
