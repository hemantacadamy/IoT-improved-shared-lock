package com.example.hemant.sharedlock;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int flag=0;
    private List<Packet> packets;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<Double> xs = new ArrayList<>();
    private ArrayList<Double> ys = new ArrayList<>();
    private ArrayList<String> lock_nos = new ArrayList<>();
    private List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        Toast.makeText(getApplicationContext(),location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_LONG).show();

        PersonAsyncTask task1 = new PersonAsyncTask();
        task1.execute();


        HotelAsyncTask task = new HotelAsyncTask();
        task.execute();

        //    methodcall();
        while(flag==0);

        Toast.makeText(getApplicationContext(),ids.size()+" "+ids,Toast.LENGTH_LONG).show();
        Log.e("id",ids.toString());

        for (int i=0;i<ids.size();i++){
            double x=xs.get(i);
            double y=ys.get(i);
            String id=ids.get(i);
            //Toast.makeText(getApplicationContext(),x+ " "+y+" "+id,Toast.LENGTH_LONG).show();
            LatLng latLng1=new LatLng(x,y);
            mMap.addMarker(new MarkerOptions().position(latLng1).title(id));
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent=new Intent(getApplicationContext(),ShowProfile.class);

                for (int i=0;i<persons.size();i++){
                    if (persons.get(i).getId().equals(marker.getTitle())){
                        intent.putExtra("id",marker.getTitle());
                        intent.putExtra("name",persons.get(i).getName());
                        intent.putExtra("email",persons.get(i).getEmail());
                        intent.putExtra("password",persons.get(i).getPassword());
                        intent.putExtra("contact",persons.get(i).getContact());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

    }
    private class HotelAsyncTask extends AsyncTask<Void,Void,List<Packet>> {

        @Override
        protected ArrayList<Packet> doInBackground(Void... params){
            packets=PacketUtils.fetchData("http://"+IpAddress.getIp()+"/map.php");

            //    adapter=new PacketAdapter(MainActivity.this,(ArrayList<Packet>)packets);

            //   i=new Intent(getApplicationContext(),MapsActivity.class);

            for(int j=0;j<persons.size();j++) {

                for (int i = 0; i < packets.size(); i++) {

                    if(persons.get(j).getLock_no().equals(packets.get(i).getId())) {
                        lock_nos.add(packets.get(i).getId());
                        xs.add(Double.parseDouble(packets.get(i).getX()));
                        ys.add(Double.parseDouble(packets.get(i).getY()));
                        ids.add(persons.get(j).getId());
                        Log.e("ids", ids.toString());
                    }
                }
            }
            flag=1;
            //    Log.e("ids",ids.toString());

            return null;
        }

        @Override
        protected void onPostExecute(List<Packet> result){
            //    listView.setAdapter(adapter);
            //    makemarker(mMap);
            //    Toast.makeText(getApplicationContext(),ids.toString(),Toast.LENGTH_LONG).show();
            Log.e("MainActivity","packets are shown");
        }
    }


    private class PersonAsyncTask extends AsyncTask<Void,Void,List<Person>> {

        @Override
        protected ArrayList<Person> doInBackground(Void... params){
            persons=PersonUtils.fetchData("http://"+IpAddress.getIp()+"/showprofile.php");

            //    adapter=new PacketAdapter(MainActivity.this,(ArrayList<Packet>)packets);

            //   i=new Intent(getApplicationContext(),MapsActivity.class);
            return null;
        }

        @Override
        protected void onPostExecute(List<Person> result){
            //    listView.setAdapter(adapter);
            //    makemarker(mMap);
            //    Toast.makeText(getApplicationContext(),ids.toString(),Toast.LENGTH_LONG).show();
            Log.e("MainActivity","packets are shown");
        }
    }

}
