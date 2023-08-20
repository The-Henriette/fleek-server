package run.fleek.api.controller.report;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.report.ReportApplication;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.domain.report.ReportService;
import run.fleek.domain.report.dto.ReportAddDto;

@RestController
@RequiredArgsConstructor
public class ReportController {

  private final ReportApplication reportApplication;

  @PostMapping("/report")
  public FleekGeneralResponse addReport(@RequestBody ReportAddDto reportAddDto) {
    return reportApplication.addReport(reportAddDto);
  }
}
