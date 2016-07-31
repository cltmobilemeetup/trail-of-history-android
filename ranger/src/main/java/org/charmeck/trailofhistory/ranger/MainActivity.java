package org.charmeck.trailofhistory.ranger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.google.firebase.auth.FirebaseAuth;

import org.charmeck.trailofhistory.ranger.model.PointOfInterest;
import org.charmeck.trailofhistory.ranger.adapter.PointOfInterestAdapter;
import org.charmeck.trailofhistory.ranger.manager.PointOfInterestManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Lists all points of interest currently on the trail of history
 */
public class MainActivity extends AuthenticatedActivity implements PointOfInterestManager.PointOfInterestListCallback{

    private static final String TAG = "MainActivity";

    @BindView(R.id.poiList) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PointOfInterestAdapter mAdapter;

    public static Intent newInstance(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.simple_divider);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
    }

    @Override
    protected void onResume() {
        super.onResume();

        showProgressDialog();
        PointOfInterestManager.getInstance().getPointsofInterest(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                signOut();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                createPOI();
                break;
            default:
                break;
        }
    }

    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    private void createPOI(){
        startActivity(NewPointOfInterestActivity.newInstance(this));
    }

    @Override
    public void pointsOfInterestRetrieved(List<PointOfInterest> pointOfInterestList) {
        hideProgressDialog();
        if(pointOfInterestList != null){
            mAdapter = new PointOfInterestAdapter(pointOfInterestList);
            mRecyclerView.setAdapter(mAdapter);
        }

    }
}
