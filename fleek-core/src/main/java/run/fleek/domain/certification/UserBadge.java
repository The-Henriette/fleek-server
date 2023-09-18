package run.fleek.domain.certification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_badge", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserBadge implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_badge_seq")
  @SequenceGenerator(name = "user_badge_seq", sequenceName = "user_badge_seq", allocationSize = 1)
  @Column(name = "user_badge_id", nullable = false)
  private Long userBadgeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "certification_code")
  @Enumerated(EnumType.STRING)
  private Certification certificationCode;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;
}
