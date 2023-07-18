package run.fleek.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FleekUserInfoRepository extends JpaRepository<FleekUserInfo, Long> {

  FleekUserInfo findByFleekUser(FleekUser fleekUser);
}
