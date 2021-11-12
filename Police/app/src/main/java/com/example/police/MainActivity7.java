package com.example.police;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;

public class MainActivity7 extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;
    private TextView tv;
    String[] add1 = new String[5];
   public String currentAdd = "",mane="";
    Button bt;
    public int man = 0;
    EditText manu;
    Integer c = 0;
    private Location currentLocation;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.button2);
        manu=findViewById(R.id.manual);
        tv.setText(getString(R.string.wait));
        bt.setOnClickListener(v -> testDb());

        getBaseContext();
        ConnectivityManager connec = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

        } else if(
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            nodata();
        }



        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        }
        else{
            showGPSDisabledAlertToUser();

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NotNull LocationResult locationResult) {
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
            Toast.makeText(MainActivity7.this, "Can't find current address, ",
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
            c=0;
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }
            if (resultCode == 1) {
                Toast.makeText(MainActivity7.this, "Address not found, ", Toast.LENGTH_SHORT).show();
            }
            currentAdd = resultData.getString("address_result");


            currentAdd = resultData.getString("address_result");
            add1 = currentAdd.split("-");
            tv.setText(add1[1]);
c++;
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



    public void testDb() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mane = manu.getText().toString();
        if (mane.length() != 0) {
            man = Integer.parseInt(mane);
            add1[1] = String.valueOf(man);
        }
if(c!=0) {
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        SharedPreferences shared1 = getApplicationContext().getSharedPreferences("sid2", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared1.edit();
        String ph1 = add1[1];
        editor.putString("sid2", ph1);
        editor.apply();
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    } else {
        showGPSDisabledAlertToUser();
    }
}
else {
    Toast.makeText(MainActivity7.this, "fetching Pincode, please wait",
            Toast.LENGTH_SHORT).show();
}
    }
    private void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void nodata() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("No internet connection")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {

                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }
}