package org.charmeck.trailofhistory;

import android.app.Application;
import android.content.Context;
import timber.log.Timber;

public class TrailOfHistoryApp extends Application {
  private TrailOfHistoryComponent component;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    buildComponentAndInject();
  }

  private void buildComponentAndInject() {
    component = TrailOfHistoryComponent.Initializer.init(this);
    component.inject(this);
  }

  public TrailOfHistoryComponent component() {
    return component;
  }

  public static TrailOfHistoryApp get(Context context) {
    return (TrailOfHistoryApp) context.getApplicationContext();
  }
}
