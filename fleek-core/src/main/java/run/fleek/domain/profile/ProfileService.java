package run.fleek.domain.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileService {

  private final ProfileRepository profileRepository;
}
