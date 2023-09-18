package run.fleek.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import run.fleek.application.auth.dto.SignUpResultDto;
import run.fleek.application.certification.CertificationApplication;
import run.fleek.application.auth.dto.SignUpDto;
import run.fleek.application.term.TermApplication;
import run.fleek.common.client.sendbird.SendbirdWebClient;
import run.fleek.common.client.sendbird.dto.SendbirdAddUserDto;
import run.fleek.common.client.sendbird.dto.SendbirdAddUserResponseDto;
import run.fleek.common.constants.NamePool;
import run.fleek.common.exception.FleekException;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.chat.Chat;
import run.fleek.domain.chat.ProfileChat;
import run.fleek.domain.chat.ProfileChatService;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserDetail;
import run.fleek.domain.users.FleekUserDetailService;
import run.fleek.domain.users.FleekUserService;
import run.fleek.domain.users.auth.UserAuth;
import run.fleek.domain.users.auth.UserAuthService;
import run.fleek.domain.users.wallet.UserWallet;
import run.fleek.domain.users.wallet.UserWalletService;

import java.util.UUID;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Component
@RequiredArgsConstructor
public class SignUpApplication {

  private final CertificationApplication certificationApplication;
  private final FleekUserService fleekUserService;
  private final FleekUserDetailService fleekUserDetailService;
  private final ProfileService profileService;
  private final ProfileChatService profileChatService;
  private final ProfileImageService profileImageService;
  private final TermApplication termApplication;
  private final FleekTokenProvider tokenProvider;
  private final SendbirdWebClient sendbirdWebClient;
  private final UserAuthService userAuthService;
  private final UserWalletService userWalletService;

  @Transactional
  public SignUpResultDto signUp(SignUpDto signUpDto) {
    // first, check if verification of given verification code exists.

    // create user.
    FleekUser fleekUser = fleekUserService.addFleekUser(FleekUser.of(signUpDto.getPhoneNumber()));
    // create user detail
    fleekUserDetailService.addUserDetail(FleekUserDetail.from(signUpDto, fleekUser));

    SignUpResultDto result = new SignUpResultDto();
    // create or update profile
    // if profileChatCode is given, then fetch pre-existent profile and link it to the user.
    Profile profile;
    if (signUpDto.getProfileChatCode() != null) {
      ProfileChat profileChat = profileChatService.getProfileByProfileChatCode(signUpDto.getProfileChatCode())
          .orElseThrow(() -> new FleekException("profileChatCode is invalid"));
      profile = profileChat.getProfile();
      Chat chat = profileChat.getChat();
      result.setChatUri(chat.getChatUri());

      profile.setFleekUser(fleekUser);
      // create sendbird user
      SendbirdAddUserResponseDto sendbirdUser = sendbirdWebClient.addUser(UUID.randomUUID().toString(), profile.getProfileName(),
        CollectionUtils.isEmpty(signUpDto.getProfileImageUrls()) ? null : CDN_PREFIX + signUpDto.getProfileImageUrls().get(0));
      profile.setChatProfileKey(sendbirdUser.getUserId());
      // invite user to chat
      sendbirdWebClient.inviteMember(sendbirdUser.getUserId(), chat.getChatUri());
    } else {
      profile = Profile.from(fleekUser, NamePool.getRandomName());
      // create sendbird user
      SendbirdAddUserResponseDto sendbirdUser = sendbirdWebClient.addUser(UUID.randomUUID().toString(), profile.getProfileName(),
        CollectionUtils.isEmpty(signUpDto.getProfileImageUrls()) ? null : CDN_PREFIX + signUpDto.getProfileImageUrls().get(0));
      profile.setChatProfileKey(sendbirdUser.getUserId());
    }
    profileService.addProfile(profile);
    // save profile images
    profileImageService.addProfileImages(profile, signUpDto.getProfileImageUrls());

    // save user terms
    termApplication.addUserTerms(signUpDto.getUserTerms(), fleekUser);

    // save photo certification
    UserCertification userCertification = certificationApplication.addCertification(fleekUser, signUpDto.getCertification());
    UserAuth userAuth = userAuthService.addUserAuth(fleekUser);
    TokenDto token = TokenDto.builder()
      .accessToken(userAuth.getAccessToken())
      .refreshToken(userAuth.getRefreshToken())
      .accessTokenExpiresAt(userAuth.getAccessTokenExpiresAt())
      .refreshTokenExpiresAt(userAuth.getExpiredAt())
      .grantType("Bearer")
      .build();

    userWalletService.putWallet(UserWallet.builder()
      .amount(400L)
      .fleekUser(fleekUser)
      .build());

    result.setToken(token);
    result.setChatProfileKey(profile.getChatProfileKey());

    certificationApplication.processFaceCertification(userCertification);
    return result;
  }
}
