package run.fleek.domain.fruitman.meta.dto;

import lombok.*;
import run.fleek.domain.fruitman.meta.PostalCodeMeta;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostalCodeMetaDto {

  private String postalCode;
  private String sido;
  private String sidoEng;
  private String sigungu;
  private String sigunguEng;
  private String eupmyeon;
  private String eupmyeonEng;
  private String roadCode;
  private String roadName;
  private String roadNameEng;
  private String undergroundYn;
  private String buildingMainNo;
  private String buildingSubNo;
  private String buildingManageNo;
  private String deliveryName;
  private String sigunguBuildingName;
  private String legalDongCode;
  private String legalDongName;
  private String riName;
  private String administrativeDongName;
  private String mountainYn;
  private String jibunMainNo;
  private String eupmyeonDongSerialNo;
  private String jibunSubNo;
  private String oldPostalCode;
  private String postalCodeSerialNo;

  public static PostalCodeMetaDto from(PostalCodeMeta meta) {
    return PostalCodeMetaDto.builder()
      .postalCode(meta.getPostalCode())
      .sido(meta.getSido())
      .sidoEng(meta.getSidoEng())
      .sigungu(meta.getSigungu())
      .sigunguEng(meta.getSigunguEng())
      .eupmyeon(meta.getEupmyeon())
      .eupmyeonEng(meta.getEupmyeonEng())
      .roadCode(meta.getRoadCode())
      .roadName(meta.getRoadName())
      .roadNameEng(meta.getRoadNameEng())
      .undergroundYn(meta.getUndergroundYn())
      .buildingMainNo(meta.getBuildingMainNo())
      .buildingSubNo(meta.getBuildingSubNo())
      .buildingManageNo(meta.getBuildingManageNo())
      .deliveryName(meta.getDeliveryName())
      .sigunguBuildingName(meta.getSigunguBuildingName())
      .legalDongCode(meta.getLegalDongCode())
      .legalDongName(meta.getLegalDongName())
      .riName(meta.getRiName())
      .administrativeDongName(meta.getAdministrativeDongName())
      .mountainYn(meta.getMountainYn())
      .jibunMainNo(meta.getJibunMainNo())
      .eupmyeonDongSerialNo(meta.getEupmyeonDongSerialNo())
      .jibunSubNo(meta.getJibunSubNo())
      .oldPostalCode(meta.getOldPostalCode())
      .postalCodeSerialNo(meta.getPostalCodeSerialNo())
      .build();
  }

  public PostalCodeMeta to() {
    return PostalCodeMeta.builder()
      .postalCode(this.getPostalCode())
      .sido(this.getSido())
      .sidoEng(this.getSidoEng())
      .sigungu(this.getSigungu())
      .sigunguEng(this.getSigunguEng())
      .eupmyeon(this.getEupmyeon())
      .eupmyeonEng(this.getEupmyeonEng())
      .roadCode(this.getRoadCode())
      .roadName(this.getRoadName())
      .roadNameEng(this.getRoadNameEng())
      .undergroundYn(this.getUndergroundYn())
      .buildingMainNo(this.getBuildingMainNo())
      .buildingSubNo(this.getBuildingSubNo())
      .buildingManageNo(this.getBuildingManageNo())
      .deliveryName(this.getDeliveryName())
      .sigunguBuildingName(this.getSigunguBuildingName())
      .legalDongCode(this.getLegalDongCode())
      .legalDongName(this.getLegalDongName())
      .riName(this.getRiName())
      .administrativeDongName(this.getAdministrativeDongName())
      .mountainYn(this.getMountainYn())
      .jibunMainNo(this.getJibunMainNo())
      .eupmyeonDongSerialNo(this.getEupmyeonDongSerialNo())
      .jibunSubNo(this.getJibunSubNo())
      .oldPostalCode(this.getOldPostalCode())
      .postalCodeSerialNo(this.getPostalCodeSerialNo())
      .build();
  }

}
