package run.fleek.domain.profile.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.profile.Profile;

import java.util.List;

@Repository
public interface ProfileInfoRepository extends JpaRepository<ProfileInfo, Long> {
  List<ProfileInfo> findAllByProfile_ProfileId(Long profileId);
}
