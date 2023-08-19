package run.fleek.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FleekUserDetailRepository extends JpaRepository<FleekUserDetail, Long> {
  Optional<FleekUserDetail> findByFleekUser(FleekUser user);
}
