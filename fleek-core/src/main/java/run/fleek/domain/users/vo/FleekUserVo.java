package run.fleek.domain.users.vo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.MappingProjection;
import lombok.*;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.enums.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekUserVo {
  private Long fleekUserId;
  private String phoneNumber;
  private Long dateOfBirth;
  private Gender gender;
  private Gender orientation;

  public static final FleekUserVoProjection FLEEK_USER_VO_PROJECTION = new FleekUserVoProjection();

  public static class FleekUserVoProjection extends MappingProjection<FleekUserVo> {
    public FleekUserVoProjection() {
      super(FleekUserVo.class,
        QFleekUser.fleekUser.fleekUserId,
        QFleekUser.fleekUser.phoneNumber,
        QFleekUserDetail.fleekUserDetail.birthDate,
        QFleekUserDetail.fleekUserDetail.gender,
        QFleekUserDetail.fleekUserDetail.orientation
        );
    }

    @Override
    protected FleekUserVo map(Tuple row) {
      return FleekUserVo.builder()
        .fleekUserId(row.get(QFleekUser.fleekUser.fleekUserId))
        .phoneNumber(row.get(QFleekUser.fleekUser.phoneNumber))
        .dateOfBirth(row.get(QFleekUserDetail.fleekUserDetail.birthDate))
        .gender(row.get(QFleekUserDetail.fleekUserDetail.gender))
        .orientation(row.get(QFleekUserDetail.fleekUserDetail.orientation))
        .build();
    }
  }
}
