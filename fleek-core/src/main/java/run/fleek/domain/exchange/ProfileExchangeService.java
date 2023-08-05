package run.fleek.domain.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.profile.Profile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileExchangeService {

  private final ProfileExchangeRepository profileExchangeRepository;

  @Transactional
  public void addProfileExchanges(List<ProfileExchange> exchanges) {
    profileExchangeRepository.saveAll(exchanges);
  }

  @Transactional(readOnly = true)
  public ProfileExchange getProfileExchange(Exchange exchange, Profile profile) {
    return profileExchangeRepository.getByExchangeAndProfile(exchange, profile);
  }
}
