package run.fleek.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FleekUserRepository extends JpaRepository<FleekUser, Long>, FleekUserRepositoryCustom {
  boolean existsByUserName(String userName);
  Optional<FleekUser> findByUserName(String userName);
}
