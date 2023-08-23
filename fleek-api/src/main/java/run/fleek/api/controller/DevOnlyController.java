package run.fleek.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.auth.AuthApplication;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserService;

@RestController
@RequiredArgsConstructor
public class DevOnlyController {

  private final FleekTokenProvider fleekTokenProvider;
  private final FleekUserService fleekUserService;
  private final AuthApplication authApplication;

  @GetMapping("/dev/session")
  public TokenDto getSession(@RequestParam String phoneNumber) {
    FleekUser fleekUser = fleekUserService.getUserByPhoneNumber(phoneNumber)
      .orElseThrow();

    return authApplication.addVerifiedUserAuth(fleekUser, true);
  }
}
