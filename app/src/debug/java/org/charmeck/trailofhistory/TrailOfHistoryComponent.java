package org.charmeck.trailofhistory;

import dagger.Component;
import javax.inject.Singleton;
import org.charmeck.trailofhistory.data.DebugDataModule;

@Singleton @Component(modules = { TrailOfHistoryModule.class, DebugDataModule.class })
public interface TrailOfHistoryComponent extends DebugTrailOfHistoryGraph {
  final class Initializer {
    static org.charmeck.trailofhistory.TrailOfHistoryComponent init(TrailOfHistoryApp app) {
      return DaggerTrailOfHistoryComponent.builder()
          .trailOfHistoryModule(new TrailOfHistoryModule(app))
          .build();
    }

    private Initializer() {

    }
  }
}
