package org.charmeck.trailofhistory;

import dagger.Component;
import javax.inject.Singleton;
import org.charmeck.trailofhistory.data.ReleaseDataModule;

@Singleton @Component(modules = { TrailOfHistoryModule.class, ReleaseDataModule.class })
public interface TrailOfHistoryComponent extends TrailOfHistoryGraph {
  final static class Initializer {
    static TrailOfHistoryComponent init(TrailOfHistoryApp app) {
      return DaggerTrailOfHistoryComponent.builder()
          .trailOfHistoryModule(new TrailOfHistoryModule(app))
          .build();
    }

    private Initializer() {
    }
  }
}
