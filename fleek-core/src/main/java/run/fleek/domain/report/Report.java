package run.fleek.domain.report;


import lombok.*;
import run.fleek.common.jpa.CreatedAtListener;
import run.fleek.common.jpa.SystemMetadata;
import run.fleek.common.jpa.UpdatedAtListener;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.report.dto.ReportAddDto;
import run.fleek.enums.ReportType;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "report", schema = "public")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners({CreatedAtListener.class, UpdatedAtListener.class})
public class Report implements SystemMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
  @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", allocationSize = 1)
  @Column(name = "report_id", nullable = false)
  private Long reportId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_user_id")
  private Profile reportUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "target_user_id")
  private Profile targetUser;

  @Column(name = "report_type")
  @Enumerated(EnumType.STRING)
  private ReportType reportType;

  @Column(name = "report_target_id")
  private Long reportTargetId;

  @Column(name = "report_reason")
  private String reportReason;

  @Column(name = "confirmed")
  private Boolean confirmed;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "updated_at", nullable = false)
  private Long updatedAt;

  public static Report from(Profile reportUser, Profile targetUser, ReportAddDto dto) {
    return Report.builder()
        .reportUser(reportUser)
        .targetUser(targetUser)
        .reportType(ReportType.valueOf(dto.getReportType()))
        .reportTargetId(dto.getReportTargetId())
        .confirmed(false)
        .reportReason(dto.getReportReason())
        .build();
  }

}
