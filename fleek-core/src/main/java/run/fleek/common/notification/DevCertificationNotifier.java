package run.fleek.common.notification;

import com.slack.api.Slack;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DevCertificationNotifier extends SlackNotifier {

  @Value("${notification.slack.dev-certification}")
  private String slackHookUrl;

  public void sendNotification(String message) {
    super.send(message);
  }

  @Override
  public String getSlackHookUrl() {
    return slackHookUrl;
  }
}
