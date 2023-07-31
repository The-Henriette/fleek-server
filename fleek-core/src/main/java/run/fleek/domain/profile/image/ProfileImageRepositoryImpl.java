package run.fleek.domain.profile.image;

import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;

import java.util.List;

public class ProfileImageRepositoryImpl extends FleekQueryDslRepositorySupport implements ProfileImageRepositoryCustom {


  private final QProfileImage qProfileImage = QProfileImage.profileImage;
  private final QProfile qProfile = QProfile.profile;
  private final QFleekUser qFleekUser = QFleekUser.fleekUser;

  public ProfileImageRepositoryImpl() {
    super(ProfileImage.class);
  }


  @Override
  public List<ProfileImage> listProfileImageByFleekUserId(Long fleekUserId) {
    return from(qProfileImage)
      .innerJoin(qProfileImage.profile, qProfile).fetchJoin()
      .innerJoin(qProfile.fleekUser, qFleekUser)
      .where(qFleekUser.fleekUserId.eq(fleekUserId))
      .fetchResults()
      .getResults();
  }
}
