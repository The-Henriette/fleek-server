package run.fleek.domain.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealConstraintService {

  private final DealConstraintRepository dealConstraintRepository;

  @Transactional
  public void addDealConstraint(DealConstraint from) {
    dealConstraintRepository.save(from);
  }

  @Transactional
  public DealConstraint getDealConstraint(Deal deal) {
    return dealConstraintRepository.findByDeal(deal);
  }

  @Transactional(readOnly = true)
  public List<DealConstraint> listDealConstraintByDealIds(List<Long> dealIds) {
    return dealConstraintRepository.findAllByDeal_DealIdIn(dealIds);
  }
}
