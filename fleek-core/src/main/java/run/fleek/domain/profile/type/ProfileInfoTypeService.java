package run.fleek.domain.profile.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileInfoTypeService {

  private final ProfileInfoTypeRepository profileInfoTypeRepository;
}
