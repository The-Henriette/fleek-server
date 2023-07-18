package run.fleek.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import run.fleek.configuration.auth.FleekAuthenticationProvider;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.JwtSecurityConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@ComponentScan("run.fleek.api")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FleekAuthenticationProvider fleekAuthenticationProvider;
    @Autowired
    private FleekTokenProvider fleekTokenProvider;

    public static final String[] publicPathAntPatterns = {
        "/error**", "/hello", "/auth/sign-up", "/auth/sign-in", "/auth/username/validate"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
            .and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers(publicPathAntPatterns).permitAll()
//            .anyRequest().access("@sugarAuthorizationChecker.check(request, authentication)")
            .and()
            .apply(new JwtSecurityConfig(fleekTokenProvider, fleekAuthenticationProvider))
            .and()
            .formLogin().disable();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new FleekAuthenticationEntryPoint();
    }

    @Bean
    public FleekAccessDeniedHandler accessDeniedHandler() {
        return new FleekAccessDeniedHandler();
    }

}
