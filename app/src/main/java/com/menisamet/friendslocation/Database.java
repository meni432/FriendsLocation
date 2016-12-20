package com.menisamet.friendslocation;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.menisamet.friendslocation.models.MapLocation;
import com.menisamet.friendslocation.models.MapPoint;
import com.menisamet.friendslocation.models.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by meni on 18/12/16.
 */
public class Database {
    public static final String TAG = "TAG_"+Database.class.getCanonicalName();
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private ArrayList<MapPoint> userMapPoint;
    private HashMap<MapLocation, MapPoint> mapLocationMapPointHashMap;
    private HashMap<MapPoint, MapLocation> mapPointMapLocationHashMap;
    private FirebaseUser currentUser;
    private MapLocation mRealTimeMapLocation;


    static {
        MapPoint mapPoint1 = new MapPoint();
        mapPoint1.setTag("tag");
        mapPoint1.setDate(new Date(1255));
        MapPoint mapPoint2 = new MapPoint();
        mapPoint2.setDate(new Date(999999999));
        mapPoint2.setTag("tag2");
        mapPoint2.setUserDetailsOwner(new UserDetails());
        Database.getInstance().addMapPoint(mapPoint1);
    }
    private Database() {
        userMapPoint = new ArrayList<>();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public void saveCacheToDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("map_points");
        DatabaseReference userNode = databaseReference.child(currentUser.getUid());
        userNode.child("list").setValue(userMapPoint);
        if (mRealTimeMapLocation != null) {
            userNode.child("real_time_map_point").setValue(mRealTimeMapLocation);
            Log.d(TAG, "save real time map location " + mRealTimeMapLocation.getLatitude() + " " + mRealTimeMapLocation.getLongitude() + " " +mRealTimeMapLocation.getDate());
        }
    }

    public void addMapPoint(MapPoint mapPoint){
        userMapPoint.add(mapPoint);
    }

    public void setRealTimeMapPoint(MapLocation mapLocation) {
        mRealTimeMapLocation = mapLocation;
        Log.d(TAG, "set reailTimeMapLocation");
    }



}
