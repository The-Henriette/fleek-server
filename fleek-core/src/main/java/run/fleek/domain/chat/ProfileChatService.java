package run.fleek.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import run.fleek.domain.chat.vo.ProfileChatVo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileChatService {

  private final ProfileChatRepository profileChatRepository;

  @Transactional(readOnly = true)
  public Optional<ProfileChatVo> getProfileChatVoByParticipants(String senderProfileName, String receiverProfileName) {
    if (!StringUtils.hasLength(senderProfileName) || !StringUtils.hasLength(receiverProfileName)) {
      return Optional.empty();
    }

    return profileChatRepository.findBySenderAndReceiverName(senderProfileName, receiverProfileName);
  }

  public ProfileChat addProfileChat(ProfileChat profileChat) {
    return profileChatRepository.save(profileChat);
  }

  public Optional<ProfileChat> getProfileByProfileChatCode(String profileChatCode) {
    return profileChatRepository.findProfileByProfileChatCode(profileChatCode);
  }

  @Transactional(readOnly = true)
  public List<ProfileChat> listProfileChatsByChatUrl(String chatUrl) {
    return profileChatRepository.listProfileChatsByChatUrl(chatUrl);
  }
}
