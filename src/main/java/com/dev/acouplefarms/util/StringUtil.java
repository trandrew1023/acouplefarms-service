package com.dev.acouplefarms.util;

public class StringUtil {

  /**
   * Scrubs string to save as KEY in database.
   *
   * @return scrubbed {@link String}
   */
  public static String scrubString(final String stringToScrub) {
    return stringToScrub.replaceAll("\\s", "").toUpperCase();
  }
}
