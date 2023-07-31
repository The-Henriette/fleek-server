package run.fleek.domain.users.verification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.VerificationType;
import run.fleek.utils.RandomUtil;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "user_verification", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserVerification implements SystemMetadata {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_verification_seq")
  @SequenceGenerator(name = "user_verification_seq", sequenceName = "user_verification_seq", allocationSize = 1)
  @Column(name = "user_verification_id", nullable = false)
  private Long userVerificationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "verification_type")
  @Enumerated(EnumType.STRING)
  private VerificationType verificationType;

  @Column(name = "verification_code")
  private String verificationCode;

  @Column(name = "verification_number")
  private String verificationNumber;

  @Column(name = "verified")
  private Boolean verified;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static UserVerification init(VerificationType verificationType, FleekUser fleekUser) {
    return UserVerification.builder()
      .fleekUser(fleekUser)
      .verificationType(verificationType)
      .verificationCode(UUID.randomUUID().toString())
      .verificationNumber(RandomUtil.generateRandomSixDigitNumber())
      .verified(false)
      .build();
  }

}
