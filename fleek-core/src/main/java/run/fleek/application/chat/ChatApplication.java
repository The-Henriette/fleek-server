package run.fleek.application.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import run.fleek.application.chat.dto.*;
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
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.enums.ImageType;
import run.fleek.utils.RandomUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatApplication {

  private final ChatService chatService;
  private final ProfileChatService profileChatService;
  private final SendbirdWebClient sendbirdWebClient;
  private final ProfileService profileService;
  private final ProfileImageService profileImageService;

  @Transactional
  public ChatDto createChat(ChatCreateDto chatCreateDto, boolean external) {

    // First check that whether the external chat between given two profiles already exists.
    Optional<ProfileChatVo> profileChatVoOpt = profileChatService.getProfileChatVoByParticipants(chatCreateDto.getSenderProfileName(), chatCreateDto.getReceiverProfileName());
    if (profileChatVoOpt.isPresent()) {
      return fetchPreExistentChat(profileChatVoOpt.get(), chatCreateDto, external);
    }

    // If the external chat does not exist, then create it.
    // 1. Create temporary profile for the external chat.
    Profile senderProfile = external ? profileService.addProfile(Profile.fromExternalChat(
      NamePool.getRandomName(),
     "fleek_anonymous_1"
    )) : profileService.getProfileByProfileName(chatCreateDto.getSenderProfileName())
      .orElseThrow(new FleekException("Sender profile does not exist."));

    // 2. Create a channel for the external chat.
    Profile receiverProfile = profileService.getProfileByProfileName(chatCreateDto.getReceiverProfileName())
      .orElseThrow(new FleekException("Receiver profile does not exist."));
    SendbirdAddChatResponseDto sendbirdChannelResult =
      sendbirdWebClient.addChat(Arrays.asList(senderProfile.getChatProfileKey(), receiverProfile.getChatProfileKey()),
        senderProfile.getProfileName(), external);

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
      .profileChatCode(external ? receiverProfile.getProfileName() + RandomUtil.generateRandomSixDigitNumber() : null)
      .anonymousChatKey(external ? senderProfile.getChatProfileKey() : null)
      .build()
    );

    if (StringUtils.hasLength(chatCreateDto.getFirstMessage())) {
      sendbirdWebClient.sendMessage(sendbirdChannelResult.getChannelUrl(), chatCreateDto.getFirstMessage(), senderProfile.getChatProfileKey(), null, null);
    }

    return ChatDto.builder()
      .chatUserId(senderProfile.getChatProfileKey())
      .senderProfileName(senderProfile.getProfileName())
      .channelUrl(chat.getChatUri())
      .profileChatCode(external ? senderProfileChat.getProfileChatCode() : null)
      .build();
  }

  public ChatDetailDto getChatDetail(String chatUrl, String profileName) {
    List<ProfileChat> profileChatList = profileChatService.listProfileChatsByChatUrl(chatUrl);

    ProfileChat counterPartProfileChat = profileChatList.stream()
      .filter(pc -> !pc.getProfile().getProfileName().equals(profileName))
      .findFirst().orElseThrow(new FleekException("invalid chatUrl"));
    List<ProfileImage> profileImageList =
      profileImageService.listProfileImageByProfileId(counterPartProfileChat.getProfile().getProfileId());
    boolean isCertified = profileImageList.stream()
      .anyMatch(pi -> pi.getImageType().equals(ImageType.FACE_IMAGE));

    ProfileChat ownProfileChat = profileChatList.stream()
      .filter(pc -> pc.getProfile().getProfileName().equals(profileName))
      .findFirst().orElseThrow(new FleekException("invalid chatUrl"));

    return ChatDetailDto.builder()
      .counterpart(ChatCounterpartDto.builder()
        .faceCertified(isCertified)
        .profileUrl(CollectionUtils.isEmpty(profileImageList) ? null : profileImageList.get(0).getImageUrl())
        .profileName(counterPartProfileChat.getProfile().getProfileName())
        .build())
      .hasAnonymous(StringUtils.hasLength(ownProfileChat.getAnonymousChatKey()) ||
        StringUtils.hasLength(counterPartProfileChat.getProfileChatCode()))
      .wasAnonymous(StringUtils.hasLength(ownProfileChat.getAnonymousChatKey()))
      .build();
  }

  private ChatDto fetchPreExistentChat(ProfileChatVo chatVo, ChatCreateDto chatCreateDto, boolean external) {
    sendbirdWebClient.sendMessage(chatVo.getChannelUrl(), chatCreateDto.getFirstMessage(),
      external ? chatVo.getAnonymousChatKey() : chatVo.getSenderChatProfileKey(), null, null);

    return ChatDto.builder()
      .chatUserId(chatVo.getSenderChatProfileKey())
      .senderProfileName(chatVo.getSenderProfileName())
      .channelUrl(chatVo.getChannelUrl())
      .profileChatCode(chatVo.getProfileChatCode())
      .build();

  }
}
