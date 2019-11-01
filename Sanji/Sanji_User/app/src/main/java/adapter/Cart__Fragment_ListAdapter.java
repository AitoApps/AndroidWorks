package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.ConnectionDetecter;
import com.sanji.DatabaseHandler;
import com.sanji.FragmentCart;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Cart_FeedItem;
import java.util.List;

public class Cart__Fragment_ListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    DatabaseHandler db;
    Typeface face;
    Typeface face1;

    public List<Cart_FeedItem> feedItems;
    FragmentCart fragment;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtpkey = "";
    UserDatabaseHandler udb;

    public Cart__Fragment_ListAdapter(Activity activity2, List<Cart_FeedItem> feedItems2, FragmentCart fragment2) {
        activity = activity2;
        feedItems = feedItems2;
        fragment = fragment2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        udb = new UserDatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
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
        int i = position;
        String str = " ";
        String str2 = "%.2f";
        try {
            if (inflater == null) {
                try {
                    inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
                } catch (Exception e) {
                    int i2 = i;
                    return convertView;
                }
            }
            if (convertView == null) {
                convertView2 = inflater.inflate(R.layout.custom_cartlist, null);
            } else {
                convertView2 = convertView;
            }
            try {
                ImageView image = (ImageView) convertView2.findViewById(R.id.image);
                TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
                TextView itemprice = (TextView) convertView2.findViewById(R.id.itemprice);
                TextView itemtotal = (TextView) convertView2.findViewById(R.id.itemtotal);
                TextView txtitemprice = (TextView) convertView2.findViewById(R.id.txtitemprice);
                TextView txtitemtotal = (TextView) convertView2.findViewById(R.id.txtitemtotal);
                TextView txtitemqty = (TextView) convertView2.findViewById(R.id.txtitemqty);
                TextView itemqty = (TextView) convertView2.findViewById(R.id.itemqty);
                ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
                Cart_FeedItem item = (Cart_FeedItem) feedItems.get(i);
                convertView = convertView2;
                ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
                String rupee = context.getResources().getString(R.string.Rs);
                itemname.setText(item.getitemname());
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                ImageView edit2 = edit;
                try {
                    sb.append(String.format(str2, new Object[]{Float.valueOf(Float.parseFloat(item.getitemprice()))}));
                    sb.append("/");
                    sb.append(item.getMinqty());
                    sb.append(str);
                    sb.append((String) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype())));
                    itemprice.setText(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(rupee);
                    sb2.append(item.gettotal());
                    itemtotal.setText(sb2.toString());
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(item.getqty());
                    sb3.append(str);
                    sb3.append((String) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype())));
                    itemqty.setText(sb3.toString());
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(rupee);
                    sb4.append(String.format(str2, new Object[]{Float.valueOf(Float.parseFloat(item.gettotal()))}));
                    itemtotal.setText(sb4.toString());
                    itemname.setTypeface(face);
                    itemprice.setTypeface(face);
                    itemtotal.setTypeface(face);
                    txtitemprice.setTypeface(face);
                    txtitemtotal.setTypeface(face);
                    txtitemqty.setTypeface(face);
                    itemqty.setTypeface(face);
                    RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
                    RequestManager with = Glide.with(context);
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(Temp.weblink);
                    sb5.append("productpicsmall/");
                    sb5.append(item.getitemid());
                    sb5.append("_1.jpg");
                    with.load(sb5.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
                    final int i3 = position;
                    try {
                        edit2.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Cart_FeedItem cart_FeedItem = (Cart_FeedItem) Cart__Fragment_ListAdapter.feedItems.get(i3);
                                    Cart_FeedItem item = (Cart_FeedItem) Cart__Fragment_ListAdapter.feedItems.get(i3);
                                    Cart__Fragment_ListAdapter.txtpkey = item.getpkey();
                                    Cart__Fragment_ListAdapter.fragment.cartupdate(item.getitemid(), item.getitemname(), item.getitemprice(), item.getMinqty(), item.getUnittype(), item.getqty());
                                } catch (Exception e) {
                                }
                            }
                        });
                        delete.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                try {
                                    Cart_FeedItem cart_FeedItem = (Cart_FeedItem) Cart__Fragment_ListAdapter.feedItems.get(i3);
                                    Cart_FeedItem item = (Cart_FeedItem) Cart__Fragment_ListAdapter.feedItems.get(i3);
                                    Cart__Fragment_ListAdapter.txtpkey = item.getpkey();
                                    Cart__Fragment_ListAdapter.pos = i3;
                                    Cart__Fragment_ListAdapter.showalert("Are you sure want to delete this item from cart?");
                                } catch (Exception e) {
                                }
                            }
                        });
                        return convertView;
                    } catch (Exception e2) {
                        return convertView;
                    }
                } catch (Exception e3) {
                    int i4 = position;
                    return convertView;
                }
            } catch (Exception e4) {
                int i5 = i;
                View view = convertView2;
                return convertView2;
            }
        } catch (Exception e5) {
            int i6 = i;
            return convertView;
        }
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
                Cart__Fragment_ListAdapter.db.deletecart_byid(Cart__Fragment_ListAdapter.txtpkey);
                Cart__Fragment_ListAdapter.feedItems.remove(Cart__Fragment_ListAdapter.pos);
                Cart__Fragment_ListAdapter.fragment.calculate_delicharge();
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
