package run.fleek.utils;

public final class EmailUtil {
  public static String extractDomainFromEmail(String email) {
    if (email == null || !email.contains("@")) {
      return null; // or throw an IllegalArgumentException
    }
    return email.substring(email.indexOf("@") + 1);
  }
}
