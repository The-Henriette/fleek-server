package run.fleek.domain.users;

import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.users.vo.FleekUserVo;

public class FleekUserRepositoryImpl extends FleekQueryDslRepositorySupport implements FleekUserRepositoryCustom {

  public FleekUserRepositoryImpl() {
    super(FleekUser.class);
  }

  private final QFleekUser qFleekUser = QFleekUser.fleekUser;
  private final QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;

  @Override
  public FleekUserVo getFleekUserVoById(Long fleekUserId) {
    return from(qFleekUser)
      .where(qFleekUser.fleekUserId.eq(fleekUserId))
      .innerJoin(qFleekUserDetail).on(qFleekUser.fleekUserId.eq(qFleekUserDetail.fleekUser.fleekUserId))
      .select(FleekUserVo.FLEEK_USER_VO_PROJECTION)
      .fetchOne();
  }
}
