package org.charmeck.trailofhistory.data.api;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public final class ApiModule {

  @Provides @Singleton Retrofit providesRetrofit(HttpUrl baseUrl, @Named("Api") OkHttpClient client,
      Gson gson) {
    return new Retrofit.Builder().client(client)
        .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }
}
