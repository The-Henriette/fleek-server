package run.fleek.domain.fruitman.deal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealConstraintRepository extends JpaRepository<DealConstraint, Long> {
  DealConstraint findByDeal(Deal deal);

  List<DealConstraint> findAllByDeal_DealIdIn(List<Long> dealIds);
}
