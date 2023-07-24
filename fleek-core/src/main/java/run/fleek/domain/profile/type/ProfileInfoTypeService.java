package run.fleek.domain.profile.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileInfoTypeService {

  private final ProfileInfoTypeRepository profileInfoTypeRepository;

  @Transactional(readOnly = true)
  public List<ProfileInfoType> listProfileInfoTypes() {
    return profileInfoTypeRepository.findAll();
  }
}
