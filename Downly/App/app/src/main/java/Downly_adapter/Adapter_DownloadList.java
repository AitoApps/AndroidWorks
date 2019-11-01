package Downly_adapter;

import Downly_Data.Feed_DownloadList;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.downly_app.DataBase;
import com.downly_app.DownloadsList;
import com.downly_app.InternetConncetivity;
import com.downly_app.R;

import es.dmoral.toasty.Toasty;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class Adapter_DownloadList extends BaseAdapter {
    public Activity activity;
    public InternetConncetivity cd;
    public Context context;
    DataBase db;
    Typeface face;
    public List<Feed_DownloadList> feedItems;
    private LayoutInflater inflater;
    DownloadManager manager;
    ProgressDialog pd;
    int pos = 0;
    Runnable runnableCode;
    public String txtdownpath = "";
    public String txtpkey = "";

    public Adapter_DownloadList(Activity activity2, List<Feed_DownloadList> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new InternetConncetivity(context);
        pd = new ProgressDialog(activity2);
        db = new DataBase(context);
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_listdownloads, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView title = (TextView) convertView2.findViewById(R.id.title);
        TextView persentage = (TextView) convertView2.findViewById(R.id.persentage);
        ProgressBar progress = (ProgressBar) convertView2.findViewById(R.id.progress);
        TextView filesize = (TextView) convertView2.findViewById(R.id.filesize);
        ImageView stop = (ImageView) convertView2.findViewById(R.id.stop);
        final ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        RelativeLayout layout = (RelativeLayout) convertView2.findViewById(R.id.layout);
        Feed_DownloadList item = (Feed_DownloadList) feedItems.get(i);
        if (item.getDownname().contains(".jpg") || item.getDownname().contains(".JPG") | item.getDownname().contains(".jpeg") || item.getDownname().contains(".JPEG") || item.getDownname().contains(".png") || item.getDownname().contains(".PNG")) {
            image.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(item.getDownpath().replace("file://", "")), 80, 60));
        }
        else if(item.getDownname().contains(".mp3") || item.getDownname().contains(".MP3"))
        {
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.new_mp3));
        }
        else {
            image.setImageBitmap(ThumbnailUtils.createVideoThumbnail(item.getDownpath().replace("file://", ""), 1));
        }

        title.setText(item.getDownname());
        delete.setVisibility(View.VISIBLE);
        title.setTypeface(face);
        persentage.setTypeface(face);
        final Handler handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                try
                {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(Long.parseLong(item.getDownid()));
                    Cursor cursor = manager.query(q);
                    if(cursor.moveToFirst())
                    {
                        long bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        long bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
                        if(dl_progress>=100)
                        {
                            stop.setVisibility(View.INVISIBLE);
                            delete.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.INVISIBLE);
                            filesize.setText(HRC(bytes_total,true));
                            persentage.setVisibility(View.INVISIBLE);

                        }
                        else{


                                persentage.setVisibility(View.VISIBLE);
                                progress.setVisibility(View.VISIBLE);
                                progress.setProgress(dl_progress);
                                filesize.setVisibility(View.VISIBLE);
                                filesize.setText(HRC(bytes_downloaded,true)+ " / "+HRC(bytes_total,true));
                                persentage.setText(dl_progress+"%");
                                stop.setVisibility(View.VISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                                handler.postDelayed(this, 2000);


                        }
                    }
                    else{

                        if(item.getDownname().contains(".jpg") || item.getDownname().contains(".JPG") | item.getDownname().contains(".jpeg") || item.getDownname().contains(".JPEG") || item.getDownname().contains(".png") || item.getDownname().contains(".PNG"))
                        {
                            stop.setVisibility(View.INVISIBLE);
                            delete.setVisibility(View.VISIBLE);
                            filesize.setVisibility(View.VISIBLE);
                            persentage.setVisibility(View.INVISIBLE);
                            progress.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            stop.setVisibility(View.INVISIBLE);
                            delete.setVisibility(View.VISIBLE);
                            filesize.setVisibility(View.VISIBLE);
                            persentage.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.INVISIBLE);
                        }

                    }
                }
                catch (Exception a)
                {
                }
            }
        };
        handler.post(runnableCode);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Feed_DownloadList item = feedItems.get(position);
                item = feedItems.get(position);
                txtpkey=item.getPkey();
                pos=position;
                manager.remove(Long.parseLong(item.getDownid()));
                stop.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.VISIBLE);
                filesize.setVisibility(View.VISIBLE);
                persentage.setVisibility(View.VISIBLE);
            }
        });


        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Feed_DownloadList item = (Feed_DownloadList) feedItems.get(i);
                txtpkey = item.getPkey();
                txtdownpath = item.getDownpath();
                pos = i;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case -2:
                                db.delete_downid_byid(txtpkey);
                                ((DownloadsList) activity).removeitem(pos);
                                break;
                            case -1:
                                db.delete_downid_byid(txtpkey);
                                ((DownloadsList) activity).removeitem(pos);
                                try {
                                    File f = new File(txtdownpath.replace("file://", ""));
                                    if (f.exists()) {
                                        if (!f.exists()) {
                                            break;
                                        } else {
                                            f.delete();
                                            break;
                                        }
                                    } else {
                                        File f1 = new File(txtdownpath.replace(".", "VG.").replace("file://", ""));
                                        if (f1.exists()) {
                                            f1.delete();
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    break;
                                }
                                break;
                        }
                        dialog.dismiss();
                    }
                };
                new Builder(activity).setMessage("Are you sure want to delete this file from gallery also ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).setCancelable(true).show();
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Feed_DownloadList item = (Feed_DownloadList) feedItems.get(i);
                try {

                    File f = new File(item.getDownpath().replace("file://", ""));
                    if (f.exists()) {
                        ((DownloadsList) activity).opendownfile(f.getAbsolutePath());
                        return;
                    }
                    File f1 = new File(item.getDownpath().replace(".", "VG.").replace("file://", ""));
                    if (f1.exists()) {
                        ((DownloadsList) activity).opendownfile(f1.getAbsolutePath());
                    } else {
                        Toasty.error(context, "Unable to play", 0, true).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        return convertView2;
    }

    public static String HRC(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }


}
