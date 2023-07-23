package run.fleek.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.fleek.domain.users.dto.SignUpDto;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FleekUserDetailService {

  private final FleekUserDetailRepository fleekUserDetailRepository;

  @Transactional
  public void addUserDetail(FleekUserDetail userDetail) {
    fleekUserDetailRepository.save(userDetail);
  }
}
