package run.fleek.domain.users.term.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTermDto {
  private Long termId;
  private Boolean agreed;
}
