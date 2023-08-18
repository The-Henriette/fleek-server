package run.fleek.enums;


import lombok.Getter;

@Getter
public enum ExchangeFailureCode implements FleekEnum {
  NOT_ENOUGH_BALANCE("잔액이 부족합니다."),
  ALREADY_WATCHED("서로의 안전을 위해 단 한 번만 사진을 볼 수 있습니다.");


  ExchangeFailureCode(String description) {
    this.description = description;
  }

  private final String description;

  public String getName() {
    return this.name();
  }
}
