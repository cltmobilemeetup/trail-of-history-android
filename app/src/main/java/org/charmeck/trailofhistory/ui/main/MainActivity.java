package org.charmeck.trailofhistory.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import javax.inject.Inject;
import org.charmeck.trailofhistory.R;
import org.charmeck.trailofhistory.TrailOfHistoryApp;
import org.charmeck.trailofhistory.data.api.Results;
import org.charmeck.trailofhistory.data.api.TrailOfHistoryService;
import org.charmeck.trailofhistory.poi.PointOfInterestAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.recyclerView) RecyclerView recyclerView;
  PointOfInterestAdapter poiAdapter;

  @Inject TrailOfHistoryService tohService;

  CompositeSubscription subscriptions;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerMainActivityComponent.builder()
        .trailOfHistoryComponent(TrailOfHistoryApp.get(this).component())
        .build()
        .inject(this);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    poiAdapter = new PointOfInterestAdapter(new ArrayList<>());
    recyclerView.setAdapter(poiAdapter);

    subscriptions = new CompositeSubscription();

    fetchStatues();
  }

  public void fetchStatues() {
    final ProgressDialog progressDialog =
        ProgressDialog.show(this, "Fetching Statues", "Loading...");

    subscriptions.add(tohService.statues()
        .filter(Results.isSuccess())
        .map(stringResult -> stringResult.response().body())
        .flatMapIterable(pointOfInterests -> pointOfInterests)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(pointOfInterest -> poiAdapter.addPointOfIntrest(pointOfInterest), //
            throwable -> {
              throw new RuntimeException(throwable);
            }, () -> {
              progressDialog.dismiss();
              poiAdapter.notifyDataSetChanged();
            }));
  }

  @Override protected void onDestroy() {
    if (subscriptions != null) {
      subscriptions.unsubscribe();
    }
    super.onDestroy();
  }
}
