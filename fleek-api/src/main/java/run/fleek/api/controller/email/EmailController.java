package run.fleek.api.controller.email;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.api.controller.email.dto.EmailSendDto;
import run.fleek.common.email.EmailService;

@RestController
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;

  @PostMapping("/email")
  public void sendEmail(@RequestBody EmailSendDto sendDto) {
    emailService.send(sendDto.getTo(), sendDto.getSubject(), sendDto.getText());
  }
}
