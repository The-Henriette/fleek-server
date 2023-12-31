package run.fleek.domain.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.fruitman.deal.dto.DealPageDto;
import run.fleek.common.exception.FleekException;
import run.fleek.domain.fruitman.deal.vo.DealVo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {
  private final DealRepository dealRepository;

  @Transactional
  public void addDeal(Deal deal) {
    dealRepository.save(deal);
  }

  @Transactional
  public List<DealVo> listDeal(Long from, Long to) {
    return dealRepository.listDealIn(from, to);
  }

  @Transactional
  public List<Deal> listDealByIds(List<Long> dealIds) {
    return dealRepository.findAllById(dealIds);
  }

  @Transactional(readOnly = true)
  public Deal getDeal(Long dealId) {
    return dealRepository.findById(dealId)
      .orElseThrow(() -> new FleekException("존재하지 않는 상품입니다."));
  }

  @Transactional(readOnly = true)
  public DealPageDto pageDealsBefore(Long from, int size, int page) {
    return dealRepository.pageDealsBefore(from, size, page);
  }
}
