package chintha_adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd.OnUnifiedNativeAdLoadedListener;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.pkmmte.view.CircularImageView;
import com.suhi_chintha.Chintha_Likes;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.ExtendTextView;
import com.suhi_chintha.HeartOf_App;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Updates_ChinthaLikes;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;
import com.suhi_chintha.VideoPlayer;
import com.suhi_chintha.status_frag;
import com.vanniktech.emoji.EmojiTextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import chintha_data.ChinthaFeeds;
import es.dmoral.toasty.Toasty;

public class ChinathakalAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public Activity activity;
    public NetConnection cd;
    public Context context;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    Typeface face;
    Typeface face1;
    Typeface face2;
    private List<ChinthaFeeds> feedStatuses;
    public status_frag fragment;
    UnifiedNativeAd nativeAd;
    ProgressDialog progress;
    public String statusid = "";
    public User_DataDB userDataDB;


    public class viewHolder extends ViewHolder {
        FrameLayout adplaceholder;
        ImageView block;
        TextView chinthacount;
        RelativeLayout commentlyt;
        ImageView del;
        CircularImageView doticon;
        ImageView doverify;
        ImageView feeddownloadicon;
        ImageView feedimg;
        RelativeLayout feedlayout;
        RelativeLayout feedlyt;
        ImageView feedplayicon;
        TextView feedtime;
        TextView feedtitle;
        ImageView img;
        ImageView imgcomment;
        ImageView imglike;
        ImageView imgnolike;
        ImageView imgshare;
        RelativeLayout likelyt;
        TextView mobile;
        TextView name;
        TextView posttime;
        ImageView settings;
        RelativeLayout sharelyt;
        RelativeLayout shotlayout;
        TextView shotstatus;
        EmojiTextView status;
        RelativeLayout statuslyt;
        RelativeLayout statuspart;
        TextView txtcomment;
        TextView txtlike;
        TextView txtshare;
        ImageView veriicon;
        RelativeLayout writings;
        public viewHolder(View itemView) {
            super(itemView);
            feedtitle = (TextView) itemView.findViewById(R.id.feedtitle);
            feedtime = (TextView) itemView.findViewById(R.id.feedtime);
            feedimg = (ImageView) itemView.findViewById(R.id.feedimg);
            feedplayicon = (ImageView) itemView.findViewById(R.id.feedplayicon);
            feeddownloadicon = (ImageView) itemView.findViewById(R.id.feeddownloadicon);
            likelyt = (RelativeLayout) itemView.findViewById(R.id.likelyt);
            commentlyt = (RelativeLayout) itemView.findViewById(R.id.commentlyt);
            sharelyt = (RelativeLayout) itemView.findViewById(R.id.sharelyt);
            txtlike = (TextView) itemView.findViewById(R.id.txtlike);
            txtcomment = (TextView) itemView.findViewById(R.id.txtcomment);
            txtshare = (TextView) itemView.findViewById(R.id.txtshare);
            imgnolike = (ImageView) itemView.findViewById(R.id.imgnolike);
            imglike = (ImageView) itemView.findViewById(R.id.imglike);
            imgcomment = (ImageView) itemView.findViewById(R.id.imgcomment);
            imgshare = (ImageView) itemView.findViewById(R.id.imgshare);
            settings = (ImageView) itemView.findViewById(R.id.settings);
            img = (ImageView) itemView.findViewById(R.id.img);
            statuslyt = (RelativeLayout) itemView.findViewById(R.id.statuslyt);
            doticon = (CircularImageView) itemView.findViewById(R.id.doticon);
            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            block = (ImageView) itemView.findViewById(R.id.block);
            del = (ImageView) itemView.findViewById(R.id.del);
            statuspart = (RelativeLayout) itemView.findViewById(R.id.statuspart);
            doverify = (ImageView) itemView.findViewById(R.id.doverify);
            posttime = (TextView) itemView.findViewById(R.id.post_time);
            status = (EmojiTextView) itemView.findViewById(R.id.chintha);
            veriicon = (ImageView) itemView.findViewById(R.id.verifiedicon);
            shotstatus = (TextView) itemView.findViewById(R.id.shotstatus);
            shotlayout = (RelativeLayout) itemView.findViewById(R.id.shotlayout);
            adplaceholder = (FrameLayout) itemView.findViewById(R.id.adplaceholder);
            chinthacount = (TextView) itemView.findViewById(R.id.chinthacount);
            writings = (RelativeLayout) itemView.findViewById(R.id.writings);
            feedlyt = (RelativeLayout) itemView.findViewById(R.id.feedlyt);
            feedlayout = (RelativeLayout) itemView.findViewById(R.id.feedlayout);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public ChinathakalAdapter(Activity activity2, List<ChinthaFeeds> feedStatuses2, status_frag fragment2) {
        activity = activity2;
        feedStatuses = feedStatuses2;
        fragment = fragment2;
        context = activity2.getApplicationContext();
        dataDb1 = new DataDB1(context);
        dataDb2 = new DataDB2(context);
        cd = new NetConnection(context);
        userDataDB = new User_DataDB(context);
        progress = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "asset_fonts/proximanormal.ttf");
        face2 = Typeface.createFromAsset(context.getAssets(), "asset_fonts/proxibold.otf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.chinthakal_customlyt, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.bottomview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.full_loaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if (position == feedStatuses.size() && feedStatuses.size() > 10) {
            return 1;
        }
        if (position < feedStatuses.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return feedStatuses.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                final ChinthaFeeds item = (ChinthaFeeds) feedStatuses.get(position);
                final viewHolder viewHolder2 = (viewHolder) holder;
                if (item.getstatustype().equalsIgnoreCase("0")) {
                    viewHolder2.writings.setVisibility(View.VISIBLE);
                    viewHolder2.feedlyt. setVisibility(View.GONE);
                    viewHolder2.statuslyt.setVisibility(View.VISIBLE);
                    viewHolder2.name.setText(item.getName());
                    viewHolder2.txtshare.setText(" ഷെയര്‍ ");
                    viewHolder2.txtshare.setTypeface(face1);
                    viewHolder2.txtlike.setTypeface(face1);
                    viewHolder2.txtcomment.setTypeface(face1);
                    if (item.getshowmobile().equalsIgnoreCase("1")) {
                        viewHolder2.mobile.setText(item.getMobile());
                        viewHolder2.mobile. setVisibility(View.GONE);
                    } else {
                        viewHolder2.mobile. setVisibility(View.GONE);
                    }
                    if (item.getverified().equalsIgnoreCase("1")) {
                        viewHolder2.veriicon.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder2.veriicon. setVisibility(View.GONE);
                    }
                    if (item.getshortstatus().equalsIgnoreCase("NA")) {
                        viewHolder2.shotlayout. setVisibility(View.GONE);
                    } else {
                        viewHolder2.shotlayout.setVisibility(View.VISIBLE);
                        viewHolder2.shotstatus.setText(item.getshortstatus());
                        try {
                            int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
                            viewHolder2.doticon.setBackgroundColor(androidColors[new Random().nextInt(androidColors.length)]);
                        } catch (Exception e) {
                        }
                    }
                    if (item.get_iscmntlock().equalsIgnoreCase("1")) {
                        viewHolder2.commentlyt.setVisibility(View.INVISIBLE);
                    } else {
                        viewHolder2.commentlyt.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(item.getStatus())) {
                        try {
                            viewHolder2.status.setText(item.getStatus());
                        } catch (Exception e2) {
                        }
                    } else {
                        viewHolder2.status. setVisibility(View.GONE);
                    }
                    if (Integer.parseInt(item.get_statuscount()) > 0) {
                        viewHolder2.chinthacount.setVisibility(View.VISIBLE);
                        viewHolder2.chinthacount.setText("ചിന്ത : "+item.get_statuscount());
                    } else {
                        viewHolder2.chinthacount.setVisibility(View.INVISIBLE);
                    }
                    new RequestOptions().placeholder((int) R.drawable.img_noimage);
                    Glide.with(context).load(item.get_dppic()).apply(RequestOptions.circleCropTransform().signature(new ObjectKey(item.getimgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.img);


                    if (dataDb2.get_fvrt(item.getId().trim()).equalsIgnoreCase("")) {
                        viewHolder2.imgnolike.setVisibility(View.VISIBLE);
                        viewHolder2.imglike.setVisibility(View.INVISIBLE);
                    } else {
                        viewHolder2.imgnolike.setVisibility(View.INVISIBLE);
                        viewHolder2.imglike.setVisibility(View.VISIBLE);
                    }

                    if (Integer.parseInt(item.get_likes()) > 0) {
                        viewHolder2.txtlike.setText(item.get_likes()+" ലൈക്ക് ");
                    } else {
                        viewHolder2.txtlike.setText(" ലൈക്ക് ");
                    }
                    if (Integer.parseInt(item.getcmntcount()) <= 0) {
                        viewHolder2.txtcomment.setText(" അഭിപ്രായം ");
                    } else {
                        viewHolder2.txtcomment.setText(item.getcmntcount()+" അഭിപ്രായം ");
                    }
                    viewHolder2.posttime.setText(item.get_postdate());
                    viewHolder2.status.setOnLongClickListener(new OnLongClickListener() {
                        public boolean onLongClick(View arg0) {
                            try {
                                Static_Variable.chintha_text = item.getStatus();
                                if (VERSION.SDK_INT < 11) {
                                    ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(Static_Variable.chintha_text);
                                } else {
                                    ((android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", Static_Variable.chintha_text));
                                }
                                Toasty.info(context, (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                            }
                            return false;
                        }
                    });
                    viewHolder2.settings.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            try {
                                Static_Variable.viewd_pfle = 1;
                                Static_Variable.chintha_Id = item.getId();
                                Static_Variable.pos = position;
                                Static_Variable.chintha_text = item.getStatus();
                                Static_Variable.userid = item.getuserid();
                                Static_Variable.username = item.getName();
                                Static_Variable.usermobile = item.getMobile();
                                Static_Variable.txtstatustype = item.getstatustype();
                                Static_Variable.sm_text = item.getStatus();
                                ((HeartOf_App) activity).show_popup();
                            } catch (Exception e) {
                            }
                        }
                    });
                    viewHolder2.commentlyt.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            try {
                                dataDb1.deletecmntdetails();
                                dataDb1.add_cmntdtails(item.getId(), item.getuserid(), item.getName(), item.getStatus(), item.getimgsig(), item.getstatustype(), item.get_imgurl(), item.get_photodim());
                                Static_Variable.viewd_pfle = 1;
                                Intent i = new Intent(context, Lists_ChinthaComments.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            } catch (Exception e) {
                            }
                        }
                    });
                    viewHolder2.imglike.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            try {
                                dataDb2.deletefvrt(item.getId());
                                viewHolder2.imglike.setVisibility(View.INVISIBLE);
                                viewHolder2.imgnolike.setVisibility(View.VISIBLE);
                                if (viewHolder2.txtlike.getText().toString().equalsIgnoreCase(" ലൈക്ക് ")) {
                                    viewHolder2.txtlike.setText(" ലൈക്ക് ");
                                    return;
                                }
                                String[] p = viewHolder2.txtlike.getText().toString().split(" ");
                                viewHolder2.txtlike.setText(Integer.parseInt(p[0]) - 1+" ലൈക്ക് ");
                                if (Integer.parseInt(viewHolder2.txtlike.getText().toString().split(" ")[0]) <= 0) {
                                    viewHolder2.txtlike.setText(" ലൈക്ക് ");
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                    viewHolder2.imgnolike.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            try {
                                ArrayList arrayList = userDataDB.get_user();
                                String[] c = (String[]) arrayList.toArray(new String[arrayList.size()]);
                                try {
                                    dataDb2.addtofvrt(item.getId(), item.getuserid(), item.getName(), item.getMobile(), Base64.encodeToString(item.getStatus().getBytes(StandardCharsets.UTF_8), 0), item.getshowmobile(), item.getstatustype(), item.get_imgurl(), item.get_photodim());
                                    dataDb1.add_fvrtstaus(c[0], item.getId(), item.getuserid(), c[1], item.getStatus(), userDataDB.get_imgsig());
                                } catch (Exception e) {
                                }
                                try {
                                    viewHolder2.imglike.setVisibility(View.VISIBLE);
                                    viewHolder2.imgnolike.setVisibility(View.INVISIBLE);
                                    if (viewHolder2.txtlike.getText().toString().equalsIgnoreCase(" ലൈക്ക് ")) {
                                        viewHolder2.txtlike.setText("1 ലൈക്ക് ");
                                    } else {
                                        String[] p = viewHolder2.txtlike.getText().toString().split(" ");
                                        viewHolder2.txtlike.setText((Integer.parseInt(p[0]) + 1)+" ലൈക്ക് ");
                                        if (Integer.parseInt(viewHolder2.txtlike.getText().toString().split(" ")[0]) <= 0) {
                                            viewHolder2.txtlike.setText(" ലൈക്ക് ");
                                        }
                                    }
                                    new Updates_ChinthaLikes(context).likeupdate();
                                } catch (Exception e2) {
                                }
                            } catch (Exception e3) {
                            }
                        }
                    });
                    viewHolder2.img.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            try {
                                Static_Variable.viewd_pfle = 1;
                                Static_Variable.userid = item.getuserid();
                                Static_Variable.username = item.getName();
                                Intent i = new Intent(context, Image_View.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            } catch (Exception e) {
                            }
                        }
                    });
                    viewHolder2.sharelyt.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            Static_Variable.viewd_pfle = 1;
                            if (item.getstatustype().equalsIgnoreCase("0")) {
                                try {
                                    ((HeartOf_App) activity).statusshare(item.getStatus());
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
                    viewHolder2.name.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Static_Variable.userid = item.getuserid();
                            Static_Variable.username = item.getName();
                            Static_Variable.usermobile = item.getMobile();
                            Static_Variable.showmobile = item.getshowmobile();
                            Intent i = new Intent(context, Users_Chinthakal.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    });
                    viewHolder2.mobile.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Static_Variable.userid = item.getuserid();
                            Static_Variable.username = item.getName();
                            Static_Variable.usermobile = item.getMobile();
                            Static_Variable.showmobile = item.getshowmobile();
                            Intent i = new Intent(context, Users_Chinthakal.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    });
                    viewHolder2.likelyt.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            Static_Variable.viewd_pfle = 1;
                            Static_Variable.userid = item.getId();
                            Intent i = new Intent(context, Chintha_Likes.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    });
                    viewHolder2.block.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Static_Variable.userid = item.getuserid();
                            showalert("Are you sure want to block this user ?");
                        }
                    });
                    viewHolder2.del.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Static_Variable.userid = item.getuserid();
                            Static_Variable.chintha_Id = item.getId();
                            showalert1("Are you sure want to delete status ?");
                        }
                    });
                    viewHolder2.doverify.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            Static_Variable.userid = item.getuserid();
                            showalertdoverify("Are you sure want to verify this person ?");
                        }
                    });
                } else {
                    viewHolder2.writings. setVisibility(View.GONE);
                    viewHolder2.feedlyt.setVisibility(View.VISIBLE);
                    viewHolder2.feedtitle.setTypeface(face2);
                    viewHolder2.feedtime.setTypeface(face2);
                    viewHolder2.feedtime.setText("View All");
                    String[] k = item.get_likes().split("x");
                    float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(dataDb2.get_screenwidth()).floatValue() - 40.0f);
                    viewHolder2.feedimg.getLayoutParams().height = Math.round(calheight);
                    Glide.with(context).load(item.getshowmobile()).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.feedimg);
                    if (item.getuserid().equalsIgnoreCase("1")) {
                        viewHolder2.feedplayicon.setVisibility(View.VISIBLE);
                    } else if (item.getuserid().equalsIgnoreCase("2")) {
                        viewHolder2.feedplayicon. setVisibility(View.GONE);
                    }
                    if (!item.getName().equalsIgnoreCase("NA")) {
                        if (!item.getName().equalsIgnoreCase("")) {
                            viewHolder2.feedtitle.setVisibility(View.VISIBLE);
                            viewHolder2.feedtitle.setText(item.getName());
                            viewHolder2.feeddownloadicon.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    if (item.getuserid().equalsIgnoreCase("1")) {
                                        if (item.getName().equalsIgnoreCase("NA") || item.getName().equalsIgnoreCase("")) {
                                            Static_Variable.videodownslink = System.currentTimeMillis()+".mp4";
                                        } else {
                                            Static_Variable.videodownslink = item.getName().replaceAll(" ", "_")+".mp4";
                                        }
                                        download(item.getverified());
                                        return;
                                    }
                                    if (item.getName().equalsIgnoreCase("NA") || item.getName().equalsIgnoreCase("")) {
                                        Static_Variable.videodownslink = System.currentTimeMillis()+".jpg";
                                    } else {
                                        Static_Variable.videodownslink = item.getName().replaceAll(" ", "_")+".jpg";
                                    }
                                    download(item.getshowmobile());
                                }
                            });
                            viewHolder2.feedlayout.setOnClickListener(new OnClickListener() {
                                public void onClick(View arg0) {
                                    if (item.getuserid().equalsIgnoreCase("1")) {
                                        Static_Variable.videolinks = item.getverified();
                                        Intent i = new Intent(context, VideoPlayer.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);
                                    }
                                }
                            });
                            viewHolder2.feedtime.setOnClickListener(new OnClickListener() {
                                public void onClick(View view) {
                                    try {
                                        ((HeartOf_App) activity).movetovideo();
                                    } catch (Exception e) {
                                    }
                                }
                            });
                        }
                    }
                    viewHolder2.feedtitle. setVisibility(View.GONE);
                    viewHolder2.feeddownloadicon.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (item.getuserid().equalsIgnoreCase("1")) {
                                if (item.getName().equalsIgnoreCase("NA") || item.getName().equalsIgnoreCase("")) {
                                    Static_Variable.videodownslink = System.currentTimeMillis()+".mp4";
                                } else {
                                    Static_Variable.videodownslink = item.getName().replaceAll(" ", "_")+".mp4";
                                }
                                download(item.getverified());
                                return;
                            }
                            if (item.getName().equalsIgnoreCase("NA") || item.getName().equalsIgnoreCase("")) {
                                Static_Variable.videodownslink = System.currentTimeMillis()+".jpg";
                            } else {
                                Static_Variable.videodownslink = item.getName().replaceAll(" ", "_")+".jpg";
                            }
                            download(item.getshowmobile());
                        }
                    });
                    viewHolder2.feedlayout.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (item.getuserid().equalsIgnoreCase("1")) {
                                Static_Variable.videolinks = item.getverified();
                                Intent i = new Intent(context, VideoPlayer.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }
                        }
                    });
                    viewHolder2.feedtime.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            try {
                                ((HeartOf_App) activity).movetovideo();
                            } catch (Exception e) {
                            }
                        }
                    });
                }
                if (item.getshowads().equalsIgnoreCase("1")) {
                    Builder builder = new Builder((Context) activity, "ca-app-pub-2432830627480060/5890429850");
                    builder.forUnifiedNativeAd(new OnUnifiedNativeAdLoadedListener() {
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            if (nativeAd != null) {
                                nativeAd.destroy();
                            }
                            nativeAd = unifiedNativeAd;
                            UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, null);
                            populateUnifiedNativeAdView(unifiedNativeAd, adView);
                            viewHolder2.adplaceholder.removeAllViews();
                            viewHolder2.adplaceholder.addView(adView);
                        }
                    });
                    builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
                    builder.withAdListener(new AdListener() {
                        public void onAdFailedToLoad(int errorCode) {
                        }

                        public void onAdLoaded() {
                            super.onAdLoaded();
                            viewHolder2.adplaceholder.setVisibility(View.VISIBLE);
                        }
                    }).build().loadAd(new AdRequest.Builder().build());
                    return;
                }
                viewHolder2.adplaceholder. setVisibility(View.GONE);
            } catch (Exception e3) {

               Log.w("Salmanponnani",Log.getStackTraceString(e3));
            }
        } else if (holder instanceof viewHolderFooter) {
            ViewHolder viewHolder3 = holder;
            if (feedStatuses.size() > 0) {
                fragment.loadmore();
            }
        }
    }

    public void timerdly_progress(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert1(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    try {
                        new delete_chintha1().execute(new String[0]);
                    } catch (Exception a) {
                        //Toasty.info(context, (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalert3(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new unblock().execute(new String[0]);
                } else {
                    Toasty.info(context, (CharSequence) "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalert4(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new doverify().execute(new String[0]);
                } else {
                    Toasty.info(context, (CharSequence) "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new user_block().execute(new String[0]);
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showsave(String message, final ImageView imgview) {
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

    public void showalertdoverify(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new doverify().execute(new String[0]);
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    public void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd2, UnifiedNativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd2.getHeadline());
        if (nativeAd2.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd2.getBody());
        }
        if (nativeAd2.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd2.getCallToAction());
        }
        if (nativeAd2.getIcon() == null) {
            adView.getIconView(). setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd2.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd2.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd2.getPrice());
        }
        if (nativeAd2.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd2.getStore());
        }
        if (nativeAd2.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd2.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd2.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd2.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd2);
        VideoController vc = nativeAd2.getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoLifecycleCallbacks() {
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }

    public void download(String downlink1) {
        try {
            Request request = new Request(Uri.parse(downlink1));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Static_Variable.videodownslink);
            Context context2 = context;
            Context context3 = context;
            ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
            Toasty.success(context, (CharSequence) "Download Started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }
    public class delete_chintha1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
            timerdly_progress(50000, progress);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"deletestatus_adminfromapp.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.userid+":%"+Static_Variable.chintha_Id, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (progress != null || progress.isShowing()) {
                progress.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(context, (CharSequence) "Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class doverify extends AsyncTask<String, Void, String> {
        public doverify() {
        }
        public void onPreExecute() {
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
            timerdly_progress(50000, progress);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"doverify.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.userid, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (progress != null || progress.isShowing()) {
                progress.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(context, (CharSequence) "Verifed", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class unblock extends AsyncTask<String, Void, String> {
        public unblock() {
        }
        public void onPreExecute() {
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
            timerdly_progress(50000, progress);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"unblockuser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.userid, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (progress != null || progress.isShowing()) {
                progress.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(context, (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class user_block extends AsyncTask<String, Void, String> {
        public user_block() {
        }
        public void onPreExecute() {
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
            timerdly_progress(50000, progress);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"blockuser_admin_direc.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.userid, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (progress != null || progress.isShowing()) {
                progress.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(context, (CharSequence) "Blocked", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
