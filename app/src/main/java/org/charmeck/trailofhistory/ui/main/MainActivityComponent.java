package org.charmeck.trailofhistory.ui.main;

import dagger.Component;
import org.charmeck.trailofhistory.SingleIn;
import org.charmeck.trailofhistory.TrailOfHistoryComponent;

@SingleIn(MainActivityComponent.class)
@Component(dependencies = TrailOfHistoryComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
  void inject(MainActivity activity);
}
