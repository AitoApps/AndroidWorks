package chintha_adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Base64;
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

import androidx.appcompat.app.AppCompatActivity;
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
import com.suhi_chintha.Chintha_Fvrtusers;
import com.suhi_chintha.Chintha_Likes;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Updates_ChinthaLikes;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;
import com.vanniktech.emoji.EmojiTextView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import chintha_data.ChinthaFeeds;
import es.dmoral.toasty.Toasty;

public class PriyapettavrudeChinathakalAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public AppCompatActivity activity;
    public NetConnection cd;
    public Context context;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    Typeface face;
    Typeface face1;
    private List<ChinthaFeeds> feedStatuses;
    UnifiedNativeAd nativeAd;
    ProgressDialog progress;
    public String statusid = "";
    public User_DataDB userDataDB;

    public class viewHolder extends ViewHolder {
        FrameLayout adplaceholder;
        TextView chinthacount;
        RelativeLayout commentlyt;
        CircularImageView doticon;
        ImageView doverify;
        ImageView img;
        ImageView imgcomment;
        ImageView imglike;
        ImageView imgnolike;
        ImageView imgshare;
        RelativeLayout likelyt;
        TextView mobile;
        TextView name;
        TextView posttime;
        ImageView reportstatus1;
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

        public viewHolder(View itemView) {
            super(itemView);
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
            reportstatus1 = (ImageView) itemView.findViewById(R.id.reportstatus1);
            statuspart = (RelativeLayout) itemView.findViewById(R.id.statuspart);
            doverify = (ImageView) itemView.findViewById(R.id.doverify);
            posttime = (TextView) itemView.findViewById(R.id.post_time);
            status = (EmojiTextView) itemView.findViewById(R.id.chintha);
            veriicon = (ImageView) itemView.findViewById(R.id.verifiedicon);
            shotstatus = (TextView) itemView.findViewById(R.id.shotstatus);
            shotlayout = (RelativeLayout) itemView.findViewById(R.id.shotlayout);
            adplaceholder = (FrameLayout) itemView.findViewById(R.id.adplaceholder);
            chinthacount = (TextView) itemView.findViewById(R.id.chinthacount);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public PriyapettavrudeChinathakalAdapter(AppCompatActivity activity2, List<ChinthaFeeds> feedStatuses2) {
        activity = activity2;
        feedStatuses = feedStatuses2;
        context = activity2.getApplicationContext();
        dataDb1 = new DataDB1(context);
        dataDb2 = new DataDB2(context);
        cd = new NetConnection(context);
        userDataDB = new User_DataDB(context);
        progress = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "asset_fonts/proximanormal.ttf");
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
                viewHolder2.statuslyt.setVisibility(View.VISIBLE);
                viewHolder2.name.setText(item.getName());
                viewHolder2.txtshare.setText(" ഷെയര്‍ ");
                viewHolder2.txtshare.setTypeface(face1);
                viewHolder2.txtlike.setTypeface(face1);
                viewHolder2.txtcomment.setTypeface(face1);
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
                } else {
                    viewHolder2.adplaceholder. setVisibility(View.GONE);
                }
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
                viewHolder2.status.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewHolder2.status.getMaxLines()==Integer.MAX_VALUE)
                        {
                            viewHolder2.status.setMaxLines(8);
                        }
                        else
                        {
                            viewHolder2.status.setMaxLines(Integer.MAX_VALUE);
                        }

                    }
                });

                if (Integer.parseInt(item.get_statuscount()) > 0) {
                    viewHolder2.chinthacount.setText("ചിന്ത : "+item.get_statuscount());
                } else {
                    viewHolder2.chinthacount.setVisibility(View.INVISIBLE);
                }

                Glide.with(context).load(item.get_dppic()).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.img_placeholder).signature(new ObjectKey(item.getimgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.img);

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
                            ((Chintha_Fvrtusers) activity).show_popup();
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
                                ((Chintha_Fvrtusers) activity).status_share(item.getStatus());
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
            } catch (Exception e3) {
            }
        } else if (holder instanceof viewHolderFooter) {
            ViewHolder viewHolder3 = holder;
            if (feedStatuses.size() > 0) {
                ((Chintha_Fvrtusers) activity).loadmore();
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
}
