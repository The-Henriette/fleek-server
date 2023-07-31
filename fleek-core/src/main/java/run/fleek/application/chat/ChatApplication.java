package run.fleek.application.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import run.fleek.application.chat.dto.ExternalChatCreateDto;
import run.fleek.application.chat.dto.ExternalChatDto;
import run.fleek.common.client.sendbird.SendbirdWebClient;
import run.fleek.common.client.sendbird.dto.SendbirdAddChatResponseDto;
import run.fleek.common.constants.NamePool;
import run.fleek.common.exception.FleekException;
import run.fleek.domain.chat.Chat;
import run.fleek.domain.chat.ChatService;
import run.fleek.domain.chat.ProfileChat;
import run.fleek.domain.chat.ProfileChatService;
import run.fleek.domain.chat.vo.ProfileChatVo;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.utils.RandomUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChatApplication {

  private final ChatService chatService;
  private final ProfileChatService profileChatService;
  private final SendbirdWebClient sendbirdWebClient;
  private final ProfileService profileService;

  @Transactional
  public ExternalChatDto createExternalChat(ExternalChatCreateDto externalChatCreateDto) {

    // First check that whether the external chat between given two profiles already exists.
    Optional<ProfileChatVo> profileChatVoOpt = profileChatService.getProfileChatVoByParticipants(externalChatCreateDto.getSenderProfileName(), externalChatCreateDto.getReceiverProfileName());
    if (profileChatVoOpt.isPresent()) {
      sendbirdWebClient.sendMessage(profileChatVoOpt.get().getChannelUrl(), externalChatCreateDto.getFirstMessage(),
        profileChatVoOpt.get().getAnonymousChatKey());

      return ExternalChatDto.builder()
        .chatUserId(profileChatVoOpt.get().getSenderChatProfileKey())
        .senderProfileName(profileChatVoOpt.get().getSenderProfileName())
        .channelUrl(profileChatVoOpt.get().getChannelUrl())
        .profileChatCode(profileChatVoOpt.get().getProfileChatCode())
        .build();
    }

    // If the external chat does not exist, then create it.
    // 1. Create temporary profile for the external chat.
    Profile senderProfile = profileService.addProfile(Profile.fromExternalChat(
      NamePool.getRandomName(),
     "fleek_anonymous_1"
    ));

    // 2. Create a channel for the external chat.
    Profile receiverProfile = profileService.getProfileByProfileName(externalChatCreateDto.getReceiverProfileName())
      .orElseThrow(new FleekException("Receiver profile does not exist."));
    SendbirdAddChatResponseDto sendbirdChannelResult =
      sendbirdWebClient.addChat(Arrays.asList(senderProfile.getChatProfileKey(), receiverProfile.getChatProfileKey()));

    Chat chat = chatService.addChat(Chat.builder()
      .chatUri(sendbirdChannelResult.getChannelUrl())
      .build());

    // 3. Create a profile chat for the sender and receiver.
    ProfileChat receiverProfileChat = profileChatService.addProfileChat(ProfileChat.builder()
      .profile(receiverProfile)
      .chat(chat)
      .build());

    ProfileChat senderProfileChat = profileChatService.addProfileChat(ProfileChat.builder()
      .profile(senderProfile)
      .chat(chat)
      .profileChatCode(receiverProfile.getProfileName() + RandomUtil.generateRandomSixDigitNumber())
      .anonymousChatKey(senderProfile.getChatProfileKey())
      .build()
    );

    if (StringUtils.hasLength(externalChatCreateDto.getFirstMessage())) {
      sendbirdWebClient.sendMessage(sendbirdChannelResult.getChannelUrl(), externalChatCreateDto.getFirstMessage(), senderProfile.getChatProfileKey());
    }

    return ExternalChatDto.builder()
      .chatUserId(senderProfile.getChatProfileKey())
      .senderProfileName(senderProfile.getProfileName())
      .channelUrl(chat.getChatUri())
      .profileChatCode(senderProfileChat.getProfileChatCode())
      .build();
  }

}
