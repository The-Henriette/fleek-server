package run.fleek.domain.profile.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileInfoTypeOptionRepository extends JpaRepository<ProfileInfoTypeOption, Long> {

}
