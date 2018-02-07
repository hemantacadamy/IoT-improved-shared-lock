package com.example.hemant.sharedlock;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OtpVerify extends AppCompatActivity {

    EditText otp;
    Button verify;
    String otps;
    private List<Otpv> otpvs;
    private int flag=0;
    ProgressDialog pDialog;
    String id,name,email,contact,password,lock_no;
    OwnerDetails ownerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        otp=(EditText)findViewById(R.id.otp);
        verify=(Button)findViewById(R.id.verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otps=otp.getText().toString();

                OtpAsyncTask otpAsyncTask=new OtpAsyncTask();
                otpAsyncTask.execute();

            }
        });
    }

    private class OtpAsyncTask extends AsyncTask<Void,Void,List<Otpv>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog=new ProgressDialog(OtpVerify.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<Otpv> doInBackground(Void... params){
            otpvs=OtpUtils.fetchData("http://"+IpAddress.getIp()+"/checkotp.php");

            return null;
        }

        @Override
        protected void onPostExecute(List<Otpv> result){

            pDialog.cancel();
            String temptype = null;


            for(int i=0;i<1;i++){
                Otpv otpv=otpvs.get(i);
                String tempotp=otpv.getOtp();


                if(tempotp.equals(otps)){
                    flag=1;
                    break;
                }
            }
            if (flag==1){
                Toast.makeText(getApplicationContext(),"Successfully Verified",Toast.LENGTH_LONG).show();
                Bundle bundle=getIntent().getExtras();
                id=bundle.getString("id");
                name=bundle.getString("bundle");
                password=bundle.getString("password");
                email=bundle.getString("email");
                contact=bundle.getString("contact");
                lock_no=bundle.getString("lock_no");
                Log.e("extra",bundle.toString());

                if(isConnected()){

                    //     Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                    // call AsynTask to perform network operation on separate thread
                    if(lock_no.equals("temp")){
                        new HttpAsyncTask().execute("http://"+IpAddress.getIp()+"/signupvisitor.php");
                    }
                    else{
                        new HttpAsyncTask().execute("http://"+IpAddress.getIp()+"/signupowner.php");
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
            }
            Log.e("MainActivity","packets are shown");
        }
    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static String POST(String url, OwnerDetails ownerDetails){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("contact", ownerDetails.getContact());
            jsonObject.accumulate("id", ownerDetails.getId());
            jsonObject.accumulate("name", ownerDetails.getName());
            jsonObject.accumulate("password", ownerDetails.getPassword());
            jsonObject.accumulate("email", ownerDetails.getEmail());
            jsonObject.accumulate("lock_no", ownerDetails.getLock_no());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.e("json",json);

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){

            super.onPreExecute();
            pDialog=new ProgressDialog(OtpVerify.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            ownerDetails = new OwnerDetails();
            ownerDetails.setContact(contact);
            ownerDetails.setId(id);
            ownerDetails.setName(name);
            ownerDetails.setEmail(email);
            ownerDetails.setPassword(password);
            ownerDetails.setLock_no(lock_no);

            return POST(urls[0],ownerDetails);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
