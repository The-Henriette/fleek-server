package run.fleek.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.auth.dto.*;
import run.fleek.common.exception.FleekException;
import run.fleek.common.notification.DevVerificationNotifier;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.profile.image.dto.ProfileImageDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserService;
import run.fleek.domain.users.auth.UserAuth;
import run.fleek.domain.users.auth.UserAuthService;
import run.fleek.domain.users.verification.UserVerification;
import run.fleek.domain.users.verification.UserVerificationService;
import run.fleek.domain.users.vo.FleekUserVo;
import run.fleek.enums.VerificationType;
import run.fleek.utils.TimeUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthApplication {

  private final UserVerificationService userVerificationService;
  private final FleekUserService fleekUserService;
  private final FleekTokenProvider tokenProvider;
  private final FleekUserContext fleekUserContext;
  private final ProfileImageService profileImageService;
  private final UserAuthService userAuthService;
  private final DevVerificationNotifier devVerificationNotifier;

  public UserInfoDto authCheck() {
    FleekUserVo fleekUserVo = fleekUserContext.getFleekUser();
    List<ProfileImageDto> profileImages = profileImageService.listProfileImagesByUserId(fleekUserVo.getFleekUserId());

    return UserInfoDto.builder()
      .profiles(profileImages)
      .phoneNumber(fleekUserVo.getPhoneNumber())
      .age(TimeUtil.calculateAge(fleekUserVo.getDateOfBirth()))
      .gender(fleekUserVo.getGender().name())
      .orientation(fleekUserVo.getOrientation().name())
      .build();
  }

  @Transactional
  public VerificationCodeDto addVerification(VerificationType verificationType, String phoneNumber) {
    Optional<FleekUser> fleekUser = fleekUserService.getUserByPhoneNumber(phoneNumber);
    UserVerification userVerification;
    if (fleekUser.isPresent()) {
      userVerification = userVerificationService.addUserVerification(verificationType, fleekUser.get());
    } else {
      userVerification = userVerificationService.addUserVerification(verificationType, null);
    }

    devVerificationNotifier.send(userVerification.getVerificationNumber());
    return VerificationCodeDto.builder()
      .verificationCode(userVerification.getVerificationCode())
      .build();
  }

  public VerificationResultDto verify(VerificationDto verificationDto) {
    UserVerification userVerification = userVerificationService.getVerificationByCode(verificationDto.getVerificationCode())
      .orElseThrow(new FleekException());

    if (verificationDto.getVerificationNumber().equals(userVerification.getVerificationNumber())) {
      userVerificationService.verify(userVerification);
      if (Objects.nonNull(userVerification.getFleekUser())) {
        UserAuth userAuth = userAuthService.addUserAuth(userVerification.getFleekUser());
//        TokenDto tokenDto = tokenProvider.generateTokenDto(userVerification.getFleekUser());
        TokenDto tokenDto = TokenDto.builder()
          .accessToken(userAuth.getAccessToken())
          .refreshToken(userAuth.getRefreshToken())
          .grantType("Bearer")
          .accessTokenExpiresAt(userAuth.getAccessTokenExpiresAt())
          .refreshTokenExpiresAt(userAuth.getExpiredAt())
          .build();

        return VerificationResultDto.success(tokenDto);
      }

      return VerificationResultDto.success(null);
    }

    return VerificationResultDto.builder()
      .success(false)
      .build();
  }

  public TokenDto refresh(RefreshRequestDto refreshRequestDto) {
    UserAuth userAuth = userAuthService.putUserAuth(refreshRequestDto);
    return TokenDto.builder()
      .accessToken(userAuth.getAccessToken())
      .refreshToken(userAuth.getRefreshToken())
      .grantType("Bearer")
      .refreshTokenExpiresAt(userAuth.getExpiredAt())
      .build();
  }
}
