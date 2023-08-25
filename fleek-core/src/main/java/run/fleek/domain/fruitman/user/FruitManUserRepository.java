package run.fleek.domain.fruitman.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.enums.ProviderCode;

import java.util.Optional;

@Repository
public interface FruitManUserRepository extends JpaRepository<FruitManUser, Long> {
  Optional<FruitManUser> findByProviderCodeAndProviderId(ProviderCode providerCode, String providerId);
}
