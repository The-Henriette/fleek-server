package run.fleek.domain.chat.vo;

import com.querydsl.core.types.MappingProjection;
import lombok.*;
import run.fleek.domain.chat.QProfileChat;
import run.fleek.domain.profile.QProfile;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ProfileChatVo {

  private Long senderProfileId;
  private String senderProfileName;
  private String senderChatProfileKey;
  private String anonymousChatKey;
  private String profileChatCode;
  private Long receiverProfileId;
  private String receiverProfileName;
  private String receiverProfileKey;
  private String channelUrl;

  public ProfileChatVo(Long senderProfileId, String senderProfileName, String senderChatProfileKey,
                       String anonymousChatKey, String profileChatCode, Long receiverProfileId, String receiverProfileName,
                       String receiverProfileKey, String channelUrl) {
    this.senderProfileId = senderProfileId;
    this.senderProfileName = senderProfileName;
    this.senderChatProfileKey = senderChatProfileKey;
    this.anonymousChatKey = anonymousChatKey;
    this.profileChatCode = profileChatCode;
    this.receiverProfileId = receiverProfileId;
    this.receiverProfileName = receiverProfileName;
    this.receiverProfileKey = receiverProfileKey;
    this.channelUrl = channelUrl;
  }

}
