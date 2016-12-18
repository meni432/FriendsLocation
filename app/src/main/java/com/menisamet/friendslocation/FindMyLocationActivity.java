package com.menisamet.friendslocation;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import com.google.firebase.auth.FirebaseAuth;

public class FindMyLocationActivity extends AppCompatActivity {

    // Last geographical location
    protected Location mLastLocation;
    protected LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_my_location);
    }

    public void getByGPSSelected(View view) {
        Utility.checkAuthAndGoToActivity(this, FindByGPSActivity.class);
    }

    public void getByQRSelected(View view){
        Utility.checkAuthAndGoToActivity(this, FindByQRActivity.class);
    }
}
