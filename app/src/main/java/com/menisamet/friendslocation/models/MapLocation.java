package com.menisamet.friendslocation.models;

import android.location.Location;

import java.util.Date;

/**
 * Created by meni on 19/12/16.
 */

public class MapLocation {
    private double latitude;
    private double longitude;

    public MapLocation() {
    }

    public MapLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MapLocation(Location location) {
        setLocation(location);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocation(Location mLocation) {
        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();
    }

}
