package run.fleek.common.client.nhn;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import run.fleek.common.client.FleekWebClient;
import run.fleek.common.client.nhn.dto.NhnMessageRequestDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NhnWebClient {

  private final FleekWebClient fleekWebClient;

  @Value("${external.token.nhn}")
  private String nhnToken;

  private final String nhnMessageUrl = "https://api-alimtalk.cloud.toast.com/alimtalk/v2.3/appkeys/ScHJApK1LlgyPUUU/messages";

  public void sendNhnMessage(String templateCode, List<NhnMessageRequestDto.NhnRecipientDto> recipientList) {
    Map<String, String> headers = Maps.newHashMap();
    headers.put("X-Secret-Key", nhnToken);

    NhnMessageRequestDto request = NhnMessageRequestDto.builder()
      .templateCode(templateCode)
      .recipientList(recipientList)
      .build();

    String response = fleekWebClient.postFromMap(nhnMessageUrl,
      request,
      headers
      ).bodyToMono(String.class)
      .block();

  }
}
