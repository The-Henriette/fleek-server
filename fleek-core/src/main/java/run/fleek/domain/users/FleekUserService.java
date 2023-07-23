package run.fleek.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FleekUserService {

    private final FleekUserRepository fleekUserRepository;

    @Transactional
    public FleekUser addFleekUser(FleekUser fleekUser) {
        return fleekUserRepository.save(fleekUser);
    }

    @Transactional(readOnly = true)
    public boolean existsUserByUserName(String userName) {
        return fleekUserRepository.existsByPhoneNumber(userName);
    }

    @Transactional(readOnly = true)
    public Optional<FleekUser> getByUserId(Long userId) {
        return fleekUserRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<FleekUser> getByUserName(String userName) {
        return fleekUserRepository.findByPhoneNumber(userName);
    }
}
