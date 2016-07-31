package org.charmeck.trailofhistory.data;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import org.charmeck.trailofhistory.data.api.ReleaseApiModule;

@Module(includes = { DataModule.class, ReleaseApiModule.class })
public final class ReleaseDataModule {
  @Provides @Singleton OkHttpClient provideOkHttpClient(Application app) {
    return DataModule.createOkHttpClient(app).build();
  }
}
