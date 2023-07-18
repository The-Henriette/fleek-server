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
  public void addTraceUserDetail(FleekUser fleekUser, SignUpDto signUpDto) {
    fleekUserDetailRepository.save(FleekUserDetail.create(fleekUser, signUpDto));
  }
}
