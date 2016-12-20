package com.menisamet.friendslocation.models;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meni on 19/12/16.
 */

public class MapPoint {

    private UserDetails mUserDetailsOwner;
    private Date mDate;
    private String mTag;
    private MapLocation mMapLocation;
    private String dbKey;


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("UserDetails", mUserDetailsOwner);
        result.put("Date", mDate);
        result.put("Tag", mTag);
        result.put("MapLocation", mMapLocation);

        return result;
    }
    public MapPoint() {
    }

    public UserDetails getUserDetailsOwner() {
        return mUserDetailsOwner;
    }

    public void setUserDetailsOwner(UserDetails mUserDetailsOwner) {
        this.mUserDetailsOwner = mUserDetailsOwner;
    }

    public Date getDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
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
}
