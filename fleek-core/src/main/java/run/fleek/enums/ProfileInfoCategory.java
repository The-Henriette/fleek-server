package run.fleek.enums;

import lombok.Getter;

@Getter
public enum ProfileInfoCategory implements FleekEnum {
  BASIC("나에 대한 정보", 1),
  LIFESTYLE("라이프스타일", 2),
  DATING_STYLE("연애스타일", 3),
  CHARACTERISTICS("나만의 특징", 4);


  private final String description;
  private final int order;

  public String getName() {
    return this.name();
  }

  ProfileInfoCategory(String description, int order) {
    this.description = description;
    this.order = order;
  }

}
