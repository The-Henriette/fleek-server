package run.fleek.common.client.sendbird.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdAddUserDto {
    @JsonProperty("user_id")
    private String userId;

    private String nickname;

    @JsonProperty("profile_url")
    private String profileUrl;

}
