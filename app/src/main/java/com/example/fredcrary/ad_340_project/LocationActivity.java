package com.example.fredcrary.ad_340_project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;

public class LocationActivity extends ToolBarClass implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        OnMapReadyCallback,
        LocationListener
{
    protected static final String TAG = LocationActivity.class.getSimpleName();

    protected static final int MY_PERMISSIONS_FINE_LOCATION = 1;

    protected GoogleApiClient mGoogleApiClient;     // Entry point to Google Play Services

    // Basic location information
    protected Location mLastLocation = null;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    // Map information
    protected GoogleMap mMap = null;
    // Location updates
    private LocationRequest mLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Set up the toolbar
        Toolbar appToolBar = (Toolbar) findViewById(R.id.locationToolbar);
        appToolBar.inflateMenu(R.menu.toolbar);
        setSupportActionBar(appToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.mCurrentPageId = R.id.locationAction;  // Remove this page from toolbar

        // Start the process to fetch the map
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up for location
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById(R.id.latitude_text);
        mLongitudeText = (TextView) findViewById(R.id.longitude_text);
        buildGoogleApiClient();
        createLocationRequest();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected() started");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();     // requires GoogleApiClient to be connected
            updateDisplay();
        } else {
            Log.d(TAG, "Location permission failed; asking for permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                   String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission received");
                    // Update the display
                    updateDisplay();
                } else {
                    Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    protected void updateDisplay() {
        Log.d(TAG, "updateDisplay()");
        // We get here when either (a) we have location permission (hence a location), or
        // (b) we have a map. Allow for the possibility that some information may be missing.
        if (mLastLocation == null) {
            mLastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (mLastLocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                    mLastLocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                    mLastLocation.getLongitude()));
        }

        if (mLastLocation != null && mMap != null ) {
            LatLng currLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            //mMap.clear();         // clear previous marker (and more)
            mMap.addMarker(new MarkerOptions().position(currLoc).title("You are here"));
            mMap.setMinZoomPreference(11);      // city level
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currLoc));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    // ========== Map function

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;       // Save the map
        updateDisplay();        // Update the display
    }

    // ========== Functions for location updates

    protected void createLocationRequest() {
        Log.d(TAG, "createLocationRequest()");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);        // milliseconds
        mLocationRequest.setFastestInterval(10000); // milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "startLocationUpdates()");
            if(mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
                Log.d(TAG, "updates requested");
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateDisplay();
    }

    // ========== Functions to get the address of the current location

    /*
    private void getCurrentAddress() {
        // mLastLocation != null implies location permission
        if (mLastLocation == null || mMap == null) {
            return;
        }

        @SuppressWarnings("MissingPermission")
        PendingResult<PlaceLikelyhoodBuffer> result =
                Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
    }
    */
}
