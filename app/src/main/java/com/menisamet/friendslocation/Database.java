package com.menisamet.friendslocation;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.menisamet.friendslocation.models.MapLocation;
import com.menisamet.friendslocation.models.MapPoint;
import com.menisamet.friendslocation.models.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by meni on 18/12/16.
 */
public class Database {
    public static final String TAG = "TAG_" + Database.class.getCanonicalName();
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private FirebaseUser currentUser;

    private UserDetails selfUser;
    private ArrayList<MapPoint> mapPoints;
    private ArrayList<UserDetails> allUserDetail;

    private HashMap<String, MapPoint> mapDbKeyToMapPoint;
    private HashMap<String, UserDetails> userUUIDtoUserDetailsHashMap;

    DatabaseReference mMapPointReference;
    DatabaseReference mUserDetailsReference;

    private Database() {
        selfUser = new UserDetails();
        mapDbKeyToMapPoint = new HashMap<>();
        mapPoints = new ArrayList<>();
        allUserDetail = new ArrayList<>();
        userUUIDtoUserDetailsHashMap = new HashMap<>();
        mMapPointReference = FirebaseDatabase.getInstance().getReference().child("map_points");
        mUserDetailsReference = FirebaseDatabase.getInstance().getReference().child("user_detail");
    }

    static {

    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
        selfUser.setFirebaseUser(currentUser);
    }

    public void saveCacheToDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user_detail");
        DatabaseReference userNode = databaseReference.child(currentUser.getUid());
        userNode.setValue(selfUser);
    }


    public void addMapPoint(MapPoint mapPoint) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("map_points");
        DatabaseReference myNewMapPoint = databaseReference.push();
        Map<String, Object> postValues = mapPoint.toMap();
        myNewMapPoint.updateChildren(postValues);
        String key = myNewMapPoint.getKey();
        mapPoint.setDbKey(key);
        mapPoints.add(mapPoint);
        mapDbKeyToMapPoint.put(mapPoint.getDbKey(), mapPoint);
    }


    public void loadDataFromDB() {

//        MapPoint m1 = new MapPoint();
//        m1.setDbKey("12");
//        m1.setMapLocation(new MapLocation(34.3, 45.99));
//        m1.setMapLocation(new MapLocation(1.3, 99.3));
//        m1.setUserDetailsOwner(Database.getInstance().selfUser);
//        Database.getInstance().addMapPoint(m1);


        ValueEventListener mapPointListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MapPoint> mapPointArrayList = new ArrayList<>();
                Log.d(TAG, "map point listener ");
                // Get Post object and use the values to update the UI
                for (DataSnapshot mapPointSnapshot : dataSnapshot.getChildren()) {
                    MapPoint mapPoint = new MapPoint();
                    boolean contain = false;
//                    if (getMapDbKeyToMapPoint().containsKey(mapPointSnapshot.getKey())) {
//                        mapPoint = getMapDbKeyToMapPoint().get(mapPointSnapshot.getKey());
//                        contain = true;
//                    }else {
//                        mapPoint = new MapPoint();
//                    }


//                    MapLocation mapLocation = mapPointSnapshot.child("lastLocation").getValue(MapLocation.class);
//                    Date date = mapPointSnapshot.child("Date").getValue(Date.class);
//                    String latString = mapPointSnapshot.child("MapLocation").child("latitude").getValue(String.class);
                    if (mapPointSnapshot.child("MapLocation").child("latitude").exists()) {
                        double latDouble = mapPointSnapshot.child("MapLocation").child("latitude").getValue(Double.class);
                        double lotDouble = mapPointSnapshot.child("MapLocation").child("longitude").getValue(Double.class);
                        MapLocation mapLocation = new MapLocation(latDouble, lotDouble);
                        mapPoint.setMapLocation(mapLocation);
                        mapPoint.setDbKey(mapPointSnapshot.getKey());
                        Log.d(TAG, "getKey() " + mapPointSnapshot.getKey());
                    }
                    String userName = mapPointSnapshot.child("userName").getValue(String.class);
                    if (userName != null) {
                        mapPoint.setTag("User Name: " + userName);
                    }
                    String firebaseUserUUID = mapPointSnapshot.child("firebaseUserUUID").getValue(String.class);
                    if (firebaseUserUUID != null) {
                        UserDetails userDetails;
                        if (userUUIDtoUserDetailsHashMap.containsKey(firebaseUserUUID)) {
                            userDetails = userUUIDtoUserDetailsHashMap.get(firebaseUserUUID);
                            mapPoint.setUserDetailsOwner(userDetails);
                        } else {
                        }
                    }

                    mapPointArrayList.add(mapPoint);
//                    if (!contain && mapPoint != null) {
//                        mapPoints.add(mapPoint);
//                        if (mapPoint.getDbKey() != null) {
//                            mapDbKeyToMapPoint.put(mapPoint.getDbKey(), mapPoint);
//                        }
//
//                    }

                }

                mapPoints = mapPointArrayList;

                Log.d(TAG, "maps points: " + mapPoints);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };


        ValueEventListener userDetailsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "on data change user detils listener");
                // Get Post object and use the values to update the UI
                ArrayList<UserDetails> userDetailsArrayList = new ArrayList<>();
                for (DataSnapshot userDetailsSnapshot : dataSnapshot.getChildren()) {
                    UserDetails userDetails = new UserDetails();
                    double latDouble = userDetailsSnapshot.child("lastLocation").child("latitude").getValue(Double.class);
                    double lotDouble = userDetailsSnapshot.child("lastLocation").child("longitude").getValue(Double.class);
                    MapLocation mapLocation = new MapLocation(latDouble, lotDouble);
                    String userName = userDetailsSnapshot.child("userName").getValue(String.class);
                    String firebaseUserUUID = userDetailsSnapshot.child("firebaseUserUUID").getValue(String.class);
                    if (firebaseUserUUID != null) {
                        userUUIDtoUserDetailsHashMap.put(firebaseUserUUID, userDetails);
                    }
                    userDetails.setUserName(userName);
                    userDetails.setLastLocation(mapLocation);
                    userDetails.setFirebaseUserUUID(firebaseUserUUID);
                    userDetailsArrayList.add(userDetails);
                }
                allUserDetail = userDetailsArrayList;

                Log.d(TAG, " all users: " + allUserDetail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        mMapPointReference.addValueEventListener(mapPointListener);
        mUserDetailsReference.addValueEventListener(userDetailsListener);

    }

    public HashMap<String, MapPoint> getMapDbKeyToMapPoint() {
        return mapDbKeyToMapPoint;
    }

    public void setRealTimeMapPoint(MapLocation mapLocation) {
        if (selfUser != null && !mapLocation.equals(selfUser.getLastLocation())) {
            selfUser.setLastLocation(mapLocation);
            saveCacheToDatabase();
        }
    }


}
