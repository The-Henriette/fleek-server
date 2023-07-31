package run.fleek.common.notification;

import com.slack.api.Slack;
import com.slack.api.model.block.LayoutBlock;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Component
@RequiredArgsConstructor
public class DevVerificationNotifier extends SlackNotifier {

  @Value("${notification.slack.dev-verification}")
  private String slackHookUrl;

  public void sendNotification(String message) {
    super.send(message);
  }

  @Override
  public String getSlackHookUrl() {
    return slackHookUrl;
  }
}
