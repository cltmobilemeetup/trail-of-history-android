package org.charmeck.trailofhistory;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class TrailOfHistoryModule {
  private final TrailOfHistoryApp app;

  public TrailOfHistoryModule(TrailOfHistoryApp app) {
    this.app = app;
  }

  @Provides @Singleton Application providesApplication() {
    return app;
  }
}
