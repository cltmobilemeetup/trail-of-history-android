package org.charmeck.trailofhistory;

import org.charmeck.trailofhistory.data.api.TrailOfHistoryService;

public interface TrailOfHistoryGraph {
  void inject(TrailOfHistoryApp aoo);

  TrailOfHistoryService trailOfHistoryService();
}
