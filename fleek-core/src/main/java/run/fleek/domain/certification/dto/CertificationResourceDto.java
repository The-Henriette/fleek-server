package run.fleek.domain.certification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResourceDto {
  private String resourceUrl;
  private String resourceContext;
}
