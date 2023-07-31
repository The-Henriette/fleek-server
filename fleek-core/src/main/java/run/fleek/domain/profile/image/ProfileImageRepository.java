package run.fleek.domain.profile.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long>, ProfileImageRepositoryCustom {
  List<ProfileImage> findAllByProfile_ProfileId(Long profileId);
}
