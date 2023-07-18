package run.fleek.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import run.fleek.common.response.FleekErrorResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FleekAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String UNAUTHENTICATED_MESSAGE = "Unauthenticated.";
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(mapper.writeValueAsString(
            FleekErrorResponse.from(UNAUTHENTICATED_MESSAGE, authException.getMessage())
        ));
    }
}
