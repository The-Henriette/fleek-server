package run.fleek.common.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService {

  @Value("${spring.mail.username}")
  private String from;

  public final JavaMailSender emailSender;

  @Async(value = "defaultTaskExecutor")
  public void send(String to, String subject, String text) {
    sendSimpleMessage(to, subject, text);
  }

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom(from);
    message.setSubject(subject);
    message.setText(text);
    try {
      emailSender.send(message);
      log.info("Success to send mail to {} with subject : {}", to, subject);
    } catch (MailException e) {
      log.error(MessageFormat.format("Failed to send mail to {0} with subject : {1}, {2}", to, subject, e));
    }
  }

  public void sendMessageHtml(String to, String subject, String htmlText) {
    MimeMessage message = emailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, false);

      helper.setTo(to);
      helper.setFrom(from);
      helper.setSubject(subject);
      helper.setText(htmlText, true);

    } catch (MessagingException e) {
      log.error(MessageFormat.format("Failed to add attachment to mail when sending to {0} with subject : {1}", to, subject));
      return;
    }

    try {
      emailSender.send(message);
    } catch (MailException e) {
      log.error(MessageFormat.format("Failed to send mail to {0} with subject : {1}", to, subject));
    }
  }

  public void sendMessageWithAttachment(String to, String subject, String text, Map<String, File> attachmentMap) {
    MimeMessage message = emailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text);

      attachmentMap.forEach((attachmentName, attachment) -> {
        try {
          helper.addAttachment(attachmentName, attachment);
        } catch (MessagingException e) {
          log.error(MessageFormat.format("Failed to add attachment {1} with name {2}", attachment.getName(), attachmentName));
        }
      });
    } catch (MessagingException e) {
      log.error(MessageFormat.format("Failed to add attachment to mail when sending to {0} with subject : {1}", to, subject));
      return;
    }
    try {
      emailSender.send(message);
    } catch (MailException e) {
      log.error(MessageFormat.format("Failed to send mail to {0} with subject : {1}", to, subject));
    }
  }
}
