package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDealTrackingService {
  private final UserDealTrackingRepository userDealTrackingRepository;

  @Transactional
  public void addUserDealTracking(UserDealTracking userDealTracking) {
    userDealTrackingRepository.save(userDealTracking);
  }

  @Transactional
  public void putUserDealTrackingList(List<UserDealTracking> userDealTrackingList) {
    if (CollectionUtils.isEmpty(userDealTrackingList)) {
      return;
    }

    userDealTrackingRepository.saveAll(userDealTrackingList);
  }
}
