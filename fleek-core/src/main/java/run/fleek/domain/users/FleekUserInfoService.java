package run.fleek.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FleekUserInfoService {

  private final FleekUserInfoRepository fleekUserInfoRepository;

  @Transactional
  public void addTraceUserInfo(FleekUser fleekUser) {
    fleekUserInfoRepository.save(FleekUserInfo.builder()
      .fleekUser(fleekUser)
      .build());
  }

  @Transactional(readOnly = true)
  public FleekUserInfo getTraceUserInfoByUser(FleekUser fleekUser) {
    return fleekUserInfoRepository.findByFleekUser(fleekUser);
  }
}
