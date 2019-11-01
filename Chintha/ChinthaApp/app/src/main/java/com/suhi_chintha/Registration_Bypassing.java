package com.suhi_chintha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registration_Bypassing extends AppCompatActivity {
    final DataDB1 dataDb1 =new DataDB1(this);
    final DataDB4 dataDb4 =new DataDB4(this);
    final User_DataDB userDataDB =new User_DataDB(this);
    String android_id = "";
    public static String txtusername="",txtmobile="",txtuserid="",txtcountrycode="",txtverificationcode="";
    Typeface face;
    final DataDb dataDb =new DataDb(this);
    TextView text,textverify;
    Button regs;
    NetConnection cd;
    ProgressDialog pd;
    final DataDB2 dataDb2 =new DataDB2(this);
    EditText name,mobile,countryode,verificationkey;
    ImageView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bypass_registrations_actvty);
        try {
            verificationkey= findViewById(R.id.veikey);
            regs = findViewById(R.id.verify);
            help = findViewById(R.id.help);
            name = findViewById(R.id.name);
            mobile = findViewById(R.id.mobile);
            countryode = findViewById(R.id.countryode);


            face = Typeface.createFromAsset(getAssets(), "asset_fonts/common.ttf");
            text = findViewById(R.id.text);
            textverify = findViewById(R.id.textverify);
            text.setText(Static_Variable.application_title);
            text.setTypeface(face);
            text.setSelected(true);
            textverify.setText(Static_Variable.vericode_get);
            textverify.setTypeface(face);
            cd = new NetConnection(this);
            pd = new ProgressDialog(this);
            help.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Developer_Details.class);
                    startActivity(i);
                }
            });

            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            PhoneNumberUtil phoneutil = PhoneNumberUtil.getInstance();
            countryode.setText("+" + phoneutil.getCountryCodeForRegion(manager.getSimCountryIso().toUpperCase()));
            regs.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (name.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), Static_Variable.nameofyou, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (containsDigit(name.getText().toString()) == true) {
                        Toasty.info(getApplicationContext(), Static_Variable.number_noted, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (name.getText().toString().length() >= 20) {
                        Toasty.info(getApplicationContext(), Static_Variable.namelength, Toast.LENGTH_SHORT).show();
                        name.requestFocus();

                    } else if (mobile.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), Static_Variable.mobile_txt, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (mobile.getText().toString().length() < 6) {
                        Toasty.info(getApplicationContext(), Static_Variable.mobile_crtion, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (countryode.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), Static_Variable.cntrycod_txt, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    } else if (!countryode.getText().toString().contains("+")) {
                        Toasty.info(getApplicationContext(), Static_Variable.countrycode, Toast.LENGTH_SHORT).show();
                        mobile.requestFocus();
                    }
                    else if(verificationkey.getText().toString().equalsIgnoreCase(""))
                    {
                        Toasty.info(getApplicationContext(), Static_Variable.vericode_notget, Toast.LENGTH_SHORT).show();
                        verificationkey.requestFocus();
                    }
                    else {
                        dataDb2.addfcmid(FirebaseInstanceId.getInstance().getToken());
                        txtusername = name.getText().toString();
                        txtcountrycode = countryode.getText().toString();
                        txtmobile = mobile.getText().toString();
                        txtverificationcode=verificationkey.getText().toString();

                        if (cd.isConnectingToInternet()) {
                            pd.setMessage("Verifying...Please wait");
                            pd.setCancelable(false);
                            pd.show();
                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {

                                            if (!task.isSuccessful()) {
                                                pd.dismiss();
                                                Toasty.info(getApplicationContext(), "താല്\u200Dക്കാലിക പ്രശ്\u200Cനം ! ദയവായി 10 മിനിട്ടിന് ശേഷം ശ്രമിക്കുക ", Toast.LENGTH_LONG).show();
                                                return;
                                            }

                                            String fcmId = task.getResult().getToken();
                                            dataDb2.addfcmid(fcmId);
                                            try {
                                                android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                            } catch (Exception a) {
                                                android_id = "NA";
                                            }
                                            FirebaseMessaging.getInstance().subscribeToTopic("statusupdate");
                                            neregistration();

                                        }
                                    });


                        } else {
                            Toasty.info(getApplicationContext(), Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });


        } catch (Exception a) {

        }
    }

    public void neregistration()
    {
        try
        {

            regs.setEnabled(false);
            OkHttpClient client = new OkHttpClient();

            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name", txtusername)
                    .addFormDataPart("mobile", txtmobile)
                    .addFormDataPart("countrycode", txtcountrycode)
                    .addFormDataPart("fcmid", dataDb2.getfcmid())
                    .addFormDataPart("verificationcode", txtverificationcode)
                    .addFormDataPart("aid", android_id)
                    .build();
            Request request = new Request.Builder()
                    .url(Static_Variable.entypoint1 + "registrationnewbypass.php")
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
                                regs.setEnabled(true);
                                if(result.contains("404"))
                                {
                                    pd.dismiss();

                                }
                                else
                                {
                                    if(result.contains("%:ok"))
                                    {
                                        String[] k=result.split("%:");
                                        userDataDB.add_user(k[0],txtusername,txtcountrycode+txtmobile,"0","1");

                                        Toasty.info(getApplicationContext(), Static_Variable.cmplted_veri,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        Toasty.info(getApplicationContext(),"ദയവായി താങ്കള്\u200D നല്\u200Dകിയ വെരിഫിക്കേഷന്\u200D കോഡ് പരിശോധിക്കുക ",Toast.LENGTH_SHORT).show();

                                    }

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
    public static boolean containsDigit(final String s)
    {
        if (s != null && !s.isEmpty())
        {
            for (char c : s.toCharArray())
            {
                if (Character.isDigit(c))
                {
                    return true;
                }
            }
        }

        return false;
    }


}

