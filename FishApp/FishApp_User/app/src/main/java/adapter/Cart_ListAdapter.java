package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fishapp.user.Cart;
import com.fishapp.user.ConnectionDetecter;
import com.fishapp.user.DatabaseHandler;
import com.fishapp.user.Image_viewer;
import com.fishapp.user.R;
import com.fishapp.user.Temp;
import data.Cart_FeedItem;
import java.util.List;

public class Cart_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;

    public Context context;
    public DatabaseHandler db;
    Typeface face;

    public List<Cart_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    Float priceflag;
    Float totalgrms;
    public String txtpkey = "";

    public Cart_ListAdapter(Activity activity2, List<Cart_FeedItem> feedItems2) {
        Float valueOf = Float.valueOf(0.0f);
        totalgrms = valueOf;
        priceflag = valueOf;
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
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
            convertView2 = inflater.inflate(R.layout.custom_cartlist, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        ImageView minus = (ImageView) convertView2.findViewById(R.id.minus);
        ImageView plus = (ImageView) convertView2.findViewById(R.id.plus);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView txtitemqty = (TextView) convertView2.findViewById(R.id.txtitemqty);
        TextView qty = (TextView) convertView2.findViewById(R.id.qty);
        TextView unit = (TextView) convertView2.findViewById(R.id.unit);
        TextView txtitemprice = (TextView) convertView2.findViewById(R.id.txtitemprice);
        final TextView itemprice = (TextView) convertView2.findViewById(R.id.itemprice);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        RelativeLayout relativeLayout = (RelativeLayout) convertView2.findViewById(R.id.layout);
        Cart_FeedItem item = (Cart_FeedItem) feedItems.get(i);
        String rupee = context.getResources().getString(R.string.Rs);
        itemname.setText(item.getFishname());
        itemname.setTypeface(face);
        txtitemqty.setTypeface(face);
        qty.setTypeface(face);
        unit.setTypeface(face);
        txtitemprice.setTypeface(face);
        itemprice.setTypeface(face);

        TextView txtitemprice2 = txtitemprice;
        
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"fishpicsmall/"+item.getFishid().trim()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        TextView textView = itemname;
        
        totalgrms = Float.valueOf(0.0f);
        totalgrms = Float.valueOf(0.0f);
         
        if (item.getOgunit().equalsIgnoreCase("gm")) {
            totalgrms = Float.valueOf(Float.parseFloat(item.getOgqty()));
        } else if (item.getOgunit().equalsIgnoreCase("Kg")) {
            totalgrms = Float.valueOf(Float.parseFloat(item.getOgqty()) * Float.valueOf("1000").floatValue());
        }
        priceflag = Float.valueOf(Float.parseFloat(item.getOgprice()) / totalgrms.floatValue());
        float kilograms = Float.parseFloat(item.getQty()) / Float.parseFloat("1000");
        
        qty.setText(String.format("%.2f",Float.parseFloat(kilograms+"")));
        
        itemprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getTotalprice())));
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Cart_FeedItem item = (Cart_FeedItem) feedItems.get(i);
                Temp.img_title = item.getFishname();
                Temp.imgsig = item.getImgsig();
                Temp.img_link =Temp.weblink+"fishpic/"+item.getFishid()+".jpg";
                Intent i = new Intent(context, Image_viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Cart_FeedItem item = (Cart_FeedItem) feedItems.get(i);
                    txtpkey = item.getPkey();
                    pos = i;
                    showalert("Are you sure want to delete this fish from cart?");
                } catch (Exception e) {
                }
            }
        });
        plus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Cart_FeedItem item = (Cart_FeedItem) feedItems.get(position);
                    Float plusgram = Float.valueOf((Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000")) + 500.0f);
                    float kilograms = plusgram.floatValue() / Float.parseFloat("1000");
                    qty.setText(String.format("%.2f", Float.parseFloat(kilograms+"")));
                    itemprice.setText(rupee+String.format("%.2f", Float.parseFloat((plusgram.floatValue() * priceflag.floatValue())+"")));
                    Float qtyingram = Float.valueOf(Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000"));
                    db.addcart_existupdate(item.getFishid(), qtyingram+"", itemprice.getText().toString().replace(rupee, ""));



                } catch (Exception e) {
                }
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {

                    Float minusgram = Float.valueOf((Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000")) - 500.0f);

                    if (minusgram.floatValue() < totalgrms.floatValue()) {
                        float kilograms = totalgrms.floatValue() / Float.parseFloat("1000");
                        qty.setText(String.format("%.2f", Float.parseFloat(kilograms+"")));
                        itemprice.setText(rupee+String.format("%.2f", Float.parseFloat((totalgrms.floatValue() * priceflag.floatValue())+"")));

                    } else {
                        float kilograms= minusgram.floatValue() / Float.parseFloat("1000");
                        qty.setText(String.format("%.2f", Float.parseFloat(kilograms+"")));
                        itemprice.setText(rupee+String.format("%.2f", Float.parseFloat((minusgram.floatValue() * priceflag.floatValue())+"")));
                    }
                    Float qtyingram = Float.valueOf(Float.parseFloat(qty.getText().toString().trim()) * Float.parseFloat("1000"));
                    db.addcart_existupdate(item.getFishid(), qtyingram+"", itemprice.getText().toString().replace(rupee, ""));


                } catch (Exception e) {
                }
            }
        });
        return convertView2;
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deletecart_byid(txtpkey);
                Cart j = (Cart) activity;
                j.removeitem(pos);
                j.calculatetotal();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
