package run.fleek.application.profile;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import run.fleek.application.profile.dto.ProfileInfoMetaDto;
import run.fleek.application.profile.dto.ProfileOptionMetaDto;
import run.fleek.application.profile.dto.ProfileViewDto;
import run.fleek.application.profile.vo.ProfileEditDto;
import run.fleek.common.exception.FleekException;
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
import run.fleek.enums.ImageType;
import run.fleek.enums.ProfileInfoCategory;
import run.fleek.enums.ProfileInfoInputType;
import run.fleek.utils.TimeUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ProfileApplication {

  private final ProfileService profileService;
  private final ProfileInfoService profileInfoService;
  private final ProfileImageService profileImageService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;
  private final ProfileInfoProcessor profileInfoProcessor;

  @Transactional
  public void putProfileDetail(ProfileEditDto dto) {
    Profile targetProfile = profileService.getProfileByProfileName(dto.getProfileName())
        .orElseThrow(new FleekException("profile.not.found"));
    if (StringUtils.isEmpty(targetProfile.getBio()) || !targetProfile.getBio().equals(dto.getBio())) {
        targetProfile.setBio(dto.getBio());
        profileService.addProfile(targetProfile);
    }

    profileImageService.putProfileImageList(targetProfile, dto.getProfileImages());

    List<ProfileInfo> originalProfileInfoList = profileInfoService.listProfileInfoByProfile(targetProfile.getProfileId());
    Map<String /* typeCode */, ProfileInfo> originalInfoMap = originalProfileInfoList.stream()
        .collect(Collectors.toMap(ProfileInfo::getTypeCode, Function.identity()));

    List<ProfileInfo> updateTargets = dto.getDetails().stream()
      .filter(info -> !(StringUtils.isEmpty(info.getTypeValue()) && CollectionUtils.isEmpty(info.getTypeOptions())))
      .flatMap(info -> {
        ProfileInfoType profileInfoType = profileInfoTypeHolder.getProfileInfoType(info.getTypeCode());
        ProfileInfo processorResult =
          profileInfoProcessor.process(targetProfile, originalInfoMap.get(info.getTypeCode()), profileInfoType, info);

        if (Objects.isNull(processorResult)) {
          return Stream.empty();
        }

        return Stream.of(processorResult);
      }).collect(Collectors.toList());
    profileInfoService.putProfileInfos(updateTargets);
  }

  public ProfileViewDto getProfileDetail(String profileName) {
    ProfileVo targetProfile = profileService.getProfileVoByName(profileName);
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
      .dateOfBirth(profile.getDateOfBirth())
      .gender(profile.getGender().name())
      .orientation(profile.getOrientation().name())
      .bio(profile.getBio())
      .profileImages(profileImageList.stream()
        .filter(pi -> pi.getImageType().equals(ImageType.PROFILE_POST))
        .sorted(Comparator.comparing(ProfileImage::getOrderNumber))
        .map(ProfileImage::getImageUrl)
        .collect(Collectors.toList()))
      .certifications(Lists.newArrayList())
      .profileImagePaths(profileImageList.stream()
        .filter(pi -> pi.getImageType().equals(ImageType.PROFILE_POST))
        .sorted(Comparator.comparing(ProfileImage::getOrderNumber))
        .map(ProfileImage::getImageUrl)
        .collect(Collectors.toList()))
      .build();

    Map<ProfileInfoCategory, List<ProfileInfoType>> profileInfoMap = profileInfoTypeHolder.getProfileInfoTypeList().stream()
      .collect(Collectors.groupingBy(ProfileInfoType::getProfileInfoCategory));
    Map<String, ProfileInfo> userProfileInfoMap = profileInfoList.stream()
      .collect(Collectors.toMap(ProfileInfo::getTypeCode, Function.identity()));

    List<ProfileCategoryInfoDto> profileInfoDtoList = profileInfoMap.entrySet().stream()
      .map(e -> {
        List<ProfileInfoDto> infos = e.getValue().stream()
          .map(pi -> {
            ProfileInfo userProfileInfo = userProfileInfoMap.get(pi.getProfileInfoTypeCode());

            ProfileInfoDto profileInfoDto = ProfileInfoDto.builder()
              .typeCode(pi.getProfileInfoTypeCode())
              .typeName(pi.getProfileInfoTypeName())
              .inputType(pi.getInputType().name())
              .emoji(pi.getEmoji())
              .order(pi.getOrderNumber())
              .build();
            if (Objects.nonNull(userProfileInfo) && pi.getInputType().equals(ProfileInfoInputType.CUSTOM)) {
              profileInfoDto.setTypeValue(userProfileInfo.getTypeValue());
              return profileInfoDto;
            }

            profileInfoDto.setTypeOptions(this.buildOptionDtoList(pi, userProfileInfo));
            return profileInfoDto;
          })
          .sorted(Comparator.comparingInt(ProfileInfoDto::getOrder))
          .collect(Collectors.toList());

        return ProfileCategoryInfoDto.builder()
          .categoryCode(e.getKey().name())
          .categoryDescription(e.getKey().getDescription())
          .category(e.getKey())
          .infos(infos)
          .build();
      })
      .sorted(Comparator.comparingInt(pci -> pci.getCategory().getOrder()))
      .collect(Collectors.toList());

    profileViewDto.setDetails(profileInfoDtoList);
    return profileViewDto;
  }

  private List<ProfileInfoOptionDto> buildOptionDtoList(ProfileInfoType profileInfoType, ProfileInfo userProfileInfo) {
      if (Objects.isNull(userProfileInfo)) {
          return Lists.newArrayList();
      }

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

    public List<ProfileInfoMetaDto> listProfileMeta() {
        List<ProfileInfoType> profileInfoTypeList = profileInfoTypeHolder.getProfileInfoTypeList();
        Map<String, List<ProfileInfoTypeOption>> typeToOptions = profileInfoTypeHolder.getProfileInfoTypeOptionList().stream()
            .collect(Collectors.groupingBy(
                ProfileInfoTypeOption::getProfileInfoTypeCode
            ));

        typeToOptions.forEach((k, v) -> typeToOptions.put(k, v.stream()
            .sorted(Comparator.comparing(ProfileInfoTypeOption::getOrderNumber))
            .collect(Collectors.toList())));

        return profileInfoTypeList.stream()
            .map(type -> ProfileInfoMetaDto.builder()
                .typeCode(type.getProfileInfoTypeCode())
                .typeName(type.getProfileInfoTypeName())
                .options(Optional.ofNullable(typeToOptions.get(type.getProfileInfoTypeCode())).orElse(Lists.newArrayList()).stream()
                    .map(option -> ProfileOptionMetaDto.builder()
                        .optionCode(option.getOptionCode())
                        .optionName(option.getOptionName())
                        .build())
                    .collect(Collectors.toList()))
                .build())
            .collect(Collectors.toList());
    }
}
