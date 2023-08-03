package run.fleek.domain.chat;

import run.fleek.domain.chat.vo.ProfileChatVo;
import run.fleek.domain.profile.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileChatRepositoryCustom {
  Optional<ProfileChatVo> findBySenderAndReceiverName(String senderName, String receiverName);
  Optional<ProfileChat> findProfileByProfileChatCode(String profileChatCode);
  List<ProfileChat> listProfileChatsByChatUrl(String chatUrl);
}
