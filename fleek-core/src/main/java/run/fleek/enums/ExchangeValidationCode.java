package run.fleek.enums;

import lombok.Getter;

@Getter
public enum ExchangeValidationCode implements FleekEnum {
  NOT_CERTIFIED("사진 인증을 먼저 진행해주세요"),
  PENDING_CERTIFICATION("회원님의 사진 본인 인증을 진행하고 있습니다");

  ExchangeValidationCode(String description) {
    this.description = description;
  }

  private final String description;

  @Override
  public String getName() {
    return this.name();
  }
}
