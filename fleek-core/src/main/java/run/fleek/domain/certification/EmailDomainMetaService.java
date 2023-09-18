package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.fleek.enums.Certification;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailDomainMetaService {

  private final EmailDomainMetaRepository emailDomainMetaRepository;

  public Optional<EmailDomainMeta> getByDomainAndCertificationCode(String emailDomain, Certification certificationCode) {
    return emailDomainMetaRepository.findByEmailDomainAndCertificationCode(emailDomain, certificationCode);
  }
}
