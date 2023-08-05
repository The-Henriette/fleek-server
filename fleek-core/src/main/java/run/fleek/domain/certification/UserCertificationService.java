package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCertificationService {

    private final UserCertificationRepository userCertificationRepository;

    @Transactional
    public UserCertification addUserCertification(UserCertification userCertification) {
        return userCertificationRepository.save(userCertification);
    }

    @Transactional(readOnly = true)
    public Optional<UserCertification> getUserCertification(Long userCertificationId) {
      return userCertificationRepository.findById(userCertificationId);
    }
}
