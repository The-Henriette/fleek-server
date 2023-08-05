package run.fleek.domain.certification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationResourceRepository extends JpaRepository<CertificationResource, Long> {
  List<CertificationResource> findAllByUserCertification(UserCertification userCertification);
}
