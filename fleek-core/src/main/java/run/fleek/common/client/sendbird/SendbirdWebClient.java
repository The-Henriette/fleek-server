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

//  private final String sendbirdUri = "https://api-6EAB14E9-EB51-4E9C-B14C-1B25E2420C9C.sendbird.com/v3/"; // production
  private final String sendbirdUri = "https://api-BB65F2D7-EAC5-403E-8E9B-40103DFE4AA8.sendbird.com/v3/"; // local

//  private final String sendbirdToken = "f929720544a30c5c2d0a7668d9ef9b2fbc07f22a"; // production
  private final String sendbirdToken = "5af7260d38456d19baa0ade886dffb95931b79ca"; // local

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

  public SendbirdAddChatResponseDto addChat(List<String> sendbirdUserIds, String nickname, boolean external) {
    Map<String, String> customData = Maps.newHashMap();
    if (external) {
      customData.put("anonymousProfile", nickname);
    }

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

  public SendbirdSendMessageDto getMessage(String channelUrl, String messageId) {
    String sendbirdResponse = fleekWebClient.getFromMap(sendbirdUri + "group_channels/" + channelUrl + "/messages/" + messageId,
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
