package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.hellokhd.R;

import java.util.List;

import data.IndividualResult_FeedItem;
import data.SchoolResult_FeedItem;

public class Individual_Result_Adapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;

    public Context context;
    Typeface face;

    public List<IndividualResult_FeedItem> feedItems;
    private LayoutInflater inflater;
    public Individual_Result_Adapter(Activity activity2, List<IndividualResult_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face=Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }
    public class viewHolder extends ViewHolder {
        TextView txtitemname,itemnamedot,mark,studentname;

        public viewHolder(View itemView) {
            super(itemView);
            txtitemname = itemView.findViewById(R.id.txtitemname);
            itemnamedot=itemView.findViewById(R.id.itemnamedot);
            mark=itemView.findViewById(R.id.mark);
            studentname=itemView.findViewById(R.id.studentname);

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
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_individual_result, parent, false));
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

                IndividualResult_FeedItem item = feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                if(position>0)
                {
                    viewHolder2.studentname.setVisibility(View.GONE);
                }
                else
                {
                    viewHolder2.studentname.setVisibility(View.VISIBLE);
                }
                viewHolder2.txtitemname.setTypeface(face);
                viewHolder2.mark.setTypeface(face);
                viewHolder2.studentname.setTypeface(face);
                viewHolder2.txtitemname.setText(item.getItemname());
                viewHolder2.studentname.setText(item.getStudentname());
                viewHolder2.mark.setText(item.getMark());
            } catch (Exception e) {

            }
        }
    }
}
