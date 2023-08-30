package run.fleek.domain.fruitman.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAreaGroupRepository extends JpaRepository<DeliveryAreaGroup, Long> {
}
