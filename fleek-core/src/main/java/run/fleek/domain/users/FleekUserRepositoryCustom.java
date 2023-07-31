package run.fleek.domain.users;

import run.fleek.domain.users.vo.FleekUserVo;

public interface FleekUserRepositoryCustom {
  FleekUserVo getFleekUserVoById(Long fleekUserId);
}
