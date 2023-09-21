package run.fleek.enums;

import com.google.common.collect.ImmutableSet;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;
import lombok.Getter;

import java.util.Set;

@Getter
public enum DealTrackingStatus implements FleekEnum {
  PENDING("결제 대기"),
  PAID("결제 완료"),
  CANCELLED("결제 취소"),
  TEAM_PURCHASE_PENDING("팀 구매 성사 대기"),
  TEAM_PURCHASE_FAILED("팀 구매 성사 실패"),
  TEAM_PURCHASE_SUCCESS("팀 구매 성사 완료"),
  ON_THE_WAY("배송중"),
  DELIVERY_COMPLETED("배송완료")
  ;

  private final String description;

  DealTrackingStatus(String description) {
    this.description = description;
  }

  public static final Set<DealTrackingStatus> COUNTABLE_STATES = ImmutableSet.of(
    PAID, TEAM_PURCHASE_PENDING, TEAM_PURCHASE_FAILED, TEAM_PURCHASE_SUCCESS, ON_THE_WAY, DELIVERY_COMPLETED
  );

  public static final Set<DealTrackingStatus> VISIBLE_STATES = ImmutableSet.of(
    PAID, CANCELLED, TEAM_PURCHASE_PENDING, TEAM_PURCHASE_FAILED, TEAM_PURCHASE_SUCCESS, ON_THE_WAY, DELIVERY_COMPLETED
  );

  public static final Set<DealTrackingStatus> REFUNDABLE_STATES = ImmutableSet.of(
    PENDING, PAID, TEAM_PURCHASE_PENDING, TEAM_PURCHASE_FAILED
  );

  public static final Set<DealTrackingStatus> PAID_STATES = ImmutableSet.of(
    PAID, TEAM_PURCHASE_PENDING, TEAM_PURCHASE_FAILED
  );

  @Override
  public String getName() {
    return name();
  }
}
