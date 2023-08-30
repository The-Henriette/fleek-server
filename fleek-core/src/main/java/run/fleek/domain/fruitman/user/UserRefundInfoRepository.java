package run.fleek.domain.fruitman.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefundInfoRepository extends JpaRepository<UserRefundInfo, Long> {
  Optional<UserRefundInfo> findByFruitManUser(FruitManUser fruitManUser);
}
