package run.fleek.domain.users.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
  Optional<UserAuth> getUserAuthByFleekUser_FleekUserId(Long fleekUserId);
  Optional<UserAuth> getUserAuthByRefreshToken(String refreshToken);
}
