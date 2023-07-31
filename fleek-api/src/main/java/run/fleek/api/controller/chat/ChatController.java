package run.fleek.api.controller.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.chat.ChatApplication;
import run.fleek.application.chat.dto.ExternalChatCreateDto;
import run.fleek.application.chat.dto.ExternalChatDto;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatApplication chatApplication;

  @PostMapping("/chat/create/external")
  public ExternalChatDto createExternalChat(@RequestBody ExternalChatCreateDto externalChatCreateDto) {

    return chatApplication.createExternalChat(externalChatCreateDto);
  }
}
