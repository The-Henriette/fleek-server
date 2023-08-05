package run.fleek.common.client.sendbird;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import run.fleek.common.client.FleekWebClient;
import run.fleek.common.client.sendbird.dto.*;
import run.fleek.utils.JsonUtil;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendbirdWebClient {

  private final String sendbirdUri = "https://api-AC3C369D-9B6F-4D89-A7B0-47A7460DED7E.sendbird.com/v3/";

  private final String sendbirdToken = "03a0e5dc6158e8a312ee30e27b1c8fa1bddaf1c1";

  private final FleekWebClient fleekWebClient;

  public SendbirdAddUserResponseDto addUser(String userId, String nickname, String profileUrl) {
    String sendbirdResponse = fleekWebClient.postFromMap(sendbirdUri + "users",
      SendbirdAddUserDto.builder()
        .userId(userId)
        .nickname(nickname)
        .profileUrl(profileUrl)
        .build(),
      getHeaders()
      )
    .bodyToMono(String.class)
    .block();

    return JsonUtil.read(sendbirdResponse, SendbirdAddUserResponseDto.class);
  }

  public SendbirdAddChatResponseDto addChat(List<String> sendbirdUserIds, String nickname) {
    Map<String, String> customData = Maps.newHashMap();
    customData.put("anonymousProfile", nickname);

    String sendbirdResponse = fleekWebClient.postFromMap(sendbirdUri + "group_channels",
        SendbirdAddChatDto.builder()
          .userIds(sendbirdUserIds)
          .isDistinct(false)
          .data(JsonUtil.write(customData))
          .build(),
      getHeaders()
      )
    .bodyToMono(String.class)
    .block();

    return JsonUtil.read(sendbirdResponse, SendbirdAddChatResponseDto.class);
  }

  public SendbirdSendMessageDto sendMessage(String sendbirdChannelUrl, String message, String sendbirdUserId,
                                            String customType, String data) {
    if (!StringUtils.hasLength(message)) {
      return null;
    }

    SendbirdSendMessageDto sendMessage = SendbirdSendMessageDto.builder()
      .messageType("MESG")
      .userId(sendbirdUserId)
      .message(message)
      .build();

    if (StringUtils.hasLength(customType)) {
      sendMessage.setCustomType(customType);
    }

    if (StringUtils.hasLength(data)) {
      sendMessage.setData(data);
    }

    String sendbirdResponse = fleekWebClient.postFromMap(sendbirdUri + "group_channels/" + sendbirdChannelUrl + "/messages",
       sendMessage,
      getHeaders()
      )
    .bodyToMono(String.class)
    .block();

    return JsonUtil.read(sendbirdResponse, SendbirdSendMessageDto.class);
  }

  public SendbirdSendMessageDto updateMessage(String messageId, String channelUrl, String message,
                                              String customType, String data) {
    SendbirdSendMessageDto sendMessage = SendbirdSendMessageDto.builder()
      .messageType("MESG")
      .message(message)
      .build();

    if (StringUtils.hasLength(customType)) {
      sendMessage.setCustomType(customType);
    }

    if (StringUtils.hasLength(data)) {
      sendMessage.setData(data);
    }

    String sendbirdResponse = fleekWebClient.putFromMap(sendbirdUri + "group_channels/" + channelUrl + "/messages/" + messageId,
        sendMessage,
      getHeaders()
      ).bodyToMono(String.class)
      .block();

    return JsonUtil.read(sendbirdResponse, SendbirdSendMessageDto.class);
  }

  public void inviteMember(String memberId, String channelUrl) {
    fleekWebClient.postFromMap(sendbirdUri + "group_channels/" + channelUrl + "/invite",
        SendbirdUserInviteDto.builder()
          .userIds(List.of(memberId))
          .build(),
      getHeaders()
      ).bodyToMono(String.class).block();
  }

  private Map<String, String> getHeaders() {
    Map<String, String> headers = Maps.newHashMap();

    headers.put("Api-Token", sendbirdToken);
    return headers;
  }

}
