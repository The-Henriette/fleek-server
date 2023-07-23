package run.fleek.domain.users.term;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
  List<Term> findAllByTermIdIn(List<Long> termIds);
}
