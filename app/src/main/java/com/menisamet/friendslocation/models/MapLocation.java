package com.menisamet.friendslocation.models;

import android.location.Location;

import java.util.Date;

/**
 * Created by meni on 19/12/16.
 */

public class MapLocation {
    private double latitude;
    private double longitude;
    private Date date;

    public MapLocation() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        updataData();
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        updataData();
    }

    public void setLocation(Location mLocation) {
        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();
        updataData();
    }

    private void updataData() {
        date = new Date(System.currentTimeMillis());
    }
}
