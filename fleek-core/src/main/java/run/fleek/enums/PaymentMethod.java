package run.fleek.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
  CASH_ON_DELIVERY("Cash on Delivery"),
  BANK_TRANSFER("무통장입금"),
  CREDIT_CARD("신용카드"),
  DEBIT_CARD("체크카드"),
  VOUCHER("상품권");

  private final String description;

  PaymentMethod(String description) {
    this.description = description;
  }

}
