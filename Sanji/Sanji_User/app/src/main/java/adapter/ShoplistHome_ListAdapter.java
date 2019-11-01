package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.DatabaseHandler;
import com.sanji.ProductList_New;
import com.sanji.R;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.ShoplistHome_Feed;
import java.util.List;

public class ShoplistHome_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    public Context context;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<ShoplistHome_Feed> feedItems;
    private LayoutInflater inflater;
    UserDatabaseHandler udb;

    public ShoplistHome_ListAdapter(Activity activity2, List<ShoplistHome_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
        face2 = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
        db=new DatabaseHandler(context);
        udb=new UserDatabaseHandler(context);
    }


    public class viewHolder extends ViewHolder {
        ImageView image;
        TextView km;
        TextView place;
        TextView shopname;
        RelativeLayout shplyt;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            shopname = (TextView) itemView.findViewById(R.id.shopname);
            shplyt = (RelativeLayout) itemView.findViewById(R.id.shplyt);
            place = (TextView) itemView.findViewById(R.id.place);
            km = (TextView) itemView.findViewById(R.id.km);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_shoplist_home, parent, false));
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

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                ShoplistHome_Feed item = (ShoplistHome_Feed) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getShopimgisg()));
                Glide.with(context).load(Temp.weblink+"shoppicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.shopname.setText(item.getShopname());
                viewHolder2.shopname.setTypeface(face1);
                viewHolder2.km.setTypeface(face1);
                viewHolder2.place.setTypeface(face1);
                viewHolder2.km.setText(item.getDistance());
                viewHolder2.place.setText(viewHolder2.place+" , ");
                viewHolder2.shplyt.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ShoplistHome_Feed item = (ShoplistHome_Feed) feedItems.get(position);
                        Temp.shopid = item.getSn();
                        Temp.shopname = item.getShopname();
                        Temp.shopkm = item.getDistance();
                        Temp.shopplace = item.getPlace();
                        Temp.shopweb = item.getWebsite().trim();
                        Temp.shopinstagram = item.getInstagram().trim();
                        Temp.shop_facebook = item.getFacebook().trim();
                        Temp.shop_pinterst = item.getPinterest().trim();
                        Temp.shop_youtube = item.getYoutube().trim();
                        Temp.shopimgsig = item.getShopimgisg();
                        Temp.shop_latittude = item.getLatitude();
                        Temp.shop_longtitude = item.getLongtitude();
                        Intent i = new Intent(context, ProductList_New.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
