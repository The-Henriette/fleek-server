package run.fleek.domain.users.term.dto;

import lombok.*;
import run.fleek.domain.users.term.Term;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermDto {
  private Long termId;
  private String termName;
  private Boolean mandatory;
  private String termAppeal;
  private String termContentsUrl;

  public static TermDto from(Term term) {
    return TermDto.builder()
      .termId(term.getTermId())
      .termName(term.getTermName())
      .mandatory(term.getMandatory())
      .termAppeal(term.getTermAppeal())
      .termContentsUrl(term.getTermContentsUrl())
      .build();
  }
}
