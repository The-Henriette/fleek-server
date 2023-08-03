package run.fleek.application.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDetailDto {
  private ChatCounterpartDto counterpart;
  private Boolean wasAnonymous;
  private Boolean hasAnonymous;
}
