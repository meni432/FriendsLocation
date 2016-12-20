package com.menisamet.friendslocation.models;

import android.location.Location;

import java.util.Date;

/**
 * Created by meni on 19/12/16.
 */

public class MapPoint {

    private UserDetails mUserDetailsOwner;
    private Date mDate;
    private String mNodeTag;

    public MapPoint() {
    }

    public void setTag(String tag) {
        this.mNodeTag = tag;
    }

    public String getTag() {
        return mNodeTag;
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

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }
}
