package run.fleek.domain.profile;

import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.profile.vo.ProfileVo;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;

import static run.fleek.domain.profile.vo.ProfileVo.PROFILE_VO_PROJECTION;

public class ProfileRepositoryImpl extends FleekQueryDslRepositorySupport implements ProfileRepositoryCustom {

  private final QProfile qProfile = QProfile.profile;
  private final QFleekUser qFleekUser = QFleekUser.fleekUser;
  private final QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;

  public ProfileRepositoryImpl() {
    super(Profile.class);
  }

  @Override
  public ProfileVo getProfileVoById(Long profileId) {
    return from(qProfile)
      .where(qProfile.profileId.eq(profileId))
      .leftJoin(qProfile.fleekUser, qFleekUser)
      .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
      .select(PROFILE_VO_PROJECTION)
      .fetchOne();
  }
}
