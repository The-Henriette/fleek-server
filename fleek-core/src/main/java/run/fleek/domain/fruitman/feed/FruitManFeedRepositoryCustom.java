package run.fleek.domain.fruitman.feed;

import run.fleek.application.fruitman.feed.dto.FeedPageDto;

public interface FruitManFeedRepositoryCustom {
  FeedPageDto pageFruitManFeeds(int size, int page);
}
