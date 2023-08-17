package run.fleek.api.controller.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.chat.ChatApplication;
import run.fleek.application.chat.dto.ChatCreateDto;
import run.fleek.application.chat.dto.ChatDetailDto;
import run.fleek.application.chat.dto.ChatDto;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatApplication chatApplication;

  @PostMapping("/chat/create/external")
  public ChatDto createExternalChat(@RequestBody ChatCreateDto externalChatCreateDto) {

    return chatApplication.createChat(externalChatCreateDto, true);
  }

  @PostMapping("/chat/create")
  public ChatDto createChat(@RequestBody ChatCreateDto chatCreateDto) {
    return chatApplication.createChat(chatCreateDto, false);
  }

  @GetMapping("/chat/{chatUrl}/detail")
  public ChatDetailDto getChatDetail(@PathVariable String chatUrl, @RequestParam String profileName) {
    return chatApplication.getChatDetail(chatUrl, profileName);
  }
}
