package run.fleek.api.controller.term;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.domain.users.term.TermService;
import run.fleek.domain.users.term.dto.TermDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TermController {

  private final TermService termService;

  @GetMapping("/terms")
  public List<TermDto> listTerms() {
    return termService.listTerms();
  }
}
