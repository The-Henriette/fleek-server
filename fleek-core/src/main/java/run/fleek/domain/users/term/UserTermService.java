package run.fleek.domain.users.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTermService {

  private final UserTermRepository userTermRepository;
}
