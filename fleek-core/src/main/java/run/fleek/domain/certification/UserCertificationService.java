package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;
import run.fleek.enums.CertificationStatus;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCertificationService {

    private final UserCertificationRepository userCertificationRepository;

    @Transactional
    public UserCertification putUserCertification(UserCertification userCertification) {
        return userCertificationRepository.save(userCertification);
    }

    @Transactional
    public UserCertification addUserCertification(UserCertification userCertification) {
        boolean existsActiveCertification =
          userCertificationRepository.existsByFleekUser_FleekUserIdAndActiveIsTrueAndCertificationCode(userCertification.getFleekUser().getFleekUserId(), userCertification.getCertificationCode());

        if (existsActiveCertification) {
            UserCertification preExistentUserCertification =
              userCertificationRepository.findByFleekUserAndCertificationCodeAndActiveIsTrue(
                userCertification.getFleekUser(),
                userCertification.getCertificationCode())
                .orElseThrow();
            preExistentUserCertification.setActive(false);
            userCertificationRepository.save(preExistentUserCertification);
        }
        return userCertificationRepository.save(userCertification);
    }

    @Transactional(readOnly = true)
    public Optional<UserCertification> getUserCertification(Long userCertificationId) {
      return userCertificationRepository.findById(userCertificationId);
    }

    @Transactional(readOnly = true)
    public Optional<UserCertification> getUserCertificationByUserAndType(FleekUser user, Certification certification) {
      return userCertificationRepository.findByFleekUserAndCertificationCodeAndActiveIsTrue(user, certification);
    }

    @Transactional(readOnly = true)
    public List<UserCertification> listUserCertifications(Long userId) {
      return userCertificationRepository.findAllByFleekUser_FleekUserIdAndActiveIsTrue(userId);
    }

    @Transactional(readOnly = true)
    public List<UserCertification> listConfirmedUserCertifications(Long userId) {
        return userCertificationRepository.findAllByFleekUser_FleekUserIdAndCertificationStatus(userId, CertificationStatus.ACCEPTED);
    }

    @Async
    @Transactional
    public void resolveRejection(UserCertification userCertification) {
        if (!userCertification.getRejectRead()) {
            userCertification.setRejectRead(true);
            userCertificationRepository.save(userCertification);
        }
    }
}
