package e.hp.redo;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MyMap extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    LocationManager locationManager;
    String geolocateCity;
    static String setAddress;
    TextView inputSearch;
    String source;
    android.support.v7.widget.Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        inputSearch=findViewById(R.id.search);

        //toolbar
        my_toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        source=getIntent().getStringExtra("cityName");
        if(source.equals("1")){
            geolocateCity=MainActivity.pickupCity.getText().toString();
        }else
            geolocateCity=MainActivity.dropoffCity.getText().toString();
        Log.e("cityname",geolocateCity);
        initMap();
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
            geoLocate(geolocateCity);
        }else {
            Toast.makeText(getApplicationContext(),"Map is not ready",Toast.LENGTH_SHORT).show();
        }

            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLng every = mMap.getCameraPosition().target;
                    if (source.equals("1"))
                        MainActivity.startingLocationLatLng=every;
                    else
                        MainActivity.endingLocationLatLng=every;
                    Log.i("Latitude and Longitude", "lat" + every.latitude + " lon " + every.longitude);
                    Address address = getAddress(every.latitude, every.longitude, getApplicationContext());
                    if (address != null) {
                        setAddress = setingAddress(address);
                        Log.i("kya ho rha h", setTextView(setAddress,38));
                        String s = setTextView(setAddress,42);
                        inputSearch.setText(s);
                    }
                }

            });

    }

    public void moveCamera (LatLng latLng,float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        Log.i("Camera successfuL moved", "yes");
    }


    private void geoLocate(String geolocateCity) {
        Geocoder geocoder = new Geocoder(this);
        Log.i("city name", geolocateCity);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(geolocateCity, 1);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Invalid Address", Toast.LENGTH_LONG).show();
            Log.i("Exception::", "yes");
        }
        Log.i("mmap m kya h", "kya:" + mMap.toString());
        if (list.size() > 0) {
            Address address = list.get(0);
            setAddress = address.getFeatureName() + "," + address.getAdminArea() + "," + address.getCountryName() + "," + address.getPostalCode() + "(" + address.getLocality() + ")";
            Log.i("address type::", address.toString());
            LatLng loc = new LatLng(address.getLatitude(), address.getLongitude());
            if (source.equals("1"))
                MainActivity.startingLocationLatLng=loc;
            else
                MainActivity.endingLocationLatLng=loc;
            mMap.clear();
            //mMap.addMarker(new MarkerOptions().position(loc).title(city));
            moveCamera(loc,10f);
        } else {
            Toast.makeText(getApplicationContext(), "No such location found", Toast.LENGTH_SHORT).show();
        }
    }

    public Address getAddress(double lattitude,double longitude,Context context){
        Geocoder geocoder=new Geocoder(context);
        Address address=null;
        List<Address> list;
        try {
            list = geocoder.getFromLocation(lattitude, longitude, 1);
            if (list.size() > 0) {
                address = list.get(0);
                return address;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("can't locate address","yes");
        }
        return address;
    }
    public static String setTextView(String text,int range){
        String set,con="...";
        if(text.length()>range) {
            set = text.substring(0, range-3);
            Log.i("set phli string",set);
            Log.i("con string",con);
            set+=con;
            Log.i("set hone string",set);
            return set;
        }else {
            set=text;
            return set;
        }
    }
    public String setingAddress(Address address){
        String settingAdd="";
        if(address.getSubThoroughfare()!=null){
            settingAdd+=address.getSubThoroughfare()+",";
            Log.i("ky1:","h"+address.getSubThoroughfare());
        }if(address.getThoroughfare()!=null){
            settingAdd+=address.getThoroughfare()+",";
            Log.i("ky2:","h"+address.getThoroughfare());
        }if(address.getSubLocality()!=null){
            settingAdd+=address.getSubLocality()+",";
            Log.i("ky*:","h"+address.getSubLocality());
        }if(address.getLocality()!=null){
            settingAdd+=address.getLocality()+",";
            Log.i("ky4:","h"+address.getLocality());
        }if(address.getFeatureName()!=null){
            settingAdd+=address.getFeatureName()+",";
            Log.i("ky5:","h"+address.getFeatureName());
        }if(address.getAdminArea()!=null){
            settingAdd+=address.getAdminArea()+",";
            Log.i("ky6:","h"+address.getAdminArea());
        }if(address.getCountryName()!=null){
            settingAdd+=address.getCountryName();
            Log.i("ky7:","h"+address.getCountryName());
        }
        return settingAdd;

    }

    public void performShow(){
        Log.i("ja kya rha h:",setAddress.toString());
        Intent i1=new Intent(this,MainActivity.class);
        if (source.equals("1"))
            i1.putExtra("result","1");
        else
            i1.putExtra("result","2");
        setResult(RESULT_OK,i1);
        finish();
    }

    public void setLocation(View view) {
        performShow();
    }
}
