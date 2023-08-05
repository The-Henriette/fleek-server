package run.fleek.domain.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

  private final ExchangeRepository exchangeRepository;

  @Transactional
  public Exchange addExchange(Exchange exchange) {
    return exchangeRepository.save(exchange);
  }

  @Transactional(readOnly = true)
  public Exchange getExchange(Long exchangeId) {
    return exchangeRepository.getById(exchangeId);
  }
}
