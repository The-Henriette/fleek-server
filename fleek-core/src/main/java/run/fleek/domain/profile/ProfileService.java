package run.fleek.domain.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional(readOnly = true)
  public Optional<Profile> getProfileByProfileName(String profileName) {
    return profileRepository.findByProfileName(profileName);
  }
}
