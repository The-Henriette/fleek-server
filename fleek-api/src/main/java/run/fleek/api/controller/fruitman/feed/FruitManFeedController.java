package run.fleek.api.controller.fruitman.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.fruitman.feed.FruitManFeedApplication;
import run.fleek.application.fruitman.feed.dto.FeedPageDto;
import run.fleek.domain.fruitman.feed.FruitManFeedService;

@RestController
@RequiredArgsConstructor
public class FruitManFeedController {

  private final FruitManFeedService feedService;

  @GetMapping("/fruitman/feed")
  public FeedPageDto pageFeeds(@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
    return feedService.pageFeeds(size, page);

  }
}
