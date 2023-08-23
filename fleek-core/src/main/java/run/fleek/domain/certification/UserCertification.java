package run.fleek.domain.certification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.CertificationMethod;
import run.fleek.enums.CertificationStatus;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_certification", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class UserCertification implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_certification_seq")
  @SequenceGenerator(name = "user_certification_seq", sequenceName = "user_certification_seq", allocationSize = 1)
  @Column(name = "user_certification_id", nullable = false)
  private Long userCertificationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleek_user_id")
  private FleekUser fleekUser;

  @Column(name = "certification_code")
  private String certificationCode;

  @Column(name = "certification_status")
  @Enumerated(EnumType.STRING)
  private CertificationStatus certificationStatus;

  @Column(name = "certification_method")
  @Enumerated(EnumType.STRING)
  private CertificationMethod certificationMethod;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public UserCertification(Certification targetCertification, FleekUser fleekUser, CertificationMethod method) {
    this.certificationCode = targetCertification.getCertificationCode();
    this.fleekUser = fleekUser;
    this.certificationStatus = CertificationStatus.PENDING;
    this.active = true;
    this.certificationMethod = method;
  }
}
