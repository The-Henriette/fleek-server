package run.fleek.domain.profile.vo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.MappingProjection;
import lombok.*;
import run.fleek.domain.profile.QProfile;
import run.fleek.domain.users.QFleekUser;
import run.fleek.domain.users.QFleekUserDetail;
import run.fleek.enums.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileVo {
  private Long profileId;
  private String profileName;
  private String bio;

  // user info
  private Long dateOfBirth;
  private Gender gender;
  private Gender orientation;

  public static final ProfileVoProjection PROFILE_VO_PROJECTION = new ProfileVoProjection();

  public static class ProfileVoProjection extends MappingProjection<ProfileVo> {

    public ProfileVoProjection() {
      super(ProfileVo.class,
        QProfile.profile.profileId,
        QProfile.profile.profileName,
        QProfile.profile.bio,
        QFleekUserDetail.fleekUserDetail.birthDate,
        QFleekUserDetail.fleekUserDetail.gender,
        QFleekUserDetail.fleekUserDetail.orientation
        );
    }

    @Override
    protected ProfileVo map(Tuple row) {
      return ProfileVo.builder()
        .profileId(row.get(QProfile.profile.profileId))
        .profileName(row.get(QProfile.profile.profileName))
        .bio(row.get(QProfile.profile.bio))
        .dateOfBirth(row.get(QFleekUserDetail.fleekUserDetail.birthDate))
        .gender(row.get(QFleekUserDetail.fleekUserDetail.gender))
        .orientation(row.get(QFleekUserDetail.fleekUserDetail.orientation))
        .build();
    }
  }
}
