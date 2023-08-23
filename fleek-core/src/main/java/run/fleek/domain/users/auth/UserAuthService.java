package run.fleek.domain.users.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.auth.dto.RefreshRequestDto;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.utils.TimeUtil;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {

  private final UserAuthRepository userAuthRepository;
  private final FleekTokenProvider fleekTokenProvider;

  @Transactional
  public UserAuth putUserAuthDev(FleekUser fleekUser) {
    UserAuth userAuth = userAuthRepository.getFirstByFleekUser_FleekUserId(fleekUser.getFleekUserId())
      .orElseGet(() -> addUserAuth(fleekUser));

    TokenDto tokenDto = fleekTokenProvider.generateTokenDto(fleekUser);
    userAuth.update(tokenDto);
    userAuth.setAccessTokenExpiresAt(tokenDto.getAccessTokenExpiresAt());
    return userAuthRepository.save(userAuth);
  }

  @Transactional
  public UserAuth putUserAuth(RefreshRequestDto refreshRequest) {
    UserAuth userAuth = userAuthRepository.getUserAuthByRefreshToken(refreshRequest.getRefreshToken())
      .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));

    TokenDto tokenDto = fleekTokenProvider.generateTokenDto(userAuth.getFleekUser());
    userAuth.update(tokenDto);
    userAuth.setAccessTokenExpiresAt(tokenDto.getAccessTokenExpiresAt());
    return userAuthRepository.save(userAuth);
  }

  @Transactional
  public UserAuth addUserAuth(FleekUser fleekUser) {
    TokenDto tokenDto = fleekTokenProvider.generateTokenDto(fleekUser);
    UserAuth userAuth = UserAuth.from(tokenDto, fleekUser);
    userAuth.setAccessTokenExpiresAt(tokenDto.getAccessTokenExpiresAt());
    return userAuthRepository.save(userAuth);
  }
}
