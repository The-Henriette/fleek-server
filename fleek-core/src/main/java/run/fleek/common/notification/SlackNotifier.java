package run.fleek.common.notification;

import com.google.common.collect.Lists;
import com.slack.api.Slack;
import com.slack.api.model.block.LayoutBlock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.webhook.WebhookPayloads.payload;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class SlackNotifier {
  public abstract String getSlackHookUrl();

  public void send(String message) {
    List<LayoutBlock> layoutBlocks = Lists.newArrayList();
    layoutBlocks.add(section(section -> section.text(markdownText(message))));
    this.send(layoutBlocks);
  }

  public void send(List<LayoutBlock> layoutBlocks, String slackHookUrl) {
    try {
      if (StringUtils.isBlank(getSlackHookUrl())) {
        return;
      }
      Slack.getInstance().send(slackHookUrl, payload(p -> p.blocks(layoutBlocks)));
    } catch (IOException e) {
      log.error("slack 메시지 발송실패", e);
    }
  }

  public void send(List<LayoutBlock> layoutBlocks) {
    try {
      if (StringUtils.isBlank(getSlackHookUrl())) {
        return;
      }
      Slack.getInstance().send(getSlackHookUrl(), payload(p -> p.blocks(layoutBlocks)));
    } catch (IOException e) {
      log.error("slack 메시지 발송실패", e);
    }
  }

}
