package run.fleek.common.constants;

public final class Messages {
  public static final class ExceptionError {
    public static final String BAD_REQUEST_MESSAGE = "요청을 처리하지 못했습니다.";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "처리 중 오류가 발생했습니다.";
    public static final String FORBIDDEN_MESSAGE = "접근 권한이 없습니다.";
    public static final String UNAUTHORIZED_MESSAGE = "인증에 실패했습니다.";
    public static final String LIFECYCLE_EVENT_REPLAY_MESSAGE = "즉시 처리에 실패하여 재처리 요청 되었습니다. 잠시후 새로고침을 시도하여 결과를 확인해 주세요.";
    public static final String DUPLICATED_REQUEST = "중복처리 요청 입니다.";
    public static final String METHOD_NOT_ALLOWED = "유효하지 않은 요청입니다.";
    public static final String NOT_SUPPORTED_OPERATION = "지원되지 않는 요청입니다.";
  }

}
