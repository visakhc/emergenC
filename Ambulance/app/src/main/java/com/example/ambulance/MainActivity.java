package com.example.ambulance;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    String[] add1 =new String[5];
    Button bt;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        bt=findViewById(R.id.button2);
        bt.setOnClickListener(v -> testDb());
        TextView t = findViewById(R.id.textView);


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

    }

    public void testDb()
    {

        Intent i = new Intent(this, MainActivity7.class);
        startActivity(i);
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