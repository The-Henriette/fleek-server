package run.fleek.domain.users.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.users.term.dto.TermDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermService {

  private final TermRepository termRepository;

  @Transactional(readOnly = true)
  public List<Term> listTermsByIds(List<Long> termIds) {
    return termRepository.findAllByTermIdIn(termIds);
  }

  @Transactional(readOnly = true)
  public List<TermDto> listTerms() {
    return termRepository.findAll().stream()
        .map(TermDto::from)
        .collect(Collectors.toList());
  }
}
