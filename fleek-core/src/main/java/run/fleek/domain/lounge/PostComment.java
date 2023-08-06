package run.fleek.domain.lounge;

import lombok.*;
import run.fleek.application.post.dto.CommentAddDto;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "post_comment", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class PostComment implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_comment_seq")
  @SequenceGenerator(name = "post_comment_seq", sequenceName = "post_comment_seq", allocationSize = 1)
  @Column(name = "post_comment_id", nullable = false)
  private Long postCommentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_post_id")
  private LoungePost loungePost;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "parent_profile_id")
  private Long parentProfileId;

  @Column(name = "content")
  private String content;

  @Column(name = "likes")
  private Integer likes;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static PostComment from(CommentAddDto dto, LoungePost loungePost, Profile profile) {
    return PostComment.builder()
      .loungePost(loungePost)
      .profile(profile)
      .content(dto.getContent())
      .likes(0)
      .build();
  }
}
