package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.InstagramToFacebook;
import com.chintha_admin.R;
import com.chintha_admin.Tempvariable;
import data.Instagram_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class InstagramListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    public List<Instagram_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_title = "";
    public String txtsn = "";
    public View views;

    public InstagramListAdapter(Activity activity2, List<Instagram_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
    }

    public int getCount() {
        return feedItems.size();
    }

    public Object getItem(int location) {
        return feedItems.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        final int i = position;
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView2 = inflater.inflate(R.layout.instagram_customlayout, null);
            } else {
                convertView2 = convertView;
            }
            try {
                RelativeLayout layout = (RelativeLayout) convertView2.findViewById(R.id.layout);
                final EditText title = (EditText) convertView2.findViewById(R.id.title);
                ImageView photo = (ImageView) convertView2.findViewById(R.id.photo);
                final VideoView video = (VideoView) convertView2.findViewById(R.id.video);
                ImageView disapprove = (ImageView) convertView2.findViewById(R.id.disapprove);
                ImageView approve = (ImageView) convertView2.findViewById(R.id.approve);
                ImageView playvideo = (ImageView) convertView2.findViewById(R.id.playvideo);
                ImageView pausevideo = (ImageView) convertView2.findViewById(R.id.pausevideo);
                Instagram_FeedItem item = (Instagram_FeedItem) feedItems.get(i);
                if (item.getTypes().equalsIgnoreCase("2")) {
                    try {
                        video.setVisibility(View.GONE);
                        playvideo.setVisibility(View.GONE);
                        pausevideo.setVisibility(View.GONE);
                        photo.setVisibility(View.VISIBLE);
                        title.setText("");
                        Glide.with(context).load(item.getUrl()).into(photo);
                    } catch (Exception e) {
                    }
                } else if (item.getTypes().equalsIgnoreCase("1")) {
                    video.setVisibility(View.VISIBLE);
                    playvideo.setVisibility(View.VISIBLE);
                    pausevideo.setVisibility(View.INVISIBLE);
                    photo.setVisibility(View.GONE);
                    title.setText(item.getTitle());
                }
                    playvideo.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                playvideo.setVisibility(View.INVISIBLE);
                                pausevideo.setVisibility(View.VISIBLE);
                                video.setVideoPath(((Instagram_FeedItem) feedItems.get(position)).getUrl());
                                video.start();
                            } catch (Exception e) {
                            }
                        }
                    });

                pausevideo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        approve.setVisibility(View.VISIBLE);
                        photo.setVisibility(View.INVISIBLE);
                        video.pause();
                    }
                });

                    disapprove.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            Instagram_FeedItem item = (Instagram_FeedItem) feedItems.get(i);
                            txtsn = item.getSn();
                            pos = i;
                            showalert1("Are you sure want to delete ? ");
                        }
                    });
                    approve.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (!item.getTypes().equalsIgnoreCase("1") || !title.getText().toString().equalsIgnoreCase("")) {
                                Instagram_FeedItem item = (Instagram_FeedItem) feedItems.get(i);
                                txtsn = item.getSn();
                                t_title = title.getText().toString();
                                pos = i;
                                showalert_uploadvideo("Are you sure want to uplaod ?");
                                return;
                            }
                            else {
                                Toast.makeText(context, "Please enter title", Toast.LENGTH_SHORT).show();
                                title.requestFocus();
                            }

                        }
                    });

                } catch (Exception e2) {

                }
            return convertView2;
            } catch (Exception e3) {

            }

        return null;
    }

    public void showalert1(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new statusdelete().execute(new String[0]);
                } else {
                    Toast.makeText(context, "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalert_uploadvideo(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new uploadvideo().execute(new String[0]);
                } else {
                    Toast.makeText(context, "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public class statusdelete extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Tempvariable.weblink +"deleteinstagram.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtsn, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    InstagramToFacebook h=(InstagramToFacebook)activity;
                    h.removeitem(pos);
                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class uploadvideo extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"upload_instatofb.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtsn+":%"+t_title, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    ((InstagramToFacebook) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
