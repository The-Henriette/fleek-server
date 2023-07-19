package run.fleek.domain.users;

import run.fleek.configuration.database.FleekQueryDslRepositorySupport;

public class FleekUserRepositoryImpl extends FleekQueryDslRepositorySupport implements FleekUserRepositoryCustom {

  public FleekUserRepositoryImpl() {
    super(FleekUser.class);
  }

  private final QFleekUser qFleekUser = QFleekUser.fleekUser;
  private final QFleekUserDetail qFleekUserDetail = QFleekUserDetail.fleekUserDetail;

}
