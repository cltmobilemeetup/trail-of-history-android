package org.charmeck.trailofhistory.ranger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.charmeck.trailofhistory.ranger.manager.PointOfInterestManager;
import org.charmeck.trailofhistory.ranger.model.PointOfInterest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPointOfInterestActivity extends AppCompatActivity {

    private static String KEY_POI = "pointOfInterest";

    @BindView(R.id.poiname) EditText nameField;

    private PointOfInterest pointOfInterest;

    public static Intent newInstance(Context context) {
        return new Intent(context, NewPointOfInterestActivity.class);
    }

    /**
     * Used for editing POI
     * @param context
     * @param pointOfInterest
     * @return
     */
    public static Intent newInstance(Context context, PointOfInterest pointOfInterest) {
        Intent intent =  new Intent(context, NewPointOfInterestActivity.class);
        intent.putExtra(KEY_POI, pointOfInterest);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_point_of_interest);
        ButterKnife.bind(this);

        PointOfInterest poi = getIntent().getParcelableExtra(KEY_POI);
        if(poi != null){
            setPointOfInterest(poi);
        }
    }

    @OnClick(R.id.saveButton)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                validateAndSave();
                break;
            default:
                break;
        }
    }

    private void validateAndSave(){
        if(validateForm()){
            save();
        }
    }
    private void save(){
        if(pointOfInterest == null){
            pointOfInterest = new PointOfInterest();
        }

        pointOfInterest.setName(nameField.getText().toString());
        pointOfInterest = PointOfInterestManager.getInstance().savePOI(pointOfInterest, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    //go to detail view
                    startActivity(DetailPointOfInterestActivity.newInstance(NewPointOfInterestActivity.this, pointOfInterest));
                    finish();
                }
            }
        });
    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(nameField.getText())){
            nameField.setError(getString(R.string.error_required));
            valid = false;
        }

        return valid;
    }

    private void setPointOfInterest(PointOfInterest pointOfInterest){
        this.pointOfInterest = pointOfInterest;
        nameField.setText(pointOfInterest.getName());
    }


}
