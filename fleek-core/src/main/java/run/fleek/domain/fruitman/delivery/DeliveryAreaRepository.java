package run.fleek.domain.fruitman.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, Long> {
  boolean existsByDeliveryAreaGroupAndPostalCode(DeliveryAreaGroup deliveryAreaGroup, String postalCode);
}
