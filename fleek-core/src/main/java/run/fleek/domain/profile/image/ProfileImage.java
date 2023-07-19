package run.fleek.domain.profile.image;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;
import run.fleek.enums.ImageType;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "profile_image", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class ProfileImage implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_image_seq")
  @SequenceGenerator(name = "profile_image_seq", sequenceName = "profile_image_seq", allocationSize = 1)
  @Column(name = "profile_image_id", nullable = false)
  private Long profileInfoId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "image_type")
  @Enumerated(EnumType.STRING)
  private ImageType imageType;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "order_number")
  private Integer orderNumber;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
