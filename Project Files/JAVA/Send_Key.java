package com.example.hemant.sharedlock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Hemant on 10-04-2017.
 */

public class Send_Key extends Fragment {

    TextView call;
    TextView sms;
    TextView whatsapp;
    EditText contact_no;
    Button generate;
    TextView random;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_send_key, container, false);

        call=(TextView)rootView.findViewById(R.id.call);
        sms=(TextView)rootView.findViewById(R.id.sms);
        whatsapp=(TextView)rootView.findViewById(R.id.whatsapp);
        contact_no=(EditText)rootView.findViewById(R.id.contact);
        generate=(Button)rootView.findViewById(R.id.generate);
        random=(TextView)rootView.findViewById(R.id.random);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SALTCHARS="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt=new StringBuilder();
                Random rnd=new Random();
                while(salt.length()<10){
                    if (salt.length()==4 || salt.length()==8){
                        salt.append('A');
                    }
                    else {
                        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                        salt.append(SALTCHARS.charAt(index));
                    }
                }
                String saltStr=salt.toString();
                random.setText(saltStr);
            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact=contact_no.getText().toString();
                Intent callIntent=new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:"+contact));
                startActivity(callIntent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact=contact_no.getText().toString();
                Intent smsIntent=new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"+contact));
                startActivity(smsIntent);
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact=contact_no.getText().toString();
                Intent smsIntent=new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"+contact));
                smsIntent.setPackage("com.whatsapp");
                startActivity(smsIntent);
            }
        });
        return rootView;

    }
}
