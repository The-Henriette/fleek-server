package run.fleek.domain.report.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAddDto {

  private String reporterName;
  private String targetProfileName;
  private String reportType;
  private Long reportTargetId;
  private String reportReason;
}
