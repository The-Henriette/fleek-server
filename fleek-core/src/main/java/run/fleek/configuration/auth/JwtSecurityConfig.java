package run.fleek.configuration.auth;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final FleekTokenProvider fleekTokenProvider;
  private final FleekAuthenticationProvider fleekAuthenticationProvider;

  public void configure(HttpSecurity http) {
    JwtFilter customFilter = new JwtFilter(fleekTokenProvider, fleekAuthenticationProvider);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }

  public JwtSecurityConfig(final FleekTokenProvider fleekTokenProvider, final FleekAuthenticationProvider fleekAuthenticationProvider) {
    this.fleekTokenProvider = fleekTokenProvider;
    this.fleekAuthenticationProvider = fleekAuthenticationProvider;
  }
}
