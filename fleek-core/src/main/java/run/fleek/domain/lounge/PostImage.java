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
@Table(name = "post_image", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class PostImage implements SystemMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_image_seq")
  @SequenceGenerator(name = "post_image_seq", sequenceName = "post_image_seq", allocationSize = 1)
  @Column(name = "post_image_id", nullable = false)
  private Long profileInfoId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_post_id")
  private LoungePost loungePost;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
