package run.fleek.api.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.profile.ProfileApplication;
import run.fleek.application.profile.dto.ProfileInfoMetaDto;
import run.fleek.application.profile.dto.ProfileViewDto;
import run.fleek.application.profile.vo.ProfileEditDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileApplication profileApplication;

  @GetMapping("/profile/{profileName}/detail")
  public ProfileViewDto getProfileDetail(@PathVariable String profileName) {
    return profileApplication.getProfileDetail(profileName);
  }

  @GetMapping("/profile/meta")
  public List<ProfileInfoMetaDto> listProfileMeta() {
    return profileApplication.listProfileMeta();
  }

  @PostMapping("/profile/edit")
  public void editProfile(@RequestBody ProfileEditDto profileEditDto) {
    profileApplication.putProfileDetail(profileEditDto);
  }
}
