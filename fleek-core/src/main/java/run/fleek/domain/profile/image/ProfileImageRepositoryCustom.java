package run.fleek.domain.profile.image;

import java.util.List;

public interface ProfileImageRepositoryCustom {

  List<ProfileImage> listProfileImageByFleekUserId(Long fleekUserId);
}
