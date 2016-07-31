package org.charmeck.trailofhistory;

import javax.inject.Scope;

@Scope public @interface SingleIn {
  Class<?> value();
}
