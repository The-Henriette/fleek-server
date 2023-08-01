package run.fleek.api.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.profile.ProfileApplication;
import run.fleek.application.profile.dto.ProfileViewDto;

@RestController
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileApplication profileApplication;

  @GetMapping("/profile/{profileName}/detail")
  public ProfileViewDto getProfileDetail(@PathVariable String profileName) {
    return profileApplication.getProfileDetail(profileName);
  }
}
