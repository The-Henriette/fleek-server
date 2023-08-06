package run.fleek.domain.lounge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungePostRepository extends JpaRepository<LoungePost, Long>, LoungePostRepositoryCustom {
}
