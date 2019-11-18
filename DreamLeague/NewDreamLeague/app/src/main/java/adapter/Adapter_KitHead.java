package adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dlkitmaker_feeds.R;
import com.dlkitmaker_feeds.Temp;
import com.dlkitmaker_feeds.WebResources;

import java.util.List;

import data.Feed_KitHeads;

public class Adapter_KitHead extends RecyclerView.Adapter<Adapter_KitHead.Myviewholder> {
    private List<Feed_KitHeads> items;
    private Context context;
    Activity activity;
    public Adapter_KitHead(Activity activity, List<Feed_KitHeads> items, Context context) {
        this.items = items;
        this.context=context;
        this.activity=activity;
    }
    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kithead_item_layout, parent, false);
        return new Myviewholder(view);
    }
    @Override
    public void onBindViewHolder(final Myviewholder holder, int position) {
        holder.imageView.setImageResource(items.get(position).image);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.getAdapterPosition()==-1)
                {
                    return;
                }
                else
                {
                    switch (holder.getAdapterPosition())
                    {
                        case 0:
                            Temp.resourcelink="https://www.kuchalana.com";
                            Temp.resourcetitle="Kuchalana";
                            opensubkit();
                            break;
                        case 1:
                            Temp.resourcelink="http://dlscenter.com";
                            Temp.resourcetitle="DLS Center";
                            opensubkit();
                            break;
                        case 2:
                            Temp.resourcelink="https://www.dlsvn.com";
                            Temp.resourcetitle="DLS VN";
                            opensubkit();
                            break;
                        case 3:
                            Temp.resourcelink="https://dreamleaguesoccer.com.br";
                            Temp.resourcetitle="DL Soccer 1";
                            opensubkit();
                            break;
                        case 4:
                            Temp.resourcelink="https://dreamleaguesoccerkitss.com";
                            Temp.resourcetitle="DL Soccer 2";
                            opensubkit();
                            break;
                        case 5:
                            Temp.resourcelink="https://www.dream11.today/";
                            Temp.resourcetitle="Dream 11";
                            opensubkit();
                            break;

                        case 6:
                            Temp.resourcelink="https://www.dreamkitsoccer.com/";
                            Temp.resourcetitle="DLS";
                            opensubkit();
                            break;
                    }
                }
            }
        });
    }


    public void opensubkit()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {


                Intent i = new Intent(context, WebResources.class);
                context.startActivity(i);

            }

        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public Myviewholder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }

}
