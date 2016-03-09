package org.charmeck.trailofhistory.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jakewharton.rxbinding.widget.RxTextView;
import javax.inject.Inject;
import org.charmeck.trailofhistory.R;
import org.charmeck.trailofhistory.TrailOfHistoryApp;
import org.charmeck.trailofhistory.data.api.Results;
import org.charmeck.trailofhistory.data.api.TrailOfHistoryService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.tv_results) TextView resultsView;

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

    subscriptions = new CompositeSubscription();
  }

  @OnClick(R.id.btn_requestData) public void fetchStatues() {
    tohService.statues()
        .filter(Results.isSuccess())
        .map(stringResult -> stringResult.response().body())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(RxTextView.text(resultsView), throwable -> {
          throw new RuntimeException(throwable);
        });
  }

  @Override protected void onDestroy() {
    if (subscriptions != null) {
      subscriptions.unsubscribe();
    }
    super.onDestroy();
  }
}
