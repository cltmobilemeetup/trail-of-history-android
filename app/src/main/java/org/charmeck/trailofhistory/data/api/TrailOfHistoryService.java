package org.charmeck.trailofhistory.data.api;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import rx.Observable;

public interface TrailOfHistoryService {

  @GET("statues") Observable<Result<String>> statues();
}
