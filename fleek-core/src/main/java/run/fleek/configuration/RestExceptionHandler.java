package run.fleek.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import run.fleek.common.exception.FleekException;
import run.fleek.common.response.FleekErrorResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpStatus.*;
import static run.fleek.common.constants.Messages.ExceptionError.*;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final int LIFECYCLE_REPLAY_HTTP_STATUS_CODE = 593;
  private final ErrorNotificationService errorNotificationService;

  @ExceptionHandler(value = { FleekException.class })
  public final ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
    log.warn("[{}] : {}", BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    return new ResponseEntity<>(FleekErrorResponse.from(BAD_REQUEST_MESSAGE, ex.getMessage()), BAD_REQUEST);
  }

  @ExceptionHandler(value = { Exception.class })
  public final ResponseEntity<Object> handleInternalServerError(RuntimeException ex) {
    log.error("[{}] : {}", INTERNAL_SERVER_ERROR.getReasonPhrase(), getExceptionTrace(ex));
    errorNotificationService.sendNotification(ex);
    return new ResponseEntity<>(FleekErrorResponse.from(INTERNAL_SERVER_ERROR_MESSAGE, ex.getMessage()), INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = { DeadlockLoserDataAccessException.class })
  public final ResponseEntity<Object> handleInternalServerError(DeadlockLoserDataAccessException ex) {
    log.error("[{}] : {}", INTERNAL_SERVER_ERROR.getReasonPhrase(), getErrorTrace(ex));
    errorNotificationService.sendNotification(new FleekException(DUPLICATED_REQUEST));
    return new ResponseEntity<>(FleekErrorResponse.from(DUPLICATED_REQUEST,DUPLICATED_REQUEST), BAD_REQUEST);
  }

  @ExceptionHandler(value = { AccessDeniedException.class })
  public final ResponseEntity<Object> handleForbidden(RuntimeException ex) {
    log.warn("[{}] : {}", FORBIDDEN.getReasonPhrase(), ex.getMessage());
    return new ResponseEntity<>(FleekErrorResponse.from(FORBIDDEN_MESSAGE, ex.getMessage()), FORBIDDEN);
  }

//  @ExceptionHandler(value = { UnauthorizedAccessException.class })
//  public final ResponseEntity<Object> handleUnauthorizedAccess(RuntimeException ex) {
//    log.warn("[{}] : {}", UNAUTHORIZED.getReasonPhrase(), getExceptionTrace(ex));
//    return new ResponseEntity<>(FleekErrorResponse.from(UNAUTHORIZED_MESSAGE, ex.getMessage()), UNAUTHORIZED);
//  }
//
//  @ExceptionHandler(value = { ForbiddenException.class })
//  public final ResponseEntity<Object> handleForbiddenAccess(RuntimeException ex) {
//    log.warn("[{}] : {}", FORBIDDEN.getReasonPhrase(), getExceptionTrace(ex));
//    return new ResponseEntity<>(FleekErrorResponse.from(FORBIDDEN_MESSAGE, ex.getMessage()), FORBIDDEN);
//  }

  @Override
  public final ResponseEntity<Object> handleNoHandlerFoundException(
    NoHandlerFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
    log.warn("[{}] : {}", NOT_FOUND.getReasonPhrase(),ex.getMessage());
    if (OPTIONS.name().equals(ex.getHttpMethod())) {
      return new ResponseEntity<>(null, OK);
    }
    return new ResponseEntity<>(FleekErrorResponse.from("존재하지 않는 리소스입니다.", ex.getMessage()), NOT_FOUND);
  }

  @Override
  public final ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                   @NotNull HttpHeaders headers,
                                                                   @NotNull HttpStatus status,
                                                                   @NotNull WebRequest request) {
    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
      .findFirst()
      .map(e -> e.getDefaultMessage() + "(" + e.getField()  + ")").orElse(null);
    return new ResponseEntity<>(FleekErrorResponse.from(errorMessage, null), BAD_REQUEST);
  }

  private String getExceptionTrace(RuntimeException ex) {
    StringWriter sw = new StringWriter();
    ex.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  private String getErrorTrace(Throwable throwable) {
    StringWriter sw = new StringWriter();
    throwable.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}
