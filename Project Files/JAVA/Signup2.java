package com.example.hemant.sharedlock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Signup2 extends AppCompatActivity {

    ProgressDialog pDialog;
    private List<Lock> creds;
    EditText lock;
    EditText isbn;
    String lock_no;
    String isbn_no;
    Button button;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        lock=(EditText)findViewById(R.id.editText2);
        isbn=(EditText)findViewById(R.id.editText);
        button=(Button)findViewById(R.id.go);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creds=new ArrayList<>();
                lock_no=lock.getText().toString();
                isbn_no=isbn.getText().toString();

                LockAsyncTask task = new LockAsyncTask();
                task.execute();

            }
        });

    }
    private class LockAsyncTask extends AsyncTask<Void,Void,List<Cred>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog=new ProgressDialog(Signup2.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<Cred> doInBackground(Void... params){
            creds=LockUtils.fetchData("http://"+IpAddress.getIp()+"/lockcheck.php");

            return null;
        }

        @Override
        protected void onPostExecute(List<Cred> result){

            pDialog.cancel();
            String temptype = null;


            for(int i=0;i<creds.size();i++){
                Lock cred=creds.get(i);
                String tempuser=cred.getLock_no();
                String temppass=cred.getIsbn();


                if(tempuser.equals(lock_no) && temppass.equals(isbn_no)){
                    flag=1;
                    break;
                }
            }
            if (flag==1){
                Toast.makeText(getApplicationContext(),"Successfully Owned Lock",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),OwnerSignup.class);
                intent.putExtra("lock_no",lock_no);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),"Wrong Combination",Toast.LENGTH_SHORT).show();
            }

            Log.e("MainActivity","packets are shown");
        }
    }
}
