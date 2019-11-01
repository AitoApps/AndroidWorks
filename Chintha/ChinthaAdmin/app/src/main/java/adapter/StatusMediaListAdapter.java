package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.R;
import com.chintha_admin.Status_Media_Upload;
import com.chintha_admin.Tempvariable;

import data.StatusMedia_Feeditem;
import data.UploadtoYoutube_FeedItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class StatusMediaListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    public List<StatusMedia_Feeditem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_title = "";
    public String t_type = "";
    public String txtsn = "";
    public String txtuserid = "";
    public View views;

    public StatusMediaListAdapter(Activity activity2, List<StatusMedia_Feeditem> feedItems2) {
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
                convertView2 = inflater.inflate(R.layout.statusmedia_customlayout, null);
            } else {
                convertView2 = convertView;
            }
            try {
                views = convertView2;
                RelativeLayout layout = (RelativeLayout) convertView2.findViewById(R.id.layout);
                TextView title = (TextView) convertView2.findViewById(R.id.title);
                ImageView photo = (ImageView) convertView2.findViewById(R.id.photo);
                final VideoView video = (VideoView) convertView2.findViewById(R.id.video);
                ImageView disapprove = (ImageView) convertView2.findViewById(R.id.disapprove);
                ImageView approve = (ImageView) convertView2.findViewById(R.id.approve);
                ImageView playvideo = (ImageView) convertView2.findViewById(R.id.playvideo);
                ImageView pausevideo = (ImageView) convertView2.findViewById(R.id.pausevideo);
                StatusMedia_Feeditem item = (StatusMedia_Feeditem) feedItems.get(i);
                title.setText(item.getTitle());
                if (item.getMediatype().equalsIgnoreCase("1")) {
                    try {
                        video.setVisibility(View.GONE);
                        playvideo.setVisibility(View.GONE);
                        pausevideo.setVisibility(View.GONE);
                        photo.setVisibility(View.VISIBLE);
                       Glide.with(context).load(Tempvariable.weblink+"medialist/"+item.getPath()).into(photo);
                    } catch (Exception e) {
                    }
                } else if (item.getMediatype().equalsIgnoreCase("2")) {
                    video.setVisibility(View.VISIBLE);
                    playvideo.setVisibility(View.VISIBLE);
                    pausevideo.setVisibility(View.INVISIBLE);
                    photo.setVisibility(View.GONE);
                }

                playvideo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            playvideo.setVisibility(View.INVISIBLE);
                            pausevideo.setVisibility(View.VISIBLE);
                            StatusMedia_Feeditem item = (StatusMedia_Feeditem) feedItems.get(position);
                            video.setVideoPath(Tempvariable.weblink+"instavideo/"+item.getPath()+".mp4");
                            video.start();
                        } catch (Exception e) {
                        }
                    }
                });
                pausevideo.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        playvideo.setVisibility(View.VISIBLE);
                        pausevideo.setVisibility(View.INVISIBLE);
                        video.pause();
                    }
                });

                    disapprove.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            StatusMedia_Feeditem item = (StatusMedia_Feeditem) feedItems.get(i);
                            txtsn = item.getSn();
                            showalert1("Are you sure want to delete ? ");
                        }
                    });
                    approve.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            StatusMedia_Feeditem statusMedia_Feeditem = (StatusMedia_Feeditem) feedItems.get(i);
                            StatusMedia_Feeditem item = (StatusMedia_Feeditem) feedItems.get(i);
                            if (item.getMediatype().equalsIgnoreCase("1")) {
                                txtsn = item.getSn();
                                pos = i;
                                showalert_uploadimage("Are you sure want to upload this image ?");
                            } else if (item.getMediatype().equalsIgnoreCase("2")) {
                                txtsn = item.getSn();
                                showalert_uploadvideo("Are you sure want to uplaod this video ?");
                            }
                        }
                    });

                return convertView;

                } catch (Exception e2) {

                }
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

    public void showalert_uploadimage(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new uploadimage().execute(new String[0]);
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
                String link=Tempvariable.weblink+"deletemoments_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtsn+":%"+txtuserid+":%"+t_title, "UTF-8");
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

                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class uploadimage extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...Upload Image");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"upload_image_mediatofb.php";
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
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                pd.dismiss();
                if (result.contains("ok")) {
                    Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    ((Status_Media_Upload) activity).removeitem(pos);
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
                String link=Tempvariable.weblink+"verifymoments_admin.php";
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
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();

                    if (t_type.equalsIgnoreCase("2")) {
                        new uploadimage().execute(new String[0]);
                        return;
                    }

                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
