package com.example.ambulance;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(MainActivity4.la, MainActivity4.lo);

        mMap.addMarker(new MarkerOptions().position(location).title("Victim location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        CameraPosition myPosition = new CameraPosition.Builder()
                .target(location).zoom(17).bearing(0).tilt(40).build();
        googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(myPosition));
    }
}