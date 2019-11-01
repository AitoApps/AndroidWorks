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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dailybill.DatabaseHandler;
import com.dailybill.R;
import com.dailybill.Temp;
import java.util.List;
import data.Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
public class Product_List_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public Activity activity;
    public Context context;
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Productlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    public Product_List_ListAdapter(Activity activity2, List<Productlist_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "Rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
        face2 = Typeface.createFromAsset(context.getAssets(), "proximanormal.ttf");
    }
    public class viewHolder extends ViewHolder {
        Button add;
        ImageView image;
        RelativeLayout layout;
        TextView itemname,outofstock,qty,unit,price;
        ImageView minus,plus;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            add=itemView.findViewById(R.id.add);
            outofstock=itemView.findViewById(R.id.outofstock);
            qty=itemView.findViewById(R.id.qty);
            unit=itemView.findViewById(R.id.unit);
            price=itemView.findViewById(R.id.price);
            minus=itemView.findViewById(R.id.minus);
            plus=itemView.findViewById(R.id.plus);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_productlist, parent, false));
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
                Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                String rupee = context.getResources().getString(R.string.Rs);
                viewHolder viewHolder2 = (viewHolder) holder;
                try {
                    viewHolder2.itemname.setText(item.getItemname().toUpperCase());
                } catch (Exception e) {
                    viewHolder2.itemname.setText(item.getItemname());
                }


                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig1()));
                Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getItemid()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);

                viewHolder2.price.setText(rupee+String.format("%.2f", Float.parseFloat(item.getPrice())));

                viewHolder2.add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                    }
                });
            } catch (Exception e2) {
            }
        }
    }


    public String getincrement(String ogunit1,String minimumunit)
    {
      String amt="";
      if(ogunit1.equalsIgnoreCase("1")) //kg
      {
          if(minimumunit.equalsIgnoreCase("gm"))
          {
              amt="250";
          }
          else if(minimumunit.equalsIgnoreCase("Kg"))
          {
              amt="250";
          }
          else if(minimumunit.equalsIgnoreCase("Other"))
          {
              amt="1";
          }
      }
      return amt;
    }
    public String getincrementamt(String ogunit1,String minimum,String minimunit,String price)
    {
        String amt="";
        if(ogunit1.equalsIgnoreCase("1")) //kg
        {
            float priceper=Float.parseFloat(price)/Float.parseFloat(minimum);
            amt=priceper+"";
        }
        return amt;
    }

    public String uniconversion(String ogunit1,String count)
    {
        String amt="";
        if(ogunit1.equalsIgnoreCase("1")) //kg
        {
            Float divs=Float.parseFloat(count)/1000;
            if(divs>=1)
            {
                amt=divs+","+"kg";
            }
            else
            {
                amt=divs+","+"gm";
            }
        }
        return amt;
    }
}
