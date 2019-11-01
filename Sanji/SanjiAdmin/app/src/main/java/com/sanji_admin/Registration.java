package com.sanji_admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Registration extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public EditText name;
    ProgressDialog pd;
    Button register;
    TextView text;
    public String txtname = "";
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class registration extends AsyncTask<String, Void, String> {
        public registration() {
        }
        public void onPreExecute() {
            Registration.register.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("registration_admin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Registration.txtname);
                sb3.append(":%");
                sb3.append(Registration.udb.getfcmid());
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                Registration.register.setEnabled(true);
                Registration.pd.dismiss();
                if (result.contains(",ok")) {
                    Registration.udb.adduser(result.split(",")[0]);
                    Toasty.info(Registration.getApplicationContext(), Temp.registrationsucess, Toast.LENGTH_LONG).show();
                    Registration.startActivity(new Intent(Registration.getApplicationContext(), MainActivity.class));
                    Registration.finish();
                    return;
                }
                if (result.contains("error")) {
                    Toasty.info(Registration.getApplicationContext(), "ക്ഷമിക്കണം !!! താങ്കള്‍ എന്റര്‍ ചെയ്ത വിവരങ്ങള്‍ തെറ്റാണ്‌ ", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(Registration.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_registraion);
        text = (TextView) findViewById(R.id.text);
        name = (EditText) findViewById(R.id.name);
        register = (Button) findViewById(R.id.register);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setText(Temp.apptitle);
        text.setTypeface(face);
        register.setTypeface(face);
        name.setTypeface(face);
        FirebaseApp.initializeApp(this);
        String[] PERMISSIONS = {"android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.CALL_PHONE"};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!Registration.cd.isConnectingToInternet()) {
                    Toasty.info(Registration.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (Registration.name.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Registration.getApplicationContext(), "ദയവായി താങ്കളുടെ സീക്രട്ട്‌ എന്റര്‍ ചെയ്യുക ", Toast.LENGTH_LONG).show();
                    Registration.name.requestFocus();
                } else {
                    Registration.pd.setMessage("Please wait...");
                    Registration.pd.setCancelable(false);
                    Registration.pd.show();
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Registration.pd.dismiss();
                                Toasty.info(Registration.getApplicationContext(), "താല്‍ക്കാലിക പ്രശ്‌നം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Registration.udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                            Registration.txtname = Registration.name.getText().toString();
                            new registration().execute(new String[0]);
                        }
                    });
                }
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
