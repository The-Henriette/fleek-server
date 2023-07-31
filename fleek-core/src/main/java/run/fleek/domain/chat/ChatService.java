package run.fleek.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRepository chatRepository;

  public Chat addChat(Chat chat) {
    return chatRepository.save(chat);
  }
}
