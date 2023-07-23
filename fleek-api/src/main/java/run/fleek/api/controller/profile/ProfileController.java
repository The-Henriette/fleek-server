package run.fleek.api.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

  @GetMapping("/profile/{profileId}/detail")
  public void getProfileDetail(@PathVariable Long profileId) {

  }
}
