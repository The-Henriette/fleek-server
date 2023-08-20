package run.fleek.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.fleek.domain.profile.Profile;
import run.fleek.enums.ReportType;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
  boolean existsByReportUserAndTargetUserAndReportTypeAndReportTargetId(Profile reportUser, Profile targetUser, ReportType reportType, Long reportTargetId);
}
