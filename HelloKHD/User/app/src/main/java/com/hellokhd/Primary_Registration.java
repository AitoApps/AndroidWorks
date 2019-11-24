package com.hellokhd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class Primary_Registration extends AppCompatActivity {
    RelativeLayout lytnumber,lytfacebook,lytemail;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    public int RC_SIGN_IN=100;
    ProgressDialog pd;
    ConnectionDetecter cd;
    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    public int whichlogin=0; //1-number 2-facebok 3-email
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary__registration);
        lytnumber=findViewById(R.id.lytnumber);
        lytfacebook=findViewById(R.id.lytfacebook);
        lytemail=findViewById(R.id.lytemail);
        pd=new ProgressDialog(this);
        cd=new ConnectionDetecter(this);

        callbackManager = CallbackManager.Factory.create();
        lytfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whichlogin=2;
                try {
                    if (!hasPermissions(Primary_Registration.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Primary_Registration.this, PERMISSIONS, PERMISSION_ALL);
                    }
                    else
                    {
                        File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                        if (!folder.exists()) {
                            folder.mkdir();
                            try {
                                new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        LoginManager.getInstance().logInWithReadPermissions(Primary_Registration.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

                    }

                } catch (Exception e2) {
                }


            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("837505932252-4bidsj37sv758mkrhq3o1o0hrclt1jdu.apps.googleusercontent.com")
                .requestEmail()
                .build();


        AppSignatureHelper a=new AppSignatureHelper(this);
        a.getAppSignatures();

       mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

       lytemail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               whichlogin=3;
               try {
                   if (!hasPermissions(Primary_Registration.this, PERMISSIONS)) {
                       ActivityCompat.requestPermissions(Primary_Registration.this, PERMISSIONS, PERMISSION_ALL);
                   }
                   else
                   {
                       File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                       if (!folder.exists()) {
                           folder.mkdir();
                           try {
                               new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }

                       Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                       startActivityForResult(signInIntent, RC_SIGN_IN);
                   }

               } catch (Exception e2) {
               }



           }
       });

       lytnumber.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               whichlogin=1;
               try {
                   if (!hasPermissions(Primary_Registration.this, PERMISSIONS)) {
                       ActivityCompat.requestPermissions(Primary_Registration.this, PERMISSIONS, PERMISSION_ALL);
                   }
                   else
                   {
                       File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                       if (!folder.exists()) {
                           folder.mkdir();
                           try {
                               new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }

                       startActivity(new Intent(getApplicationContext(),Registartion.class));
                   }

               } catch (Exception e2) {
               }


           }
       });
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                           // Log.w("LoginActivity", response.toString());
                                           // Log.w("GetPRofile",object.toString());
                                            pd.setMessage("Please wait...");
                                            pd.setCancelable(false);
                                            pd.show();
                                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        pd.dismiss();
                                                        Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                                                    try {

                                                        Registartion.txtname=object.getString("name");
                                                        Registartion.txtmobile=object.getString("email");
                                                        new fb_registration().execute(new String[0]);
                                                    }
                                                    catch (Exception a)
                                                    {
                                                        Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            });


                                        }
                                        catch (Exception a)
                                        {

                                        }


                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                      //  Log.w("Resukr","Cancell");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //Log.w("Resukr","Error"+exception.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        pd.dismiss();
                        Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_LONG).show();
                        return;
                    }
                    udb.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                    Registartion.txtname=account.getDisplayName();
                    Registartion.txtmobile=account.getEmail();

                    new email_registration().execute(new String[0]);
                }
            });

            //Log.w("Details",account.getEmail()+account.getDisplayName());

        } catch (ApiException e) {
           // Log.w("Errorrrrr", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }



    public class email_registration extends AsyncTask<String, Void, String> {

        public void onPreExecute() {

        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"registration_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("3:%"+Registartion.txtname+":%"+Registartion.txtmobile+":%"+udb.getfcmid(), "UTF-8");
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

                if (result.contains(",ok")) {
                    pd.dismiss();
                    FirebaseMessaging.getInstance().subscribeToTopic(Temp.fcmtopic);
                    String[] k = result.split(",");
                    udb.adduserid(k[0],k[1]);
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                    return;
                }
                pd.dismiss();
                Toasty.info(getApplicationContext(), "Please contact Hello KHD TEAM", Toast.LENGTH_SHORT).show();
            } catch (Exception a) {
                //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class fb_registration extends AsyncTask<String, Void, String> {

        public void onPreExecute() {

        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"registration_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("2:%"+Registartion.txtname+":%"+Registartion.txtmobile+":%"+udb.getfcmid(), "UTF-8");
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

                if (result.contains(",ok")) {
                    pd.dismiss();
                    FirebaseMessaging.getInstance().subscribeToTopic(Temp.fcmtopic);
                    String[] k = result.split(",");
                    udb.adduserid(k[0],k[1]);
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                    return;
                }
                pd.dismiss();
                Toasty.info(getApplicationContext(), "Please contact Hello KHD TEAM", Toast.LENGTH_SHORT).show();
            } catch (Exception a) {
                //Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (hasPermissions(Primary_Registration.this, PERMISSIONS)) {
                        if(whichlogin==1)
                        {
                         File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                            if (!folder.exists()) {
                                folder.mkdir();
                                try {
                                    new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                         startActivity(new Intent(getApplicationContext(),Registartion.class));
                        }
                        else if(whichlogin==2)
                        {
                            File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                            if (!folder.exists()) {
                                folder.mkdir();
                                try {
                                    new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            LoginManager.getInstance().logInWithReadPermissions(Primary_Registration.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

                        }
                        else if(whichlogin==3)
                        {
                            File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                            if (!folder.exists()) {
                                folder.mkdir();
                                try {
                                    new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                            startActivityForResult(signInIntent, RC_SIGN_IN);
                        }
                    }
                } else {
                    Log.i("Logs", "Permission denied by user.");
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
