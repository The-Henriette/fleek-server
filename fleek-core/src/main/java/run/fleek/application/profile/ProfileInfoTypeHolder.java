package run.fleek.application.profile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.domain.profile.type.ProfileInfoTypeOption;
import run.fleek.domain.profile.type.ProfileInfoTypeOptionService;
import run.fleek.domain.profile.type.ProfileInfoTypeService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileInfoTypeHolder {

  private final ProfileInfoTypeService profileInfoTypeService;
  private final ProfileInfoTypeOptionService profileInfoTypeOptionService;

  public final List<ProfileInfoType> infoTypeList = Lists.newArrayList();
  public final List<ProfileInfoTypeOption> optionList = Lists.newArrayList();
  public final Map<String /* profileInfoTypeCode */, ProfileInfoType> profileInfoTypeMap = Maps.newHashMap();
  public final Map<String /* profileInfoOptionCode */, ProfileInfoTypeOption> profileInfoOptionMap = Maps.newHashMap();

  @PostConstruct
  public void init() {
    infoTypeList.addAll(profileInfoTypeService.listProfileInfoTypes());
    optionList.addAll(profileInfoTypeOptionService.listProfileInfoTypeOptions());

    Map<String, ProfileInfoType> infoTypeMap = infoTypeList.stream()
      .collect(Collectors.toMap(ProfileInfoType::getProfileInfoTypeCode, infoType -> infoType));
    profileInfoTypeMap.putAll(infoTypeMap);

    Map<String, ProfileInfoTypeOption> infoTypeOptionMap = optionList.stream()
      .collect(Collectors.toMap(ProfileInfoTypeOption::getOptionCode, option -> option));
    profileInfoOptionMap.putAll(infoTypeOptionMap);
  }

  public ProfileInfoType getProfileInfoType(String code) {
    return profileInfoTypeMap.get(code);
  }

  public ProfileInfoTypeOption getProfileInfoTypeOption(String code) {
    return profileInfoOptionMap.get(code);
  }

  public List<ProfileInfoType> getProfileInfoTypeList() {
    return infoTypeList;
  }

  public List<ProfileInfoTypeOption> getProfileInfoTypeOptionList() {
    return optionList;
  }
}
