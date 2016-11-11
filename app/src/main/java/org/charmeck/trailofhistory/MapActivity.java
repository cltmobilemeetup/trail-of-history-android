package org.charmeck.trailofhistory;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import org.charmeck.trailofhistory.core.model.PointOfInterest;
import timber.log.Timber;

/**
 * MapActivity to show the trail of history with a google map
 *
 * Created by Chris on 10/24/16.
 */
public class MapActivity extends AppCompatActivity
    implements OnMapReadyCallback, OnMarkerClickListener {

  private static final String TAG = MapActivity.class.getSimpleName();
  private static final String SELECTED_INDEX_KEY = "SELECTED_INDEX_KEY";
  private static final String POI_LIST_KEY = "POI_LIST_KEY";
  private GoogleMap map = null;
  private DatabaseReference databaseReference;
  private ArrayList<PointOfInterest> poiList = new ArrayList<>();
  private PoiPagerAdapter adapter = null;
  private List<Marker> markers = new ArrayList<>();
  private int savedIndex = -1;
  private ViewPager.OnPageChangeListener onPageChangeListener =
      new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override public void onPageSelected(int position) {
          selectMapPin(position);
        }

        @Override public void onPageScrollStateChanged(int state) { }
      };

  @BindView(R.id.view_pager) ViewPager viewPager;

  @BindView(R.id.toolbar) Toolbar toolbar;

  /***************************************
   * Lifecycle Methods
   ***************************************/

  @Override protected void onCreate(Bundle savedInstanceState) {
    Timber.tag(TAG).d("onCreate");
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_map);

    loadSavedInstanceState(savedInstanceState);
    databaseReference = FirebaseDatabase.getInstance().getReference();

    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    Timber.tag(TAG).d("onCreateOptionsMenu");
    getMenuInflater().inflate(R.menu.menu_map_view, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    Timber.tag(TAG).d("onOptionsItemSelected");
    switch (item.getItemId()) {
      case R.id.option_view:
        onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    Timber.tag(TAG).d("onBackPressed");
    finish();
  }

  @Override protected void onStart() {
    Timber.tag(TAG).d("onStart");
    super.onStart();
    viewPager.addOnPageChangeListener(onPageChangeListener);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    Timber.tag(TAG).d("onSaveInstanceState");
    if (poiList != null && poiList.size() > 0) {
      outState.putParcelableArrayList(POI_LIST_KEY, poiList);
    }
    outState.putInt(SELECTED_INDEX_KEY, viewPager.getCurrentItem());
    super.onSaveInstanceState(outState);
  }

  @Override protected void onStop() {
    Timber.tag(TAG).d("onStop");
    viewPager.removeOnPageChangeListener(onPageChangeListener);
    super.onStop();
  }

  @Override protected void onDestroy() {
    Timber.tag(TAG).d("onDestroy");
    map.setOnMarkerClickListener(null);
    map = null;
    databaseReference = null;
    adapter = null;
    markers.clear();
    poiList.clear();
    super.onDestroy();
  }

  /***************************************
   * Event Methods
   ***************************************/

  @Override public void onMapReady(GoogleMap googleMap) {
    Timber.tag(TAG).d("onMapReady");
    map = googleMap;
    map.setOnMarkerClickListener(this);
    if (poiList == null || poiList.size() == 0) {
      fetchStatues();
    } else {
      setupPoi();
    }
    styleMap(googleMap);
  }

  @Override public boolean onMarkerClick(Marker marker) {
    Timber.tag(TAG).d("On Marker Click: %d", (int) marker.getTag());
    viewPager.setCurrentItem((int) marker.getTag(), true);
    return true;
  }

  /***************************************
   * Data Methods
   ***************************************/

  private void fetchStatues() {
    Timber.tag(TAG).d("fetchStatues");
    final ProgressDialog progressDialog =
        ProgressDialog.show(this, "Fetching Statues", "Loading...");
    databaseReference.child("pointOfInterest")
        .addListenerForSingleValueEvent(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot poiSnapshot : dataSnapshot.getChildren()) {
              PointOfInterest poi = poiSnapshot.getValue(PointOfInterest.class);
              poiList.add(poi);
            }
            setupPoi();
            progressDialog.dismiss();
          }

          @Override public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();
          }
        });
  }

  /***************************************
   * Helper Methods
   ***************************************/

  private void selectMapPin(int index) {
    Timber.tag(TAG).d("selectMapPin");
    deselectMapPins();
    Marker marker = markers.get(index);
    BitmapDescriptor descriptor =
        BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin_selected);
    marker.setIcon(descriptor);
    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15);
    map.animateCamera(update);
  }

  private void deselectMapPins() {
    Timber.tag(TAG).d("deselectMapPins");
    BitmapDescriptor descriptor =
        BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin_normal);
    for (Marker marker : markers) {
      marker.setIcon(descriptor);
    }
  }

  /***************************************
   * UI Setup Methods
   ***************************************/

  private void loadSavedInstanceState(Bundle savedInstanceState) {
    Timber.tag(TAG).d("loadSavedInstanceState");
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(SELECTED_INDEX_KEY)) {
        savedIndex = savedInstanceState.getInt(SELECTED_INDEX_KEY);
      }
      if (savedInstanceState.containsKey(POI_LIST_KEY)) {
        poiList = savedInstanceState.getParcelableArrayList(POI_LIST_KEY);
      }
    }
  }

  private void styleMap(GoogleMap googleMap) {
    Timber.tag(TAG).d("styleMap");
    try {
      boolean success =
          googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

      if (!success) {
        Timber.e("Style parsing failed.");
      }
    } catch (Resources.NotFoundException e) {
      Timber.e("Can't find style.", e);
    }
  }

  private void setupPoi() {
    Timber.tag(TAG).d("setupPoi");
    addMarkers();
    setupViewPager();
  }

  private void addMarkers() {
    Timber.tag(TAG).d("addMarkers");
    for (int i = 0; i < poiList.size(); i++) {
      addMarker(poiList.get(i), i);
    }
  }

  private void addMarker(PointOfInterest poi, int index) {
    Timber.tag(TAG).d("addMarker");
    LatLng latLng = new LatLng(poi.getLatitude(), poi.getLongitude());
    BitmapDescriptor pin = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin_normal);
    Marker marker =
        map.addMarker(new MarkerOptions().position(latLng).title(poi.getName()).icon(pin));
    marker.setTag(index);
    markers.add(marker);
  }

  private void setupViewPager() {
    Timber.tag(TAG).d("setupViewPager");
    FragmentManager fm = getSupportFragmentManager();
    if (adapter == null) {
      adapter = new PoiPagerAdapter(fm, poiList);
    }
    viewPager.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    if (savedIndex != -1 && savedIndex < poiList.size()) {
      selectMapPin(savedIndex);
      savedIndex = -1;
    } else if (poiList.size() > 0) {
      selectMapPin(0);
    }
  }
}
