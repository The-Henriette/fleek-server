package run.fleek.common.client.sendbird.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdAddChatResponseDto {

  @JsonProperty("channel_url")
  private String channelUrl;

  private String name;

  @JsonProperty("cover_url")
  private String coverUrl;

  private String data;

  @JsonProperty("member_count")
  private int memberCount;

  @JsonProperty("joined_member_count")
  private int joinedMemberCount;

  @JsonProperty("max_length_message")
  private int maxLengthMessage;

  @JsonProperty("created_at")
  private long createdAt;

  @JsonProperty("custom_type")
  private String customType;

  @JsonProperty("is_distinct")
  private boolean isDistinct;

  @JsonProperty("is_super")
  private boolean isSuper;

  @JsonProperty("is_broadcast")
  private boolean isBroadcast;

  @JsonProperty("is_public")
  private boolean isPublic;

  @JsonProperty("is_discoverable")
  private boolean isDiscoverable;

  private boolean freeze;

  @JsonProperty("is_ephemeral")
  private boolean isEphemeral;

  @JsonProperty("unread_message_count")
  private int unreadMessageCount;

  @JsonProperty("unread_mention_count")
  private int unreadMentionCount;

  @JsonProperty("ignore_profanity_filter")
  private boolean ignoreProfanityFilter;

  private int id;

}
