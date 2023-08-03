package run.fleek.api.controller.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.chat.ChatApplication;
import run.fleek.application.chat.dto.ChatDetailDto;
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

  @GetMapping("/chat/{chatUrl}/detail")
  public ChatDetailDto getChatDetail(@PathVariable String chatUrl, @RequestParam String profileName) {
    return chatApplication.getChatDetail(chatUrl, profileName);
  }
}
