package run.fleek.domain.profile.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileInfoService {

  private final ProfileInfoRepository profileInfoRepository;
}
