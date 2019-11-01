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
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji.DatabaseHandler;
import com.sanji.Product_List;
import com.sanji.Registration;
import com.sanji.Temp;
import com.sanji.UserDatabaseHandler;
import data.Product_List_FeedItem;
import es.dmoral.toasty.Toasty;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Product_List_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;

    public Activity activity;

    public Context context;
    public DatabaseHandler db = new DatabaseHandler(context);
    Typeface face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
    Typeface face1 = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    Typeface face2 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");

    public List<Product_List_FeedItem> feedItems;
    private LayoutInflater inflater;
    UserDatabaseHandler udb = new UserDatabaseHandler(context);

    public class viewHolder extends ViewHolder {
        Button buy;
        ImageView call;
        ImageView discription;
        ImageView image;
        RelativeLayout layout;
        ImageView location;
        RelativeLayout lytimage;
        RelativeLayout lytshopstatus;
        TextView offerpersentage;
        TextView offerprice;
        TextView orginalprice;
        TextView productname;
        TextView shopdetails;
        TextView shopstatus;
        TextView shoptiming;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            discription = (ImageView) itemView.findViewById(R.id.discription);
            productname = (TextView) itemView.findViewById(R.id.productname);
            offerprice = (TextView) itemView.findViewById(R.id.offerprice);
            orginalprice = (TextView) itemView.findViewById(R.id.orginalprice);
            offerpersentage = (TextView) itemView.findViewById(R.id.offerpersentage);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            shopdetails = (TextView) itemView.findViewById(R.id.shopdetails);
            buy = (Button) itemView.findViewById(R.id.buy);
            lytimage = (RelativeLayout) itemView.findViewById(R.id.lytimage);
            call = (ImageView) itemView.findViewById(R.id.call);
            location = (ImageView) itemView.findViewById(R.id.location);
            shoptiming = (TextView) itemView.findViewById(R.id.shoptiming);
            shopstatus = (TextView) itemView.findViewById(R.id.shopstatus);
            lytshopstatus = (RelativeLayout) itemView.findViewById(R.id.lytshopstatus);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Product_List_ListAdapter(Activity activity2, List<Product_List_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
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

    /* JADX WARNING: Removed duplicated region for block: B:41:0x01fe A[Catch:{ Exception -> 0x0277 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0204 A[Catch:{ Exception -> 0x0277 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0213 A[Catch:{ Exception -> 0x0277 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x021b A[Catch:{ Exception -> 0x0277 }] */
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder2 = holder;
        final int i = position;
        String str = "0";
        String str2 = "NA";
        if (viewHolder2 instanceof viewHolder) {
            try {
                if (i == feedItems.size() - 1) {
                    ((Product_List) activity).loadmore();
                }
                Product_List_FeedItem item = (Product_List_FeedItem) feedItems.get(i);
                viewHolder viewHolder3 = (viewHolder) viewHolder2;
                if (item.getdeli_type().equalsIgnoreCase("2")) {
                    viewHolder3.buy.setVisibility(View.VISIBLE);
                } else if (item.getdeli_type().equalsIgnoreCase("1")) {
                    viewHolder3.buy.setVisibility(View.GONE);
                }
                try {
                    viewHolder3.productname.setText(item.getitemname().toUpperCase());
                } catch (Exception e) {
                    viewHolder3.productname.setText(item.getitemname().toUpperCase());
                }
                viewHolder3.productname.setTypeface(face1);
                viewHolder3.buy.setTypeface(face1);
                viewHolder3.orginalprice.setTypeface(face2);
                viewHolder3.offerpersentage.setTypeface(face1);
                viewHolder3.shopdetails.setTypeface(face1);
                viewHolder3.orginalprice.setTextColor(SupportMenu.CATEGORY_MASK);
                String rupee = context.getResources().getString(R.string.Rs);
                viewHolder3.offerprice.setTypeface(face2);
                TextView textView = viewHolder3.offerprice;
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(item.getprice());
                textView.setText(sb.toString());
                if (item.getitemdiscription().toUpperCase().equalsIgnoreCase(str2)) {
                    viewHolder3.discription.setVisibility(View.INVISIBLE)
                } else {
                    viewHolder3.discription.setVisibility(View.VISIBLE);
                }
                TextView textView2 = viewHolder3.shopdetails;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(item.getshopname());
                sb2.append(" , ");
                sb2.append(item.getshopplace());
                textView2.setText(sb2.toString());
                String str3 = "";
                if (item.getogprice().equalsIgnoreCase(str)) {
                    viewHolder3.offerpersentage.setText("1%");
                } else {
                    try {
                        float p = ((Float.parseFloat(item.getogprice()) - Float.parseFloat(item.getprice())) / Float.parseFloat(item.getogprice())) * 100.0f;
                        viewHolder3.offerpersentage.setVisibility(View.VISIBLE);
                        TextView textView3 = viewHolder3.offerpersentage;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Math.abs(Math.round(p)));
                        sb3.append("%");
                        textView3.setText(sb3.toString());
                    } catch (Exception e2) {
                        viewHolder3.offerpersentage.setVisibility(View.INVISIBLE)
                        viewHolder3.offerpersentage.setText(str3);
                    }
                }
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
                RequestManager with = Glide.with(context);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Temp.weblink);
                sb4.append("productpicsmall/");
                sb4.append(item.getsn());
                sb4.append("_1.jpg");
                with.load(sb4.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder3.image);
                if (!item.getogprice().equalsIgnoreCase(str2)) {
                    if (!item.getogprice().equalsIgnoreCase(str)) {
                        viewHolder3.orginalprice.setVisibility(View.VISIBLE);
                        TextView textView4 = viewHolder3.orginalprice;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(rupee);
                        sb5.append(item.getogprice());
                        textView4.setText(sb5.toString());
                        viewHolder3.orginalprice.setPaintFlags(viewHolder3.orginalprice.getPaintFlags() | 16);
                        if (!item.getitemdiscription().equalsIgnoreCase(str3)) {
                            viewHolder3.discription.setVisibility(View.INVISIBLE)
                        } else {
                            viewHolder3.discription.setVisibility(View.VISIBLE);
                        }
                        if (!shopopencheck(item.getshoptime())) {
                            viewHolder3.lytshopstatus.setVisibility(View.GONE);
                        } else {
                            viewHolder3.lytshopstatus.setVisibility(View.VISIBLE);
                            viewHolder3.shoptiming.setText(str3);
                            viewHolder3.shoptiming.setTypeface(face2);
                            viewHolder3.shopstatus.setText("അടച്ചിരിക്കുന്നു ");
                            viewHolder3.shoptiming.setTypeface(face2);
                        }
                        viewHolder3.discription.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                ((Product_List) Product_List_ListAdapter.activity).showdiscription(item.getitemdiscription(), item.getdeli_type(), item.getdlcharge(), item.getddsic());
                            }
                        });
                        viewHolder3.buy.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (Product_List_ListAdapter.udb.get_userid().equalsIgnoreCase("")) {
                                    Toasty.info(Product_List_ListAdapter.context, "ദയവായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ റെജിസ്ട്രര്‍ ചെയ്യുക ", 0).show();
                                    Intent i = new Intent(Product_List_ListAdapter.context, Registration.class);
                                    i.setFlags(268435456);
                                    Product_List_ListAdapter.context.startActivity(i);
                                    return;
                                }
                                Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List product_List = (Product_List) Product_List_ListAdapter.activity;
                                product_List.showaddcart(item.getshopid(), item.getsn(), item.getitemname(), item.getprice(), "1", item.getprice(), item.getimgsig1(), item.getmaxorder());
                            }
                        });
                        viewHolder3.image.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                ((Product_List) Product_List_ListAdapter.activity).photoview(item.getimgsig1(), item.getsn());
                            }
                        });
                        viewHolder3.shopdetails.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List h = (Product_List) Product_List_ListAdapter.activity;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Are you sure want to call to ");
                                sb.append(item.getshopname());
                                sb.append(" ?");
                                h.showalertcall(sb.toString(), item.getshopmobile());
                            }
                        });
                        viewHolder3.call.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List h = (Product_List) Product_List_ListAdapter.activity;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Are you sure want to call to ");
                                sb.append(item.getshopname());
                                sb.append(" ?");
                                h.showalertcall(sb.toString(), item.getshopmobile());
                            }
                        });
                        viewHolder3.location.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                                ((Product_List) Product_List_ListAdapter.activity).showmap(item.getlocation().replace("-", ","), item.getshopname());
                            }
                        });
                    }
                }
                viewHolder3.orginalprice.setVisibility(View.GONE);
                if (!item.getitemdiscription().equalsIgnoreCase(str3)) {
                }
                if (!shopopencheck(item.getshoptime())) {
                }
                viewHolder3.discription.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        ((Product_List) Product_List_ListAdapter.activity).showdiscription(item.getitemdiscription(), item.getdeli_type(), item.getdlcharge(), item.getddsic());
                    }
                });
                viewHolder3.buy.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Product_List_ListAdapter.udb.get_userid().equalsIgnoreCase("")) {
                            Toasty.info(Product_List_ListAdapter.context, "ദയവായി താങ്കളുടെ മൊബൈല്‍ നമ്പര്‍ റെജിസ്ട്രര്‍ ചെയ്യുക ", 0).show();
                            Intent i = new Intent(Product_List_ListAdapter.context, Registration.class);
                            i.setFlags(268435456);
                            Product_List_ListAdapter.context.startActivity(i);
                            return;
                        }
                        Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List product_List = (Product_List) Product_List_ListAdapter.activity;
                        product_List.showaddcart(item.getshopid(), item.getsn(), item.getitemname(), item.getprice(), "1", item.getprice(), item.getimgsig1(), item.getmaxorder());
                    }
                });
                viewHolder3.image.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        ((Product_List) Product_List_ListAdapter.activity).photoview(item.getimgsig1(), item.getsn());
                    }
                });
                viewHolder3.shopdetails.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List h = (Product_List) Product_List_ListAdapter.activity;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Are you sure want to call to ");
                        sb.append(item.getshopname());
                        sb.append(" ?");
                        h.showalertcall(sb.toString(), item.getshopmobile());
                    }
                });
                viewHolder3.call.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List h = (Product_List) Product_List_ListAdapter.activity;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Are you sure want to call to ");
                        sb.append(item.getshopname());
                        sb.append(" ?");
                        h.showalertcall(sb.toString(), item.getshopmobile());
                    }
                });
                viewHolder3.location.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_List_FeedItem product_List_FeedItem = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        Product_List_FeedItem item = (Product_List_FeedItem) Product_List_ListAdapter.feedItems.get(i);
                        ((Product_List) Product_List_ListAdapter.activity).showmap(item.getlocation().replace("-", ","), item.getshopname());
                    }
                });
            } catch (Exception a) {
                Toasty.info(context, Log.getStackTraceString(a), 1).show();
            }
        }
    }

    /* JADX WARNING: type inference failed for: r6v3, types: [boolean] */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r6v20 */
    /* JADX WARNING: type inference failed for: r6v21 */
    /* JADX WARNING: type inference failed for: r6v22 */
    /* JADX WARNING: type inference failed for: r6v23 */
    /* JADX WARNING: type inference failed for: r6v24 */
    /* JADX WARNING: type inference failed for: r6v25 */
    /* JADX WARNING: type inference failed for: r6v26 */
    /* JADX WARNING: type inference failed for: r6v27 */
    /* JADX WARNING: type inference failed for: r6v28 */
    /* JADX WARNING: type inference failed for: r6v29 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v3, types: [boolean]
  assigns: []
  uses: [?[int, short, byte, char], boolean]
  mth insns count: 110
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 8 */
    public boolean shopopencheck(String timedata) {
        ? r6;
        String weekDay;
        boolean z;
        String[] time = timedata.split("##");
        String[] time1 = time[0].split("-");
        String str = ":";
        String[] start = time1[0].split(str);
        String[] stop = time1[1].split(str);
        int start_h = Integer.parseInt(start[0]);
        int start_m = Integer.parseInt(start[1]);
        int stop_h = Integer.parseInt(stop[0]);
        int stop_m = Integer.parseInt(stop[1]);
        String[] now = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()).split(str);
        int now_h = Integer.parseInt(now[0]);
        int now_m = Integer.parseInt(now[1]);
        String weekDay2 = "";
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(7);
        if (2 == dayOfWeek) {
            int i = dayOfWeek;
            weekDay = "2";
            r6 = 1;
        } else if (3 == dayOfWeek) {
            int i2 = dayOfWeek;
            weekDay = "3";
            r6 = 1;
        } else if (4 == dayOfWeek) {
            int i3 = dayOfWeek;
            weekDay = "4";
            r6 = 1;
        } else if (5 == dayOfWeek) {
            int i4 = dayOfWeek;
            weekDay = "5";
            r6 = 1;
        } else if (6 == dayOfWeek) {
            int i5 = dayOfWeek;
            weekDay = "6";
            r6 = 1;
        } else if (7 == dayOfWeek) {
            int i6 = dayOfWeek;
            weekDay = "7";
            r6 = 1;
        } else {
            ? r62 = 1;
            if (1 == dayOfWeek) {
                int i7 = dayOfWeek;
                weekDay = "1";
                r6 = r62;
            } else {
                int i8 = dayOfWeek;
                weekDay = weekDay2;
                r6 = r62;
            }
        }
        Calendar calendar = c;
        if (!time[r6].contains(weekDay)) {
            return false;
        }
        if (start_h == stop_h && start_m == stop_m) {
            return r6;
        }
        if (start_h > now_h || stop_h < now_h) {
            if (stop_h > now_h) {
                z = false;
            } else if (start_h <= stop_h) {
                z = false;
                if (stop_h < now_h) {
                    return false;
                }
                if (stop_h == now_h) {
                    return stop_m >= now_m;
                }
            } else if (stop_h != now_h) {
                return true;
            } else {
                if (stop_m >= now_m) {
                    return true;
                }
                return false;
            }
        } else if (start_h < now_h) {
            return true;
        } else {
            if (start_h != now_h) {
                z = false;
            } else if (start_m >= now_m) {
                return true;
            } else {
                return false;
            }
        }
        return z;
    }
}
