package com.menisamet.friendslocation.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by meni on 19/12/16.
 */

public class MapPoint {

    private UserDetails mUserDetailsOwner;
    private String mFirebaseUserUUIDOwner;
    private String mTag;
    private MapLocation mMapLocation;
    private String dbKey;


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firebaseUserUUID", mUserDetailsOwner.getFirebaseUserUUID());
        result.put("Tag", mTag);
        result.put("MapLocation", mMapLocation.toMap());

        return result;
    }
    public MapPoint() {
    }


    public String getFirebaseUserUUIDOwner() {
        return mFirebaseUserUUIDOwner;
    }

    public void setFirebaseUserUUIDOwner(String firebaseUserUUIDOwner) {
        this.mFirebaseUserUUIDOwner = firebaseUserUUIDOwner;
    }

    public String getUserDetailsOwnerUUID() {
        if (mUserDetailsOwner != null) {
            mUserDetailsOwner.getFirebaseUserUUID();
        }
        return null;
    }

    public UserDetails getUserDetailsOwner() {
        return mUserDetailsOwner;
    }

    public void setUserDetailsOwner(UserDetails mUserDetailsOwner) {
        this.mUserDetailsOwner = mUserDetailsOwner;
    }

    public String getmTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public MapLocation getMapLocation() {
        return mMapLocation;
    }

    public void setMapLocation(MapLocation mMapLocation) {
        this.mMapLocation = mMapLocation;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    @Override
    public String toString() {
        return "MapPoint{" +
                "mUserDetailsOwner=" + mUserDetailsOwner +
                ", mTag='" + mTag + '\'' +
                ", mMapLocation=" + mMapLocation +
                ", dbKey='" + dbKey + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapPoint mapPoint = (MapPoint) o;

        if (mTag != null ? !mTag.equals(mapPoint.mTag) : mapPoint.mTag != null) return false;
        if (!mMapLocation.equals(mapPoint.mMapLocation)) return false;
        return dbKey != null ? dbKey.equals(mapPoint.dbKey) : mapPoint.dbKey == null;

    }

    @Override
    public int hashCode() {
        int result = mTag != null ? mTag.hashCode() : 0;
        result = 31 * result + mMapLocation.hashCode();
        result = 31 * result + (dbKey != null ? dbKey.hashCode() : 0);
        return result;
    }
}
