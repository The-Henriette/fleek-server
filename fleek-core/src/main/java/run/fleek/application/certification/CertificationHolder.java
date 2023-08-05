package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.domain.certification.Certification;
import run.fleek.domain.certification.CertificationService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificationHolder {

  private final CertificationService certificationService;
  private Map<String, Certification> certificationMap;

//  @PostConstruct
//  public void init() {
//    certificationMap = certificationService.listCertifications().stream()
//      .collect(Collectors.toMap(Certification::getCertificationCode, Function.identity()));
//  }

  public Certification getCertification(String certificationCode) {
    return certificationMap.get(certificationCode);
  }

}
