package org.charmeck.trailofhistory.data.api;

import java.util.List;
import org.charmeck.trailofhistory.model.PointOfInterest;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import rx.Observable;

public interface TrailOfHistoryService {

  @GET("statues") Observable<Result<List<PointOfInterest>>> statues();
}
