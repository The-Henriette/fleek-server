package run.fleek.domain.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCertificationService {

    private final UserCertificationRepository userCertificationRepository;

    @Transactional
    public UserCertification addUserCertification(UserCertification userCertification) {
        return userCertificationRepository.save(userCertification);
    }
}
