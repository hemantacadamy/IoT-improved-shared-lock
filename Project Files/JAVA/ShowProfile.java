package com.example.hemant.sharedlock;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowProfile extends AppCompatActivity {

    String name;
    String id;
    String email;
    String contact;
    TextView ID,NAME,EMAIL,CONTACT;
    TextView call,sms,whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        Bundle bundle=getIntent().getExtras();
        name=bundle.getString("id");
        id=bundle.getString("name");
        email=bundle.getString("email");
        contact=bundle.getString("contact");

    //    ID=(TextView)findViewById(R.id.ide);
        NAME=(TextView)findViewById(R.id.name);
        EMAIL=(TextView)findViewById(R.id.email);
        CONTACT=(TextView)findViewById(R.id.contact);
        call=(TextView)findViewById(R.id.call);
        sms=(TextView)findViewById(R.id.sms);
        whatsapp=(TextView)findViewById(R.id.whatsapp);

//        ID.setText(id);
        NAME.setText(name);
        EMAIL.setText(email);
        CONTACT.setText(contact);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:"+contact));
                startActivity(callIntent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent=new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"+contact));
                startActivity(smsIntent);
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent=new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"+contact));
                smsIntent.setPackage("com.whatsapp");
                startActivity(smsIntent);
            }
        });

    }
}
