package run.fleek.enums;

import lombok.Getter;

@Getter
public enum PurchaseOption implements FleekEnum {

  TEAM(1, "팀 구매", "가능지역 확인 필수", "팀 구매 성공 이후 1일 이내, 직배송"),
  ALONE(2, "혼자 구매", null, "주문일 익일 발송, CJ 대한통운");

  private final Integer order;
  private final String name;
  private final String requirements;
  private final String description;

  PurchaseOption(Integer order, String name, String requirements, String description) {
    this.order = order;
    this.name = name;
    this.requirements = requirements;
    this.description = description;
  }

  @Override
  public String getName() {
    return this.name;
  }

}
