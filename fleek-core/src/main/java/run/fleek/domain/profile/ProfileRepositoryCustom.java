package run.fleek.domain.profile;

import run.fleek.domain.profile.vo.ProfileVo;

public interface ProfileRepositoryCustom {
  ProfileVo getProfileVoByProfileName(String profileName);
}
