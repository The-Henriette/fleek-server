package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.CertificationStatus;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCertificationService {

    private final UserCertificationRepository userCertificationRepository;

    @Transactional
    public UserCertification addUserCertification(UserCertification userCertification) {
        boolean existsActiveCertification =
          userCertificationRepository.existsByFleekUser_FleekUserIdAndActiveIsTrueAndCertificationCode(userCertification.getFleekUser().getFleekUserId(), userCertification.getCertificationCode().getName());

        if (existsActiveCertification) {
            userCertification.setActive(false);
        }
        return userCertificationRepository.save(userCertification);
    }

    @Transactional(readOnly = true)
    public Optional<UserCertification> getUserCertification(Long userCertificationId) {
      return userCertificationRepository.findById(userCertificationId);
    }

    @Transactional(readOnly = true)
    public List<UserCertification> listUserCertifications(Long userId) {
      return userCertificationRepository.findAllByFleekUser_FleekUserIdAndActiveIsTrue(userId);
    }

    @Transactional(readOnly = true)
    public List<UserCertification> listConfirmedUserCertifications(Long userId) {
        return userCertificationRepository.findAllByFleekUser_FleekUserIdAndCertificationStatus(userId, CertificationStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    public Optional<UserCertification> getActiveCertification(FleekUser fleekUser, String certificationCode) {
      return userCertificationRepository.findByFleekUserAndCertificationCodeAndActiveIsTrue(fleekUser, certificationCode);
    }
}
