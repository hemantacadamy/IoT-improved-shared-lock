package com.example.hemant.sharedlock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetIp extends AppCompatActivity {

    EditText Ip;
    Button SetIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);
        Ip=(EditText)findViewById(R.id.ip);
        SetIp=(Button)findViewById(R.id.setip);

        SetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpAddress.setIp(Ip.getText().toString());
                Intent intent=new Intent(getApplicationContext(),Splash.class);
                startActivity(intent);
            }
        });
    }
}
