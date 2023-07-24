package run.fleek.application.profile;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.application.profile.dto.ProfileViewDto;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.dto.ProfileCategoryInfoDto;
import run.fleek.domain.profile.dto.ProfileInfoDto;
import run.fleek.domain.profile.dto.ProfileInfoOptionDto;
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.profile.info.ProfileInfo;
import run.fleek.domain.profile.info.ProfileInfoService;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.domain.profile.type.ProfileInfoTypeOption;
import run.fleek.domain.profile.vo.ProfileVo;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.FleekUserService;
import run.fleek.enums.ImageType;
import run.fleek.enums.ProfileInfoCategory;
import run.fleek.enums.ProfileInfoInputType;
import run.fleek.utils.TimeUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileApplication {

  private final ProfileService profileService;
  private final ProfileInfoService profileInfoService;
  private final ProfileImageService profileImageService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;

  public ProfileViewDto getProfileDetail(Long profileId) {
    ProfileVo targetProfile = profileService.getProfileVoById(profileId);
    List<ProfileInfo> profileInfoList = profileInfoService.listProfileInfoByProfile(targetProfile.getProfileId());
    List<ProfileImage> profileImageList = profileImageService.listProfileImageByProfileId(targetProfile.getProfileId());
    // TODO: add certifications
    return this.buildProfileDetail(targetProfile, profileInfoList, profileImageList);
  }

  private ProfileViewDto buildProfileDetail(ProfileVo profile, List<ProfileInfo> profileInfoList,
                                            List<ProfileImage> profileImageList) {
    ProfileViewDto profileViewDto = ProfileViewDto.builder()
      .profileId(profile.getProfileId())
      .profileName(profile.getProfileName())
      .age((long) TimeUtil.calculateAge(profile.getDateOfBirth()))
      .gender(profile.getGender().name())
      .orientation(profile.getOrientation().name())
      .bio(profile.getBio())
      .profileImages(profileImageList.stream()
        .filter(pi -> pi.getImageType().equals(ImageType.PROFILE_POST))
        .sorted(Comparator.comparing(ProfileImage::getOrderNumber))
        .map(ProfileImage::getImageUrl)
        .collect(Collectors.toList()))
      .certifications(Lists.newArrayList())
      .build();

    Map<ProfileInfoCategory, List<ProfileInfo>> profileInfoMap = profileInfoList.stream()
      .collect(Collectors.groupingBy(ProfileInfo::getProfileInfoCategory));

    List<ProfileCategoryInfoDto> profileInfoDtoList = profileInfoMap.entrySet().stream()
      .map(e -> {
        List<ProfileInfoDto> infos = e.getValue().stream()
          .map(pi -> {
            ProfileInfoType profileInfoType = profileInfoTypeHolder.getProfileInfoType(pi.getTypeCode());

            ProfileInfoDto profileInfoDto = ProfileInfoDto.builder()
              .typeCode(profileInfoType.getProfileInfoTypeCode())
              .typeName(profileInfoType.getProfileInfoTypeName())
              .inputType(profileInfoType.getInputType().name())
              .emoji(profileInfoType.getEmoji())
              .build();
            if (profileInfoType.getInputType().equals(ProfileInfoInputType.CUSTOM)) {
              profileInfoDto.setTypeValue(pi.getTypeValue());
              return profileInfoDto;
            }

            profileInfoDto.setTypeOptions(this.buildOptionDtoList(profileInfoType, pi));
            return profileInfoDto;
          })
          .collect(Collectors.toList());

        return ProfileCategoryInfoDto.builder()
          .categoryCode(e.getKey().name())
          .categoryDescription(e.getKey().getDescription())
          .infos(infos)
          .build();
      })
      .collect(Collectors.toList());

    profileViewDto.setDetails(profileInfoDtoList);
    return profileViewDto;
  }

  private List<ProfileInfoOptionDto> buildOptionDtoList(ProfileInfoType profileInfoType, ProfileInfo userProfileInfo) {
    List<String> optionCodeList = Lists.newArrayList();

    if (profileInfoType.getInputType().equals(ProfileInfoInputType.SINGLE)) {
      optionCodeList.add(userProfileInfo.getTypeOption());
    } else {
      String[] optionCodes = userProfileInfo.getTypeOption().split(",");
      optionCodeList.addAll(Arrays.asList(optionCodes));
    }

    return optionCodeList.stream()
      .map(code -> {
        ProfileInfoTypeOption option = profileInfoTypeHolder.getProfileInfoTypeOption(code);

        return ProfileInfoOptionDto.builder()
          .optionCode(code)
          .optionName(option.getOptionName())
          .optionEmoji(option.getEmoji())
          .build();
      })
      .collect(Collectors.toList());
  }
}
