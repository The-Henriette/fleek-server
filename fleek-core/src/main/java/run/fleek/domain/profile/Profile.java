package run.fleek.domain.profile;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "profile", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Profile implements SystemMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
  @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
  @Column(name = "profile_id", nullable = false)
  private Long profileId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "profile_name")
  private String profileName;

  @Column(name = "bio")
  private String bio;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
