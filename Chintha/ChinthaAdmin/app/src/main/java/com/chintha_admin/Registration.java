package com.chintha_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import okhttp3.MediaType;

public class Registration extends AppCompatActivity {
    public static String txtname = "";
    public final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Button login;
    EditText name;
    ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_registration);

        name = (EditText) findViewById(R.id.name);
        login = (Button) findViewById(R.id.login);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);

        try {

            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), Tempvariable.tempproblem, Toast.LENGTH_LONG).show();
                        return;
                    }
                    db.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"FCM Updated",Toast.LENGTH_SHORT).show();

                }
            });

            login.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (name.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    txtname = name.getText().toString();
                    if (db.getfcmid().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "pelase add FCM ID", Toast.LENGTH_SHORT).show();
                    } else if (cd.isConnectingToInternet()) {
                        new registration().execute(new String[0]);
                    } else {
                        Toast.makeText(getApplicationContext(), Tempvariable.nointernet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
        }
    }
    public class registration extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"admin_register.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtname+":%"+db.getfcmid(), "UTF-8");
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

            Log.w("Resut",result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (result.contains("ok")) {
                db.adduserid("ok");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return;
            }
            Toast.makeText(getApplicationContext(), Tempvariable.tempproblem, Toast.LENGTH_SHORT).show();
        }
    }
}
