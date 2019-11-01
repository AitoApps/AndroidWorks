package Downly_adapter;

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
import com.downly_app.InternetConncetivity;
import com.downly_app.R;
import com.downly_app.Video_to_Mp3;
import java.util.List;
import Downly_Data.Feed_MP3;
public class MP3ConvertedList_Adapter extends BaseAdapter {
	private List<Feed_MP3> dataItems;
	private Activity activity;
	private LayoutInflater inflater;
	private Context context;
	ProgressDialog pd;
	Typeface face;
	public InternetConncetivity cd;
	public MP3ConvertedList_Adapter(Activity activity, List<Feed_MP3> dataItems) {
		 this.activity = activity;
		 this.dataItems = dataItems;
		 context=activity.getApplicationContext();
		cd=new InternetConncetivity(context);
		pd=new ProgressDialog(activity);
		face= Typeface.createFromAsset(context.getAssets(), "commonfont.otf");
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
			convertView = inflater.inflate(R.layout.mp3_custom_layout, null);
		}

		final ImageView share=convertView.findViewById(R.id.share);
		RelativeLayout layout=convertView.findViewById(R.id.layout);
		final ImageView image=convertView.findViewById(R.id.img);
		final TextView title =convertView.findViewById(R.id.viewtitle);
		final Feed_MP3 item = dataItems.get(position);

		title.setText(item.get_title());

		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Video_to_Mp3 mp3=(Video_to_Mp3)activity;
				mp3.playmusic(item.get_fpath());

			}
		});

		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Video_to_Mp3 mp3=(Video_to_Mp3)activity;
				mp3.audioshare(item.get_fpath());
			}
		});

		return convertView;
	}

}
