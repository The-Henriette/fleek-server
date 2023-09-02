package run.fleek.application.fruitman.feed.dto;

import lombok.*;
import run.fleek.domain.fruitman.feed.FruitManFeed;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FruitManFeedDto {
  private String feedType;
  private String feedSource;
  private String thumbnail;
  private String feedUrl;
  private String title;

  public static FruitManFeedDto from(FruitManFeed fruitManFeed) {
    return FruitManFeedDto.builder()
      .feedType(fruitManFeed.getFeedType().name())
      .feedSource(fruitManFeed.getFeedSource().name())
      .thumbnail(fruitManFeed.getThumbnail())
      .feedUrl(fruitManFeed.getFeedUrl())
      .title(fruitManFeed.getTitle())
      .build();
  }

  public String getThumbnail() {
    return CDN_PREFIX + thumbnail;
  }

  public String getFeedUrl() {
    return CDN_PREFIX + feedUrl;
  }
//  {
//    "feedType": "VIDEO", // "LINK", "LIVE"
//    "feedSource": "CUSTOM", // "INSTAGRAM", "YOUTUBE",
//    "thumbnail": "aslkdalksmdkas",
//    "feedUrl": "https://awscdn.aws-fleek.com/sexvid.mp4",
//    "title": "8/24 전체 사입 과정"
//  }
}
