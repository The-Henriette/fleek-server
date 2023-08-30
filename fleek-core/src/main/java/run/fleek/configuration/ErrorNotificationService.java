package run.fleek.configuration;

import com.google.api.client.util.Lists;
import com.slack.api.model.block.LayoutBlock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorNotificationService {
  private static final String DEV_TEAM_EMAIL_ADDRESS = "pasta-backend-developer@techtaka.com";
  private static final List<String> PASTA_SLAVES = Arrays.asList("chance@techtaka.com", "mac@techtaka.com", "murloc@techtaka.com");

//  @Value("${argo.service}")
  private final String serviceName = "fleek";

//  @Value("${spring.profiles.active}")
  private final String profile = "dev";

  @Async(value = "defaultTaskExecutor")
  public void sendNotification(Exception ex) {
    String id = UUID.randomUUID().toString();
    String cause = ExceptionUtils.getRootCauseMessage(ex);
    String subject = String.format("[Server error / %s / %s] : ID: %s, CAUSE: %s", profile, serviceName, id, cause);

    if (profile.equals("local")) {
      return;
    }

    if (profile.equals("dev")) {
      String localSubject = "[DEV!!!!!]" + subject;
      //PASTA_SLAVES.forEach(slave -> emailService.justSend(slave, localSubject, ExceptionUtils.getStackTrace(ex)));
    }
//    serverErrorSlackNotifier.send(getErrorMessage(id, cause));
//    emailService.sendOnlyInProduction(DEV_TEAM_EMAIL_ADDRESS, subject, ExceptionUtils.getStackTrace(ex));
  }

  @Async(value = "defaultTaskExecutor")
  public void sendNotification(Throwable throwable) {
    String id = UUID.randomUUID().toString();
    String subject = String.format("[Server error / %s / %s] : ID: %s, CAUSE: %s", profile, serviceName, id, throwable);

    String rootCauseMessage = ExceptionUtils.getRootCauseMessage(throwable);

//    serverErrorSlackNotifier.send(getErrorMessage(id, rootCauseMessage));
//    emailService.sendOnlyInProduction(DEV_TEAM_EMAIL_ADDRESS, subject, ExceptionUtils.getStackTrace(throwable));
  }

  private List<LayoutBlock> getErrorMessage(String id, String cause) {
    String errorMessage = "> [" + serviceName + "] \n"
      + ">> ID : *" + id + "*\n"
      + ">> CAUSE : " + cause + "\n";
    List<LayoutBlock> layoutBlocks = Lists.newArrayList();
    layoutBlocks.add(section(section -> section.text(markdownText(errorMessage))));
    return layoutBlocks;
  }

}