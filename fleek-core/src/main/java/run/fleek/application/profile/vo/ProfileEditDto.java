package run.fleek.application.profile.vo;

import lombok.*;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.profile.dto.ProfileInfoDto;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEditDto {
    private String profileName;
    private String bio;
    private List<String> profileImages;
    private List<ProfileInfoDto> details;
}
