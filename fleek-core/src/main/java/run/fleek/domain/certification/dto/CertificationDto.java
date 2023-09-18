package run.fleek.domain.certification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDto {
  private String certificationCode;
  private String certificationName;
  private String certificationDescription;
  private String certificationStatus;
  private Boolean rejectRead;
}
