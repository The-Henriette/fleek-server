package run.fleek.domain.users;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "fleek_user_info", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class FleekUserInfo implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fleek_user_info_seq")
  @SequenceGenerator(name = "fleek_user_info_seq", sequenceName = "fleek_user_info_seq", allocationSize = 1)
  @Column(name = "fleek_user_info_id", nullable = false)
  private Long fleekUserInfo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "name")
  private String name;

  @Column(name = "bio")
  private String bio;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
