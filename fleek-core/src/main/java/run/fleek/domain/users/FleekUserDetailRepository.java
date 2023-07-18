package run.fleek.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FleekUserDetailRepository extends JpaRepository<FleekUserDetail, Long> {
}
