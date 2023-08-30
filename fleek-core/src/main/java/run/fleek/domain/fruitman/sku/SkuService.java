package run.fleek.domain.fruitman.sku;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkuService {

  private final SkuRepository skuRepository;

  @Transactional
  public void addSku(Sku sku) {
    skuRepository.save(sku);
  }

  public List<Sku> listSku(List<Long> skuIds) {
    return skuRepository.findAllById(skuIds);
  }
}
