package run.fleek.domain.fruitman.tracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeliveryDetailRepository extends JpaRepository<UserDeliveryDetail, Long> {
  UserDeliveryDetail findByUserDeal(UserDeal userDeal);
}
