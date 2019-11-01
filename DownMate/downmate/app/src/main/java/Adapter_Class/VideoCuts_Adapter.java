package Adapter_Class;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.down_mate.R;
import com.down_mate.ThirtySecondSplitter;
import com.down_mate.TempValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import Feed_Data.VideoCuts_Feed;
import es.dmoral.toasty.Toasty;
public class VideoCuts_Adapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<VideoCuts_Feed> dataItems;
	 private Context context;
	Typeface face;
	public VideoCuts_Adapter(Activity activity, List<VideoCuts_Feed> dataItems) {
		 this.activity = activity;
		 this.dataItems = dataItems;
		 context=activity.getApplicationContext();
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
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.videocuts_custom, null);
		}
		final TextView vtitle =convertView.findViewById(R.id.viewtitle);
		final TextView dur=convertView.findViewById(R.id.duration);
		final TextView fpath=convertView.findViewById(R.id.fpath);
		final ImageView save=convertView.findViewById(R.id.saveicon);
		RelativeLayout lyt=convertView.findViewById(R.id.layout);
		final ImageView  share=convertView.findViewById(R.id.shareicon);
		final ImageView img=convertView.findViewById(R.id.img);
		VideoCuts_Feed item = dataItems.get(position);
		fpath.setText(item.get_fpath());
	    img.setImageBitmap(item.getbmp());
		vtitle.setText(item.get_title());
		dur.setText(item.getfileduration());
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try
				{
					VideoCuts_Feed item = dataItems.get(position);
					item =dataItems.get(position);
					File f=new File(item.get_fpath());
					String exrension=item.get_fpath().substring(item.get_fpath().lastIndexOf("."));
					File f1=new File(Environment.getExternalStorageDirectory()+"/"+ TempValues.destifolder +"/Splitted_Gallery/"+ System.currentTimeMillis()+"S"+exrension);
					ThirtySecondSplitter h=(ThirtySecondSplitter)activity;
					h.copyfile(f,f1);
					Toasty.success(context,"Saved to gallery", Toast.LENGTH_SHORT).show();
				}
				catch (Exception a)
				{
					Toasty.success(context,"Sorry ! Unable to Save", Toast.LENGTH_SHORT).show();
				}
			}
		});
		lyt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				VideoCuts_Feed item = dataItems.get(position);
				item = dataItems.get(position);
				ThirtySecondSplitter m=(ThirtySecondSplitter)activity;
				m.openfile(item.get_fpath());
			}
		});
	    share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				VideoCuts_Feed item = dataItems.get(position);
				item = dataItems.get(position);
				try
				{
					ArrayList<Uri> files = new ArrayList<Uri>();
					File f=new File(item.get_fpath());
					Uri uri = Uri.fromFile(f);
					files.add(uri);
					ThirtySecondSplitter h=(ThirtySecondSplitter)activity;
					if(h.isPackageExisted("com.whatsapp")&& h.isPackageExisted("com.whatsapp.w4b")&& h.isPackageExisted("com.img_whatsapp_gb"))
					{
						h.showshare_wp(files,1);
					}
					else if(h.isPackageExisted("com.whatsapp.w4b"))
					{
						h.onClickApp("com.whatsapp.w4b",files);
					}
					else if(h.isPackageExisted("com.whatsapp")&& h.isPackageExisted("com.whatsapp.w4b"))
					{
						h.showshare_wp(files,0);
					}
					else if(h.isPackageExisted("com.whatsapp"))
					{
						h.onClickApp("com.whatsapp",files);
					}
					else
					{
						Toasty.error(context, "Please install Whatsapp app", Toast.LENGTH_SHORT).show();
					}
				}
				catch (Exception a)
				{
					Toasty.error(context, "Sorry ! Unable to Share", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return convertView;
	}
}
