package run.fleek.domain.profile.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileInfoTypeOptionService {

    private final ProfileInfoTypeOptionRepository profileInfoTypeOptionRepository;

    @Transactional(readOnly = true)
    public List<ProfileInfoTypeOption> listProfileInfoTypeOptions() {
        return profileInfoTypeOptionRepository.findAll();
    }
}
