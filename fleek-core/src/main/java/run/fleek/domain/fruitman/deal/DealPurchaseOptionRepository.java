package run.fleek.domain.fruitman.deal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealPurchaseOptionRepository extends JpaRepository<DealPurchaseOption, Long> {
  List<DealPurchaseOption> findByDeal(Deal deal);

  List<DealPurchaseOption> findAllByDealIn(List<Deal> deals);
}
