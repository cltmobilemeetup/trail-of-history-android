package org.charmeck.trailofhistory.data;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import org.charmeck.trailofhistory.data.api.ApiModule;

@Module(includes = ApiModule.class) public class DataModule {
  static final int DICK_CACHE_SIZE = 50 * 1024 * 1024; //50MB

  @Provides @Singleton Gson providesGson() {
    return new GsonBuilder().create();
  }

  static OkHttpClient.Builder createOkHttpClient(Application app) {
    File cacheDir = new File(app.getCacheDir(), "http");
    Cache cache = new Cache(cacheDir, DICK_CACHE_SIZE);

    return new OkHttpClient.Builder().cache(cache);
  }
}
