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
import java.sql.Statement;

public class MainActivity5 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button b1;
    Button b2,b3;
    String sid="";
    String s1="";
    String sid3="";
    int result,result3;
    GPSTracker gps;
    public static double longitude,latitude;
    String currentAdd;
    String[] add1 =new String[5];
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        locvk();


        b1=findViewById(R.id.button5);
        b1.setOnClickListener(v -> acc());
        b2=findViewById(R.id.button6);
        b2.setOnClickListener(v -> rob());
        b3=findViewById(R.id.button8);
        b3.setOnClickListener(v -> wom());

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };
        startLocationUpdates();

    }

    @SuppressWarnings("MissingPermission")
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
    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(MainActivity5.this, "Can't find current address, ",
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
                Toast.makeText(MainActivity5.this, "Address not found, ", Toast.LENGTH_SHORT).show();
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





    public void wom(){
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
            sid="ALERT"+result;


            SharedPreferences shared1 = getSharedPreferences("sid2", MODE_PRIVATE);
            String ph1 = shared1.getString("sid2","");

            String t="police";
            String status="quick_pending";




            PreparedStatement ps1= con.prepareStatement("select * from user where phone='"+ph1+"' ");




            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                s1 = rs1.getString(1);



            }



            Statement st=con.createStatement();
            String query ="insert into tbl_alert(alert_id,pincode,name,address,phone,target,status,longitude,latitude)values('"+sid+"','"+add1[0]+"','"+s1+"','"+ add1[1]+"','"+ph1+"','"+t+"','"+status+"','"+longitude+"','"+latitude+"')";
            st.executeUpdate(query);




            Statement st1=con.createStatement();


            String q1="update param set aid='"+result+"' ";

            st1.executeUpdate(q1);


done();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("query error found")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }


    }


    public void acc() {

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
            sid="ALERT"+result;




            SharedPreferences shared1 = getSharedPreferences("sid2", MODE_PRIVATE);

            String ph1 = shared1.getString("sid2","");
            String t="police";
            String status="quick_pending";

            String t1="ambulance";


            PreparedStatement ps1= con.prepareStatement("select * from user where phone='"+ph1+"' ");




            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                s1 = rs1.getString(1);



            }



            Statement st=con.createStatement();
            String query ="insert into tbl_alert(alert_id,pincode,name,address,phone,target,status,longitude,latitude)values('"+sid+"','"+add1[0]+"','"+s1+"','"+ add1[1]+"','"+ph1+"','"+t+"','"+status+"','"+longitude+"','"+latitude+"')";
            st.executeUpdate(query);




            Statement st1=con.createStatement();


            String q1="update param set aid='"+result+"' ";

            st1.executeUpdate(q1);


            PreparedStatement ps3= con.prepareStatement("select * from param");

            ResultSet rs3=ps3.executeQuery();

            while(rs3.next())
            {
                result3 = rs3.getInt(2) ;

            }
            result3=result3+1;
            sid3="ALERT"+result3;
            Statement st3=con.createStatement();
            String query3 ="insert into tbl_alert(alert_id,pincode,name,address,phone,target,status,longitude,latitude)values('"+sid3+"','"+add1[0]+"','"+s1+"','"+ add1[1]+"','"+ph1+"','"+t1+"','"+status+"','"+longitude+"','"+latitude+"')";
            st3.executeUpdate(query3);
            Statement st4=con.createStatement();


            String q4="update param set aid='"+result3+"' ";

            st4.executeUpdate(q4);

        done();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("query error found")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }



    }
    public void rob(){

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
            sid="ALERT"+result;



            SharedPreferences shared1 = getSharedPreferences("sid2", MODE_PRIVATE);
            String ph1 = shared1.getString("sid2","");
            String t="police";
            String status="quick_pending";




            PreparedStatement ps1= con.prepareStatement("select * from user where phone='"+ph1+"' ");




            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                s1 = rs1.getString(1);



            }



            Statement st=con.createStatement();
            String query ="insert into tbl_alert(alert_id,pincode,name,address,phone,target,status,longitude,latitude)values('"+sid+"','"+add1[0]+"','"+s1+"','"+ add1[1]+"','"+ph1+"','"+t+"','"+status+"','"+longitude+"','"+latitude+"')";
            st.executeUpdate(query);




            Statement st1=con.createStatement();


            String q1="update param set aid='"+result+"' ";

            st1.executeUpdate(q1);


done();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("query error found")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }



    }


    private void locvk() {

        gps = new GPSTracker(MainActivity5.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        } else {

            gps.showSettingsAlert();
        }

    }

    private void done() {
        Intent f = new Intent(this, MainActivity3.class);
        finish();
        startActivity(f);
        Toast.makeText(this,"Successfully Registered", Toast.LENGTH_LONG).show();
    }
}
