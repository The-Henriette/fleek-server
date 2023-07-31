package run.fleek.domain.chat;

import run.fleek.domain.chat.vo.ProfileChatVo;

import java.util.Optional;

public interface ProfileChatRepositoryCustom {
  Optional<ProfileChatVo> findBySenderAndReceiverName(String senderName, String receiverName);
}
