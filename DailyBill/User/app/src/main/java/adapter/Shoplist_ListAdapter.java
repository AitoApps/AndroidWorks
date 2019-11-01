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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dailybill.DatabaseHandler;
import com.dailybill.ProductList;
import com.dailybill.R;
import com.dailybill.Temp;
import java.util.List;
import data.Shoplist_Feed;

public class Shoplist_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    public Context context;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Shoplist_Feed> feedItems;
    private LayoutInflater inflater;
    public Shoplist_ListAdapter(Activity activity2, List<Shoplist_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "proximanormal.ttf");
        face2 = Typeface.createFromAsset(context.getAssets(), "Rachana.ttf");
        db=new DatabaseHandler(context);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_shoplist, parent, false));
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
                Shoplist_Feed item = (Shoplist_Feed) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getShopimgisg()));
                Glide.with(context).load(Temp.weblink+"shoppicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.shopname.setText(item.getShopname());
                viewHolder2.shopname.setTypeface(face1);
                viewHolder2.km.setTypeface(face1);
                viewHolder2.place.setTypeface(face1);
                viewHolder2.km.setText(item.getDistance());
                viewHolder2.place.setText(item.getPlace()+" , ");
                viewHolder2.shplyt.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {

                        Shoplist_Feed item = (Shoplist_Feed) feedItems.get(position);
                        Temp.shopid = item.getSn();
                        Temp.shopname = item.getShopname();
                        Temp.shopkm = item.getDistance();
                        Temp.shopplace = item.getPlace();
                        Temp.shopimgsig = item.getShopimgisg();
                        Temp.shoplatitude = item.getLatitude();
                        Temp.shoplongtitude = item.getLongtitude();
                        Intent i = new Intent(context, ProductList.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
