package run.fleek.domain.users.platform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import run.fleek.domain.users.FleekUser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPlatformInfoService {
  private final UserPlatformInfoRepository userPlatformInfoRepository;

  @Transactional
  public void putPlatformInfo(FleekUser fleekUser, String platformType, String pushToken, String previousPushToken) {
    UserPlatformInfo userPlatformInfo;

    if (StringUtils.hasLength(previousPushToken)) {
      userPlatformInfo = userPlatformInfoRepository.findByPushToken(previousPushToken)
        .orElseThrow(() -> new IllegalArgumentException("UserPlatformInfo not found"));
      userPlatformInfo.setPushToken(pushToken);
      userPlatformInfo.setPlatformType(platformType);
    } else {
      userPlatformInfo = UserPlatformInfo.builder()
        .fleekUser(fleekUser)
        .platformType(platformType)
        .pushToken(pushToken)
        .build();
    }

    userPlatformInfoRepository.save(userPlatformInfo);
  }

  @Transactional(readOnly = true)
  public List<UserPlatformInfo> listPlatformInfos(FleekUser fleekUser) {
    return userPlatformInfoRepository.findByFleekUser(fleekUser);
  }
}
