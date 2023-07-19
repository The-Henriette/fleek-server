package run.fleek.domain.profile.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileInfoTypeRepository extends JpaRepository<ProfileInfoType, Long> {
}
