package run.fleek.configuration.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import run.fleek.configuration.auth.vo.FleekCredentialsVo;
import run.fleek.configuration.auth.vo.FleekPrincipalVo;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FleekAuthenticationProvider {

    private final FleekUserService fleekUserService;
    private final PasswordEncoder passwordEncoder;

    public Authentication getAuthentication(Long userId) {
        FleekUser user = fleekUserService.getByUserId(userId)
          .orElseThrow();

        return FleekAuthentication.fromUser(user);
    }

    public FleekUser authenticate(FleekPrincipalVo principal, FleekCredentialsVo credentials) {
        FleekUser fleekUser;
        if (Objects.nonNull(principal.getFleekUserId())) {
            fleekUser = fleekUserService.getByUserId(principal.getFleekUserId())
              .orElseThrow();
        } else {
            fleekUser = fleekUserService.getByUserName(principal.getUserName())
              .orElseThrow();
        }

        if (passwordEncoder.matches(credentials.getPassword(), fleekUser.getPhoneNumber())) {
            return fleekUser;
        }

        return null;
    }
}
