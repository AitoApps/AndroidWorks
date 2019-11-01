package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.Cart;
import com.sanji.ConnectionDetecter;
import com.sanji.DatabaseHandler;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Cart_FeedItem;
import java.util.List;

public class Subcart_New_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    DatabaseHandler db;
    Typeface face;
    Typeface face1;

    public List<Cart_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtpkey = "";
    UserDatabaseHandler udb;

    public Subcart_New_ListAdapter(Activity activity2, List<Cart_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
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
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_cartlist, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView itemprice = (TextView) convertView2.findViewById(R.id.itemprice);
        TextView itemtotal = (TextView) convertView2.findViewById(R.id.itemtotal);
        TextView txtitemprice = (TextView) convertView2.findViewById(R.id.txtitemprice);
        EditText qty = (EditText) convertView2.findViewById(R.id.qty);
        TextView unittype = (TextView) convertView2.findViewById(R.id.unittype);
        TextView txtitemtotal = (TextView) convertView2.findViewById(R.id.txtitemtotal);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        RelativeLayout relativeLayout = (RelativeLayout) convertView2.findViewById(R.id.layout);
        Cart_FeedItem item = (Cart_FeedItem) feedItems.get(i);
        String rupee = context.getResources().getString(R.string.Rs);
        itemname.setText(item.getitemname());
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        String str = "%.2f";
        sb.append(String.format(str, new Object[]{Float.valueOf(Float.parseFloat(item.getitemprice()))}));
        sb.append("/");
        sb.append(item.getMinqty());
        sb.append(" ");
        ImageView delete2 = delete;
        sb.append((String) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype())));
        itemprice.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(rupee);
        sb2.append(item.gettotal());
        itemtotal.setText(sb2.toString());
        qty.setText(item.getqty());
        qty.setSelection(item.getqty().length());
        unittype.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype())));
        StringBuilder sb3 = new StringBuilder();
        sb3.append(rupee);
        sb3.append(String.format(str, new Object[]{Float.valueOf(Float.parseFloat(item.gettotal()))}));
        itemtotal.setText(sb3.toString());
        itemname.setTypeface(face);
        itemprice.setTypeface(face);
        itemtotal.setTypeface(face);
        txtitemprice.setTypeface(face);
        txtitemtotal.setTypeface(face);
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        RequestManager with = Glide.with(context);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(Temp.weblink);
        sb4.append("productpicsmall/");
        sb4.append(item.getitemid());
        sb4.append("_1.jpg");
        with.load(sb4.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        ImageView imageView = image;
        AnonymousClass1 r9 = r0;
        final EditText editText = qty;
        String rupee2 = rupee;
        final Cart_FeedItem cart_FeedItem = item;
        TextView textView = itemname;
        ImageView delete3 = delete2;
        RequestOptions requestOptions = rep;
        final TextView textView2 = itemtotal;
        TextView textView3 = txtitemtotal;
        final String str2 = rupee2;
        AnonymousClass1 r0 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                String str = "";
                if (!editText.getText().toString().equalsIgnoreCase(str) && Integer.parseInt(editText.getText().toString()) >= 0) {
                    float qty1 = Float.parseFloat(editText.getText().toString()) / Float.parseFloat(cart_FeedItem.getMinqty());
                    TextView textView = textView2;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(Float.parseFloat(cart_FeedItem.getitemprice()) * qty1);
                    sb.append(str);
                    textView.setText(sb.toString());
                    Subcart_New_ListAdapter.db.addcart_update(editText.getText().toString(), textView2.getText().toString().replace(str2, str), cart_FeedItem.getitemid());
                }
            }
        };
        qty.addTextChangedListener(r9);
        delete3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Cart_FeedItem cart_FeedItem = (Cart_FeedItem) Subcart_New_ListAdapter.feedItems.get(i);
                    Cart_FeedItem item = (Cart_FeedItem) Subcart_New_ListAdapter.feedItems.get(i);
                    Subcart_New_ListAdapter.txtpkey = item.getpkey();
                    Subcart_New_ListAdapter.pos = i;
                    Subcart_New_ListAdapter.showalert("Are you sure want to delete this item from cart?");
                } catch (Exception e) {
                }
            }
        });
        return convertView2;
    }

    public void showalert(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Subcart_New_ListAdapter.db.deletecart_byid(Subcart_New_ListAdapter.txtpkey);
                Cart cart = (Cart) Subcart_New_ListAdapter.activity;
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
