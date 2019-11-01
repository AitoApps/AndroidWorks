package chintha_adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.ClipboardManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.ExtendTextView;
import com.suhi_chintha.FvrtChinthakal_List;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Status_To_Image;
import com.suhi_chintha.Users_Chinthakal;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import chintha_data.ChinthaFeed;
import es.dmoral.toasty.Toasty;

public class FvrtsLitsAdapter extends BaseAdapter {

    public AppCompatActivity activity;

    public Context context;
    public DataDb dataDb = new DataDb(context);
    public DataDB2 dataDb2 = new DataDB2(context);
    Typeface face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");

    public List<ChinthaFeed> feed;
    private LayoutInflater inflater;

    public FvrtsLitsAdapter(AppCompatActivity activity2, List<ChinthaFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
    }

    public int getCount() {
        return feed.size();
    }

    public Object getItem(int location) {
        return feed.get(location);
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
            convertView2 = inflater.inflate(R.layout.fvrt_custom, null);
        } else {
            convertView2 = convertView;
        }
        TextView name = (TextView) convertView2.findViewById(R.id.name);
        TextView phone = (TextView) convertView2.findViewById(R.id.mobile);
        ExtendTextView status = (ExtendTextView) convertView2.findViewById(R.id.chintha);
        final ImageView img = (ImageView) convertView2.findViewById(R.id.img);
        ImageView statusimage = (ImageView) convertView2.findViewById(R.id.imagetostatus);
        RelativeLayout layout = (RelativeLayout) convertView2.findViewById(R.id.layout);
        final ImageView photostatus = (ImageView) convertView2.findViewById(R.id.photostatus);
        ImageView history = (ImageView) convertView2.findViewById(R.id.chintha_histroy);
        ImageView share = (ImageView) convertView2.findViewById(R.id.share);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.del);
        ChinthaFeed item = (ChinthaFeed) feed.get(i);
        name.setText(item.getname());
        if (item.getshowmobile().equalsIgnoreCase("1")) {
            phone.setVisibility(View.VISIBLE);
            phone.setText(item.getmobile());
        } else {
            phone. setVisibility(View.GONE);
        }
        img.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                try {
                    showsave(Static_Variable.wanttosave, img);
                } catch (Exception e) {
                }
                return true;
            }
        });
        if (item.getstatustype().equalsIgnoreCase("0")) {
            status.setVisibility(View.VISIBLE);
            photostatus. setVisibility(View.GONE);
            statusimage.setVisibility(View.VISIBLE);
            try {
                try {
                    status.setText(new String(Base64.decode(item.getstatus(), 0), StandardCharsets.UTF_8));
                } catch (Exception e) {
                }
            } catch (Exception e2) {
            }
        } else {
            if (item.getstatustype().equalsIgnoreCase("1")) {
                status.setVisibility(View.VISIBLE);
                photostatus.setVisibility(View.VISIBLE);
                statusimage. setVisibility(View.GONE);
                try {
                    status.setText(new String(Base64.decode(item.getstatus(), 0), StandardCharsets.UTF_8));
                } catch (Exception e3) {
                }
                String[] k = item.getphotodimension().split("x");
                float ogheight = Float.valueOf(dataDb2.get_screenwidth()).floatValue() - 20.0f;
                float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * ogheight;
                photostatus.getLayoutParams().height = Math.round(calheight);
                Glide.with(context).load(item.getphotourl()).transition(DrawableTransitionOptions.withCrossFade()).into(photostatus);
            }
        }
        Glide.with(context).load(item.getProfilePic()).apply(new RequestOptions().placeholder((int) R.drawable.img_noimage)).into(img);
        status.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                try {
                    ChinthaFeed chinthaFeed = (ChinthaFeed) feed.get(i);
                    String text1 = new String(Base64.decode(((ChinthaFeed) feed.get(i)).getstatus(), 0), StandardCharsets.UTF_8);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(text1);
                    } else {
                        ((android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhaila", text1));
                    }
                    Toasty.info(context, (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
                return false;
            }
        });
        img.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ChinthaFeed item = (ChinthaFeed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.getname();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        statusimage.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    ChinthaFeed chinthaFeed = (ChinthaFeed) feed.get(i);
                    Static_Variable.sm_text = new String(Base64.decode(((ChinthaFeed) feed.get(i)).getstatus(), 0), StandardCharsets.UTF_8);
                    Intent i = new Intent(context, Status_To_Image.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        share.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    ChinthaFeed chinthaFeed = (ChinthaFeed) feed.get(i);
                    ChinthaFeed item = (ChinthaFeed) feed.get(i);
                    if (item.getstatustype().equalsIgnoreCase("0")) {
                        try {
                            ((FvrtChinthakal_List) activity).status_share(new String(Base64.decode(item.getstatus(), 0), StandardCharsets.UTF_8));
                        } catch (Exception e) {
                        }
                    } else if (item.getstatustype().equalsIgnoreCase("1")) {
                        try {
                            FvrtChinthakal_List h = (FvrtChinthakal_List) activity;
                            photostatus.buildDrawingCache();
                            h.sharetoall(photostatus.getDrawingCache());
                        } catch (Exception e2) {
                        }
                    }
                } catch (Exception e3) {
                }
            }
        });
        photostatus.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                try {
                    showsave(Static_Variable.wanttosave, photostatus);
                } catch (Exception e) {
                }
                return true;
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Object obj = feed.get(i);
                dataDb2.deletefvrt(((ChinthaFeed) feed.get(i)).getstatusid());
                ((FvrtChinthakal_List) activity).clearitem(i);
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChinthaFeed item = (ChinthaFeed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.getname();
                Static_Variable.usermobile = item.getmobile();
                Static_Variable.showmobile = item.getshowmobile();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        history.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChinthaFeed item = (ChinthaFeed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.getname();
                Static_Variable.usermobile = item.getmobile();
                Static_Variable.showmobile = item.getshowmobile();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView2;
    }

    public void showsave(String message, final ImageView imgview) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog4, int id) {
                imgview.buildDrawingCache();
                Bitmap bm = imgview.getDrawingCache();
                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"Status_Chinthakal/pic_"+timeStamp+".png"));
                    bm.compress(CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toasty.info(context, (CharSequence) "Saved", Toast.LENGTH_SHORT).show();
                    try {
                        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(Environment.getExternalStorageDirectory()+File.separator+"Status_Chinthakal/pic_"+timeStamp+".png"))));
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    Toasty.info(context, (CharSequence) "Unable to Save", Toast.LENGTH_SHORT).show();
                }
                dialog4.dismiss();
                return;
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.text)).setTypeface(face);
        } catch (Exception e) {
        }
    }
}
