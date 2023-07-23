package run.fleek.enums;

import run.fleek.common.exception.FleekException;

public enum Gender {
  MALE,
  FEMALE;

  public static Gender of(String gender) {
    try {
      return Gender.valueOf(gender);
    } catch (IllegalArgumentException e) {
      throw new FleekException();
    }
  }

  public Gender getDefaultOrientation() {
    if (this.equals(Gender.MALE)) {
      return FEMALE;
    } else {
      return MALE;
    }
  }
}
