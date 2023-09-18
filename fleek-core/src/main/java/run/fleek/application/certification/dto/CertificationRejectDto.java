package run.fleek.application.certification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRejectDto {
  private String rejectReason;
  private String rejectReasonDetail;
}
