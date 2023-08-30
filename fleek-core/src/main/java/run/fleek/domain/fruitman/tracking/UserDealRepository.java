package run.fleek.domain.fruitman.tracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.fruitman.deal.Deal;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDealRepository extends JpaRepository<UserDeal, Long>, UserDealRepositoryCustom {
  Optional<UserDeal> findFirstByDeal(Deal deal);

  List<UserDeal> findAllByDeal(Deal deal);

  Optional<UserDeal> findByOrderId(String orderId);
}
