package run.fleek.application.profile;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.dto.ProfileInfoDto;
import run.fleek.domain.profile.dto.ProfileInfoOptionDto;
import run.fleek.domain.profile.info.ProfileInfo;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.utils.JoinUtil;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileInfoProcessor {

    public ProfileInfo process(Profile targetProfile,
                               ProfileInfo originalProfileInfo,
                               ProfileInfoType profileInfoType,
                               ProfileInfoDto profileInfoDto) {
        if (CollectionUtils.isEmpty(profileInfoDto.getTypeOptions()) && StringUtils.isEmpty(profileInfoDto.getTypeValue())) {
            return null;
        }

        ProfileInfo resultProfileInfo = Objects.isNull(originalProfileInfo) ? ProfileInfo.builder()
          .profile(targetProfile)
          .profileInfoCategory(profileInfoType.getProfileInfoCategory())
          .typeCode(profileInfoType.getProfileInfoTypeCode())
          .typeName(profileInfoType.getProfileInfoTypeName())
          .build() : originalProfileInfo;

        resultProfileInfo.setTypeValue(profileInfoDto.getTypeValue());
        resultProfileInfo.setTypeOption(JoinUtil.COMMA_JOINER.join(profileInfoDto.getTypeOptions().stream()
          .map(ProfileInfoOptionDto::getOptionCode)
          .collect(Collectors.toList())));

        return resultProfileInfo;
    }

}
