package com.kamanam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView positions,web,whatsapp;
    TextView text,help1,txtweb,txtwhatsapp;
    Typeface face,face1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        positions=findViewById(R.id.positions);
        help1=findViewById(R.id.help1);
        text=findViewById(R.id.text);
        web=findViewById(R.id.web);
        whatsapp=findViewById(R.id.whatsapp);
        txtweb=findViewById(R.id.txtweb);
        txtwhatsapp=findViewById(R.id.txtwhatsapp);
        face = Typeface.createFromAsset(getAssets(), "fonts/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "fonts/proximanormal.ttf");
        text.setTypeface(face);
        text.setText("മലയാളം കാമസൂത്ര");
        txtweb.setText("വെബ്\u200Cസൈറ്റ് ");
        txtwhatsapp.setText("വാട്\u200Cസ്ആപ്പ്\u200C");
        txtwhatsapp.setTypeface(face);
        txtweb.setTypeface(face);

        help1.setText("ക്ഷമിക്കണം ! പ്ലേസ്റ്റോര്\u200D അനുവദിക്കാത്തത് കാരണം ഈ ആപ്പില്\u200D ചിത്രങ്ങള്\u200D ഉള്\u200Dപ്പെടുത്തിയിട്ടില്ല.ചിത്രങ്ങള്\u200D ഉള്\u200Dപ്പെടുത്തിയ ഒറിജിനല്\u200D ആപ്പ് ഞങ്ങളുടെ വെബ്\u200Cസൈറ്റില്\u200D നിന്നും ഡൗണ്\u200Dലോഡ് ചെയ്\u200Cതെടുക്കാവുന്നതാണ്. താഴെയുള്ള വെബ് ഐക്കണില്\u200D പ്രസ്സ് ചെയ്ത് വെബ്\u200Cസൈറ്റ് തുറക്കാം ");
        help1.setTypeface(face1);
        positions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Position_List.class);
                startActivity(i);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://malayalamkamasuthra.xyz"));
                startActivity(browserIntent);

            }
        });
        txtweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://malayalamkamasuthra.xyz"));
                startActivity(browserIntent);

            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openWhatsApp("+91 9048801231");
            }
        });

        txtwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("+91 9048801231");
            }
        });


    }

    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            startActivity(sendIntent);

        } catch(Exception e) {

            Toast.makeText(getApplicationContext(),number+" എന്ന നമ്പര്\u200D സേവ് ചെയ്ത് വാട്\u200Cസ്ആപ്പ് അയക്കുക ",Toast.LENGTH_LONG).show();
        }
    }
}
