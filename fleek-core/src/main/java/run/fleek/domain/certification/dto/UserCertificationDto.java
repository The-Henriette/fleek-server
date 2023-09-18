package run.fleek.domain.certification.dto;

import lombok.*;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserCertification;
import run.fleek.enums.CertificationMethod;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCertificationDto {
  private String certificationType;
  private String certificationDescription;
  private String certificationStatus;
  private String certificationMethod;
  private Boolean rejectRead;
  private String resourceUrl;
  private String resourceContext;
  private String rejectReason;

  public String getResourceContext() {
//    if (certificationMethod.equals(CertificationMethod.PHOTO.name())) {
//
//    }

    return resourceUrl;
  }

  public static UserCertificationDto from(UserCertification userCertification, CertificationResource resource) {
    return UserCertificationDto.builder()
      .certificationType(userCertification.getCertificationCode().name())
      .certificationDescription(userCertification.getCertificationCode().getDescription())
      .certificationStatus(userCertification.getCertificationStatus().name())
      .certificationMethod(userCertification.getCertificationMethod().name())
      .rejectRead(userCertification.getRejectRead())
      .resourceUrl(resource.getResourceUrl())
      .resourceContext(resource.getResourceContext())
      .rejectReason(resource.getRejectReason())
      .build();
  }
}
