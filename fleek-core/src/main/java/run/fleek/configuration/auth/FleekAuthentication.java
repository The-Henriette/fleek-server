package run.fleek.configuration.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import run.fleek.configuration.auth.vo.FleekCredentialsVo;
import run.fleek.configuration.auth.vo.FleekPrincipalVo;
import run.fleek.configuration.auth.vo.FleekUserDetailsVo;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.dto.SignInDto;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Builder
public class FleekAuthentication implements Authentication {

    private Object principal; // Fleek_user info
    private Object credentials; // password, or token for social login
    private Object details; // locale, language and so on...
    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;

    public FleekPrincipalVo fetchPrincipal() {
        return (FleekPrincipalVo) principal;
    }
    public FleekCredentialsVo fetchCredentials() {
        if (Objects.isNull(credentials)) {
            return null;
        }

        return (FleekCredentialsVo) credentials;
    }

    public FleekUserDetailsVo fetchDetails() {
        if (Objects.isNull(details)) {
            return null;
        }

        return (FleekUserDetailsVo) details;
    }

    @Override
    public String getName() {
        return principal.toString();
    }

    public static FleekAuthentication fromUser(FleekUser FleekUser) {
        return FleekAuthentication.builder()
          .principal(FleekPrincipalVo.builder()
            .fleekUserId(FleekUser.getFleekUserId())
            .userName(FleekUser.getPhoneNumber())
            .build())
          .details(FleekUserDetailsVo.builder()
            .languageCode("ko")
            .countryCode("ko")
            .build())
          .build();
    }

    public static FleekAuthentication fromSignIn(SignInDto signInDto) {
        return FleekAuthentication.builder()
          .principal(FleekPrincipalVo.builder()
            .userName(signInDto.getUserName())
            .build())
          .credentials(FleekCredentialsVo.builder()
            .password(signInDto.getPassword())
            .build())
          .build();
    }
}
