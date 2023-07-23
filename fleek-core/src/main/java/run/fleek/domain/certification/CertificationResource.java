package run.fleek.domain.certification;

import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.certification.dto.CertificationResourceDto;
import run.fleek.enums.CertificationStatus;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Entity
@Table(name = "certification_resource", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class CertificationResource implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certification_resource_seq")
  @SequenceGenerator(name = "certification_resource_seq", sequenceName = "certification_resource_seq", allocationSize = 1)
  @Column(name = "certification_resource_id", nullable = false)
  private Long certificationResourceId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_certification_id")
  private UserCertification userCertification;

  @Column(name = "resource_url")
  private String resourceUrl;

  @Column(name = "resource_context")
  private String resourceContext;

  @Column(name = "certification_status")
  @Enumerated(EnumType.STRING)
  private CertificationStatus certificationStatus;

  @Column(name = "reject_reason")
  private String rejectReason;

  @Column(name = "reject_reason_detail")
  private String rejectReasonDetail;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static List<CertificationResource> from(List<CertificationResourceDto> dtoList, UserCertification userCertification) {
    return dtoList.stream()
      .map(dto -> CertificationResource.from(dto, userCertification))
      .collect(Collectors.toList());
  }

  public static CertificationResource from(CertificationResourceDto dto, UserCertification userCertification) {
    return CertificationResource.builder()
      .userCertification(userCertification)
      .resourceUrl(dto.getResourceUrl())
      .resourceContext(dto.getResourceContext())
      .certificationStatus(CertificationStatus.PENDING)
      .build();
  }
}
