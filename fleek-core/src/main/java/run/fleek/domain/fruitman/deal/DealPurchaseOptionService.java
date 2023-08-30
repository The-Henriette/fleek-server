package run.fleek.domain.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealPurchaseOptionService {

  private final DealPurchaseOptionRepository dealPurchaseOptionRepository;

  @Transactional
  public void addDealPurchaseOptions(List<DealPurchaseOption> options) {
    dealPurchaseOptionRepository.saveAll(options);
  }

  @Transactional
  public List<DealPurchaseOption> listDealPurchaseOption(Deal deal) {
    return dealPurchaseOptionRepository.findByDeal(deal);
  }
}
