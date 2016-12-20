package com.menisamet.friendslocation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.menisamet.friendslocation.models.MapLocation;
import com.menisamet.friendslocation.models.MapPoint;

public class FindByGPSActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "TAG_" + FindByGPSActivity.class.getCanonicalName();
    public static Location static_location = null;

    private Activity currentActivity = this;

    private static final long LOCATION_REQUEST_INTERVAL_FREQ = 10000;
    private static final long LOCATION_REQUEST_FAST_INTERVAL_FREQ = 5000;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // Google play services entry point
    protected GoogleApiClient mGoogleApiClient;


    // Last geographical location
    protected Location mLastLocation;
    protected LocationRequest mLocationRequest;

    protected boolean mAddressRequested;

    long mLastUpdateTime;

    // GUI element
//    Button mFetchAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_gps);

        mAddressRequested = false;
        mLastUpdateTime = 0;
        buildGoogleApiClient();
        createLocationRequest();
        updateUIWidgets();

        Database.getInstance().saveCacheToDatabase();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void updateUIWidgets() {
    }

    /**
     * Builds a GoogleApiClient. Uses {@code #addApi} to request the LocationServices API.
     */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Log.d(TAG, "build google api client");
    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL_FREQ);
        mLocationRequest.setFastestInterval(LOCATION_REQUEST_FAST_INTERVAL_FREQ);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.build();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        static_location = location;
        Database.getInstance().setRealTimeMapPoint(new MapLocation(mLastLocation));
        Database.getInstance().saveCacheToDatabase();
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(location.toString());
        Log.d(TAG, "on location change");
    }


    private void updateCurrentLocation() {
        Log.d(TAG, "update current location");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionLocationCheck();
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null){
            Log.d(TAG, mLastLocation.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_find_by_gps_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_find_location:
                updateCurrentLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void buttonLocation(View view) {
        updateCurrentLocation();
    }


    private void permissionLocationCheck() {
        Log.d(TAG, "permission check");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(currentActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(currentActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            return;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateCurrentLocation();
        if (mLastLocation != null) {
//            setFinalLocation(mLastLocation, "");
            if (!Geocoder.isPresent()) {
                Utility.showToast(this, getString(R.string.no_geocoder_aviable));
                return;
            }

            // start location update for father location change. or, new gps data for current location
            Log.d(TAG," on connected");
            startLocationUpdates();
        }
    }


    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionLocationCheck();
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
