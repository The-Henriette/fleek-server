package run.fleek.domain.users.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.VerificationType;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVerificationService {

  private final UserVerificationRepository userVerificationRepository;

  @Transactional
  public UserVerification addUserVerification(VerificationType verificationType, FleekUser fleekUser) {
    return userVerificationRepository.save(UserVerification.init(verificationType, fleekUser));
  }

  @Transactional(readOnly = true)
  public Optional<UserVerification> getVerificationByCode(String verificationCode) {
    return userVerificationRepository.findByVerificationCode(verificationCode);
  }

  @Transactional
  public void verify(UserVerification userVerification) {
    userVerification.setVerified(true);

    userVerificationRepository.save(userVerification);
  }
}
