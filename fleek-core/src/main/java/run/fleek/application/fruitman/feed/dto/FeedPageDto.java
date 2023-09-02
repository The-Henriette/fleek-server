package run.fleek.application.fruitman.feed.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPageDto {

  private Integer page;
  private Long totalSize;
  private List<FruitManFeedDto> feeds;
}
