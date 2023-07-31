package run.fleek.domain.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.profile.vo.ProfileVo;
import run.fleek.domain.users.FleekUser;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ProfileService {

  private final ProfileRepository profileRepository;

  @Transactional
  public Profile putProfile(FleekUser fleekUser, String profileName) {
    Optional<Profile> profileOpt = getProfileByProfileName(profileName);

    Profile profile;
    if (profileOpt.isPresent()) {
      profile = profileOpt.get();
      profile.setFleekUser(fleekUser);
    } else {
      profile = Profile.from(fleekUser, profileName);
    }
    return profileRepository.save(profile);
  }

  @Transactional
  public Profile addProfile(Profile profile) {
    return profileRepository.save(profile);
  }

  @Transactional(readOnly = true)
  public Optional<Profile> getProfileByProfileName(String profileName) {
    return profileRepository.findByProfileName(profileName);
  }

  @Transactional(readOnly = true)
  public Optional<Profile> getProfileById(Long profileId) {
    return profileRepository.findById(profileId);
  }

  @Transactional(readOnly = true)
  public ProfileVo getProfileVoById(Long profileId) {
    return profileRepository.getProfileVoById(profileId);
  }
}
