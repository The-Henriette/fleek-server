package run.fleek.common.client.toss;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import run.fleek.common.client.FleekWebClient;
import run.fleek.common.client.toss.dto.TossCancelRequestDto;
import run.fleek.common.client.toss.dto.TossErrorResponse;
import run.fleek.common.client.toss.dto.TossPaymentRequestDto;
import run.fleek.common.client.toss.dto.TossPaymentResponseDto;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.fruitman.tracking.UserPayment;
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
  private final FleekUserContext fleekUserContext;

  public TossPaymentResponseDto requestPayment(TossPaymentRequestDto tossPaymentRequestDto) {
    Map<String, String> headers = Maps.newHashMap();



    byte[] encodedBytes = !fleekUserContext.getUserId().equals(0L) ?
      Base64.getEncoder().encode((tossPaymentsToken + ":").getBytes())
    : Base64.getEncoder().encode(("test_sk_GePWvyJnrKbmyAgaaObVgLzN97Eo" + ":").getBytes());
    String encodedString = new String(encodedBytes);

    headers.put("Content-Type", "application/json");
    headers.put("Authorization", String.format("Basic %s", encodedString));

    try {
      String tossResponse = fleekWebClient.postFromMap(tossPaymentsUrl + "/v1/payments/confirm",
        tossPaymentRequestDto,
        headers
      )
      .bodyToMono(String.class)
      .block();

      TossPaymentResponseDto response = JsonUtil.read(tossResponse, TossPaymentResponseDto.class);
      response.setSuccess(true);
      return response;
    } catch (Exception e) {

      String tossResponse = e.getMessage();
      TossErrorResponse tossErrorResponse = JsonUtil.read(tossResponse, TossErrorResponse.class);
      return TossPaymentResponseDto.builder()
        .success(false)
        .errorMessage(tossErrorResponse.getMessage())
        .errorCode(tossErrorResponse.getCode())
        .build();
    }

  }


  public TossPaymentResponseDto cancelPayment(UserPayment userPayment) {
    Map<String, String> headers = Maps.newHashMap();
    byte[] encodedBytes = Base64.getEncoder().encode((tossPaymentsToken + ":").getBytes());
    String encodedString = new String(encodedBytes);

    headers.put("Idempotency-Key", userPayment.getTossPaymentKey());
    headers.put("Content-Type", "application/json");
    headers.put("Authorization", String.format("Basic %s", encodedString));

    String tossResponse = fleekWebClient.postFromMap(tossPaymentsUrl + String.format("/v1/payments/%s/cancel", userPayment.getTossPaymentKey()),
        TossCancelRequestDto.builder().cancelReason("고객 변심").build(),
        headers
      )
      .bodyToMono(String.class)
      .block();


    return JsonUtil.read(tossResponse, TossPaymentResponseDto.class);

  }
}
