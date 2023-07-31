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

  public SendbirdAddChatResponseDto addChat(List<String> sendbirdUserIds) {
    String sendbirdResponse = fleekWebClient.postFromMap(sendbirdUri + "group_channels",
        SendbirdAddChatDto.builder()
          .userIds(sendbirdUserIds)
          .isDistinct(false)
          .build(),
      getHeaders()
      )
    .bodyToMono(String.class)
    .block();

    return JsonUtil.read(sendbirdResponse, SendbirdAddChatResponseDto.class);
  }

  public SendbirdSendMessageDto sendMessage(String sendbirdChannelUrl, String message, String sendbirdUserId) {
    if (!StringUtils.hasLength(message)) {
      return null;
    }
    String sendbirdResponse = fleekWebClient.postFromMap(sendbirdUri + "group_channels/" + sendbirdChannelUrl + "/messages",
        SendbirdSendMessageDto.builder()
          .messageType("MESG")
          .userId(sendbirdUserId)
          .message(message)
          .build(),
      getHeaders()
      )
    .bodyToMono(String.class)
    .block();

    return JsonUtil.read(sendbirdResponse, SendbirdSendMessageDto.class);
  }

  private Map<String, String> getHeaders() {
    Map<String, String> headers = Maps.newHashMap();

    headers.put("Api-Token", sendbirdToken);
    return headers;
  }

}
