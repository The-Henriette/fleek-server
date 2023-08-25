package run.fleek.application.fruitman.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import run.fleek.common.client.kakao.KakaoWebClient;
import run.fleek.common.client.kakao.dto.KakaoIdTokenDto;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.domain.fruitman.user.FruitManUserService;
import run.fleek.enums.ProviderCode;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KakaoProvider implements FruitManAuthProvider {

  @Value("${external.token.kakao}")
  private String kakaoRestToken;
  @Value("${redirect.kakao}")
  private String redirectUrl;

  private final RestTemplate restTemplate;
  private final KakaoWebClient kakaoWebClient;
  private final FruitManUserService fruitManUserService;
  private final FleekTokenProvider fleekTokenProvider;

  @Override
  public boolean isSupport(ProviderCode providerCode) {
    return providerCode.equals(ProviderCode.KAKAO);
  }

  @Override
  public ResponseEntity<String> requestAccess() {
    String kakaoAuthorizeUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&state=KAKAO&redirect_uri=%s&client_id=%s";
    String requestUrl = String.format(kakaoAuthorizeUrl, redirectUrl, kakaoRestToken);

    return ResponseEntity.status(302)
      .header("Location", requestUrl)
      .build();
  }

  @Override
  @Transactional
  public TokenDto processRedirect(String code) {
    KakaoIdTokenDto kakaoId = kakaoWebClient.getAuthToken(code);

    FruitManUser fruitManUser;

    Optional<FruitManUser> preExistentUser =
      fruitManUserService.getFruitManUserByProviderCodeAndProviderUserId(ProviderCode.KAKAO, kakaoId.getUserId());
    if (preExistentUser.isPresent()) {
      fruitManUser = preExistentUser.get();
    } else {
      fruitManUser = FruitManUser.builder()
        .providerCode(ProviderCode.KAKAO)
        .providerId(kakaoId.getUserId())
        .nickname(kakaoId.getNickname())
        .profileUrl(kakaoId.getPicture())
        .build();
      fruitManUserService.addUser(fruitManUser);
    }

    return fleekTokenProvider.generateTokenDto(fruitManUser);
  }
}
