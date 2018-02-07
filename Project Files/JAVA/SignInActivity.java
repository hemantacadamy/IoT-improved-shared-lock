package com.example.hemant.sharedlock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Credentials;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signin;
    String uname;
    String pass;
    private List<Cred> creds;
    private int flag=0;
    int index;
    ProgressDialog pDialog;
    TextView create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signin);
        create=(TextView)findViewById(R.id.new_account);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=username.getText().toString();
                pass=password.getText().toString();

                creds=new ArrayList<>();

                CredAsyncTask task = new CredAsyncTask();
                task.execute();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Signup1.class);
                startActivity(intent);
            }
        });

    }
    private class CredAsyncTask extends AsyncTask<Void,Void,List<Cred>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog=new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<Cred> doInBackground(Void... params){
            creds=CredUtils.fetchData("http://"+IpAddress.getIp()+"/creds.php");

            return null;
        }

        @Override
        protected void onPostExecute(List<Cred> result){

            pDialog.cancel();
            String temptype = null;


                for(int i=0;i<creds.size();i++){
                    Cred cred=creds.get(i);
                    String tempuser=cred.getId();
                    String temppass=cred.getPass();


                    if(tempuser.equals(uname) && temppass.equals(pass)){
                        flag=1;
                        temptype=cred.getType();
                        break;
                    }
                }
                if (flag==1){
                    Toast.makeText(getApplicationContext(),"Successfully Logged In",Toast.LENGTH_LONG).show();
                    if(temptype.equals("owner")){
                        Intent intent=new Intent(getApplicationContext(),OwnerActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent=new Intent(getApplicationContext(),VisitorActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                }

            Log.e("MainActivity","packets are shown");
        }
    }
}
