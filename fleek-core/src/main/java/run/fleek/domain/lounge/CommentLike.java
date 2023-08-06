package run.fleek.domain.lounge;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "comment_like", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class CommentLike implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_like_seq")
  @SequenceGenerator(name = "comment_like_seq", sequenceName = "comment_like_seq", allocationSize = 1)
  @Column(name = "comment_like_id", nullable = false)
  private Long commentLikeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_comment_id")
  private PostComment comment;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

    public static CommentLike from(PostComment postComment, Profile profile) {
        return CommentLike.builder()
            .comment(postComment)
            .profile(profile)
            .build();
    }
}
