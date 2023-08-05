package run.fleek.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.users.FleekUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileRepositoryCustom {
  Optional<Profile> findByProfileName(String profileName);

  List<Profile> findAllByFleekUser(FleekUser fleekUser);
}
