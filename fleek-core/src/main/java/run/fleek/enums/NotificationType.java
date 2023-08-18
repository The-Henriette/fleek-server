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
  COLLEGE_CERTIFICATION_REJECTED("대학 인증이 거절되었습니다."),
  COLLEGE_CERTIFICATION_ACCEPTED("대학 인증이 승인되었습니다."),
  INBODY_CERTIFICATION_REJECTED("인바디 인증이 거절되었습니다."),
  INBODY_CERTIFICATION_ACCEPTED("인바디 인증이 승인되었습니다.")

  ;

  NotificationType(String message) {
    this.message = message;
  }

  private final String message;

  public static NotificationType ofConfirm(String certificationCode) {
    if (certificationCode.equals("FACE")) {
      return FACE_CERTIFICATION_ACCEPTED;
    } else if (certificationCode.equals("COMPANY")) {
      return COMPANY_CERTIFICATION_ACCEPTED;
    } else if (certificationCode.equals("COLLEGE")) {
      return COLLEGE_CERTIFICATION_ACCEPTED;
    } else {
      return INBODY_CERTIFICATION_ACCEPTED;
    }
  }
}
