package run.fleek.application.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.exchange.dto.ExchangeRequestDto;
import run.fleek.application.exchange.dto.ExchangeResponseDto;
import run.fleek.application.exchange.dto.ExchangeWatchDto;
import run.fleek.application.exchange.dto.SendbirdExchangeDataDto;
import run.fleek.common.client.sendbird.SendbirdWebClient;
import run.fleek.common.client.sendbird.dto.SendbirdSendMessageDto;
import run.fleek.common.exception.FleekException;
import run.fleek.common.response.FleekGeneralResponse;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.chat.ProfileChat;
import run.fleek.domain.chat.ProfileChatService;
import run.fleek.domain.exchange.Exchange;
import run.fleek.domain.exchange.ExchangeService;
import run.fleek.domain.exchange.ProfileExchange;
import run.fleek.domain.exchange.ProfileExchangeService;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.users.wallet.UserWallet;
import run.fleek.domain.users.wallet.UserWalletService;
import run.fleek.enums.ExchangeFailureCode;
import run.fleek.enums.ExchangeValidationCode;
import run.fleek.enums.ImageType;
import run.fleek.utils.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.Price.EXCHANGE_PRICE;

@Component
@RequiredArgsConstructor
public class ExchangeApplication {

  private final ExchangeService exchangeService;
  private final ProfileExchangeService profileExchangeService;
  private final ProfileChatService profileChatService;
  private final SendbirdWebClient sendbirdWebClient;
  private final ProfileImageService profileImageService;
  private final ProfileService profileService;
  private final FleekUserContext fleekUserContext;
  private final UserWalletService userWalletService;

  @Transactional
  public ExchangeResponseDto requestExchange(ExchangeRequestDto requestDto) {

    // 1. load profiles by chatUrl
    List<ProfileChat> profileChats = profileChatService.listProfileChatsByChatUrl(requestDto.getChatUrl());
    Profile requester = profileChats.stream()
      .filter(profileChat -> profileChat.getProfile().getProfileName().equals(requestDto.getRequesterProfileName()))
      .findFirst()
      .orElseThrow(new FleekException("Invalid profile requester"))
      .getProfile();

    Profile receiver = profileChats.stream()
      .filter(profileChat -> !profileChat.getProfile().getProfileName().equals(requestDto.getRequesterProfileName()))
      .findFirst()
      .orElseThrow(new FleekException("Invalid profile requester"))
      .getProfile();

    // 1-5. validate
    boolean faceValidated = profileImageService.isCertified(requester);
    if (!faceValidated) {
      return ExchangeResponseDto.builder()
        .success(false)
        .validationCode(ExchangeValidationCode.PENDING_CERTIFICATION.getName())
        .validationMessage(ExchangeValidationCode.PENDING_CERTIFICATION.getDescription())
        .build();
    }

    // 2. create exchange
    Exchange exchange = Exchange.builder()
      .chat(profileChats.get(0).getChat())
      .build();

    // 3. save exchange and send exchange message
    exchangeService.addExchange(exchange);
    SendbirdSendMessageDto sendMessageDto = sendbirdWebClient.sendMessage(
      requestDto.getChatUrl(),
      "사진교환",
      requester.getChatProfileKey(),
      "EXCHANGE",
      JsonUtil.write(SendbirdExchangeDataDto.builder()
        .exchangeId(exchange.getExchangeId().toString())
        .state("pending")
        .build())
    );

    // 4. set requestMessageId on exchange
    exchange.setRequestMessageId(sendMessageDto.getMessageId().toString());

    // 5. save exchange
    exchangeService.addExchange(exchange);

    // 6. save profileExchange and return responseDto
    ProfileExchange requesterProfileExchange = ProfileExchange.from(requester, exchange, true);
    ProfileExchange receiverProfileExchange = ProfileExchange.from(receiver, exchange, false);

    profileExchangeService.addProfileExchanges(List.of(requesterProfileExchange, receiverProfileExchange));

    return ExchangeResponseDto.builder()
      .success(true)
      .exchangeId(exchange.getExchangeId())
      .build();
  }

  @Transactional
  public FleekGeneralResponse processExchange(Long exchangeId, String profileName, String state) {
    Exchange exchange = exchangeService.getExchange(exchangeId);
    Profile profile = profileService.getProfileByProfileName(profileName)
      .orElseThrow(new FleekException("Invalid profile name"));

    if (state.equals("accepted")) {
      boolean faceValidated = profileImageService.isCertified(profile);
      if (!faceValidated) {
        return FleekGeneralResponse.builder()
          .success(false)
          .errorMessage("얼굴인증이 필요합니다.")
          .build();
      }
    }
    // 1. update previous sendbird message status
    sendbirdWebClient.updateMessage(exchange.getRequestMessageId(), exchange.getChat().getChatUri(), "사진교환", "EXCHANGE", JsonUtil.write(SendbirdExchangeDataDto.builder()
      .exchangeId(exchange.getExchangeId().toString())
      .state("processed")
      .build()));

    // 2. update profileExchange
    ProfileExchange profileExchange = profileExchangeService.getProfileExchange(exchange, profile);
    profileExchange.setAccepted(state.equals("accepted"));
    profileExchange.setRejected(state.equals("rejected"));
    profileExchangeService.addProfileExchanges(List.of(profileExchange));

    // 3. send accept sendbird message
    sendbirdWebClient.sendMessage(
      exchange.getChat().getChatUri(),
      "사진교환",
      profile.getChatProfileKey(),
      "EXCHANGE",
      JsonUtil.write(SendbirdExchangeDataDto.builder()
        .exchangeId(exchange.getExchangeId().toString())
        .state(state)
        .build())
    );

    return FleekGeneralResponse.builder()
      .success(true)
      .errorMessage(null)
      .build();

  }

  @Transactional
  public ExchangeWatchDto watchExchange(Long exchangeId, String profileName) {
    Long fleekUserId = fleekUserContext.getUserId();

    Exchange exchange = exchangeService.getExchange(exchangeId);

    List<ProfileChat> profileChats = profileChatService.listProfileChatsByChatUrl(exchange.getChat().getChatUri());

    Profile counterPart = profileChats.stream()
      .filter(profileChat -> !profileChat.getProfile().getProfileName().equals(profileName))
      .findFirst()
      .orElseThrow(new FleekException("Invalid profile requester"))
      .getProfile();

    Profile me = profileChats.stream()
      .filter(profileChat -> profileChat.getProfile().getProfileName().equals(profileName))
      .findFirst()
      .orElseThrow(new FleekException("Invalid profile requester"))
      .getProfile();

    ProfileExchange profileExchange = profileExchangeService.getProfileExchange(exchange, me);
    if (profileExchange.getWatched()) {
      return ExchangeWatchDto.builder()
        .success(false)
        .failureCode(ExchangeFailureCode.ALREADY_WATCHED.getName())
        .failureReason(ExchangeFailureCode.ALREADY_WATCHED.getDescription())
        .build();
    }
//    profileExchange.setWatched(true);
//    profileExchangeService.addProfileExchanges(List.of(profileExchange));

    UserWallet userWallet = userWalletService.getWallet(fleekUserId);
    if (userWallet.getAmount() < EXCHANGE_PRICE) {
      return ExchangeWatchDto.builder()
        .success(false)
        .failureCode(ExchangeFailureCode.NOT_ENOUGH_BALANCE.getName())
        .failureReason(ExchangeFailureCode.NOT_ENOUGH_BALANCE.getDescription())
        .build();
    }

    List<ProfileImage> counterPartFaceImages = profileImageService.listProfileImageByProfileId(counterPart.getProfileId());

    return ExchangeWatchDto.builder()
      .success(true)
      .faceUrls(counterPartFaceImages.stream()
        .filter(pi -> pi.getImageType().equals(ImageType.FACE_IMAGE))
        .map(ProfileImage::getImageUrl)
        .collect(Collectors.toList()))
      .build();
  }

  @Transactional
  public void readExchange(Long exchangeId, String profileName, Long readMessageId) {
    Long fleekUserId = fleekUserContext.getUserId();
    UserWallet userWallet = userWalletService.getWallet(fleekUserId);

    ProfileExchange profileExchange = profileExchangeService.getProfileExchangeByExchangeIdAndProfileName(exchangeId, profileName);
    profileExchange.setWatched(true);
    profileExchangeService.addProfileExchanges(List.of(profileExchange));

    userWallet.setAmount(userWallet.getAmount() - EXCHANGE_PRICE);
    userWalletService.putWallet(userWallet);

    Exchange exchange = profileExchange.getExchange();

    // update exchange message
    SendbirdSendMessageDto originalMessage =
      sendbirdWebClient.getMessage(exchange.getChat().getChatUri(), readMessageId.toString());
    SendbirdExchangeDataDto data = JsonUtil.read(originalMessage.getData(), SendbirdExchangeDataDto.class);

    List<String> readProfileNames = data.getReadProfileNames();
    readProfileNames.add(profileName);
    data.setReadProfileNames(readProfileNames);

    sendbirdWebClient.updateMessage(readMessageId.toString(), exchange.getChat().getChatUri(), "사진교환", "EXCHANGE",
      JsonUtil.write(data)
    );
  }
}
