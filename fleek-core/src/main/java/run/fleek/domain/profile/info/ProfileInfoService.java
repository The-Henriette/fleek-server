package run.fleek.domain.profile.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.profile.Profile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileInfoService {

  private final ProfileInfoRepository profileInfoRepository;

  @Transactional(readOnly = true)
  public List<ProfileInfo> listProfileInfoByProfile(Long profileId) {
    return profileInfoRepository.findAllByProfile_ProfileId(profileId);
  }
}
