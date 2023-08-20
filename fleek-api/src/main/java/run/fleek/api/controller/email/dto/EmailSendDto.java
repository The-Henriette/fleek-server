package run.fleek.api.controller.email.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendDto {
  private String to;
  private String subject;
  private String text;
}
