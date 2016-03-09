package org.charmeck.trailofhistory;

import android.content.Context;
import android.support.annotation.RawRes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

  private Utils() {
  }

  public static final String rawResouseAsString(Context context, @RawRes int resId) {
    InputStream inputStream = context.getResources().openRawResource(resId);
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder builder = new StringBuilder();
    String l;
    try {

      while ((l = reader.readLine()) != null) {
        builder.append(l).append('\n');
      }
    } catch (IOException e) {

    } finally {
      try {
        reader.close();
      } catch (IOException e) {
      }
    }
    return builder.toString();
  }
}
