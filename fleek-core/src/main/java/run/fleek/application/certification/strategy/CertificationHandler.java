package run.fleek.application.certification.strategy;

import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserCertification;
import run.fleek.enums.Certification;

import java.util.List;

public interface CertificationHandler {
  Certification getCertification();
  void handle(UserCertification userCertification, List<CertificationResource> resources);
  void verify(UserCertification userCertification, List<CertificationResource> resources);
}
