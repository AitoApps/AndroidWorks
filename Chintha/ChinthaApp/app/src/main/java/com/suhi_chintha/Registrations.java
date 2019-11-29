package com.suhi_chintha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registrations extends AppCompatActivity {
    RelativeLayout lytnumber,lytfacebook,lytemail;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    public int RC_SIGN_IN=100;
    ProgressDialog pd;
    NetConnection cd;
    final User_DataDB udb=new User_DataDB(this);
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            };
    final DataDB2 dataDb2 = new DataDB2(this);
    public int whichlogin=0; //1-number 2-facebok 3-email
    String android_id = "";
    TextView text,textnumber,textfacebook,textemail;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);
        lytnumber=findViewById(R.id.lytnumber);
        lytfacebook=findViewById(R.id.lytfacebook);
        lytemail=findViewById(R.id.lytemail);
        pd=new ProgressDialog(this);
        cd=new NetConnection(this);
        text=findViewById(R.id.text);
        textnumber=findViewById(R.id.textnumber);
        textfacebook=findViewById(R.id.textfacebook);
        textemail=findViewById(R.id.textemail);
        face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");

        text.setTypeface(face);
        textnumber.setTypeface(face);
        textfacebook.setTypeface(face);
        textemail.setTypeface(face);

        text.setText("ചിന്ത");
        textnumber.setText("മൊബൈല്\u200D നമ്പര്\u200D ലോഗിന്\u200D ");
        textfacebook.setText("ഫെയ്\u200Cസ്ബുക്ക് ലോഗിന്\u200D ");
        textemail.setText("ഇമെയില്\u200D ലോഗിന്\u200D ");

        try {
            android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception a) {
            android_id = "NA";
        }
        callbackManager = CallbackManager.Factory.create();
        lytfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whichlogin=2;
                try {
                    if (!hasPermissions(Registrations.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Registrations.this, PERMISSIONS, PERMISSION_ALL);
                    }
                    else
                    {
                        File folder = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername);
                        if (!folder.exists()) {
                            folder.mkdir();
                            try {
                                new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/.nomedia").createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        LoginManager.getInstance().logInWithReadPermissions(Registrations.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

                    }

                } catch (Exception e2) {
                }


            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1013706593415-t89lddetk2c44r7r1d5lseubt289jpfp.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //printHashKey(getApplicationContext());

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        lytemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whichlogin=3;
                try {
                    if (!hasPermissions(Registrations.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Registrations.this, PERMISSIONS, PERMISSION_ALL);
                    }
                    else
                    {
                        File folder = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername);
                        if (!folder.exists()) {
                            folder.mkdir();
                            try {
                                new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/.nomedia").createNewFile();
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
                    if (!hasPermissions(Registrations.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Registrations.this, PERMISSIONS, PERMISSION_ALL);
                    }
                    else
                    {
                        File folder = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername);
                        if (!folder.exists()) {
                            folder.mkdir();
                            try {
                                new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/.nomedia").createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Intent intent = new Intent(getApplicationContext(), Primary_Registration.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                        return;
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
                                            pd.setMessage("Please wait...");
                                            pd.setCancelable(false);
                                            pd.show();
                                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        pd.dismiss();
                                                        Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs, Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    dataDb2.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                                                    try {

                                                        Primary_Registration.txtname=object.getString("name");
                                                        Primary_Registration.txtmobile=object.getString("email");
                                                        fb_registration();
                                                    }
                                                    catch (Exception a)
                                                    {
                                                        Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs, Toast.LENGTH_LONG).show();
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
                        Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs, Toast.LENGTH_LONG).show();
                        return;
                    }
                    dataDb2.addfcmid(((InstanceIdResult) task.getResult()).getToken());
                    Primary_Registration.txtname=account.getDisplayName();
                    Primary_Registration.txtmobile=account.getEmail();
                    email_registration();
                }
            });

            Log.w("Details",account.getEmail()+account.getDisplayName());

        } catch (ApiException e) {
            Log.w("Errorrrrr", "signInResult:failed code=" + e.getMessage());

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


    public void email_registration()
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name", Primary_Registration.txtname)
                    .addFormDataPart("mobile", Primary_Registration.txtmobile)
                    .addFormDataPart("countrycode", "91")
                    .addFormDataPart("fcmid", dataDb2.getfcmid())
                    .addFormDataPart("aid", android_id)
                    .build();
            Request request = new Request.Builder()
                    .url(Static_Variable.entypoint1 + "appregsitartion_email.php")
                    .post(body)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, final Response response) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String result=response.body().string();
                                    if(result.contains("%:ok"))
                                    {
                                        String[] k=result.split("%:");
                                        udb.add_user(k[0],Primary_Registration.txtname,Primary_Registration.txtmobile,"0","1");
                                        Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                        return;

                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs,Toast.LENGTH_SHORT).show();
                                    }

                            }
                            catch (Exception a)
                            {
                            }
                        }
                    });
                }
            });
        }
        catch (Exception a)
        {

        }
    }


    public void fb_registration()
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name", Primary_Registration.txtname)
                    .addFormDataPart("mobile", Primary_Registration.txtmobile)
                    .addFormDataPart("countrycode", "91")
                    .addFormDataPart("fcmid", dataDb2.getfcmid())
                    .addFormDataPart("aid", android_id)
                    .build();
            Request request = new Request.Builder()
                    .url(Static_Variable.entypoint1 + "appregsitartion_fb.php")
                    .post(body)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, final Response response) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String result=response.body().string();
                                if(result.contains("%:ok"))
                                {
                                    String[] k=result.split("%:");
                                    udb.add_user(k[0],Primary_Registration.txtname,Primary_Registration.txtmobile,"0","1");
                                    Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                    return;

                                }
                                else
                                {
                                    pd.dismiss();
                                    Toasty.info(getApplicationContext(), Static_Variable.reason_tmpprobs,Toast.LENGTH_SHORT).show();

                                }

                            }
                            catch (Exception a)
                            {
                            }
                        }
                    });
                }
            });
        }
        catch (Exception a)
        {

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

                    if (hasPermissions(Registrations.this, PERMISSIONS)) {
                        if(whichlogin==1)
                        {
                            File folder = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername);
                            if (!folder.exists()) {
                                folder.mkdir();
                                try {
                                    new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/.nomedia").createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Intent intent = new Intent(getApplicationContext(), Primary_Registration.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        else if(whichlogin==2)
                        {
                            File folder = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername);
                            if (!folder.exists()) {
                                folder.mkdir();
                                try {
                                    new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/.nomedia").createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            LoginManager.getInstance().logInWithReadPermissions(Registrations.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

                        }
                        else if(whichlogin==3)
                        {
                            File folder = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername);
                            if (!folder.exists()) {
                                folder.mkdir();
                                try {
                                    new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/.nomedia").createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                            startActivityForResult(signInIntent, RC_SIGN_IN);
                        }
                    }
                } else {
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}