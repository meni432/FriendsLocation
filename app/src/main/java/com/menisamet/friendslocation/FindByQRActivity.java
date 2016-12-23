package com.menisamet.friendslocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.menisamet.friendslocation.models.MapPoint;
import com.menisamet.friendslocation.models.UserDetails;

public class FindByQRActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = "TAG_"+FindByQRActivity.class.getCanonicalName();

    TextView mContentsTextView;
    TextView mTagContentsTextView;
    TextView mOwnerContentsTextView;

    private GoogleMap mMap;
    private Marker currentLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_qr);

        mContentsTextView = (TextView) findViewById(R.id.textView_content);
        mTagContentsTextView = (TextView) findViewById(R.id.tag_context_textView);
        mOwnerContentsTextView = (TextView) findViewById(R.id.owner_content_textView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            if (scanResult.getContents() != null) {
                Log.d(TAG, scanResult.getContents());
                mContentsTextView.setText(scanResult.getContents());
                MapPoint mapPoint = Database.getInstance().getMapPointByUID(scanResult.getContents());
                if (mapPoint != null) {
                    mContentsTextView.setText(mapPoint.toString());
                    Log.d(TAG, "context map point : "+mapPoint.toString());
                    String title = mapPoint.getmTag() != null ? mapPoint.getmTag() : "Current Location";
                    mTagContentsTextView.setText(title);
                    String userName = mapPoint.getUserDetailsOwner() != null ? mapPoint.getUserDetailsOwner().getUserName() : null;
                    String owner = userName;
                    if (userName == null) {
                        UserDetails userDetails = Database.getInstance().getUserByUUID(mapPoint.getUserDetailsOwnerUUID());
                        if (userDetails != null){
                            owner = userDetails.getUserName() != null ? userDetails.getUserName() : "Unknown";
                        }
                    }
                    mOwnerContentsTextView.setText(owner);
                    if (mapPoint.getMapLocation() != null) {
                        if (mMap != null) {
                            LatLng latLng = mapPoint.getMapLocation().getLatLng();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                            if (currentLocationMarker == null) {
                                currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                                currentLocationMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                currentLocationMarker.showInfoWindow();

                            } else {
                                currentLocationMarker.setPosition(latLng);
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_qr_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                startScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng israel = new LatLng(32.106, 35.204);
        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(israel).title("Ariel"));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(israel));
    }
}
