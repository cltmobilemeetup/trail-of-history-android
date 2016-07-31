package org.charmeck.trailofhistory.ranger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.charmeck.trailofhistory.ranger.model.PointOfInterest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPointOfInterestActivity extends AppCompatActivity {

    private static String KEY_POI = "pointOfInterest";

    private PointOfInterest pointOfInterest;

    @BindView(R.id.nameField) TextView nameField;

    public static Intent newInstance(Context context, PointOfInterest pointOfInterest){
        Intent intent =  new Intent(context, DetailPointOfInterestActivity.class);
        intent.putExtra(KEY_POI, pointOfInterest);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_point_of_interest);
        ButterKnife.bind(this);

        PointOfInterest poi = getIntent().getParcelableExtra(KEY_POI);
        if(poi != null){
            setPointOfInterest(poi);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                edit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void edit(){
        if(pointOfInterest != null){
            startActivity(NewPointOfInterestActivity.newInstance(this, pointOfInterest));
        }
    }

    private void setPointOfInterest(PointOfInterest pointOfInterest){
        this.pointOfInterest = pointOfInterest;
        nameField.setText(pointOfInterest.getName());
    }
}
