package com.mal_suthra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Act_Pymnt_Card extends AppCompatActivity {
    ImageView back;
    Typeface face;
    Typeface face1;
    NetConnect nc;
    TextView pymenttext;
    TextView text;
    ProgressDialog pd;
    final DataBase dataBase = new DataBase(this);
    String cardnumber="";
    final DataBase_MobileNumber mdb = new DataBase_MobileNumber(this);
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    Button card_atm,ecreacharge,paytm,upiid;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.avty_pymtn_card);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/heading.otf");
        face1 = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        back = (ImageView) findViewById(R.id.moveback);
        text = (TextView) findViewById(R.id.text);
        nc = new NetConnect(this);
        pd=new ProgressDialog(this);
        pymenttext = (TextView) findViewById(R.id.pymenttext);
        pymenttext.setText(Html.fromHtml(Static_Veriable.cardpayment));
        upiid = (Button) findViewById(R.id.upiid);
        ecreacharge = (Button) findViewById(R.id.ecreacharge);
        card_atm = (Button) findViewById(R.id.atmcard);
        paytm = (Button) findViewById(R.id.paytm);

        text.setText("Payment");
        text.setTypeface(face);
        pymenttext.setTypeface(face1);
        upiid .setTypeface(face1);
        pymenttext.setText("40 രൂപ പേയ്‌മെന്റ് ചെയ്താല്‍ താങ്കള്‍ക്ക് എല്ലാ പൊസിഷനുകളും ആല്‍ബവും പരസ്യങ്ങളില്ലാതെ കാണാവുന്നതാണ്. താഴെയുള്ള ഏത് വഴി ഉപയോഗിച്ചും പേയ്‌മെന്റ് ചെയ്യാവുന്നതാണ്‌");
        pymenttext.setTypeface(face1);

        ecreacharge.setText(Static_Veriable.eacyrecharge);
        ecreacharge.setTypeface(face1);
        card_atm.setText(Static_Veriable.atmcard);
        card_atm.setTypeface(face1);
        pymenttext.setTypeface(face1);
        paytm.setText(Static_Veriable.paytm);
        paytm.setTypeface(face1);
        upiid.setText(Static_Veriable.upiid);
        upiid.setTypeface(face1);


        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) { onBackPressed();
            }
        });
        card_atm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                launchPayUMoneyFlow();
            }
        });


        ecreacharge.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (nc.isConnectingToInternet()) {
                    Static_Veriable.clickedmethod = 6;
                    startActivity(new Intent(getApplicationContext(), Recharge.class));
                    return;
                }
                Toasty.info(getApplicationContext(), Static_Veriable.nonet, 0).show();
            }
        });
        paytm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Static_Veriable.clickedmethod = 3;
                    startActivity(new Intent(getApplicationContext(), Paytm.class));
                } catch (Exception e) {
                }
            }
        });
        upiid.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Static_Veriable.clickedmethod = 4;
                    startActivity(new Intent(getApplicationContext(), UPIID.class));
                } catch (Exception e) {
                }
            }
        });

    }


    public void onResume() {
        super.onResume();
    }



    private void launchPayUMoneyFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setDoneButtonText("Finish");
        payUmoneyConfig.setPayUmoneyActivityTitle("Purchase Full Version");
        payUmoneyConfig.disableExitConfirmation(false);
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        String amount = "40.0";
        String txnId = System.currentTimeMillis() + "";
        String phone = mdb.get_mob();
        String productName = "Purchase Full Version";
        String firstName ="Jeeva Suthra";
        String email = "jeevasuthra@gmail.com";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl("http://jeevasuthra.xyz/sucesss")
                .setfUrl("http://jeevasuthra.xyz/failure")
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(false)
                .setKey("Etp8EG")
                .setMerchantId("5364637");

        try {
            mPaymentParams = builder.build();
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);
            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, Act_Pymnt_Card.this, -1, true);

        } catch (Exception a) {
            // some exception occurred
            Toast.makeText(this, Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            //payNowButton.setEnabled(true)
        }

    }


    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");
        stringBuilder.append("L8y4aOJS"); //salt
        String hash = Payment.hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    String payuResponse = transactionResponse.getPayuResponse();

                    // Response from SURl and FURL
                    String merchantResponse = transactionResponse.getTransactionDetails();

                    try {
                        dataBase.add_purchase("1");

                        JSONObject jsonObj1 = new JSONObject(payuResponse);
                        JSONObject jsonObj = jsonObj1.getJSONObject("result");
                        cardnumber=jsonObj.getString("cardnum");

                        new pymnt_after().execute();
                        Toasty.success(getApplicationContext(),"Payment Sucesss",Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toasty.info(getApplicationContext(),"Sorry ! Please contact to Admin",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toasty.info(getApplicationContext(),"Payment Failed",Toast.LENGTH_SHORT).show();

                }


            } else if (resultModel != null && resultModel.getError() != null) {

            } else {

            }
        }
    }

    public class pymnt_after extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Updating your payment...Please wait");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Static_Veriable.weblink +"afterpaymentsucuss1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(mdb.get_mob()+":%"+cardnumber, "UTF-8");
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
                activty_exit();
            } catch (Exception e) {
            }
        }
    }

    public void activty_exit() {
        Intent intent = new Intent(getApplicationContext(), Cpanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
