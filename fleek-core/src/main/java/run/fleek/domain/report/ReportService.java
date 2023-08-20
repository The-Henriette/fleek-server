package run.fleek.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.report.dto.ReportAddDto;
import run.fleek.enums.ReportType;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final ReportRepository reportRepository;

  @Transactional
  public Report addReport(Report report) {
    return reportRepository.save(report);
  }

  @Transactional(readOnly = true)
  public boolean isReported(Profile reportUser, Profile targetUser, String reportType, Long reportTargetId) {
    return reportRepository.existsByReportUserAndTargetUserAndReportTypeAndReportTargetId(reportUser, targetUser, ReportType.valueOf(reportType), reportTargetId);
  }
}
