package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.hellokhd.R;

import java.util.List;

import data.DistricResult_FeedItem;
import data.SchoolSearch_FeedItem;

public class SchoolSearch_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;

    public List<SchoolSearch_FeedItem> feedItems;
    private LayoutInflater inflater;
    public SchoolSearch_Adapter(Activity activity2, List<SchoolSearch_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
         RelativeLayout lytschoolname;
         TextView schoolname,totalmark;
         ImageView down,up;
         RelativeLayout lytmark;
         TextView txthsgeneral,hsgeneral,txthssgeneral,hssgeneral,txthsarabic,hsarabic,txthssanskrit,hssanskrit;
        public viewHolder(View itemView) {
            super(itemView);
            lytschoolname=itemView.findViewById(R.id.lytschoolname);
            schoolname=itemView.findViewById(R.id.schoolname);
            totalmark=itemView.findViewById(R.id.totalmark);
            down=itemView.findViewById(R.id.down);
            up=itemView.findViewById(R.id.up);
            lytmark=itemView.findViewById(R.id.lytmark);
            txthsgeneral=itemView.findViewById(R.id.txthsgeneral);
            hsgeneral=itemView.findViewById(R.id.hsgeneral);
            txthssgeneral=itemView.findViewById(R.id.txthssgeneral);
            hssgeneral=itemView.findViewById(R.id.hssgeneral);
            txthsarabic=itemView.findViewById(R.id.txthsarabic);
            hsarabic=itemView.findViewById(R.id.hsarabic);
            txthssanskrit=itemView.findViewById(R.id.txthssanskrit);
            hssanskrit=itemView.findViewById(R.id.hssanskrit);
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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_school_search, parent, false));
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
                SchoolSearch_FeedItem item = (SchoolSearch_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.schoolname.setTypeface(face);
                viewHolder2.totalmark.setTypeface(face);
                viewHolder2.txthsgeneral.setTypeface(face);
                viewHolder2.hsgeneral.setTypeface(face);
                viewHolder2.txthssgeneral.setTypeface(face);
                viewHolder2.hssgeneral.setTypeface(face);
                viewHolder2.txthsarabic.setTypeface(face);
                viewHolder2.hsarabic.setTypeface(face);
                viewHolder2.txthssanskrit.setTypeface(face);
                viewHolder2.hssanskrit.setTypeface(face);
                viewHolder2.schoolname.setText(item.getSchoolname());
                viewHolder2.totalmark.setText((Integer.parseInt(item.getHsgeneral())+Integer.parseInt(item.getHssgeneral())+Integer.parseInt(item.getHssanskrit())+Integer.parseInt(item.getHsarabic()))+"");
                viewHolder2.hsgeneral.setText(item.getHsgeneral());
                viewHolder2.hssgeneral.setText(item.getHssgeneral());
                viewHolder2.hsarabic.setText(item.getHsarabic());
                viewHolder2.hssanskrit.setText(item.getHssanskrit());

                viewHolder2.down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        viewHolder2.lytmark.setVisibility(View.VISIBLE);
                        viewHolder2.up.setVisibility(View.VISIBLE);
                        viewHolder2.down.setVisibility(View.INVISIBLE);
                    }
                });

                viewHolder2.up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder2.lytmark.setVisibility(View.GONE);
                        viewHolder2.up.setVisibility(View.INVISIBLE);
                        viewHolder2.down.setVisibility(View.VISIBLE);
                    }
                });

                viewHolder2.lytschoolname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(viewHolder2.lytmark.getVisibility()==View.VISIBLE)
                        {
                            viewHolder2.lytmark.setVisibility(View.GONE);
                            viewHolder2.up.setVisibility(View.INVISIBLE);
                            viewHolder2.down.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            viewHolder2.lytmark.setVisibility(View.VISIBLE);
                            viewHolder2.up.setVisibility(View.VISIBLE);
                            viewHolder2.down.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            } catch (Exception e) {

                Log.w("Errrr",Log.getStackTraceString(e));
            }
        }
    }
}
