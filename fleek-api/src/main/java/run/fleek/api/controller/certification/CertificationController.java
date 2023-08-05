package run.fleek.api.controller.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.certification.CertificationApplication;

@RestController
@RequiredArgsConstructor
public class CertificationController {

  private final CertificationApplication certificationApplication;

  @PostMapping("/certification/{userCertificationId}/confirm")
  public void confirmCertification(@PathVariable Long userCertificationId) {
    certificationApplication.confirmCertification(userCertificationId);
  }
}
