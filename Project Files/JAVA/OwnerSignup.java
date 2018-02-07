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


public class OwnerSignup extends AppCompatActivity {

    EditText id;
    EditText name;
    EditText email;
    EditText password;
    EditText contact;
    Button signup;
    ProgressDialog pDialog;
    Otp otp;
    String ids;
    String emails;
    String names;
    String passwords;
    String contacts;
    String locks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signup);

        id=(EditText)findViewById(R.id.username);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        contact=(EditText)findViewById(R.id.contact);
        signup=(Button)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ids=id.getText().toString();
                emails=email.getText().toString();
                names=name.getText().toString();
                passwords=password.getText().toString();
                contacts=contact.getText().toString();
                Bundle bundle=getIntent().getExtras();
                locks=bundle.getString("lock_no");

                if(isConnected()){
                    if(!validate())
                        Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                    // call AsynTask to perform network operation on separate thread
                    new HttpAsyncTask().execute("http://"+IpAddress.getIp()+"/otp/index.php");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check your connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static String POST(String url, Otp otp){
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
            jsonObject.accumulate("contact", otp.getContact());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

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
            pDialog=new ProgressDialog(OwnerSignup.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            otp = new Otp();
            otp.setContact(contacts);

            return POST(urls[0],otp);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            Intent intent=new Intent(getApplicationContext(),OtpVerify.class);
            intent.putExtra("id",ids);
            intent.putExtra("name",names);
            intent.putExtra("email",emails);
            intent.putExtra("password",passwords);
            intent.putExtra("contact",contacts);
            intent.putExtra("lock_no",locks);
            startActivity(intent);
        }
    }

    private boolean validate(){
        if(contacts.trim().equals(""))
            return false;
        else
            return true;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
