package run.fleek.domain.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
