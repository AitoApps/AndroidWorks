package Downly_adapter;

import Downly_Data.Feed_CutVideoList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.downly_app.R;
import com.downly_app.Splitter_WP;
import com.downly_app.Temp;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Cutvideo extends BaseAdapter {
    public Activity activity;
    public Context context;
    Typeface face;
    public List<Feed_CutVideoList> feedItems;
    private LayoutInflater inflater;

    public Adapter_Cutvideo(Activity activity2, List<Feed_CutVideoList> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "commonfont.otf");
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
            convertView = inflater.inflate(R.layout.custom_videolist_cut, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        TextView duration = (TextView) convertView.findViewById(R.id.duration);
        TextView filepath = (TextView) convertView.findViewById(R.id.filepath);
        ImageView shareicon = (ImageView) convertView.findViewById(R.id.shareicon);
        ImageView saveicon = (ImageView) convertView.findViewById(R.id.saveicon);
        TextView title=convertView.findViewById(R.id.title);
        
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        Feed_CutVideoList item = (Feed_CutVideoList) feedItems.get(position);
        title.setText(item.getTitle());
        duration.setText(item.getDuration());
        filepath.setText(item.getFilepath());
        imageView.setImageBitmap(item.getBmp());
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ((Splitter_WP) activity).openfile(((Feed_CutVideoList) feedItems.get(position)).getFilepath());
            }
        });
        shareicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Feed_CutVideoList item = (Feed_CutVideoList) feedItems.get(position);
                try {
                    ArrayList<Uri> files = new ArrayList<>();
                    files.add(Uri.fromFile(new File(item.getFilepath())));
                    Splitter_WP h = (Splitter_WP) activity;
                    if (h.isPackageExisted("com.whatsapp") && h.isPackageExisted("com.whatsapp.w4b") && h.isPackageExisted("com.whatsapp_gb")) {
                        h.showshare_wp(files, 1);
                    } else if (h.isPackageExisted("com.whatsapp") && h.isPackageExisted("com.whatsapp.w4b")) {
                        h.showshare_wp(files, 0);
                    } else if (h.isPackageExisted("com.whatsapp")) {
                        h.onClickApp("com.whatsapp", files);
                    } else if (h.isPackageExisted("com.whatsapp.w4b")) {
                        h.onClickApp("com.whatsapp.w4b", files);
                    } else {
                        Toasty.error(context, "Please install Whatsapp app", 0).show();
                    }
                } catch (Exception e) {
                    Toasty.error(context, "Sorry ! Unable to Share", 0).show();
                }
            }
        });
        saveicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Feed_CutVideoList item = (Feed_CutVideoList) feedItems.get(position);
                    File f = new File(item.getFilepath());
                    String exrension = item.getFilepath().substring(item.getFilepath().lastIndexOf("."));
                    ((Splitter_WP) activity).copyfile(f, new File(Environment.getExternalStorageDirectory()+"/"+Temp.Defualtfolder+"/Trimmed_Gallery/"+System.currentTimeMillis()+"S"+exrension));
                    Toasty.success(context, "Saved to gallery", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toasty.error(context, "Sorry ! Unable to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
}
