package run.fleek.application.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.report.Report;
import run.fleek.domain.report.ReportService;
import run.fleek.domain.report.dto.ReportAddDto;

@Component
@RequiredArgsConstructor
public class ReportApplication {

  private final ReportService reportService;
  private final ProfileService profileService;


  @Transactional
  public FleekGeneralResponse addReport(ReportAddDto reportAddDto) {
    Profile reportUser = profileService.getProfileByProfileName(reportAddDto.getReporterName())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필입니다."));
    Profile targetUser = profileService.getProfileByProfileName(reportAddDto.getTargetProfileName())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필입니다."));

    if (reportService.isReported(reportUser, targetUser, reportAddDto.getReportType(), reportAddDto.getReportTargetId())) {
      return FleekGeneralResponse.builder()
          .success(false)
          .errorMessage("이미 신고한 대상입니다.")
          .build();
    }

    reportService.addReport(Report.from(reportUser, targetUser, reportAddDto));
    return FleekGeneralResponse.builder()
        .success(true)
        .build();
  }
}
