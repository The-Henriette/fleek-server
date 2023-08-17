package run.fleek.domain.users.platform;


import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_platform_info", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserPlatformInfo implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_platform_info_seq")
  @SequenceGenerator(name = "user_platform_info_seq", sequenceName = "user_platform_info_seq", allocationSize = 1)
  @Column(name = "user_platform_info_id", nullable = false)
  private Long userPlatformInfoId;

  @Column(name = "push_token")
  private String pushToken;

  @Column(name = "platform_type")
  private String platformType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
