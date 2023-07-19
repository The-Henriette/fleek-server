package run.fleek.enums;

import lombok.Getter;

@Getter
public enum UserStatus implements FleekEnum {
  SIGNING_UP("Signing up"),
  ACTIVE("Active"),
  INACTIVE("Inactive"),
  DELETED("Deleted");

  private final String description;

  public String getName() {
    return this.name();
  }

  UserStatus(String description) {
    this.description = description;
  }
}
