package run.fleek.enums;

import lombok.Getter;

@Getter
public enum NotificationType {

  FACE_CERTIFICATION_ACCEPTED("교환용 사진 인증이 완료되었어요!"),
  FACE_CERTIFICATION_REJECTED("교환용 사진 인증이 반려되었어요."),
  NEW_LIKE_ADDED("새로운 좋아요가 추가되었습니다."),
  NER_COMMENT_ADDED("새로운 댓글이 추가되었습니다."),
  COMPANY_CERTIFICATION_REJECTED("회사 인증이 거절되었습니다."),
  COMPANY_CERTIFICATION_ACCEPTED("회사 인증이 승인되었습니다."),
  ;

  NotificationType(String message) {
    this.message = message;
  }

  private final String message;
}
