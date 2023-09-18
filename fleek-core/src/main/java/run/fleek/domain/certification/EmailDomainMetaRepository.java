package run.fleek.domain.certification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.enums.Certification;

import java.util.Optional;

@Repository
public interface EmailDomainMetaRepository extends JpaRepository<EmailDomainMeta, Long> {
  Optional<EmailDomainMeta> findByEmailDomainAndCertificationCode(String emailDomain, Certification certificationCode);
}
