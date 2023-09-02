package run.fleek.domain.fruitman.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.fleek.application.fruitman.feed.dto.FeedPageDto;

@Service
@RequiredArgsConstructor
public class FruitManFeedService {
  private final FruitManFeedRepository fruitManFeedRepository;

  public FeedPageDto pageFeeds(int size, int page) {
    return fruitManFeedRepository.pageFruitManFeeds(size, page);
  }
}
