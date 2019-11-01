package com.sanji;

import adapter.MainCart_Fragment_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import data.MainCart_FeedItem;
import java.util.ArrayList;
import java.util.List;

public class FragmentCart extends Fragment {
    ConnectionDetecter cd;
    Context context;
    public DatabaseHandler db;
    Typeface face;
    Typeface face1;
    private List<MainCart_FeedItem> feedItems;
    ListView list;
    private MainCart_Fragment_ListAdapter listAdapter;

    public MediaPlayer mediaPlayer;
    ImageView nodata;
    Button ordernow;
    ProgressBar pb;
    ProgressDialog pd;
    TextView title;
    TextView totalcount;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        context = getContext();
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        nodata = (ImageView) view.findViewById(R.id.nodata);
        list = (ListView) view.findViewById(R.id.list);
        ordernow = (Button) view.findViewById(R.id.ordernow);
        title = (TextView) view.findViewById(R.id.title);
        totalcount = (TextView) view.findViewById(R.id.totalcount);
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(context);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        feedItems = new ArrayList();
        listAdapter = new MainCart_Fragment_ListAdapter(getActivity(), feedItems, this);
        list.setAdapter(listAdapter);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
        ordernow.setText("Order Now ");
        ordernow.setTypeface(face1);
        title.setTypeface(face);
        totalcount.setTypeface(face);
        ordernow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = "";
                String str2 = "";
                String str3 = "";
                int islow = 0;
                ArrayList<String> id1 = FragmentCart.db.getcheck_minordrqty();
                String[] k = (String[]) id1.toArray(new String[id1.size()]);
                int i = 0;
                while (true) {
                    if (i >= k.length) {
                        break;
                    }
                    String shopname = k[i];
                    int i2 = i + 1;
                    String currntamt = k[i2];
                    int i3 = i2 + 1;
                    String minamunt = k[i3];
                    if (Float.parseFloat(minamunt) > Float.parseFloat(currntamt)) {
                        islow = 1;
                        FragmentCart fragmentCart = FragmentCart.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("ക്ഷമിക്കണം ! ");
                        sb.append(shopname);
                        sb.append("എന്ന ഷോപ്പിന്റെ മിനിമം ഓര്‍ഡര്‍ ");
                        sb.append(minamunt);
                        sb.append(" രൂപയുടേതാണ്. ദയവായി ഓര്‍ഡറില്‍ മാറ്റം വരുത്തുക\n");
                        fragmentCart.show_lowcart(sb.toString());
                        break;
                    }
                    i = i3 + 1;
                }
                if (islow == 0) {
                    FragmentCart.startActivity(new Intent(FragmentCart.context, User_Address.class));
                }
            }
        });
        calculate_delicharge();
        return view;
    }

    public void calculate_delicharge() {
        ArrayList<String> id1 = db.getcart_fordelicalc();
        String[] m = (String[]) id1.toArray(new String[id1.size()]);
        int i = 0;
        while (i < m.length) {
            String shopid = m[i];
            int i2 = i + 1;
            String shopdelicharge = m[i2];
            char c = 1;
            int i3 = i2 + 1;
            String shoptotalprice = m[i3];
            String str = "0";
            if (!shopdelicharge.equalsIgnoreCase(str) && !shopdelicharge.equalsIgnoreCase("NA")) {
                String str2 = "";
                if (!shopdelicharge.equalsIgnoreCase(str2)) {
                    String str3 = "#";
                    String str4 = "-";
                    if (!shopdelicharge.contains(str3) && shopdelicharge.contains(str4)) {
                        String[] p = shopdelicharge.split(str4);
                        Float charge = checkpricein(Float.parseFloat(p[0]), Float.parseFloat(shoptotalprice), Float.parseFloat(p[1]));
                        DatabaseHandler databaseHandler = db;
                        StringBuilder sb = new StringBuilder();
                        sb.append(charge);
                        sb.append(str2);
                        databaseHandler.update_finalcharges(shopid, sb.toString(), shoptotalprice);
                        i = i3 + 1;
                    } else if (!shopdelicharge.contains(str3) || !shopdelicharge.contains(":")) {
                        db.update_finalcharges(shopid, str, shoptotalprice);
                        i = i3 + 1;
                    } else {
                        String[] f = shopdelicharge.split(str3);
                        int y = 0;
                        while (true) {
                            if (y >= f.length) {
                                break;
                            }
                            String[] p2 = f[y].split(str4);
                            Float charge2 = checkpricein(Float.parseFloat(p2[0]), Float.parseFloat(shoptotalprice), Float.parseFloat(p2[c]));
                            if (charge2.floatValue() > 0.0f) {
                                DatabaseHandler databaseHandler2 = db;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(charge2);
                                sb2.append(str2);
                                databaseHandler2.update_finalcharges(shopid, sb2.toString(), shoptotalprice);
                                break;
                            }
                            y++;
                            c = 1;
                        }
                        i = i3 + 1;
                    }
                }
            }
            db.update_finalcharges(shopid, str, shoptotalprice);
            i = i3 + 1;
        }
        loadmaindata();
        calculatetotal();
    }

    public Float checkpricein(float to, float price, float dcharcge) {
        Float dprice = Float.valueOf(0.0f);
        if (price <= to) {
            return Float.valueOf(dcharcge);
        }
        return dprice;
    }

    public void loadmaindata() {
        nodata.setVisibility(View.GONE);
        feedItems.clear();
        ArrayList<String> id1 = db.getmain_cart();
        String[] k = (String[]) id1.toArray(new String[id1.size()]);
        int i = 0;
        while (i < k.length) {
            MainCart_FeedItem item = new MainCart_FeedItem();
            item.setShopid(k[i]);
            int i2 = i + 1;
            item.setShopname(k[i2]);
            int i3 = i2 + 1;
            item.setDelidisc(k[i3]);
            int i4 = i3 + 1;
            item.setTotalcharge(k[i4]);
            int i5 = i4 + 1;
            item.setDeliverycharge(k[i5]);
            int i6 = i5 + 1;
            item.setMiniordramt(k[i6]);
            int i7 = i6 + 1;
            item.setimgsig(k[i7]);
            feedItems.add(item);
            i = i7 + 1;
        }
        pb.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();
    }

    public void calculatetotal() {
        String rupee = getResources().getString(R.string.Rs);
        float gtotal = db.get_cartgrandtotal();
        if (gtotal > 0.0f) {
            TextView textView = totalcount;
            StringBuilder sb = new StringBuilder();
            sb.append(db.get_totalqty());
            sb.append(" Items | ");
            sb.append(rupee);
            sb.append(String.format("%.2f", new Object[]{Float.valueOf(gtotal)}));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = totalcount;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(db.get_totalqty());
            sb2.append(" items | ");
            sb2.append(rupee);
            sb2.append("0.00");
            textView2.setText(sb2.toString());
        }
        try {
            ((Control_Panel) getActivity()).setcartcount();
        } catch (Exception e) {
        }
    }

    public void cartupdate(String productid1, String cart_productname1, String cart_price1, String cart_minqty1, String cart_unittype1, String cur_qty) {
        final Dialog dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.cartupdate_fromcart);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);
        EditText qty = (EditText) dialog1.findViewById(R.id.qty);
        TextView unittype = (TextView) dialog1.findViewById(R.id.unittype);
        TextView price = (TextView) dialog1.findViewById(R.id.price);
        Button buy = (Button) dialog1.findViewById(R.id.buy);
        ImageView adminclose = (ImageView) dialog1.findViewById(R.id.adminclose);
        String rupee = getResources().getString(R.string.Rs);
        itemname.setText(cart_productname1);
        qty.setText(cur_qty);
        qty.setSelection(cur_qty.length());
        unittype.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(cart_unittype1)));
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        sb.append(cart_price1);
        price.setText(sb.toString());
        final EditText editText = qty;
        final String str = cart_minqty1;
        TextView textView = itemname;
        AnonymousClass2 r1 = r3;
        final TextView textView2 = price;
        final String str2 = rupee;
        final String str3 = cart_price1;
        AnonymousClass2 r3 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                String str = "";
                if (!editText.getText().toString().equalsIgnoreCase(str) && Float.parseFloat(editText.getText().toString()) >= 0.0f) {
                    float qty1 = Float.parseFloat(editText.getText().toString()) / Float.parseFloat(str);
                    TextView textView = textView2;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(Float.parseFloat(str3) * qty1);
                    sb.append(str);
                    textView.setText(sb.toString());
                }
            }
        };
        qty.addTextChangedListener(r1);
        adminclose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        final EditText editText2 = qty;
        final TextView textView3 = price;
        final String str4 = rupee;
        final String str5 = productid1;
        final Dialog dialog = dialog1;
        AnonymousClass4 r2 = new OnClickListener() {
            public void onClick(View view) {
                FragmentCart.db.addcart_update(editText2.getText().toString(), textView3.getText().toString().replace(str4, ""), str5);
                try {
                    FragmentCart.mediaPlayer = MediaPlayer.create(FragmentCart.getContext(), R.raw.addtocart);
                    FragmentCart.mediaPlayer.setVolume(0.1f, 0.1f);
                    FragmentCart.mediaPlayer.start();
                } catch (Exception e) {
                }
                dialog.dismiss();
                FragmentCart.calculate_delicharge();
            }
        };
        buy.setOnClickListener(r2);
        dialog1.show();
    }

    public void show_lowcart(String message1) {
        final Dialog dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.custom_cartamountlow);
        Button ok = (Button) dialog1.findViewById(R.id.ok);
        ((TextView) dialog1.findViewById(R.id.message)).setText(message1);
        ok.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
}
