package run.fleek.domain.users.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermService {

  private final TermRepository termRepository;

  @Transactional(readOnly = true)
  public List<Term> listTermsByIds(List<Long> termIds) {
    return termRepository.findAllByTermIdIn(termIds);
  }
}
