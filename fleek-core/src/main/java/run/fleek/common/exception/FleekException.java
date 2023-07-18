package run.fleek.common.exception;

import java.util.function.Supplier;

public class FleekException extends RuntimeException implements Supplier<RuntimeException> {
  public FleekException() {
    super();
  }

  public FleekException(String message) {
    super(message);
  }

  public FleekException(String message, Throwable e) {
    super(message, e);
  }

  public FleekException(String message, Object... args) {
    super(String.format(message, args));
  }

  @Override
  public RuntimeException get() {
    return this;
  }

}
