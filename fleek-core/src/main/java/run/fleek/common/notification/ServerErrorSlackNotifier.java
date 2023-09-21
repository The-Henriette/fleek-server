package run.fleek.common.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerErrorSlackNotifier extends SlackNotifier {

  @Value("${notification.slack.server-error}")
  private String slackHookUrl;


  @Override
  public String getSlackHookUrl() {
    return slackHookUrl;
  }
}
