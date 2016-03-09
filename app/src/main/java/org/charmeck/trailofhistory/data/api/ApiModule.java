package org.charmeck.trailofhistory.data.api;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

@Module public final class ApiModule {

  @Provides @Singleton Retrofit providesRetrofit(HttpUrl baseUrl,
      @Named("Api") OkHttpClient client) {
    return new Retrofit.Builder().client(client)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }
}
