package com.example.hemant.sharedlock;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hemant on 17-03-2017.
 */
public class PersonUtils {

    public static URL createUrl(String requestUrl) {

        URL url = null;
        try {
            url = new URL(requestUrl);
            Log.e("PacketUtils", "Url created successfully");
        } catch (MalformedURLException exception) {
            Log.e("PacketUtils", "Url crashed");
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //If the URL is empty then return.

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            Log.e("PacketUtils", "connected to server");

            //If the request is successfull i.e. 200,
            //then read input stream and parse string
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.e("PacketUtils", jsonResponse);
            }
        } catch (IOException e) {
            Log.e("Exception", e.getMessage().toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Person> extractFeatureFromJson(String packetJSON){

        if(TextUtils.isEmpty(packetJSON)){
            Log.e("packetUtils","packetJSON is Empty");
            return null;
        }

        List<Person> packets=new ArrayList<>();

        try {
            JSONObject baseJSONResponse=new JSONObject(packetJSON);
            JSONArray resultArray=baseJSONResponse.getJSONArray("result");

            if (resultArray.length()>0){

                for (int i=0;i<resultArray.length();i++){

                    JSONObject properties=resultArray.getJSONObject(i);
                    String id=properties.getString("id");
                    String name=properties.getString("name");
                    String email=properties.getString("email");
                    String password=properties.getString("password");
                    String contact=properties.getString("contact");
                    String lock_no=properties.getString("lock_no");

                    Person person=new Person(id,name,email,password,contact,lock_no);

                    packets.add(person);
                    Log.e("packetUtils","Packet Added Successfully");

                }
            }

        }catch (JSONException e){
            Log.e("Exception",e.getMessage().toString());
        }

        return packets;
    }

    public static List<Person> fetchData(String requestUrl){

        URL url=createUrl(requestUrl);

        String jsonResponse = null;
        try {
            Log.e("PacketUtils", "calling httpRequest()");
            Log.e("PacketUtils", "url: " + url);
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("PacketUtils", "Problem making the HTTP request.", e);
        }

        Log.e("PacketUtils", "calling extractFeatureFromJson(jsonResponse)");
        List<Person> packets = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return packets;
    }
}
