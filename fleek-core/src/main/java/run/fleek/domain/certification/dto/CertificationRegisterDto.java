package run.fleek.domain.certification.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRegisterDto {
  private String certificationCode;
  private String certificationMethod;
  private List<CertificationResourceDto> resources;
}
