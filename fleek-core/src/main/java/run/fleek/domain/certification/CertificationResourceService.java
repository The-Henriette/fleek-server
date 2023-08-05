package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationResourceService {

  private final CertificationResourceRepository certificationResourceRepository;

  @Transactional
  public void addCertificationResources(List<CertificationResource> resources) {
    certificationResourceRepository.saveAll(resources);
  }

  public List<CertificationResource> listCertificationResources(UserCertification userCertification) {
    return certificationResourceRepository.findAllByUserCertification(userCertification);
  }
}
