package run.fleek.common.constants;

public final class Constants {

  public static final String[] publicPathAntPatterns = {
    "/error**", "/hello", "/auth/sign-up", "/auth/sign-in", "/auth/username/validate", "/profile/{profileId}/detail",
    "/favicon.ico", "/auth/login", "/auth/verify", "/auth/refresh", "/dev/session"
  };

  public static final class Auth {
    public static final long ACCESS_TOKEN_TTL_MILLISECOND = 240000L; // 5 minutes
    public static final long REFRESH_TOKEN_TTL_MILLISECOND = 10368000000L; // 120 Days
  }
}
