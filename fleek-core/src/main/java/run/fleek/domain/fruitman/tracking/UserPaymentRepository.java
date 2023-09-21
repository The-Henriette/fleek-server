package run.fleek.domain.fruitman.tracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
  UserPayment findFirstByUserDeal(UserDeal userDeal);
}
