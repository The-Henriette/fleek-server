package run.fleek.common.client.toss;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import run.fleek.common.client.FleekWebClient;
import run.fleek.common.client.toss.dto.TossPaymentRequestDto;
import run.fleek.common.client.toss.dto.TossPaymentResponseDto;
import run.fleek.utils.JsonUtil;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossWebclient {

  @Value("${external.url.toss}")
  private String tossPaymentsUrl;
  @Value("${external.token.toss}")
  private String tossPaymentsToken;

  private final FleekWebClient fleekWebClient;

  public TossPaymentResponseDto requestPayment(TossPaymentRequestDto tossPaymentRequestDto) {
    Map<String, String> headers = Maps.newHashMap();
    byte[] encodedBytes = Base64.getEncoder().encode((tossPaymentsToken + ":").getBytes());
    String encodedString = new String(encodedBytes);

    headers.put("Content-Type", "application/json");
    headers.put("Authorization", String.format("Basic %s", encodedString));

    String tossResponse = fleekWebClient.postFromMap(tossPaymentsUrl + "/v1/payments/confirm",
        tossPaymentRequestDto,
        headers
      )
      .bodyToMono(String.class)
      .block();

    return JsonUtil.read(tossResponse, TossPaymentResponseDto.class);
  }
}
