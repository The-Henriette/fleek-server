package run.fleek.domain.certification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.enums.Certification;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "email_domain_meta", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class EmailDomainMeta implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_domain_meta_seq")
  @SequenceGenerator(name = "email_domain_meta_seq", sequenceName = "email_domain_meta_seq", allocationSize = 1)
  @Column(name = "email_domain_meta_id", nullable = false)
  private Long emailDomainMetaId;

  @Column(name = "email_domain", nullable = false)
  private String emailDomain;

  @Column(name = "certification_code")
  @Enumerated(EnumType.STRING)
  private Certification certificationCode;

  @Column(name = "target_name")
  private String targetName;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

}
