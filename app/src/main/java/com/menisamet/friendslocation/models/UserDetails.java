package com.menisamet.friendslocation.models;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by meni on 19/12/16.
 */

public class UserDetails {
    FirebaseUser mFirebaseUser;
    MapLocation mLastLocation;

    public FirebaseUser getFirebaseUser() {
        return mFirebaseUser;
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
