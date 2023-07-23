package run.fleek.domain.users.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTermService {

  private final UserTermRepository userTermRepository;

  @Transactional
  public void addUserTerms(List<UserTerm> userTerms) {
    userTermRepository.saveAll(userTerms);
  }
}
