package run.fleek.application.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalChatDto {
  private String chatUserId;
  private String senderProfileName;
  private String channelUrl;
  private String profileChatCode;
}
