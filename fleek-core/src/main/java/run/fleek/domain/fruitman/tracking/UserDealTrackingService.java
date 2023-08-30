package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDealTrackingService {
  private final UserDealTrackingRepository userDealTrackingRepository;

  @Transactional
  public void addUserDealTracking(UserDealTracking userDealTracking) {
    userDealTrackingRepository.save(userDealTracking);
  }
}
