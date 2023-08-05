package run.fleek.common.client.sendbird.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdSendMessageDto {

  @JsonProperty("message_id")
  private Long messageId;

  @JsonProperty("message_type")
  private String messageType;

  @JsonProperty("user_id")
  private String userId;

  private String message;

  private String data;

  @JsonProperty("custom_type")
  private String customType;
}
