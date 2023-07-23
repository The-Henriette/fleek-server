package run.fleek.application.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.term.Term;
import run.fleek.domain.users.term.TermService;
import run.fleek.domain.users.term.UserTerm;
import run.fleek.domain.users.term.UserTermService;
import run.fleek.domain.users.term.dto.UserTermDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TermApplication {

  private final TermService termService;
  private final UserTermService userTermService;

  public void addUserTerms(List<UserTermDto> userTermDtoList, FleekUser fleekUser) {
    List<Term> termList = termService.listTermsByIds(userTermDtoList.stream()
      .map(UserTermDto::getTermId)
      .collect(Collectors.toList()));

    Map<Long, Boolean> termAgreementMap = userTermDtoList.stream()
      .collect(Collectors.toMap(UserTermDto::getTermId, UserTermDto::getAgreed));

    List<UserTerm> userTerms = termList.stream()
      .map(term -> UserTerm.builder()
        .term(term)
        .fleekUser(fleekUser)
        .agreed(termAgreementMap.get(term.getTermId()))
        .build())
      .collect(Collectors.toList());

    userTermService.addUserTerms(userTerms);
  }
}
