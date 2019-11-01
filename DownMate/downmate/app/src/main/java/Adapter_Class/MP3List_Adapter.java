package Adapter_Class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.down_mate.MP3_Converts;
import com.down_mate.NetworkConnections;
import com.down_mate.R;

import java.util.List;

import Feed_Data.MP3_Files;

public class MP3List_Adapter extends BaseAdapter {
	private List<MP3_Files> dataItems;
	private Activity activity;
	private LayoutInflater inflater;
	private Context context;
	ProgressDialog pd;
	Typeface face;
	public NetworkConnections cd;
	public MP3List_Adapter(Activity activity, List<MP3_Files> dataItems) {
		 this.activity = activity;
		 this.dataItems = dataItems;
		 context=activity.getApplicationContext();
		cd=new NetworkConnections(context);
		pd=new ProgressDialog(activity);
		face= Typeface.createFromAsset(context.getAssets(), "font/fonts.otf");
	}
	@Override
	public Object getItem(int location) {
		return dataItems.get(location);
	}
	@Override
	public int getCount() {
		return dataItems.size();
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.custom_mp3_layout, null);
		}
		final ImageView image=convertView.findViewById(R.id.img);
		final TextView title =convertView.findViewById(R.id.viewtitle);

		final ImageView share=convertView.findViewById(R.id.share);
		RelativeLayout layout=convertView.findViewById(R.id.layout);

		final MP3_Files item = dataItems.get(position);

		title.setText(item.get_title());

		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				MP3_Converts mp3=(MP3_Converts)activity;
				mp3.playmusic(item.get_fpath());

			}
		});

		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MP3_Converts mp3=(MP3_Converts)activity;
				mp3.share_audio(item.get_fpath());
			}
		});

		return convertView;
	}

}
