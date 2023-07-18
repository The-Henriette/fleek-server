package run.fleek.configuration.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import run.fleek.common.exception.FleekException;
import run.fleek.configuration.auth.vo.FleekPrincipalVo;
import run.fleek.configuration.auth.vo.FleekUserDetailsVo;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserService;
import run.fleek.domain.users.dto.UserInfoDto;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FleekUserContext {

  private final FleekUserService fleekUserService;

  public FleekUser getFleekUser() {
    FleekAuthentication authentication = (FleekAuthentication) SecurityContextHolder.getContext().getAuthentication();
    long userId = authentication.fetchPrincipal().getFleekUserId();
    return fleekUserService.getByUserId(userId).orElseThrow(new FleekException("No User."));
  }

  public UserInfoDto getUserInfo() {
    FleekAuthentication authentication = (FleekAuthentication) SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication)) {
      return null;
    }
    FleekPrincipalVo principal = authentication.fetchPrincipal();
    FleekUserDetailsVo details = authentication.fetchDetails();
    return UserInfoDto.builder()
      .userId(principal.getFleekUserId())
      .userName(principal.getUserName())
      .countryCode(details.getCountryCode())
      .languageCode(details.getLanguageCode())
      .build();
  }
}
