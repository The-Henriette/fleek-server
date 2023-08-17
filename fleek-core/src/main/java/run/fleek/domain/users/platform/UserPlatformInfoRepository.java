package run.fleek.domain.users.platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.users.FleekUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPlatformInfoRepository extends JpaRepository<UserPlatformInfo, Long> {
  Optional<UserPlatformInfo> findByPushToken(String previousPushToken);

  List<UserPlatformInfo> findByFleekUser(FleekUser fleekUser);
}
