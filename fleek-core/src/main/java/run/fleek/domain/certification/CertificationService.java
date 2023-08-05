package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificationService {

  private final CertificationRepository certificationRepository;

  @Transactional(readOnly = true)
  public Optional<Certification> getCertificationByCode(String certificationCode) {
    return certificationRepository.findByCertificationCode(certificationCode);
  }

  @Transactional(readOnly = false)
  public Certification addCertification(Certification certification) {
    return certificationRepository.save(certification);
  }

  @Transactional(readOnly = true)
  public List<Certification> listCertifications() {
    return certificationRepository.findAll();
  }
}
