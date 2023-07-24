package run.fleek.api.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.auth.SignUpApplication;
import run.fleek.application.auth.dto.SignUpDto;
import run.fleek.configuration.auth.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final SignUpApplication signUpApplication;

  @PostMapping("/auth/sign-up")
  public TokenDto signUp(@RequestBody SignUpDto signUpDto) {
    return signUpApplication.signUp(signUpDto);
  }
}
