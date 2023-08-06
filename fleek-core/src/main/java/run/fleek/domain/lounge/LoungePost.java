package run.fleek.domain.lounge;

import lombok.*;
import org.springframework.util.CollectionUtils;
import run.fleek.application.post.dto.LoungePostAddDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;
import run.fleek.enums.LoungeTopic;
import run.fleek.utils.JsonUtil;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "lounge_post", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class LoungePost implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lounge_post_seq")
  @SequenceGenerator(name = "lounge_post_seq", sequenceName = "lounge_post_seq", allocationSize = 1)
  @Column(name = "lounge_post_id", nullable = false)
  private Long loungePostId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "topic")
  @Enumerated(EnumType.STRING)
  private LoungeTopic topic;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "likes")
  private Integer likes;

  @Column(name = "comments")
  private Integer comments;

  @Column(name = "views")
  private Integer views;

  @Column(name = "topic_attributes")
  private String topicAttributes;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static LoungePost from(LoungePostAddDto addDto, Profile profile) {
    return LoungePost.builder()
        .topic(LoungeTopic.valueOf(addDto.getTopicCode()))
        .profile(profile)
        .title(addDto.getTitle())
        .content(addDto.getContent())
        .thumbnail(CollectionUtils.isEmpty(addDto.getPhotos()) ? null : addDto.getPhotos().get(0))
        .topicAttributes(JsonUtil.write(addDto.getAttributes()))
        .likes(0)
        .views(0)
        .comments(0)
        .build();
  }
}
