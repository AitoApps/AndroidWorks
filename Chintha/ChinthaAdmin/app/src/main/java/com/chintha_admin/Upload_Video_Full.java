package com.chintha_admin;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Upload_Video_Full extends AppCompatActivity {
    Button Send;
    ImageView back;
    ConnectionDetecter cd;
    ImageView delete;
    EditText duration;
    EditText message;
    ProgressDialog pd;
    ImageView playicon;
    EditText title;
    public String txtmessage = "";
    ImageView verify;
    VideoView video;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_upload__video__full);
        back = (ImageView) findViewById(R.id.back);
        message = (EditText) findViewById(R.id.message);
        title = (EditText) findViewById(R.id.title);
        duration = (EditText) findViewById(R.id.duration);
        Send = (Button) findViewById(R.id.Send);
        video = (VideoView) findViewById(R.id.video);
        playicon = (ImageView) findViewById(R.id.playicon);
        verify = (ImageView) findViewById(R.id.verify);
        delete = (ImageView) findViewById(R.id.delete);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        video.setMediaController(new MediaController(this));
        VideoView videoView = video;
        StringBuilder sb = new StringBuilder();
        sb.append(Tempvariable.weblink);
        sb.append("video/");
        sb.append(Tempvariable.playlink);
        videoView.setVideoPath(sb.toString());
        playicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    playicon.setVisibility(View.GONE);
                    video.start();
                } catch (Exception e) {
                }
            }
        });
        Send.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (message.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please eneter message", Toast.LENGTH_SHORT).show();
                    message.requestFocus();
                    return;
                }
                txtmessage = message.getText().toString();
                new sendvideo().execute(new String[0]);
            }
        });
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (title.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter title", Toast.LENGTH_SHORT).show();
                } else if (duration.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter duration", Toast.LENGTH_SHORT).show();
                } else {
                    showalert_verify(title.getText().toString(), Tempvariable.playlink, duration.getText().toString());
                }
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showalert1("Delete This ?");
            }
        });
    }

    public void showalert_verify(final String title2, final String videourl, final String duration2) {
        Builder builder = new Builder(this);
        builder.setMessage("Are you sure want to upload ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    String url = Tempvariable.weblink+"youtubeupload/upload.php?filename1="+videourl+"&title1="+title2+"&duration="+duration2;
                    Intent i = new Intent("android.intent.action.VIEW");
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
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

    public void showalert1(String message2) {
        Builder builder = new Builder(this);
        builder.setMessage(message2).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new statusdelete().execute(new String[0]);
                } else {
                    Toast.makeText(getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
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
    public class sendvideo extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"uploadvideosendtoall1.php";
                String data  = URLEncoder.encode("title1", "UTF-8")
                        + "=" + URLEncoder.encode(txtmessage, "UTF-8");
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
            try {
                pd.dismiss();
                if (result.contains("ok")) {
                    message.setText("");
                    Toast.makeText(getApplicationContext(), "Sened", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception a) {
                Toast.makeText(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class statusdelete extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {

            try {
                String link=Tempvariable.weblink+"youtubeupload/deleteuplaod.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Tempvariable.playlink, "UTF-8");
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
                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
