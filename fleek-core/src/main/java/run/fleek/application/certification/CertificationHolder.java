package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.enums.Certification;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificationHolder {

  private Map<String, Certification> certificationMap;

  @PostConstruct
  public void init() {
    certificationMap = Arrays.stream(Certification.values())
      .collect(Collectors.toMap(Certification::getName, Function.identity()));
  }

  public Certification getCertification(String certificationCode) {
    return certificationMap.get(certificationCode);
  }

}
