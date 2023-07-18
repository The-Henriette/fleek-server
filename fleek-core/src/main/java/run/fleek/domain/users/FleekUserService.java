package run.fleek.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.users.dto.SignUpDto;
import run.fleek.domain.users.vo.FleekUserDetailVo;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FleekUserService {

    private final FleekUserRepository fleekUserRepository;

    @Transactional(readOnly = true)
    public FleekUserDetailVo getTraceUserDetail(Long userId) {
        return fleekUserRepository.getTraceUserDetail(userId);
    }

    @Transactional
    public FleekUser addTraceUser(SignUpDto signUpDto) {
        return fleekUserRepository.save(FleekUser.create(signUpDto));
    }

    @Transactional(readOnly = true)
    public boolean existsUserByUserName(String userName) {
        return fleekUserRepository.existsByUserName(userName);
    }

    @Transactional(readOnly = true)
    public Optional<FleekUser> getByUserId(Long userId) {
        return fleekUserRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<FleekUser> getByUserName(String userName) {
        return fleekUserRepository.findByUserName(userName);
    }
}
