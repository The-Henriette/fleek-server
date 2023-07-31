package run.fleek.common.client.sendbird.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdAddUserResponseDto {
  @JsonProperty("user_id")
  private String userId;

  private String nickname;

  @JsonProperty("profile_url")
  private String profileUrl;

  @JsonProperty("require_auth_for_profile_image")
  private boolean requireAuthForProfileImage;

  private Map<String, Object> metadata;
  private String access_token;
  private List<String> session_tokens;

  @JsonProperty("is_online")
  private boolean isOnline;

  @JsonProperty("last_seen_at")
  private long lastSeenAt;

  private List<String> discovery_keys;

  @JsonProperty("has_ever_logged_in")
  private boolean hasEverLoggedIn;

  @JsonProperty("is_active")
  private boolean isActive;

  @JsonProperty("is_created")
  private boolean isCreated;

  @JsonProperty("phone_number")
  private String phoneNumber;

}
