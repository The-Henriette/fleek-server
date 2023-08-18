package run.fleek.domain.exchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.profile.Profile;

@Repository
public interface ProfileExchangeRepository extends JpaRepository<ProfileExchange, Long> {
  ProfileExchange getByExchangeAndProfile(Exchange exchange, Profile profile);
  ProfileExchange getByExchange_ExchangeIdAndProfile_ProfileName(Long exchangeId, String profileName);
}
