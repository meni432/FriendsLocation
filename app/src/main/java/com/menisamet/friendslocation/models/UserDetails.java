package com.menisamet.friendslocation.models;

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
    private String userName;
    private String mFirebaseUserUUID;

    //    public FirebaseUser getFirebaseUser() {
//        return mFirebaseUser;
//    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firebaseUserUUID", mFirebaseUser.getUid());
        result.put("userName", userName);
        result.put("lastLocation", mLastLocation);

        return result;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirebaseUserUUID(String mFirebaseUserUUID) {
        this.mFirebaseUserUUID = mFirebaseUserUUID;
    }

    public String getFirebaseUserUUID() {
        if (mFirebaseUser != null) {
            return mFirebaseUser.getUid();
        }
        return mFirebaseUserUUID;
    }

    public String getUserName() {
        if (mFirebaseUser != null) {
            return mFirebaseUser.getDisplayName();
        } else {
            return userName;
        }
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

    @Override
    public String toString() {
        return "UserDetails{" +
                "mFirebaseUser=" + mFirebaseUser +
                ", mLastLocation=" + mLastLocation +
                ", userName='" + userName + '\'' +
                ", mFirebaseUserUUID='" + mFirebaseUserUUID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetails that = (UserDetails) o;

        if (!mLastLocation.equals(that.mLastLocation)) return false;
        return userName.equals(that.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
