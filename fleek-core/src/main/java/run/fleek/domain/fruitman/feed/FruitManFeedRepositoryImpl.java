package run.fleek.domain.fruitman.feed;

import com.querydsl.core.QueryResults;
import run.fleek.application.fruitman.feed.dto.FeedPageDto;
import run.fleek.application.fruitman.feed.dto.FruitManFeedDto;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class FruitManFeedRepositoryImpl extends FleekQueryDslRepositorySupport implements FruitManFeedRepositoryCustom {

  QFruitManFeed qFruitManFeed = QFruitManFeed.fruitManFeed;

  public FruitManFeedRepositoryImpl() {
    super(FruitManFeed.class);
  }

  @Override
  public FeedPageDto pageFruitManFeeds(int size, int page) {
    QueryResults<FruitManFeed> feedQueryResults =  from(qFruitManFeed)
      .orderBy(qFruitManFeed.updatedAt.desc())
      .offset((long) page * size)
      .limit(size)
      .select(qFruitManFeed)
      .fetchResults();

    List<FruitManFeed> results = feedQueryResults.getResults();

    return FeedPageDto.builder()
      .page(page)
      .totalSize(feedQueryResults.getTotal())
      .feeds(results.stream().map(FruitManFeedDto::from).collect(Collectors.toList()))
      .build();
  }
}
