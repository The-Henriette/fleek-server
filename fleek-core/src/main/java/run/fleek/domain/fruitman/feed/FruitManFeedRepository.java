package run.fleek.domain.fruitman.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitManFeedRepository extends JpaRepository<FruitManFeed, Long>, FruitManFeedRepositoryCustom {
}
