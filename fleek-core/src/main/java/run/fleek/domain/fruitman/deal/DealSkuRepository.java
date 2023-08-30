package run.fleek.domain.fruitman.deal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealSkuRepository extends JpaRepository<DealSku, Long> {
}
