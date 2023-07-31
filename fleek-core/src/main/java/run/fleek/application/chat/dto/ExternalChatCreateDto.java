package run.fleek.application.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalChatCreateDto {
  private String senderProfileName;
  private String receiverProfileName;
  private String firstMessage;
}
