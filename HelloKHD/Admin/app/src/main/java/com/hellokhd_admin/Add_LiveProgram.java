package com.hellokhd_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Add_LiveProgram extends AppCompatActivity {

    ImageView back;
    TextView text;
    EditText programname,programtime;
    Button update;
    ProgressDialog pd;
    String txt_programname="",txt_programtime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__live_program);
        pd=new ProgressDialog(this);
        back=findViewById(R.id.back);
        text=findViewById(R.id.text);
        programname=findViewById(R.id.programname);
        programtime=findViewById(R.id.programtime);
        update=findViewById(R.id.update);

        text.setText("Live Program - Stage : "+Temp.stagenumber);


        new getliveprogram().execute();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(programname.getText().toString().equalsIgnoreCase(""))
                {
                    programname.requestFocus();
                    Toast.makeText(getApplicationContext(),"Please enter programname",Toast.LENGTH_SHORT).show();
                }
                else if(programtime.getText().toString().equalsIgnoreCase(""))
                {
                    programtime.requestFocus();
                    Toast.makeText(getApplicationContext(),"Please enter programtime",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    txt_programname=programname.getText().toString();
                    txt_programtime=programtime.getText().toString();
                    new updateliveprogram().execute();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public class updateliveprogram extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink +"updaterunning.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.stagesn+":%"+txt_programname+":%"+txt_programtime, "UTF-8");
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
                Log.w("FFFFF",result);
                pd.dismiss();
                 finish();
                 Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }

    public class getliveprogram extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        public String doInBackground(String... arg0) {

            try {
                String link=Temp.weblink +"getcurrentruning.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.stagesn+"", "UTF-8");
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
                Log.w("RRRRRRR",result);
                pd.dismiss();
                if (result.trim().contains(",")) {
                    String[] k=result.split(",");
                    programname.setText(k[0]);
                    programtime.setText(k[1]);
                }
                else
                {
                   programname.setText("");
                   programtime.setText("");
                }
            } catch (Exception e) {
            }
        }
    }
}
