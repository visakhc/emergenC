package com.example.demouser;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity3 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
Button police;
    GPSTracker gps;
Button ambulance;
    Button quick;
    TextView nm;
    TextView resp;
    String sid="";
    int result;


    String[] add1 =new String[5];

    public static double longitude,latitude;
    String currentAdd;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private MainActivity3.LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);




        addressResultReceiver = new MainActivity3.LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };
        startLocationUpdates();





        SharedPreferences shared = getApplicationContext().getSharedPreferences("sid3", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("sid3","done");
        editor.apply();


        SharedPreferences shee = getSharedPreferences("nm", MODE_PRIVATE);
        String se1 = shee.getString("nm", "");


        if (ContextCompat.checkSelfPermission(MainActivity3.this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity3.this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainActivity3.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            ActivityCompat.requestPermissions(MainActivity3.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    gps = new GPSTracker(MainActivity3.this);

    // Check if GPS enabled
    if(gps.canGetLocation()) {
        MainActivity5.latitude = gps.getLatitude();
        MainActivity5.longitude = gps.getLongitude();

    } else {

        gps.showSettingsAlert();
    }

        police=findViewById(R.id.button4);
        police.setOnClickListener(v -> pol());
        ambulance=findViewById(R.id.button7);
        ambulance.setOnClickListener(v -> amb());
        nm=findViewById(R.id.textView11);
       // nm.setText(MainActivity.name);
        nm.setText(se1);
        quick=findViewById(R.id.btnquick);
        quick.setOnClickListener(v -> quic());
    resp=findViewById(R.id.viewreponce);
    resp.setOnClickListener(v -> res());
    }
    public void quic(){
        Intent f = new Intent(this, MainActivity5.class);
        startActivity(f);
    }
    public void res(){
        Intent f = new Intent(this, MainActivity7.class);
        startActivity(f);
    }
    public void pol() {


            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Class.forName("com.mysql.jdbc.Driver");

                Connection con= DriverManager.getConnection(url, user, pass);
                PreparedStatement ps= con.prepareStatement("select * from param");

                ResultSet rs=ps.executeQuery();

                while(rs.next())
                {
                    result = rs.getInt(2) ;

                }
                result=result+1;
              //  sid="ALERT"+result;

                sid=String.valueOf(result);




            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("connection to the server lost")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {

                        });
                AlertDialog alert = builder.create();
                alert.show();

            }



        Intent i=new Intent(MainActivity3.this,MainActivity4.class);
        i.putExtra("value",sid);
        startActivity(i);




    }
    public void amb(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);
            PreparedStatement ps= con.prepareStatement("select * from param");

            ResultSet rs=ps.executeQuery();

            while(rs.next())
            {
                result = rs.getInt(2) ;

            }
            result=result+1;
           // sid="ALERT"+result;
            sid=String.valueOf(result);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("connection to the server lost")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {

                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
        Intent i=new Intent(this,MainActivity6.class);
        i.putExtra("value",sid);
        startActivity(i);

    }








    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }
    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(MainActivity3.this, "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
            else {
                Toast.makeText(this, "Location permission not granted, " + "restart the app if you want the feature", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }
            if (resultCode == 1) {
                Toast.makeText(MainActivity3.this, "Address not found, ", Toast.LENGTH_SHORT).show();
            }
            currentAdd = resultData.getString("address_result");

            add1 = currentAdd.split("-");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }
    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }






}