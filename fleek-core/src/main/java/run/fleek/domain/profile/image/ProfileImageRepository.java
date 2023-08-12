package run.fleek.domain.profile.image;

import org.springframework.data.jpa.repository.JpaRepository;
import run.fleek.domain.profile.Profile;
import run.fleek.enums.ImageType;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long>, ProfileImageRepositoryCustom {
    List<ProfileImage> findAllByProfile_ProfileId(Long profileId);
    boolean existsByProfileAndImageType(Profile profile, ImageType imageType);

    void deleteAllByProfileAndImageType(Profile profile, ImageType imageType);
}
