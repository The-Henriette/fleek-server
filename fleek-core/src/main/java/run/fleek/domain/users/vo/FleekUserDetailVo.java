package run.fleek.domain.users.vo;

import com.querydsl.core.types.MappingProjection;
import lombok.*;

import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserInfo;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekUserDetailVo {
  private String loginId;
  private String name;
  private String profilePicture;
  private String bio;
  private Integer following;
  private Integer followers;

  public static final FleekUserDetailVoProjection FLEEK_USER_DETAIL_VO_PROJECTION = new FleekUserDetailVoProjection();

  public static class FleekUserDetailVoProjection extends MappingProjection<FleekUserDetailVo> {
    public FleekUserDetailVoProjection() {
      super(FleekUserDetailVo.class,
        QFleekUser.fleekUser.userName,
        QFleekUserInfo.fleekUserInfo.name,
        QFleekUserInfo.fleekUserInfo.profilePicture,
        QFleekUserInfo.fleekUserInfo.bio
      );
    }

    @Override
    protected FleekUserDetailVo map(com.querydsl.core.Tuple row) {
      return FleekUserDetailVo.builder()
        .loginId(row.get(QFleekUser.fleekUser.userName))
        .name(row.get(QFleekUserInfo.fleekUserInfo.name))
        .profilePicture(row.get(QFleekUserInfo.fleekUserInfo.profilePicture))
        .bio(row.get(QFleekUserInfo.fleekUserInfo.bio))
        .build();
    }

  }
}
