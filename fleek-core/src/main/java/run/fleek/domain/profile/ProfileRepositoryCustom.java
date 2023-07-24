package run.fleek.domain.profile;

import run.fleek.domain.profile.vo.ProfileVo;

public interface ProfileRepositoryCustom {
  ProfileVo getProfileVoById(Long profileId);
}
