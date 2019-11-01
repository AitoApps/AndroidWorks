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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.DatabaseHandler;
import com.sanji.ProductList_New;
import com.sanji.R;
import com.sanji.Registration;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Productlist_new_FeedItem;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class Product_Listnew_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;

    public Activity activity;

    public Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Productlist_new_FeedItem> feedItems;
    private LayoutInflater inflater;
    UserDatabaseHandler udb = new UserDatabaseHandler(context);

    public Product_Listnew_ListAdapter(Activity activity2, List<Productlist_new_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face2 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
    }

    public class viewHolder extends ViewHolder {
        Button buy;
        ImageView image;
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_produtclist_new, parent, false));
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
                    viewHolder2.productname.setText(item.getItemname().toUpperCase());
                } catch (Exception e) {
                    viewHolder2.productname.setText(item.getItemname().toUpperCase());
                }
                viewHolder2.productname.setTypeface(face1);
                viewHolder2.buy.setTypeface(face1);
                viewHolder2.orginalprice.setTypeface(face2);
                viewHolder2.orginalprice.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                String rupee = context.getResources().getString(R.string.Rs);
                viewHolder2.offerprice.setTypeface(face2);

                String unittype = (String) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype()));
                viewHolder2.offerprice.setText(item.getPrice()+" / "+item.getMinorder()+" "+unittype);

                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig1()));
                Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getSn()+"_1.jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                if (!item.getOgprice().equalsIgnoreCase("NA")) {
                    if (!item.getOgprice().equalsIgnoreCase("0")) {
                        viewHolder2.orginalprice.setVisibility(View.VISIBLE);
                        viewHolder2.orginalprice.setText(rupee+item.getOgprice());
                        viewHolder2.orginalprice.setPaintFlags(viewHolder2.orginalprice.getPaintFlags() | 16);
                        viewHolder2.buy.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (udb.get_userid().equalsIgnoreCase("")) {
                                    Toasty.info(context, "ദയവായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ റെജിസ്ട്രര്‍ ചെയ്യുക ", 0).show();
                                    Intent i = new Intent(context, Registration.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                    return;
                                }
                                Productlist_new_FeedItem item = (Productlist_new_FeedItem) feedItems.get(position);
                                ((ProductList_New) activity).showaddcart(item.getShopid(), item.getSn(), item.getItemname(), item.getPrice(), item.getImgsig1(), item.getMinorder(), item.getUnittype(), item.getShopname(), item.getDelicharge(), item.getDelidisc(), item.getMinordramt(), item.getShopimgsig());
                            }
                        });
                        viewHolder2.image.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Productlist_new_FeedItem item = (Productlist_new_FeedItem) feedItems.get(position);
                                ((ProductList_New) activity).photoview(item.getImgsig1(), item.getSn());
                            }
                        });
                    }
                }
                viewHolder2.orginalprice.setVisibility(View.GONE);
                viewHolder2.buy.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (udb.get_userid().equalsIgnoreCase("")) {
                            Toasty.info(context, "ദയവായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ റെജിസ്ട്രര്‍ ചെയ്യുക ", 0).show();
                            Intent i = new Intent(context, Registration.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                        else
                        {
                            Productlist_new_FeedItem item = (Productlist_new_FeedItem) feedItems.get(position);
                            ((ProductList_New) activity).showaddcart(item.getShopid(), item.getSn(), item.getItemname(), item.getPrice(), item.getImgsig1(), item.getMinorder(), item.getUnittype(), item.getShopname(), item.getDelicharge(), item.getDelidisc(), item.getMinordramt(), item.getShopimgsig());

                        }

                    }
                });
                viewHolder2.image.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Productlist_new_FeedItem item = (Productlist_new_FeedItem) feedItems.get(position);
                        ((ProductList_New) activity).photoview(item.getImgsig1(), item.getSn());
                    }
                });
            } catch (Exception e2) {
            }
        }
    }
}
