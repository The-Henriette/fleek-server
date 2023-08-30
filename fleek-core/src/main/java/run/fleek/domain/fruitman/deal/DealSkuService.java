package run.fleek.domain.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealSkuService {

  private final DealSkuRepository dealSkuRepository;

  @Transactional
  public void addDealSkus(List<DealSku> dealSkuList) {
    dealSkuRepository.saveAll(dealSkuList);
  }
}
