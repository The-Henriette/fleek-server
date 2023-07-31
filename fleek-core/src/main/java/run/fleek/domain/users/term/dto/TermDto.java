package run.fleek.domain.users.term.dto;

import lombok.*;

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
}
