package Adapter_Class;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.down_mate.BackEnd_DB;
import com.down_mate.VideoDownloads;
import com.down_mate.NetworkConnections;
import com.down_mate.R;

import java.io.File;
import java.util.List;

import Feed_Data.Downloads_Feed;
import es.dmoral.toasty.Toasty;

public class Downloads_Adapter extends BaseAdapter {
	private List<Downloads_Feed> dataItems;
	private Activity activity;
	private LayoutInflater inflater;
	private Context context;
	ProgressDialog pd;
	BackEnd_DB db;
	int pos=0;
	Typeface face;
	DownloadManager manager;
	Runnable runnableCode;
	public NetworkConnections cd;
    public String tpkey="",tdownpath="";
	public Downloads_Adapter(Activity activity, List<Downloads_Feed> dataItems) {
		 this.activity = activity;
		 this.dataItems = dataItems;
		 context=activity.getApplicationContext();
		db=new BackEnd_DB(context);
		manager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
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
			convertView = inflater.inflate(R.layout.downloads_custom, null);
		}
		final ImageView image=convertView.findViewById(R.id.img);
		final TextView dtitle =convertView.findViewById(R.id.viewtitle);
        final TextView filesize=convertView.findViewById(R.id.fsize);
		final TextView per=convertView.findViewById(R.id.per);
		final ProgressBar progress=convertView.findViewById(R.id.progress);

		final ImageView stop=convertView.findViewById(R.id.stop);
		final ImageView delete=convertView.findViewById(R.id.drop);
		RelativeLayout layout=convertView.findViewById(R.id.layout);

		final Downloads_Feed item = dataItems.get(position);

		if(item.getdown_name().contains(".jpg"))
		{

			Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(item.getdown_path().replace("file://","")),
					80, 60);
			image.setImageBitmap(ThumbImage);
		}
		else
		{

			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(item.getdown_path().replace("file://",""), MediaStore.Images.Thumbnails.MINI_KIND);
			image.setImageBitmap(thumb);
		}
		dtitle.setTypeface(face);
		per.setTypeface(face);
		dtitle.setText(item.getdown_name());
        delete.setVisibility(View.VISIBLE);
		final Handler handler = new Handler();
		runnableCode = new Runnable() {
			@Override
			public void run() {
				try
				{
					DownloadManager.Query q = new DownloadManager.Query();
					q.setFilterById(Long.parseLong(item.getdown_id()));
					Cursor cursor = manager.query(q);
					if(cursor.moveToFirst())
					{
						long bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
						long bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
						int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
						if(dl_progress>=100)
						{
							stop.setVisibility(View.INVISIBLE);
							delete.setVisibility(View.VISIBLE);
							progress.setVisibility(View.INVISIBLE);
							filesize.setText(HRC(bytes_total,true));
							per.setVisibility(View.INVISIBLE);

						}
						else{
                                 per.setVisibility(View.VISIBLE);
							     progress.setVisibility(View.VISIBLE);
							     progress.setProgress(dl_progress);
							     filesize.setVisibility(View.VISIBLE);
								 filesize.setText(HRC(bytes_downloaded,true)+ " / "+HRC(bytes_total,true));
								 per.setText(dl_progress+"%");
								 stop.setVisibility(View.VISIBLE);
								 delete.setVisibility(View.INVISIBLE);
								 handler.postDelayed(this, 2000);
						}
					}
					else{
						stop.setVisibility(View.INVISIBLE);
						delete.setVisibility(View.VISIBLE);
                        filesize.setVisibility(View.VISIBLE);
                        per.setVisibility(View.VISIBLE);
					}
				}
				catch (Exception a)
				{
				}
			}
		};
		handler.post(runnableCode);
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Downloads_Feed item = dataItems.get(position);
				item = dataItems.get(position);
				try
				{
					File f=new File(item.getdown_path().replace("file://",""));
					if(f.exists())
					{
						VideoDownloads h=(VideoDownloads)activity;
						h.openfile(f.getAbsolutePath());
					}
					else {
						String p = item.getdown_path().replace(".", "VG.");
						File f1 = new File(p.replace("file://", ""));
						if (f1.exists()) {
							VideoDownloads h = (VideoDownloads) activity;
							h.openfile(f1.getAbsolutePath());
						} else {
							Toasty.error(context, "Sorry ! Unable to play", Toast.LENGTH_SHORT, true).show();
						}

					}
				}
				catch (Exception a)
				{
				}
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Downloads_Feed item = dataItems.get(position);
				item = dataItems.get(position);
				tpkey=item.getpkey();
				tdownpath=item.getdown_path();
				pos=position;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.del_downid(tpkey);
                                VideoDownloads h=(VideoDownloads)activity;
                                h.removeitem(pos);
                                try
                                {
									File f=new File(tdownpath.replace("file://",""));
									if(f.exists())
									{
										if(f.exists())
										{
											f.delete();
										}
									}
									else
									{
										String p=tdownpath.replace(".","VG.");
										File f1=new File(p.replace("file://",""));
										if(f1.exists())
										{
											f1.delete();
										}
									}
                                }
                                catch (Exception a)
                                {
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                db.del_downid(tpkey);
								VideoDownloads h1=(VideoDownloads)activity;
								h1.removeitem(pos);
                                break;
                        }
                        dialog.dismiss();
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Are you sure want to img_delete this file from gallery also ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).setCancelable(true).show();
			}
		});
		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Downloads_Feed item = dataItems.get(position);
				item = dataItems.get(position);
				tpkey=item.getpkey();
				pos=position;
				manager.remove(Long.parseLong(item.getdown_id()));
				stop.setVisibility(View.INVISIBLE);
				delete.setVisibility(View.VISIBLE);
				filesize.setVisibility(View.VISIBLE);
				per.setVisibility(View.VISIBLE);
			}
		});
		return convertView;
	}
	public static String HRC(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
