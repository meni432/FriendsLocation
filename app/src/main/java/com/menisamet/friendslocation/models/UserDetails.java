package com.menisamet.friendslocation.models;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by meni on 19/12/16.
 */

public class UserDetails {
    private FirebaseUser mFirebaseUser;
    private MapLocation mLastLocation;

//    public FirebaseUser getFirebaseUser() {
//        return mFirebaseUser;
//    }

    public String getUserName() {
        return mFirebaseUser.getDisplayName();
    }
    public void setFirebaseUser(FirebaseUser mFirebaseUser) {
        this.mFirebaseUser = mFirebaseUser;
    }

    public MapLocation getLastLocation() {
        return mLastLocation;
    }


    public void setLastLocation(MapLocation mLastLocation) {
        this.mLastLocation = mLastLocation;
    }
}
