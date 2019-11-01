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
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fishapp.user.DatabaseHandler;
import com.fishapp.user.Fish_Details;
import com.fishapp.user.Fish_List;
import com.fishapp.user.R;
import com.fishapp.user.Temp;
import com.fishapp.user.UserDatabaseHandler;

import data.Fishlist_Feeditems;
import java.util.List;

public class FishList_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public Activity activity;
    public Context context;
    Typeface face;
    public List<Fishlist_Feeditems> feedItems;
    private LayoutInflater inflater;
    Float priceflag;
    Float totalgrms;
    public DatabaseHandler db;
    public UserDatabaseHandler udb;
    public FishList_Adapter(Activity activity2, List<Fishlist_Feeditems> feedItems2) {
        Float valueOf = Float.valueOf(0.0f);
        totalgrms = valueOf;
        priceflag = valueOf;
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db=new DatabaseHandler(context);
        udb=new UserDatabaseHandler(context);
        face=Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }

    public class viewHolder extends ViewHolder {
        RelativeLayout add;
        ImageView image;
        TextView itemname,buy;
        RelativeLayout layout;
        ImageView minus;
        TextView outofstock;
        ImageView plus;
        TextView price;
        TextView qty;
        TextView unit;

        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            outofstock = (TextView) itemView.findViewById(R.id.outofstock);
            qty = (TextView) itemView.findViewById(R.id.qty);
            unit = (TextView) itemView.findViewById(R.id.unit);
            price = (TextView) itemView.findViewById(R.id.price);
            image = (ImageView) itemView.findViewById(R.id.image);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            plus = (ImageView) itemView.findViewById(R.id.plus);
            add = (RelativeLayout) itemView.findViewById(R.id.add);
            buy=itemView.findViewById(R.id.buy);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_fishes, parent, false));
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
                Fishlist_Feeditems item = (Fishlist_Feeditems) feedItems.get(position);
                final viewHolder viewHolder2 = (viewHolder) holder;
                final String rupee = context.getResources().getString(R.string.Rs);
                viewHolder2.itemname.setText(item.getFishname());
                viewHolder2.itemname.setTypeface(face);
                viewHolder2.qty.setTypeface(face);
                viewHolder2.unit.setTypeface(face);
                viewHolder2.outofstock.setTypeface(face);
                viewHolder2.price.setTypeface(face);
                viewHolder2.buy.setTypeface(face);
                 

                if (item.getStock().equalsIgnoreCase("1")) {
                    viewHolder2.outofstock.setVisibility(View.INVISIBLE);
                    viewHolder2.qty.setVisibility(View.VISIBLE);
                    viewHolder2.unit.setVisibility(View.VISIBLE);
                    viewHolder2.price.setVisibility(View.VISIBLE);
                    viewHolder2.minus.setVisibility(View.VISIBLE);
                    viewHolder2.plus.setVisibility(View.VISIBLE);
                    viewHolder2.add.setVisibility(View.VISIBLE);
                    try {

                        totalgrms = Float.valueOf(0.0f);
                        if (item.getUnit().equalsIgnoreCase("gm")) {
                            totalgrms = Float.valueOf(Float.parseFloat(item.getQty()));
                        } else if (item.getUnit().equalsIgnoreCase("Kg")) {
                            totalgrms = Float.valueOf(Float.parseFloat(item.getQty()) * Float.valueOf("1000").floatValue());
                        }
                        priceflag = Float.valueOf(Float.parseFloat(item.getPrice()) / totalgrms.floatValue());
                        float kilograms = totalgrms.floatValue() / Float.parseFloat("1000");
                        viewHolder2.qty.setText(String.format("%.2f",Float.parseFloat(kilograms+"")));
                        viewHolder2.price.setText(rupee+String.format("%.2f", Float.parseFloat((totalgrms.floatValue() * priceflag.floatValue())+"")));
                        viewHolder2.minus.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Fishlist_Feeditems item = (Fishlist_Feeditems) feedItems.get(position);
                                    Float minusgram = Float.valueOf((Float.parseFloat(viewHolder2.qty.getText().toString().trim()) * Float.parseFloat("1000")) - 500.0f);
                                    if (minusgram.floatValue() < totalgrms.floatValue()) {
                                        float kilograms = totalgrms.floatValue() / Float.parseFloat("1000");
                                        viewHolder2.qty.setText(String.format("%.2f", Float.parseFloat(kilograms + "")));
                                        viewHolder2.price.setText(rupee + String.format("%.2f", Float.parseFloat((totalgrms.floatValue() * priceflag.floatValue()) + "")));

                                    } else {
                                        float kilograms = minusgram.floatValue() / Float.parseFloat("1000");
                                        viewHolder2.qty.setText(String.format("%.2f", Float.parseFloat(kilograms + "")));
                                        viewHolder2.price.setText(rupee + String.format("%.2f", Float.parseFloat((minusgram.floatValue() * priceflag.floatValue()) + "")));
                                    }
                                    Float qtyingram = Float.valueOf(Float.parseFloat(viewHolder2.qty.getText().toString().trim()) * Float.parseFloat("1000"));
                                    db.addcart_existupdate(item.getSn(), qtyingram + "", viewHolder2.price.getText().toString().replace(rupee, ""));
                                } catch (Exception e) {
                                }
                            }
                        });
                        viewHolder2.plus.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Fishlist_Feeditems item = (Fishlist_Feeditems) feedItems.get(position);
                                    Float plusgram = Float.valueOf((Float.parseFloat(viewHolder2.qty.getText().toString().trim()) * Float.parseFloat("1000")) + 500.0f);
                                    float kilograms = plusgram.floatValue() / Float.parseFloat("1000");
                                    viewHolder2.qty.setText(String.format("%.2f", Float.parseFloat(kilograms+"")));
                                    viewHolder2.price.setText(rupee+String.format("%.2f", Float.parseFloat((plusgram.floatValue() * priceflag.floatValue())+"")));
                                    Float qtyingram = Float.valueOf(Float.parseFloat(viewHolder2.qty.getText().toString().trim()) * Float.parseFloat("1000"));
                                    db.addcart_existupdate(item.getSn(), qtyingram+"", viewHolder2.price.getText().toString().replace(rupee, ""));

                                } catch (Exception e) {
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                } else {
                    viewHolder2.outofstock.setVisibility(View.VISIBLE);
                    viewHolder2.qty.setVisibility(View.INVISIBLE);
                    viewHolder2.unit.setVisibility(View.INVISIBLE);
                    viewHolder2.price.setVisibility(View.INVISIBLE);
                    viewHolder2.minus.setVisibility(View.INVISIBLE);
                    viewHolder2.plus.setVisibility(View.INVISIBLE);
                    viewHolder2.add.setVisibility(View.INVISIBLE);
                }
                float calheight = 0.75f * Float.valueOf(udb.getscreenwidth()).floatValue();
                viewHolder2.image.getLayoutParams().height = Math.round(calheight);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"fishpicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);

                viewHolder2.image.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Fishlist_Feeditems item = (Fishlist_Feeditems) feedItems.get(position);
                        Temp.fish_sn = item.getSn();
                        Temp.fish_fishname = item.getFishname();
                        Temp.fish_discription = item.getDiscription();
                        Temp.fish_qty = item.getQty();
                        Temp.fish_unit = item.getUnit();
                        Temp.fish_price = item.getPrice();
                        Temp.fish_stock = item.getStock();
                        Temp.fish_imgsig = item.getImgsig();
                        Temp.isfromad = 0;
                        Intent i = new Intent(context, Fish_Details.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         context.startActivity(i);
                    }
                });
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Fishlist_Feeditems item = (Fishlist_Feeditems) feedItems.get(position);
                        Temp.fish_sn = item.getSn();
                        Temp.fish_fishname = item.getFishname();
                        Temp.fish_discription = item.getDiscription();
                        Temp.fish_qty = item.getQty();
                        Temp.fish_unit = item.getUnit();
                        Temp.fish_price = item.getPrice();
                        Temp.fish_stock = item.getStock();
                        Temp.fish_imgsig = item.getImgsig();
                        Temp.isfromad = 0;
                        Intent i = new Intent(context, Fish_Details.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                viewHolder2.add.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Fishlist_Feeditems item = (Fishlist_Feeditems)feedItems.get(position);
                        Fish_List h = (Fish_List)activity;
                        Float qtyingram = Float.valueOf(Float.parseFloat(viewHolder2.qty.getText().toString().trim()) * Float.parseFloat("1000"));
                        h.add_tocart(item.getSn(), item.getFishname(), qtyingram+"", viewHolder2.price.getText().toString().replace(rupee, ""), item.getImgsig(), item.getUnit(), item.getQty(), item.getPrice());
                    }
                });
            } catch (Exception e2) {
            }
        }
    }
}
