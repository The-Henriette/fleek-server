package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBadgeService {
  private final UserBadgeRepository userBadgeRepository;

  @Transactional
  public void putUserBadge(UserCertification userCertification) {

    boolean existsUserBadge = userBadgeRepository.existsByFleekUserAndCertificationCode(
      userCertification.getFleekUser(),
      userCertification.getCertificationCode()
    );

    if (existsUserBadge) {
      return;
    }

    UserBadge userBadge = UserBadge.builder()
      .fleekUser(userCertification.getFleekUser())
      .certificationCode(userCertification.getCertificationCode())
      .build();

    userBadgeRepository.save(userBadge);
  }

  @Transactional(readOnly = true)
  public List<UserBadge> listUserBadge(Long userId) {
    return userBadgeRepository.findAllByFleekUser_FleekUserId(userId);
  }
}
