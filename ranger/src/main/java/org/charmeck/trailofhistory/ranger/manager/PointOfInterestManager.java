package org.charmeck.trailofhistory.ranger.manager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charmeck.trailofhistory.ranger.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trey Robinson on 7/9/16.
 */
public class PointOfInterestManager {

    private static PointOfInterestManager mInstance;

    private static String KEY_POI = "pointOfInterest";

    private DatabaseReference mDatabase;


    public static PointOfInterestManager getInstance(){

        if(mInstance == null){
            mInstance = new PointOfInterestManager();
        }

        return mInstance;
    }

    private PointOfInterestManager(){

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public PointOfInterest savePOI(PointOfInterest pointOfInterest, DatabaseReference.CompletionListener completionListener) {

        if(pointOfInterest.getUid() == null){
            pointOfInterest.setUid(mDatabase.child(KEY_POI).push().getKey());
        }

        mDatabase.child(KEY_POI).child(pointOfInterest.getUid()).setValue(pointOfInterest, completionListener);

        return pointOfInterest;
    }

    public void getPointsofInterest(final PointOfInterestListCallback callback){
        mDatabase.child(KEY_POI).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<PointOfInterest> poiList = new ArrayList<PointOfInterest>();
                        for(DataSnapshot poiSnapshot: dataSnapshot.getChildren()){
                            PointOfInterest poi = poiSnapshot.getValue(PointOfInterest.class);
                            poiList.add(poi);
                        }
                        callback.pointsOfInterestRetrieved(poiList);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public interface PointOfInterestListCallback {
        void pointsOfInterestRetrieved(List<PointOfInterest> pointOfInterestList);
    }
}
