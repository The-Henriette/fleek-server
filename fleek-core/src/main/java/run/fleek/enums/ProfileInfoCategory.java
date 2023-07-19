package run.fleek.enums;

import lombok.Getter;

@Getter
public enum ProfileInfoCategory implements FleekEnum {
  BASIC("나에 대한 정보"),
  LIFESTYLE("라이프스타일"),
  DATING_STYLE("연애스타일");


  private final String description;

  public String getName() {
    return this.name();
  }

  ProfileInfoCategory(String description) {
    this.description = description;
  }

}
