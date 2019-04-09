package com.blogspot.estamoscercadelfututro.gpsapp;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                showGoogleMapsLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setMessage("You need to grant GPS permissions ").setTitle("GPS error");
            AlertDialog dialog = alert1.create();
            dialog.show();
            return;
        }

        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locListener);

    }

    private void showAlert(String msg)
    {
        AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
        alert1.setMessage(msg).setTitle("GPS data");
        AlertDialog dialog = alert1.create();
        dialog.show();
    }

    private void showGoogleMapsLocation(Location loc)
    {
        Uri gmmIntentUri = Uri.parse("geo:" + loc.getLatitude() + "," + loc.getLongitude() +
                "?z=17&q=" + loc.getLatitude() + "," + loc.getLongitude() +
                "(Marcador_1)");

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
