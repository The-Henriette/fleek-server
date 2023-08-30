package run.fleek.enums;

public enum DeliveryMethod {
  DIRECT("직배송"),
  FREE("무료배송");

  DeliveryMethod(String label) {
    this.label = label;
  }

  private final String label;

  public String getLabel() {
    return label;
  }
}
