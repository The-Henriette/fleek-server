package run.fleek.utils;

public final class FleekStringUtils {
  public static String truncateString(String input, int maxLength) {
    if (input == null) {
      return null;
    }

    if (input.length() <= maxLength) {
      return input;
    }

    return input.substring(0, maxLength);
  }

}
