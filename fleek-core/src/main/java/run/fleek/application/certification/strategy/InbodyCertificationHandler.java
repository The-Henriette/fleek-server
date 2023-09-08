package run.fleek.application.certification.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserCertification;
import run.fleek.enums.Certification;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InbodyCertificationHandler implements CertificationHandler {

  @Override
  public Certification getCertification() {
    return Certification.INBODY;
  }

  @Override
  public void handle(UserCertification userCertification, List<CertificationResource> resources) {

  }
}
