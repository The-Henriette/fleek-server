package run.fleek.domain.certification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResourceDto {
  private String resourceUrl; // 증빙서류
  private String resourceContext;
}
