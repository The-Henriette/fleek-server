package run.fleek.domain.users;

import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.users.vo.FleekUserDetailVo;

import static run.fleek.domain.users.vo.FleekUserDetailVo.FLEEK_USER_DETAIL_VO_PROJECTION;

public class FleekUserRepositoryImpl extends FleekQueryDslRepositorySupport implements FleekUserRepositoryCustom {

  public FleekUserRepositoryImpl() {
    super(FleekUser.class);
  }

  private final QFleekUser qFleekUser = QFleekUser.fleekUser;
  private final QFleekUserInfo qFleekUserInfo = QFleekUserInfo.fleekUserInfo;
  private final QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;

  @Override
  public FleekUserDetailVo getTraceUserDetail(Long userId) {
    return from(qFleekUser)
      .innerJoin(qFleekUserInfo).on(qFleekUser.fleekUserId.eq(qFleekUserInfo.fleekUser.fleekUserId))
      .where(qFleekUser.fleekUserId.eq(userId))
      .select(FLEEK_USER_DETAIL_VO_PROJECTION)
      .fetchOne();
  }
}
