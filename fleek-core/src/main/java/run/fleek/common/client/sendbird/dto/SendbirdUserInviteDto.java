package run.fleek.common.client.sendbird.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdUserInviteDto {
  @JsonProperty("user_ids")
  private List<String> userIds;
}
