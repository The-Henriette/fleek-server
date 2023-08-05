package run.fleek.api.controller.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.exchange.ExchangeApplication;
import run.fleek.application.exchange.dto.ExchangeRequestDto;
import run.fleek.application.exchange.dto.ExchangeResponseDto;
import run.fleek.application.exchange.dto.ExchangeWatchDto;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.exchange.Exchange;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

  private final ExchangeApplication exchangeApplication;

  @PostMapping("/exchange/request")
  public ExchangeResponseDto requestExchange(@RequestBody ExchangeRequestDto requestDto) {
    return exchangeApplication.requestExchange(requestDto);
  }

  @PostMapping("/exchange/{exchangeId}/accept/{profileName}")
  public FleekGeneralResponse acceptExchange(@PathVariable Long exchangeId, @PathVariable String profileName) {
    exchangeApplication.processExchange(exchangeId, profileName, "accepted");
    return FleekGeneralResponse.builder()
      .success(true)
      .build();
  }

  @PostMapping("/exchange/{exchangeId}/reject/{profileName}")
  public FleekGeneralResponse rejectExchange(@PathVariable Long exchangeId, @PathVariable String profileName) {
    exchangeApplication.processExchange(exchangeId, profileName, "rejected");
    return FleekGeneralResponse.builder()
      .success(true)
      .build();
  }

  @PostMapping("/exchange/{exchangeId}/watch/{profileName}")
  public ExchangeWatchDto watchExchange(@PathVariable Long exchangeId, @PathVariable String profileName) {
    return exchangeApplication.watchExchange(exchangeId, profileName);
  }

}
