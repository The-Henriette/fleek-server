package run.fleek.domain.fruitman.feed;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.FeedSource;
import run.fleek.enums.FeedType;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "fruit_man_feed", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FruitManFeed implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fruit_man_feed_seq")
  @SequenceGenerator(name = "fruit_man_feed_seq", sequenceName = "fruit_man_feed_seq", allocationSize = 1)
  @Column(name = "fruit_man_feed_id", nullable = false)
  private Long fruitManFeedId;

  @Column(name = "feed_url")
  private String feedUrl;

  @Column(name = "feed_type")
  @Enumerated(EnumType.STRING)
  private FeedType feedType;

  @Column(name = "feed_source")
  @Enumerated(EnumType.STRING)
  private FeedSource feedSource;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "title")
  private String title;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
