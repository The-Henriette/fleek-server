package run.fleek.domain.profile.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileImageService {

  private final ProfileImageRepository profileImageRepository;

}
