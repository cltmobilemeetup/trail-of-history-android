package org.charmeck.trailofhistory.data;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import org.charmeck.trailofhistory.data.api.DebugApiModule;

@Module(includes = { DataModule.class, DebugApiModule.class }) public final class DebugDataModule {

  @Provides @Singleton OkHttpClient provideOkHttpClient(Application app) {
    return DataModule.createOkHttpClient(app).build();
  }

  @Provides @Singleton @IsMockMode boolean provideIsMockMode() {
    return true;
  }
}
