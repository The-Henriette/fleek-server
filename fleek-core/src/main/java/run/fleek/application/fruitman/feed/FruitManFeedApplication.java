package run.fleek.application.fruitman.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.domain.fruitman.feed.FruitManFeedService;

@Component
@RequiredArgsConstructor
public class FruitManFeedApplication {

  private final FruitManFeedService feedService;


}
