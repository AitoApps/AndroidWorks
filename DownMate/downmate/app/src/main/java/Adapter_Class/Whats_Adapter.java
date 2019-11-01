package Adapter_Class;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.down_mate.BackEnd_DB;
import com.down_mate.NetworkConnections;
import com.down_mate.R;
import com.down_mate.WP_StatusSaver;
import java.io.File;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;
import Feed_Data.Whats_Feed;
public class Whats_Adapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Whats_Feed> dataItems;
	 private Context context;
	BackEnd_DB db;
	Typeface face;
	ProgressDialog pd;
	DownloadManager manager;
	public NetworkConnections cd;
	public Whats_Adapter(Activity activity, List<Whats_Feed> dataItems) {
		 this.activity = activity;
		 this.dataItems = dataItems;
		 context=activity.getApplicationContext();
		db=new BackEnd_DB(context);
		cd=new NetworkConnections(context);
		pd=new ProgressDialog(activity);
		manager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		face= Typeface.createFromAsset(context.getAssets(), "font/fonts.otf");
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
	public Object getItem(int location) {
		return dataItems.get(location);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		try
		{
			if (inflater == null)
				inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.wplist_custom, null);
			}
			final ImageView img =convertView.findViewById(R.id.img);
			final VideoView vid=convertView.findViewById(R.id.video);
			final ImageView downicon=convertView.findViewById(R.id.downloadicon);
			final ImageView share=convertView.findViewById(R.id.shareicon);
			final ImageView pause=convertView.findViewById(R.id.pausevideo);
			final ImageView play=convertView.findViewById(R.id.videoplay);
			final Whats_Feed item = dataItems.get(position);
			if(item.getfpath().endsWith(".mp4"))
			{
				img.setVisibility(View.GONE);
				pause.setVisibility(View.INVISIBLE);
				vid.setVisibility(View.VISIBLE);
				play.setVisibility(View.VISIBLE);
				Uri video1 = Uri.parse(item.getfpath());
				vid.setVideoURI(video1);
				play.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						vid.start();
						pause.setVisibility(View.VISIBLE);
						play.setVisibility(View.INVISIBLE);
					}
				});
				vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						mp.setLooping(true);
						vid.seekTo(100);
				}
				});
				pause.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						vid.pause();
						pause.setVisibility(View.INVISIBLE);
						play.setVisibility(View.VISIBLE);
					}
				});
			}
			else if(item.getfpath().endsWith(".jpg"))
			{
				img.setVisibility(View.VISIBLE);
				vid.setVisibility(View.GONE);
				play.setVisibility(View.GONE);
				pause.setVisibility(View.GONE);
				Bitmap myBitmap = BitmapFactory.decodeFile(item.getfpath());
				img.setImageBitmap(myBitmap);
			}
			else if(item.getfpath().endsWith(".gif"))
			{
				img.setVisibility(View.VISIBLE);
				vid.setVisibility(View.GONE);
				play.setVisibility(View.GONE);
				pause.setVisibility(View.GONE);
				File file = new File(item.getfpath());
				Uri gifuri = Uri.fromFile(file);
				Glide.with(context).load(gifuri).into(img);
			}
			share.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					try
					{
						File f=new File(item.getfpath());
						WP_StatusSaver ws=(WP_StatusSaver)activity;
						ws.wpshare(f);
					}
					catch (Exception a)
					{
					}
				}
			});
			downicon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					try
					{
						File f=new File(item.getfpath());
						WP_StatusSaver ws=(WP_StatusSaver)activity;
						 ws.videosave(f);
					}
					catch (Exception a)
					{
					}
				}
			});
		}
		catch (Exception a)
		{
		}
		return convertView;
	}
}
