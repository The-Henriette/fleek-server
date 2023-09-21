package run.fleek.domain.fruitman.tracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.fruitman.deal.Deal;

import java.util.List;

@Repository
public interface UserDeliveryDetailRepository extends JpaRepository<UserDeliveryDetail, Long> {
  UserDeliveryDetail findByUserDeal(UserDeal userDeal);

  List<UserDeliveryDetail> findAllByUserDeal_Deal(Deal deal);
}
