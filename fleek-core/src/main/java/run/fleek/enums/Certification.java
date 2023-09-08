package run.fleek.enums;

import lombok.Getter;

@Getter
public enum Certification implements FleekEnum {
  FACE("교환사진 인증"),
  COMPANY("회사 인증"),
  COLLEGE("대학교 인증"),
  INBODY("인바디 인증");

  Certification(String description) {
    this.description = description;
  }

  private final String description;

  @Override
  public String getName() {
    return this.name();
  }
}
