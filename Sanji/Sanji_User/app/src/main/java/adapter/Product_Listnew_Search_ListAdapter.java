package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.DatabaseHandler;
import com.sanji.Fragment_Search;
import com.sanji.Registration;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Productlist_new_FeedItem;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class Product_Listnew_Search_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
    Typeface face1 = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    Typeface face2 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");

    public List<Productlist_new_FeedItem> feedItems;
    Fragment_Search fragment;
    private LayoutInflater inflater;
    UserDatabaseHandler udb = new UserDatabaseHandler(context);

    public class viewHolder extends ViewHolder {
        Button buy;
        ImageView image;
        TextView km;
        RelativeLayout layout;
        TextView offerprice;
        TextView orginalprice;
        TextView productname;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            productname = (TextView) itemView.findViewById(R.id.productname);
            offerprice = (TextView) itemView.findViewById(R.id.offerprice);
            orginalprice = (TextView) itemView.findViewById(R.id.orginalprice);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            buy = (Button) itemView.findViewById(R.id.buy);
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

    public Product_Listnew_Search_ListAdapter(Activity activity2, List<Productlist_new_FeedItem> feedItems2, Fragment_Search fragment2) {
        activity = activity2;
        feedItems = feedItems2;
        fragment = fragment2;
        context = activity2.getApplicationContext();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_produtclist_search_new, parent, false));
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
                Productlist_new_FeedItem item = (Productlist_new_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                try {
                    viewHolder2.productname.setText(item.getitemname().toUpperCase());
                } catch (Exception e) {
                    viewHolder2.productname.setText(item.getitemname().toUpperCase());
                }
                viewHolder2.productname.setTypeface(face1);
                viewHolder2.buy.setTypeface(face1);
                viewHolder2.orginalprice.setTypeface(face2);
                viewHolder2.orginalprice.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                String rupee = context.getResources().getString(R.string.Rs);
                viewHolder2.offerprice.setTypeface(face2);
                viewHolder2.km.setTypeface(face1);
                viewHolder2.km.setText(item.getdistance());
                String unittype = (String) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype()));
                TextView textView = viewHolder2.offerprice;
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(item.getprice());
                sb.append(" / ");
                sb.append(item.getMinorder());
                sb.append(" ");
                sb.append(unittype);
                textView.setText(sb.toString());
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
                RequestManager with = Glide.with(context);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append("productpicsmall/");
                sb2.append(item.getsn());
                sb2.append("_1.jpg");
                with.load(sb2.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                if (!item.getogprice().equalsIgnoreCase("NA")) {
                    if (!item.getogprice().equalsIgnoreCase("0")) {
                        viewHolder2.orginalprice.setVisibility(View.VISIBLE);
                        TextView textView2 = viewHolder2.orginalprice;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(rupee);
                        sb3.append(item.getogprice());
                        textView2.setText(sb3.toString());
                        viewHolder2.orginalprice.setPaintFlags(viewHolder2.orginalprice.getPaintFlags() | 16);
                        viewHolder2.buy.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (Product_Listnew_Search_ListAdapter.udb.get_userid().equalsIgnoreCase("")) {
                                    Toasty.info(Product_Listnew_Search_ListAdapter.context, "ദയവായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ റെജിസ്ട്രര്‍ ചെയ്യുക ", 0).show();
                                    Intent i = new Intent(Product_Listnew_Search_ListAdapter.context, Registration.class);
                                    i.setFlags(268435456);
                                    Product_Listnew_Search_ListAdapter.context.startActivity(i);
                                    return;
                                }
                                Productlist_new_FeedItem productlist_new_FeedItem = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                                Productlist_new_FeedItem item = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                                Product_Listnew_Search_ListAdapter.fragment.showaddcart(item.getshopid(), item.getsn(), item.getitemname(), item.getprice(), item.getimgsig1(), item.getMinorder(), item.getUnittype(), item.getshopname(), item.getDelicharge(), item.getDelidisc(), item.getMinordramt(), item.getshopimgsig());
                            }
                        });
                        viewHolder2.image.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Productlist_new_FeedItem productlist_new_FeedItem = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                                Productlist_new_FeedItem item = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                                Product_Listnew_Search_ListAdapter.fragment.photoview(item.getimgsig1(), item.getsn());
                            }
                        });
                    }
                }
                viewHolder2.orginalprice.setVisibility(View.GONE);
                viewHolder2.buy.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Product_Listnew_Search_ListAdapter.udb.get_userid().equalsIgnoreCase("")) {
                            Toasty.info(Product_Listnew_Search_ListAdapter.context, "ദയവായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ റെജിസ്ട്രര്‍ ചെയ്യുക ", 0).show();
                            Intent i = new Intent(Product_Listnew_Search_ListAdapter.context, Registration.class);
                            i.setFlags(268435456);
                            Product_Listnew_Search_ListAdapter.context.startActivity(i);
                            return;
                        }
                        Productlist_new_FeedItem productlist_new_FeedItem = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                        Productlist_new_FeedItem item = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                        Product_Listnew_Search_ListAdapter.fragment.showaddcart(item.getshopid(), item.getsn(), item.getitemname(), item.getprice(), item.getimgsig1(), item.getMinorder(), item.getUnittype(), item.getshopname(), item.getDelicharge(), item.getDelidisc(), item.getMinordramt(), item.getshopimgsig());
                    }
                });
                viewHolder2.image.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Productlist_new_FeedItem productlist_new_FeedItem = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                        Productlist_new_FeedItem item = (Productlist_new_FeedItem) Product_Listnew_Search_ListAdapter.feedItems.get(position);
                        Product_Listnew_Search_ListAdapter.fragment.photoview(item.getimgsig1(), item.getsn());
                    }
                });
            } catch (Exception a) {
                Toasty.info(context, Log.getStackTraceString(a), 1).show();
            }
        }
    }
}
