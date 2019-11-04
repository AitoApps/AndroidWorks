package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appsbag.App_List;
import com.appsbag.R;
import com.appsbag.Temp;
import com.appsbag.UserDatabaseHandler;
import com.appsbag.Video_List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import data.AppCatogery_FeedItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class AppCatogery_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    UnifiedNativeAd nativeAd;
    public Context context;
    Typeface face;
    public UserDatabaseHandler udb;
    public List<AppCatogery_FeedItem> feedItems;
    private LayoutInflater inflater;
    public AppCatogery_Adapter(Activity activity2, List<AppCatogery_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        RelativeLayout layout;
        FrameLayout adplaceholder;
        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            image = (ImageView) itemView.findViewById(R.id.image);
            adplaceholder = (FrameLayout) itemView.findViewById(R.id.adplaceholder);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_appcatogery, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.footerview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.fullloaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if (position < feedItems.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return feedItems.size() + 1;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                AppCatogery_FeedItem item = (AppCatogery_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;

                float calheight = 0.50f * Float.valueOf(udb.getscreenwidth()).floatValue();
                viewHolder2.image.getLayoutParams().height = Math.round(calheight);

                Glide.with(context).load(item.getImgurl()).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {

                        AppCatogery_FeedItem item = (AppCatogery_FeedItem) feedItems.get(position);
                        Temp.catogeryid = item.getAppid();
                        Temp.catogeryname = item.getCatogeryname();

                        if(item.getAppid().equalsIgnoreCase("4"))
                        {
                            Intent i = new Intent(context, Video_List.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                        else{
                            Intent i = new Intent(context, App_List.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }

                    }
                });
                     if(position>=1)
                     {
                         Builder builder = new Builder((Context) activity, "ca-app-pub-5517777745693327/5919696855");
                         builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
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
                     }
                     else
                     {
                         viewHolder2.adplaceholder.setVisibility(View.GONE);
                     }


            } catch (Exception e) {

            }
        }
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
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }

}