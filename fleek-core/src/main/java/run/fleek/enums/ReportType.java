package run.fleek.enums;

import lombok.Getter;

@Getter
public enum ReportType {
  PROFILE("profile"),
  POST("post"),
  COMMENT("comment"),
  MESSAGE("message"),
  ;

  private final String description;

  ReportType(String description) {
    this.description = description;
  }
}
