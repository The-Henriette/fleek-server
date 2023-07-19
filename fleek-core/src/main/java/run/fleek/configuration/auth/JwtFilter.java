package run.fleek.configuration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import run.fleek.common.response.FleekErrorResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.publicPathAntPatterns;

public class JwtFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";

  private final FleekTokenProvider FleekTokenProvider;
  private final FleekAuthenticationProvider FleekAuthenticationProvider;

  public JwtFilter(final FleekTokenProvider FleekTokenProvider,
                   final FleekAuthenticationProvider FleekAuthenticationProvider) {
    this.FleekTokenProvider = FleekTokenProvider;
    this.FleekAuthenticationProvider = FleekAuthenticationProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    String jwt = this.resolveToken(request);
    if (Arrays.stream(publicPathAntPatterns).collect(Collectors.toSet()).contains(request.getRequestURI())) {
      filterChain.doFilter(request, response);
      return;
    }
    String idStr = this.FleekTokenProvider.validateToken(jwt);
    try {
      if (StringUtils.hasLength(idStr)) {
        Long FleekUserId = Long.parseLong(this.FleekTokenProvider.validateToken(jwt));

        Authentication authentication = this.FleekAuthenticationProvider.getAuthentication(FleekUserId);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      ObjectMapper mapper = new ObjectMapper();
      response.setStatus(401);
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(mapper.writeValueAsString(FleekErrorResponse.from("Failed to authenticate", e.getMessage())));
    }
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    return StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX) ? bearerToken.substring(7) : null;
  }
}
