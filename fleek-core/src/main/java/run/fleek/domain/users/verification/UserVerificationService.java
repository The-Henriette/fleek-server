package run.fleek.domain.users.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserVerificationService {

  private final UserVerificationRepository userVerificationRepository;
}
