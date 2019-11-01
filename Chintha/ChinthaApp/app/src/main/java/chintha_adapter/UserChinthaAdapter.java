package chintha_adapter;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.suhi_chintha.Chintha_Likes;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.ExtendTextView;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Status_To_Image;
import com.suhi_chintha.Updates_ChinthaLikes;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chintha_data.ChinthakarStatusFeed;
import es.dmoral.toasty.Toasty;

public class UserChinthaAdapter extends BaseAdapter {

    public AppCompatActivity activity;
    public NetConnection cd;

    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    Typeface face;

    public List<ChinthakarStatusFeed> feed;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public String statusid = "";
    public User_DataDB userDataDB;

    public UserChinthaAdapter(AppCompatActivity activity2, List<ChinthakarStatusFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
        dataDb = new DataDb(context);
        dataDb1 = new DataDB1(context);
        dataDb2 = new DataDB2(context);
        userDataDB = new User_DataDB(context);
        cd = new NetConnection(context);
        pd = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
    }

    public Object getItem(int location) {
        return feed.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getCount() {
        return feed.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.userchintha_custom, null);
        } else {
            convertView2 = convertView;
        }
        ExtendTextView status = (ExtendTextView) convertView2.findViewById(R.id.chintha);
        TextView count = (TextView) convertView2.findViewById(R.id.likecount);
        ImageView imgstatus = (ImageView) convertView2.findViewById(R.id.imagetostatus);
        final ImageView statusphoto = (ImageView) convertView2.findViewById(R.id.photostatus);
        ImageView share = (ImageView) convertView2.findViewById(R.id.share);
        TextView comment = (TextView) convertView2.findViewById(R.id.comment);
        TextView posttime = (TextView) convertView2.findViewById(R.id.post_time);
        ImageView like = (ImageView) convertView2.findViewById(R.id.liked);
        ImageView nolike = (ImageView) convertView2.findViewById(R.id.nolike);
        ChinthakarStatusFeed item = (ChinthakarStatusFeed) feed.get(i);
        if (dataDb2.get_fvrt(item.get_chinthaid()).equalsIgnoreCase("")) {
            nolike.setVisibility(View.VISIBLE);
            like.setVisibility(View.INVISIBLE);
        } else {
            nolike.setVisibility(View.INVISIBLE);
            like.setVisibility(View.VISIBLE);
        }
        if (item.get_iscomment().equalsIgnoreCase("1")) {
            comment.setVisibility(View.INVISIBLE);
        } else {
            comment.setVisibility(View.VISIBLE);
        }
        if (item.getstatustype().equalsIgnoreCase("0")) {
            status.setVisibility(View.VISIBLE);
            statusphoto. setVisibility(View.GONE);
            imgstatus.setVisibility(View.VISIBLE);
            try {
                status.setText(new String(Base64.decode(item.getstatus(), 0), StandardCharsets.UTF_8));
            } catch (Exception e) {
            }
        } else if (item.getstatustype().equalsIgnoreCase("1")) {
            status.setVisibility(View.VISIBLE);
            statusphoto.setVisibility(View.VISIBLE);
            imgstatus. setVisibility(View.GONE);
            try {
                status.setText(new String(Base64.decode(item.getstatus(), 0), StandardCharsets.UTF_8));
            } catch (Exception e2) {
            }
            String[] k = item.getphotodimension().split("x");
            float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(dataDb2.get_screenwidth()).floatValue() - 20.0f);
            statusphoto.getLayoutParams().height = Math.round(calheight);
            Glide.with(context).load(item.getphotourl()).transition(DrawableTransitionOptions.withCrossFade()).into(statusphoto);
        }
        count.setText(item.get_likes()+" ലൈക്ക് ");
        comment.setText(item.getcmntcount()+" അഭിപ്രായം ");
        posttime.setText(item.getpostdate());


        like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try
                {

                    ChinthakarStatusFeed item = feed.get(position);
                    item = feed.get(position);
                    dataDb2.deletefvrt(item.get_chinthaid());

                    like.setVisibility(View.INVISIBLE);
                    nolike.setVisibility(View.VISIBLE);
                    String[] p=count.getText().toString().split(" ");
                    count.setText((Integer.parseInt(p[0])-1)+" ലൈക്ക് ");
                    String[] p1=count.getText().toString().split(" ");

                    if((Integer.parseInt(p1[0])<=0))
                    {
                        count.setText("0");
                    }
                }
                catch(Exception a)
                {

                }


            }
        });

        statusphoto.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                try {
                    trollsave_show(Static_Variable.wanttosave, statusphoto);
                } catch (Exception e) {
                }
                return true;
            }
        });

        nolike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try
                {

                    ChinthakarStatusFeed item = feed.get(position);
                    item = feed.get(position);

                    ArrayList<String> id1=	userDataDB.get_user();
                    String[] c=id1.toArray(new String[id1.size()]);


                    dataDb2.addtofvrt(item.get_chinthaid(), Static_Variable.userid, Static_Variable.username, Static_Variable.usermobile,item.getstatus(), Static_Variable.showmobile,item.getstatustype(),item.getphotourl(),item.getphotodimension());

                    try
                    {
                        byte[] data=Base64.decode(item.getstatus(),Base64.DEFAULT);
                        String a=new String(data, StandardCharsets.UTF_8);

                        dataDb1.add_fvrtstaus(c[0],item.get_chinthaid(), Static_Variable.userid,c[1],a,item.get_imgsig());
                    }
                    catch(Exception a)
                    {

                    }

                    like.setVisibility(View.VISIBLE);
                    nolike.setVisibility(View.INVISIBLE);
                    String[] p=count.getText().toString().split(" ");
                    count.setText((Integer.parseInt(p[0])+1)+" ലൈക്ക് ");

                    String[] p1=count.getText().toString().split(" ");

                    if((Integer.parseInt(p1[0])<=0))
                    {
                        count.setText("0");
                    }
                    Updates_ChinthaLikes w=new Updates_ChinthaLikes(context);
                    w.likeupdate();

                }
                catch(Exception a)
                {

                }

            }
        });


        status.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                try {
                    ChinthakarStatusFeed chinthakarStatusFeed = (ChinthakarStatusFeed) feed.get(i);
                    String text1 = new String(Base64.decode(((ChinthakarStatusFeed) feed.get(i)).getstatus(), 0), StandardCharsets.UTF_8);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(text1);
                    } else {
                        ((android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", text1));
                    }
                    Toasty.info(context, (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
                return false;
            }
        });
        share.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    ChinthakarStatusFeed item = (ChinthakarStatusFeed) feed.get(i);
                    if (item.getstatustype().equalsIgnoreCase("0")) {
                        try {
                            ((Users_Chinthakal) activity).status_share(new String(Base64.decode(((ChinthakarStatusFeed) feed.get(i)).getstatus(), 0), StandardCharsets.UTF_8));
                        } catch (Exception e) {
                        }
                    } else if (item.getstatustype().equalsIgnoreCase("1")) {
                        try {
                            Users_Chinthakal h = (Users_Chinthakal) activity;
                            statusphoto.buildDrawingCache();
                            h.sharetoall(statusphoto.getDrawingCache());
                        } catch (Exception e2) {
                        }
                    }
                } catch (Exception e3) {
                }
            }
        });
        count.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Object obj = feed.get(i);
                ChinthakarStatusFeed item = (ChinthakarStatusFeed) feed.get(i);
                Static_Variable.viewd_pfle = 1;
                Static_Variable.userid = item.get_chinthaid();
                Intent i = new Intent(context, Chintha_Likes.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        comment.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    ChinthakarStatusFeed item = (ChinthakarStatusFeed) feed.get(i);
                    dataDb1.deletecmntvisible();
                    dataDb1.add_cmntvisible("1");
                    String txtstatus = new String(Base64.decode(item.getstatus(), 0), StandardCharsets.UTF_8);
                    dataDb1.deletecmntdetails();
                    dataDb1.add_cmntdtails(item.get_chinthaid(), Static_Variable.userid, Static_Variable.username, txtstatus, item.get_imgsig(), item.getstatustype(), item.getphotourl(), item.getphotodimension());
                    Static_Variable.viewd_pfle = 1;
                    Intent i = new Intent(context, Lists_ChinthaComments.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        imgstatus.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    Static_Variable.sm_text = new String(Base64.decode(((ChinthakarStatusFeed) feed.get(i)).getstatus(), 0), StandardCharsets.UTF_8);
                    Static_Variable.viewd_pfle = 1;
                    Intent i = new Intent(context, Status_To_Image.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        return convertView2;
    }

    public void trollsave_show(String message, final ImageView imgview) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
