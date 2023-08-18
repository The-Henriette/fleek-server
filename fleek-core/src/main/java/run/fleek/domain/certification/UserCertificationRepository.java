package run.fleek.domain.certification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.users.FleekUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCertificationRepository extends JpaRepository<UserCertification, Long> {
  List<UserCertification> findAllByFleekUser_FleekUserIdAndActiveIsTrue(Long userId);

  boolean existsByFleekUser_FleekUserIdAndActiveIsTrueAndCertificationCode(Long fleekUserId, String certificationCode);

  Optional<UserCertification> findByFleekUserAndCertificationCodeAndActiveIsTrue(FleekUser fleekUser, String certificationCode);
}
