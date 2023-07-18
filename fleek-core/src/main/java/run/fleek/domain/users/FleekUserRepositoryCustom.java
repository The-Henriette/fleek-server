package run.fleek.domain.users;


import run.fleek.domain.users.vo.FleekUserDetailVo;

public interface FleekUserRepositoryCustom {
  FleekUserDetailVo getTraceUserDetail(Long userId);
}
