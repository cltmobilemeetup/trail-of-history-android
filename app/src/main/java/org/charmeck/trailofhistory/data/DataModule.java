package org.charmeck.trailofhistory.data;

import android.app.Application;
import dagger.Module;
import java.io.File;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import org.charmeck.trailofhistory.data.api.ApiModule;

@Module(includes = ApiModule.class) public class DataModule {
  static final int DICK_CACHE_SIZE = 50 * 1024 * 1024; //50MB

  static OkHttpClient.Builder createOkHttpClient(Application app) {
    File cacheDir = new File(app.getCacheDir(), "http");
    Cache cache = new Cache(cacheDir, DICK_CACHE_SIZE);

    return new OkHttpClient.Builder().cache(cache);
  }
}
