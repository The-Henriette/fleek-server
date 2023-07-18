package run.fleek.configuration.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import run.fleek.configuration.auth.vo.FleekCredentialsVo;
import run.fleek.configuration.auth.vo.FleekPrincipalVo;
import run.fleek.domain.users.FleekUser;

import java.util.Objects;


public class FleekAuthenticationManager implements AuthenticationManager {

    private final FleekAuthenticationProvider authenticationProvider;

    public FleekAuthenticationManager(FleekAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        FleekAuthentication FleekAuthentication = (FleekAuthentication) authentication;

        FleekPrincipalVo principal = FleekAuthentication.fetchPrincipal();
        FleekCredentialsVo credentials = FleekAuthentication.fetchCredentials();
        FleekUser authenticatedUser = authenticationProvider.authenticate(principal, credentials);

        if (Objects.nonNull(authenticatedUser)) {
            principal.setFleekUserId(authenticatedUser.getFleekUserId());
            FleekAuthentication.setPrincipal(principal);
            return FleekAuthentication;
        }

        throw new RuntimeException();
    }
}
