package run.fleek.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.application.certification.CertificationApplication;
import run.fleek.application.auth.dto.SignUpDto;
import run.fleek.application.term.TermApplication;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserDetail;
import run.fleek.domain.users.FleekUserDetailService;
import run.fleek.domain.users.FleekUserService;

@Component
@RequiredArgsConstructor
public class SignUpApplication {

  private final CertificationApplication certificationApplication;
  private final FleekUserService fleekUserService;
  private final FleekUserDetailService fleekUserDetailService;
  private final ProfileService profileService;
  private final ProfileImageService profileImageService;
  private final TermApplication termApplication;
  private final FleekTokenProvider tokenProvider;

  public TokenDto signUp(SignUpDto signUpDto) {
    // first, check if verification of given verification code exists.

    // create user.
    FleekUser fleekUser = fleekUserService.addFleekUser(FleekUser.of(signUpDto.getPhoneNumber()));
    // create user detail
    fleekUserDetailService.addUserDetail(FleekUserDetail.from(signUpDto, fleekUser));

    // create or update profile
    Profile defaultProfile = profileService.putProfile(fleekUser, signUpDto.getProfileName());
    // save profile images
    profileImageService.addProfileImages(defaultProfile, signUpDto.getProfileImageUrls());

    // save user terms
    termApplication.addUserTerms(signUpDto.getUserTerms(), fleekUser);

    // save photo certification
    certificationApplication.addCertification(fleekUser, signUpDto.getCertification());
    return tokenProvider.generateTokenDto(fleekUser);
  }
}
