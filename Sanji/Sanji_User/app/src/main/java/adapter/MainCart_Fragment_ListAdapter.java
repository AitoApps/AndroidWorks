package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
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
import com.sanji.MyListView;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Cart_FeedItem;
import data.MainCart_FeedItem;
import java.util.ArrayList;
import java.util.List;

public class MainCart_Fragment_ListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    DatabaseHandler db;
    Typeface face;
    Typeface face1;
    private List<MainCart_FeedItem> feedItems;
    FragmentCart fragment;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    UserDatabaseHandler udb;

    public MainCart_Fragment_ListAdapter(Activity activity2, List<MainCart_FeedItem> feedItems2, FragmentCart fragment2) {
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
        String str = "";
        String str2 = "%.2f";
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
            }
            if (convertView == null) {
                convertView2 = inflater.inflate(R.layout.custom_maincartlist, null);
            } else {
                convertView2 = convertView;
            }
            try {
                TextView shopname = (TextView) convertView2.findViewById(R.id.shopname);
                TextView nettotal = (TextView) convertView2.findViewById(R.id.nettotal);
                TextView deliverycharge = (TextView) convertView2.findViewById(R.id.deliverycharge);
                TextView grandtotal = (TextView) convertView2.findViewById(R.id.grandtotal);
                TextView deliverydisc = (TextView) convertView2.findViewById(R.id.deliverydisc);
                TextView txtnettotal = (TextView) convertView2.findViewById(R.id.txtnettotal);
                ImageView shopimg = (ImageView) convertView2.findViewById(R.id.shopimg);
                TextView txtdeliverycharge = (TextView) convertView2.findViewById(R.id.txtdeliverycharge);
                TextView txtgrandtotal = (TextView) convertView2.findViewById(R.id.txtgrandtotal);
                shopname.setTypeface(face);
                nettotal.setTypeface(face);
                deliverycharge.setTypeface(face);
                grandtotal.setTypeface(face);
                deliverydisc.setTypeface(face);
                txtnettotal.setTypeface(face);
                txtdeliverycharge.setTypeface(face);
                txtgrandtotal.setTypeface(face);
                MyListView list = (MyListView) convertView2.findViewById(R.id.list);
                try {
                    MainCart_FeedItem item = (MainCart_FeedItem) feedItems.get(position);
                    convertView = convertView2;
                    try {
                        TextView textView = txtnettotal;
                        String rupee = context.getResources().getString(R.string.Rs);
                        shopname.setText(item.getShopname());
                        StringBuilder sb = new StringBuilder();
                        sb.append(rupee);
                        TextView textView2 = shopname;
                        TextView textView3 = txtdeliverycharge;
                        sb.append(String.format(str2, new Object[]{Float.valueOf(Float.parseFloat(item.getTotalcharge()))}));
                        nettotal.setText(sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(rupee);
                        sb2.append(String.format(str2, new Object[]{Float.valueOf(Float.parseFloat(item.getDeliverycharge()))}));
                        deliverycharge.setText(sb2.toString());
                        if (item.getDelidisc().equalsIgnoreCase(str) || item.getDelidisc().equalsIgnoreCase("NA")) {
                            deliverydisc.setText(str);
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("*");
                            sb3.append(item.getDelidisc());
                            deliverydisc.setText(sb3.toString());
                        }
                        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
                        RequestManager with = Glide.with(context);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(Temp.weblink);
                        sb4.append("shoppicsmall/");
                        sb4.append(item.getShopid());
                        sb4.append(".jpg");
                        with.load(sb4.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(shopimg);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(rupee);
                        sb5.append(String.format(str2, new Object[]{Float.valueOf(Float.parseFloat(item.getTotalcharge()) + Float.parseFloat(item.getDeliverycharge()))}));
                        grandtotal.setText(sb5.toString());
                        List<Cart_FeedItem> cartFeedItems = new ArrayList<>();
                        list.setAdapter(new Cart__Fragment_ListAdapter(activity, cartFeedItems, fragment));
                        cartFeedItems.clear();
                        ArrayList<String> id1 = db.getcart_byshopid(item.getShopid());
                        String[] k = (String[]) id1.toArray(new String[id1.size()]);
                        RequestOptions requestOptions = rep;
                        int i = 0;
                        while (i < k.length) {
                            Cart_FeedItem item2 = new Cart_FeedItem();
                            String rupee2 = rupee;
                            item2.setpkey(k[i]);
                            int i2 = i + 1;
                            item2.setshopid(k[i2]);
                            int i3 = i2 + 1;
                            item2.setitemid(k[i3]);
                            int i4 = i3 + 1;
                            item2.setitemname(k[i4]);
                            int i5 = i4 + 1;
                            item2.setitemprice(k[i5]);
                            int i6 = i5 + 1;
                            item2.setqty(k[i6]);
                            int i7 = i6 + 1;
                            item2.settotal(k[i7]);
                            int i8 = i7 + 1;
                            item2.setimgsig(k[i8]);
                            int i9 = i8 + 1;
                            item2.setMinqty(k[i9]);
                            int i10 = i9 + 1;
                            item2.setUnittype(k[i10]);
                            cartFeedItems.add(item2);
                            i = i10 + 1;
                            rupee = rupee2;
                        }
                        return convertView;
                    } catch (Exception e) {
                        return convertView;
                    }
                } catch (Exception e2) {
                    View view = convertView2;
                    return convertView2;
                }
            } catch (Exception e3) {
                int i11 = position;
                View view2 = convertView2;
                return convertView2;
            }
        } catch (Exception e4) {
            int i12 = position;
            return convertView;
        }
    }
}
