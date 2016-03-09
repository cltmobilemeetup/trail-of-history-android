package org.charmeck.trailofhistory.data.api.mock;

import android.app.Application;
import android.content.Context;
import javax.inject.Inject;
import org.charmeck.trailofhistory.R;
import org.charmeck.trailofhistory.Utils;
import org.charmeck.trailofhistory.data.api.TrailOfHistoryService;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;

public class MockTrailOfHistoryService implements TrailOfHistoryService {

  private Context context;

  @Inject MockTrailOfHistoryService(Application application) {
    context = application.getApplicationContext();
  }

  @Override public Observable<Result<String>> statues() {
    String response = Utils.rawResouseAsString(context, R.raw.sample_statue_response);
    return Observable.just(Result.response(Response.success(response)));
  }
}
