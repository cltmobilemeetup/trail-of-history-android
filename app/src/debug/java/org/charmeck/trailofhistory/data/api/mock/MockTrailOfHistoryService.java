package org.charmeck.trailofhistory.data.api.mock;

import android.app.Application;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;
import org.charmeck.trailofhistory.R;
import org.charmeck.trailofhistory.Utils;
import org.charmeck.trailofhistory.data.api.TrailOfHistoryService;
import org.charmeck.trailofhistory.model.PointOfInterest;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;

public class MockTrailOfHistoryService implements TrailOfHistoryService {

  private Context context;
  private Gson gson;

  @Inject MockTrailOfHistoryService(Application application, Gson gson) {
    context = application.getApplicationContext();
    this.gson = gson;
  }

  @Override public Observable<Result<List<PointOfInterest>>> statues() {
    String response = Utils.rawResouseAsString(context, R.raw.sample_statue_response);
    List<PointOfInterest> pois =
        gson.fromJson(response, (Type) new TypeToken<List<PointOfInterest>>() {
        }.getType());
    return Observable.just(Result.response(Response.success(pois)));
  }
}
