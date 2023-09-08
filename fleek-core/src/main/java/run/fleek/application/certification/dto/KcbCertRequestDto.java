package run.fleek.application.certification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KcbCertRequestDto {

  @JsonProperty("NAME")
  private String name;

  @JsonProperty("BIRTHDAY")
  private String birthday; // YYYYMMDD

  @JsonProperty("SEX_CD")
  private String sex; // M , F

  @JsonProperty("NTV_FRNR_CD")
  private String nationality; // L, F

  @JsonProperty("TEL_COM_CD")
  private String agency; // 02: KT

  @JsonProperty("TEL_NO")
  private String phoneNumber;

  @JsonProperty("SITE_URL")
  private String siteUrl = "https://www.fleek.run";

  @JsonProperty("SITE_NAME")
  private String siteName = "플릭";

  @JsonProperty("RQST_CAUS_CD")
  private String requestCause = "00";

  @JsonProperty("AGREE1")
  private String agree1 = "Y";

  @JsonProperty("AGREE2")
  private String agree2 = "Y";

  @JsonProperty("AGREE3")
  private String agree3 = "Y";

  @JsonProperty("AGREE4")
  private String agree4 = "Y";
}
