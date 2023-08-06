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
@Table(name = "post_like", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class PostLike implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_like_seq")
  @SequenceGenerator(name = "post_like_seq", sequenceName = "post_like_seq", allocationSize = 1)
  @Column(name = "post_like_id", nullable = false)
  private Long postLikeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_post_id")
  private LoungePost loungePost;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static PostLike from(LoungePost loungePost, Profile profile) {
    return PostLike.builder()
        .loungePost(loungePost)
        .profile(profile)
        .build();
  }
}
