package run.fleek.domain.fruitman.meta;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "postal_code_meta", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostalCodeMeta {

  //우편번호|시도|시도영문|시군구|시군구영문|읍면|읍면영문|도로명코드|도로명|도로명영문|지하여부|건물번호본번|건물번호부번|건물관리번호|다량배달처명|시군구용건물명|법정동코드|법정동명|리명|행정동명|산여부|지번본번|읍면동일련번호|지번부번|구우편번호|우편번호일련번호

  @Id
  @Column(name = "postal_code", nullable = false)
  private String postalCode;
  @Column(name = "sido")
  private String sido;
  @Column(name = "sido_eng")
  private String sidoEng;
  @Column(name = "sigungu")
  private String sigungu;
  @Column(name = "sigungu_eng")
  private String sigunguEng;
  @Column(name = "eupmyeon")
  private String eupmyeon;
  @Column(name = "eupmyeon_eng")
  private String eupmyeonEng;
  @Column(name = "road_code")
  private String roadCode;
  @Column(name = "road_name")
  private String roadName;
  @Column(name = "road_name_eng")
  private String roadNameEng;
  @Column(name = "underground_yn")
  private String undergroundYn;
  @Column(name = "building_main_no")
  private String buildingMainNo;
  @Column(name = "building_sub_no")
  private String buildingSubNo;
  @Column(name = "building_manage_no")
  private String buildingManageNo;
  @Column(name = "delivery_name")
  private String deliveryName;
  @Column(name = "sigungu_building_name")
  private String sigunguBuildingName;
  @Column(name = "legal_dong_code")
  private String legalDongCode;
  @Column(name = "legal_dong_name")
  private String legalDongName;
  @Column(name = "ri_name")
  private String riName;
  @Column(name = "administrative_dong_name")
  private String administrativeDongName;
  @Column(name = "mountain_yn")
  private String mountainYn;
  @Column(name = "jibun_main_no")
  private String jibunMainNo;
  @Column(name = "eupmyeon_dong_serial_no")
  private String eupmyeonDongSerialNo;
  @Column(name = "jibun_sub_no")
  private String jibunSubNo;
  @Column(name = "old_postal_code")
  private String oldPostalCode;
  @Column(name = "postal_code_serial_no")
  private String postalCodeSerialNo;
}
