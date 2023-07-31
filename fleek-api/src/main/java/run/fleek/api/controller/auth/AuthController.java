package run.fleek.api.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.auth.AuthApplication;
import run.fleek.application.auth.SignUpApplication;
import run.fleek.application.auth.dto.*;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.enums.VerificationType;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final SignUpApplication signUpApplication;
  private final AuthApplication authApplication;

  @PostMapping("/auth/sign-up")
  public TokenDto signUp(@RequestBody SignUpDto signUpDto) {
    return signUpApplication.signUp(signUpDto);
  }

  @PostMapping("/auth/login")
  public VerificationCodeDto login(@RequestBody VerificationRequestDto verificationRequestDto) {
    return authApplication.addVerification(VerificationType.SIGNIN, verificationRequestDto.getPhoneNumber());
  }

  @PostMapping("/auth/verify")
  public VerificationResultDto verify(@RequestBody VerificationDto verificationDto) {
    return authApplication.verify(verificationDto);
  }

  @GetMapping("/auth/check")
  public UserInfoDto check() {
    return authApplication.authCheck();
  }

  @PostMapping("/auth/refresh")
  public TokenDto refresh(@RequestBody RefreshRequestDto refreshRequestDto) {
    return authApplication.refresh(refreshRequestDto);
  }
}
