package org.charmeck.trailofhistory.data.api;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module(includes = ApiModule.class) public final class ReleaseApiModule {

  @Provides @Singleton HttpUrl provideHttpUrl() {
    //FIXME Where there is a url
    return HttpUrl.parse("http://localhost/");
  }

  @Provides @Singleton @Named("Api") OkHttpClient provideApiClient(OkHttpClient client) {
    //Might need to add interceptor for adding API key header.
    return client;
  }

  @Provides @Singleton TrailOfHistoryService provideTrailOfHistoryService(Retrofit retrofit) {
    return retrofit.create(TrailOfHistoryService.class);
  }
}
