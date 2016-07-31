package org.charmeck.trailofhistory.data.api;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.charmeck.trailofhistory.data.IsMockMode;
import org.charmeck.trailofhistory.data.api.mock.MockTrailOfHistoryService;
import retrofit2.Retrofit;
import timber.log.Timber;

@Module(includes = ApiModule.class) public final class DebugApiModule {

  @Provides @Singleton HttpUrl provideHttpUrl() {
    return HttpUrl.parse("http://localhost/mock/");
  }

  @Provides @Singleton HttpLoggingInterceptor provideLoggingInterceptor() {
    HttpLoggingInterceptor loggingInterceptor =
        new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").v(message));
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    return loggingInterceptor;
  }

  @Provides @Singleton @Named("Api") OkHttpClient provideApiClient(OkHttpClient client,
      HttpLoggingInterceptor loggingInterceptor) {
    return client.newBuilder().addInterceptor(loggingInterceptor).build();
  }

  @Provides @Singleton TrailOfHistoryService provideTrailOfHistoryService(Retrofit retrofit,
      @IsMockMode boolean isMockMode, MockTrailOfHistoryService mockTrailOfHistoryService) {
    return isMockMode ? mockTrailOfHistoryService : retrofit.create(TrailOfHistoryService.class);
  }
}

