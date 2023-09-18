package run.fleek.domain.certification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
  boolean existsByFleekUserAndCertificationCode(FleekUser fleekUser, Certification certificationCode);

  List<UserBadge> findAllByFleekUser_FleekUserId(Long userId);
}
