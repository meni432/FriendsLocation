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
import java.util.List;
import java.util.Map;

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
    private ArrayList<MapPoint> mapLocations;
    private ArrayList<UserDetails> allUserDetail;

    private HashMap<String, MapPoint> mapDbKeyToMapPoint;

    DatabaseReference mMapPointReference;

    private Database() {
        selfUser = new UserDetails();
        mapDbKeyToMapPoint = new HashMap<>();
        mMapPointReference = FirebaseDatabase.getInstance().getReference().child("map_points");
    }

    static {
        MapPoint m1 = new MapPoint();
        m1.setDbKey("12");
        m1.setmDate(new Date(122233666));
        m1.setMapLocation(new MapLocation(1.3,99.3));
        Database.getInstance().addMapPoint(m1);
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
    }


    public void loadDataFromDB() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot mapPointSnapshot : dataSnapshot.getChildren()) {
                    MapPoint mapPoint = null;
                    boolean contain = false;
                    if (getMapDbKeyToMapPoint().containsKey(mapPointSnapshot.getKey())){
                        mapPoint = getMapDbKeyToMapPoint().get(mapPointSnapshot.getKey());
                        contain = true;
                    }

                    UserDetails userDetails = mapPointSnapshot.child("").getValue(UserDetails.class);
                    mapPoint.setUserDetailsOwner(userDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        mMapPointReference.addValueEventListener(postListener);
    }

    public HashMap<String, MapPoint> getMapDbKeyToMapPoint() {
        return mapDbKeyToMapPoint;
    }

    public void setRealTimeMapPoint(MapLocation mapLocation) {
        if (selfUser != null) {
            selfUser.setLastLocation(mapLocation);
        }
    }


}
